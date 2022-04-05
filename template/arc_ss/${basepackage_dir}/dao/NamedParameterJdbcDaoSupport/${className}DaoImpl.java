<#assign proName=projectName>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUp = className?upper_case>  
package ${basepackage}.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import ${basepackage}.model.${className}Do;
import ${basepackage}.model.${className}Bo;
import ${basepackage}.dao.I${className}Dao;

import cn.hutool.core.map.MapUtil;
import cn.laocu.core.exception.CodeException;
import cn.laocu.core.str.StriUtil;
import cn.laocu.db.sql.DynamicCondition;
import cn.laocu.spring.jdbc.JdbcReflect;

@Service("${proName}${className}Dao")
public class ${className}DaoImpl extends NamedParameterJdbcDaoSupport implements I${className}Dao 
{
	private Logger logger = LoggerFactory.getLogger(${className}DaoImpl.class);
	
	public static final RowMapper<${className}Bo> MAPPER = new RowMapper<${className}Bo>() 
	{
		public ${className}Bo mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			${className}Bo selfobj = new ${className}Bo();
			
		<#list table.columns as column>
			<#assign columnJavaTypeUp = column.javaType?cap_first >
			selfobj.set${column.columnName}(rs.get${columnJavaTypeUp}("${column.sqlName}"));
			if (rs.wasNull()) {
				selfobj.set${column.columnName}(null);
			}
		</#list>
			
			return selfobj;
		}
	};
	
	@Autowired
	${className}DaoImpl(DataSource dataSource)
	{
		setDataSource(dataSource);
	}
	
	@Override
	public int create(${className}Bo ${classNameLower})
	{
		StringBuilder strBuilder = new StringBuilder("INSERT INTO ${table.sqlName} (");
		strBuilder.append(JdbcReflect.selectReflect(${className}Do.class));
		strBuilder.append(")VALUES(");
		strBuilder.append(JdbcReflect.nameParamReflect(${className}Do.class));
		strBuilder.append(")");
		
		String sql = strBuilder.toString();
		logger.debug(sql);
		
		return getNamedParameterJdbcTemplate().update(sql, 
			new BeanPropertySqlParameterSource(${classNameLower}));
	}
	
	@Override
	public int delete(String id)
	{
		final String SQL = "DELETE FROM ${table.sqlName} WHERE ID = ?";
		logger.debug(SQL);
		
		return this.getJdbcTemplate().update(SQL, id);
	}
	
	@Override
	public int delete(DynamicCondition dyc, Map<String, Object> input, boolean safely)
	{
		final String SQL = "DELETE FROM ${table.sqlName}";
		StringBuilder strBuf = new StringBuilder(SQL);

		List<Object> args = new ArrayList<Object>();
		if (MapUtil.isNotEmpty(input))
		{
			boolean genresult = dyc.preWhere().generateSql(strBuf, input, args);
			if (!genresult && !safely)
			{
				throw new CodeException("-1", "Dynamic condition generation failed");
			}
		} else if (safely)
		{
			throw new CodeException("-1", "Input must not empty");
		}

		String sql = strBuf.toString();
		logger.debug(sql);
		
		return this.getJdbcTemplate().update(sql, args.toArray());
	}

	@Override
	public int update(${className}Bo ${classNameLower}, int secondary)
	{
		List<String> sqls = new ArrayList<String>();

		sqls.add("UPDATE ${table.sqlName} SET");
		JdbcReflect.updateReflect(${className}Do.class, ${classNameLower}, sqls);
		sqls.add("WHERE ID = :id");

		int basesize = 2;
		if (secondary > 0) {
			basesize += secondary;
		}
		if (sqls.size() <= basesize) {
			return -2;
		}

		String sql = StriUtil.join(sqls, " ");
		logger.debug(sql);
		
		return getNamedParameterJdbcTemplate().update(sql, 
			new BeanPropertySqlParameterSource(${classNameLower}));
	}
	
	@Override
	public int update(${className}Bo ${classNameLower}, DynamicCondition dyc, Map<String, Object> param)
	{
		List<Object> args = new ArrayList<Object>();
		List<String> sqls = new ArrayList<String>();

		sqls.add("UPDATE ${table.sqlName} SET");
		Map<String, Object> argmap = JdbcReflect.updateReflect(${className}Do.class,
				${classNameLower}, 
				sqls, 
				false);

		if (sqls.size() == 1) {
			return -2;
		}
		args.addAll(argmap.values());

		String sql = StriUtil.join(sqls, " ");
		logger.debug(sql);
		
		/*拼凑条件语句*/
		StringBuilder strBuilder = new StringBuilder(sql);
		if (MapUtil.isNotEmpty(param))
		{
			boolean genresult = dyc.preWhere().generateSql(strBuilder, param, args);
			if (!genresult)
			{
				throw new CodeException("-1", "动态条件生成失败");
			}
			sql = strBuilder.toString();
		}

		logger.debug(sql);
		
		return this.getJdbcTemplate().update(sql, args.toArray());
	}
	
	@Override
	public long count(DynamicCondition dyc, Map<String, Object> input)
	{
		String sql = "SELECT COUNT(ID) FROM ${table.sqlName}";
		StringBuilder strBuilder = new StringBuilder(sql);

		List<Object> args = new ArrayList<Object>();
		if (MapUtil.isNotEmpty(input))
		{
			boolean genresult = dyc.preWhere().generateSql(strBuilder, input, args);
			if (!genresult)
			{
				throw new CodeException("-1", "动态条件生成失败");
			}

			sql = strBuilder.toString();
		}

		logger.debug(sql);

		try
		{
			return this.getJdbcTemplate().queryForObject(sql, Long.class, args.toArray());
		} catch (DataAccessException e)
		{
			return 0L;
		}
	}
	
	@Override
	public long count(${className}Dyna ${classNameLower}Dyna)
	{
		List<Object> args = new ArrayList<Object>();
		List<String> sqls = new ArrayList<String>();

		final String SQL = "SELECT COUNT(ID) FROM ${table.sqlName} where 1=1";
		sqls.add(SQL.toString());
		JdbcReflect.findReflect(${className}Do.class, ${classNameLower}Dyna, args, sqls);

		String sql = StriUtil.join(sqls, " ");
		logger.debug(sql);

		try
		{
			return this.getJdbcTemplate().queryForObject(sql, Long.class, args.toArray());
		} catch (DataAccessException e)
		{
			return 0L;
		}
	}

	@Override
	public ${className}Bo findById(String id)
	{
		StringBuilder strBuilder = new StringBuilder("SELECT ");
		strBuilder.append(JdbcReflect.selectReflect(${className}Do.class));
		strBuilder.append(" FROM ${table.sqlName} WHERE ID = ?");
		
		String sql = strBuilder.toString();
		logger.debug(sql);
		
		return this.getJdbcTemplate().queryForObject(sql, MAPPER, id);
	}
	
	@Override
	public List<${className}Bo> find(DynamicCondition dyc, Map<String, Object> input)
	{
		StringBuilder strBuilder = new StringBuilder("SELECT ");
		strBuilder.append(JdbcReflect.selectReflect(${className}Do.class));
		strBuilder.append(" FROM ${table.sqlName}");
		
		List<Object> args = new ArrayList<Object>();
		if (MapUtil.isNotEmpty(input)) {
			boolean genresult = dyc.preWhere().generateSql(strBuilder, input, args);
			if (!genresult) {
				throw new CodeException("999", "Sql generate fail");
			}
		}

		String sql = strBuilder.toString();
		logger.debug(sql);
		
		return this.getJdbcTemplate().query(sql, MAPPER, args.toArray());
	}

	@Override
	public List<${className}Bo> find(${className}Dyna ${classNameLower}Dyna)
	{
		List<Object> args = new ArrayList<Object>();
		List<String> sqls = new ArrayList<String>();

		StringBuilder strBuilder = new StringBuilder("SELECT ");
		strBuilder.append(JdbcReflect.selectReflect(${className}Do.class));
		strBuilder.append(" FROM ${table.sqlName} where 1=1");
		sqls.add(strBuilder.toString());
		JdbcReflect.findReflect(${className}Do.class, ${classNameLower}Dyna, args, sqls);

		String sql = StriUtil.join(sqls, " ");
		logger.debug(sql);
		
		return this.getJdbcTemplate().query(sql, MAPPER, args.toArray());
	}

	@Override
	public List<${className}Bo> find(int pageNumber, int pageSize, ${className}Dyna ${classNameLower}Dyna)
	{
		List<Object> args = new ArrayList<Object>();
		List<String> sqls = new ArrayList<String>();

		StringBuilder strBuilder = new StringBuilder("SELECT ");
		strBuilder.append(JdbcReflect.selectReflect(${className}Do.class, "${classNameLower}1"));
		strBuilder.append(" FROM ${table.sqlName} AS ${classNameLower}1");
		
		sqls.add(strBuilder.toString());
		sqls.add("RIGHT JOIN");
		sqls.add("(SELECT ID FROM ${table.sqlName} WHERE 1=1");
		JdbcReflect.findReflect(${className}Do.class, ${classNameLower}Dyna, args, sqls);
		sqls.add("ORDER BY CREATE_TIME DESC");
		args.add(pageNumber);
		args.add(pageSize);
		sqls.add("LIMIT ?, ?) AS ${classNameLower}2");
		sqls.add("ON ${classNameLower}1.ID=${classNameLower}2.ID");
		args.add(pageSize);
		sqls.add("LIMIT ?");

		String sql = StriUtil.join(sqls, " ");
		logger.debug(sql);
		
		return this.getJdbcTemplate().query(sql, MAPPER, args.toArray());
	}
}

