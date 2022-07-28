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

import ${basepackage}.model.I${className}Dao;
import ${basepackage}.model.${className}Bo;
import ${basepackage}.dao.I${className}Dao;

import cn.hutool.core.map.MapUtil;
import cn.laocu.core.exception.CodeException;
import cn.laocu.core.str.StriUtil;
import cn.laocu.db.sql.DynamicCondition;
import cn.laocu.spring.jdbc.JdbcReflect;

/**
 * 
 * ${className} DAO 数据实现层
 * 
 * @author 卢冬榕
 * @email 736779458@qq.com
 *
 */
public class ${className}DaoImpl extends NamedParameterJdbcDaoSupport implements I${className}Dao 
{
	private static final Logger logger = LoggerFactory.getLogger(${className}DaoImpl.class);
	
	public static final RowMapper<${className}Bo> MAPPER = new RowMapper<${className}Bo>() 
	{
		public ${className}Bo mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			${className}Bo ${classNameLower} = new ${className}Bo();
			
		<#list table.columns as column>
			<#assign columnJavaTypeUp = column.javaType?cap_first >
			${classNameLower}.set${column.columnName}(rs.get${columnJavaTypeUp}("${column.sqlName}"));
		</#list>
			
			return ${classNameLower};
		}
	};
	
	@Override
	public int create(${className}Bo ${classNameLower})
	{
		final String SQL = "INSERT INTO ${table.sqlName} "
				+ "(<#list table.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list>) "
				+ "VALUES                                "
				+ "(<#list table.columns as column>:${column.columnNameLower}<#if column_has_next>, </#if></#list>)";
		if(logger.isDebugEnabled()) {
			logger.debug(SQL);
		}
		return getNamedParameterJdbcTemplate().update(SQL, new BeanPropertySqlParameterSource(${classNameLower}));
	}
	
	@Override
	public int deleteById(String id)
	{
		final String SQL = "DELETE FROM ${table.sqlName} WHERE ID = ?";
		if (logger.isDebugEnabled())
		{
			logger.debug(SQL);
		}
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
		if (logger.isDebugEnabled())
		{
			logger.debug(sql);
		}

		return this.getJdbcTemplate().update(sql, args.toArray());
	}

	@Override
	public int update(${className}Bo ${classNameLower})
	{
		List<String> sqls = new ArrayList<String>();

		sqls.add("UPDATE ${table.sqlName} SET");
		JdbcReflect.updateReflect(${className}Do.class, ${classNameLower}, sqls);
		sqls.add("WHERE ID = :id");

		// Empty update
		if (sqls.size() == 2)
		{
			return -2;
		}

		// sql
		String sql = StriUtil.join(sqls, " ");
		if (logger.isDebugEnabled())
		{
			logger.debug(sql);
		}
		return getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(${classNameLower}));
	}
	
	@Override
	public int update(String id, Map<String, Object> input)
	{
		List<String> sqls = new ArrayList<String>();

		sqls.add("UPDATE ${table.sqlName} SET");
		JdbcReflect.updateMapReflect(${className}Do.class, input, sqls);
		sqls.add("WHERE ID = :id");

		// Empty update
		if (sqls.size() == 2)
		{
			return -2;
		}
		
		//put id
		input.put("id", id);

		// sql
		String sql = StriUtil.join(sqls, " ");
		if (logger.isDebugEnabled())
		{
			logger.debug(sql);
		}
		return getNamedParameterJdbcTemplate().update(sql, new MapSqlParameterSource(input));
	}
	
	@Override
	public int update(DynamicCondition dyc, Map<String, Object> input, Map<String, Object> param)
	{
		List<Object> args = new ArrayList<Object>();
		List<String> sqls = new ArrayList<String>();

		sqls.add("UPDATE ${table.sqlName} SET");
		JdbcReflect.updateMapReflect(${className}Do.class, input, args, sqls);

		// Empty update
		if (sqls.size() == 1)
		{
			return -2;
		}

		// sql
		String sql = StriUtil.join(sqls, " ");
		if (logger.isDebugEnabled())
		{
			logger.debug(sql);
		}
		
		/*拼凑条件语句*/
		StringBuilder strBuilder = new StringBuilder(sql);
		if (MapUtil.isNotEmpty(param))
		{
			boolean genresult = dyc.preWhere().generateSql(strBuilder, input, args);
			if (!genresult)
			{
				throw new CodeException("-1", "动态条件生成失败");
			}

			sql = strBuilder.toString();
		}

		if (logger.isDebugEnabled())
		{
			logger.debug(sql);
		}
		
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

		if (logger.isDebugEnabled())
		{
			logger.debug(sql);
		}

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
		JdbcReflect.findReflect(${className}.class, ${classNameLower}Dyna, args, sqls);

		String sql = StriUtil.join(sqls, " ");
		if (logger.isDebugEnabled())
		{
			logger.debug(sql);
		}

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
		final String SQL = "SELECT <#list table.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list> FROM ${table.sqlName} WHERE ID = ?";
		if(logger.isDebugEnabled())
		{
			logger.debug(SQL);
		}
		return this.getJdbcTemplate().queryForObject(SQL, MAPPER, id);
	}
	
	@Override
	public List<${className}Bo> find(DynamicCondition dyc, Map<String, Object> input)
	{
		final String SQL = "SELECT <#list table.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list> FROM ${table.sqlName}";
		StringBuilder strBuilder = new StringBuilder(SQL);

		List<Object> args = new ArrayList<Object>();
		boolean genresult = dyc.preWhere().generateSql(strBuilder, input, args);
		if (!genresult)
		{
			throw new CodeException("999", "Sql generate fail");
		}

		String sql = strBuilder.toString();
		if (logger.isDebugEnabled())
		{
			logger.debug(sql);
		}

		return this.getJdbcTemplate().query(sql, MAPPER, args.toArray());
	}

	@Override
	public List<${className}Bo> find(${className}Dyna ${classNameLower}Dyna)
	{
		List<Object> args = new ArrayList<Object>();
		List<String> sqls = new ArrayList<String>();

		final String SQL = "SELECT <#list table.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list> FROM ${table.sqlName} where 1=1";
		sqls.add(SQL.toString());
		JdbcReflect.findReflect(${className}.class, ${classNameLower}Dyna, args, sqls);

		String sql = StriUtil.join(sqls, " ");
		if (logger.isDebugEnabled())
		{
			logger.debug(sql);
		}

		return this.getJdbcTemplate().query(sql, MAPPER, args.toArray());
	}

	@Override
	public List<${className}Bo> find(int pageNumber, int pageSize, ${className}Dyna ${classNameLower}Dyna)
	{
		List<Object> args = new ArrayList<Object>();
		List<String> sqls = new ArrayList<String>();

		final String SQL = "SELECT <#list table.columns as column>${classNameLower}1.${column.sqlName}<#if column_has_next>, </#if></#list> FROM ${table.sqlName} AS ${classNameLower}1";
		sqls.add(SQL.toString());
		sqls.add("JOIN");
		sqls.add("(SELECT ID FROM ${table.sqlName} WHERE 1=1");
		JdbcReflect.findReflect(${className}.class, ${classNameLower}Dyna, args, sqls);
		sqls.add("ORDER BY CREATE_TIME DESC");
		args.add(pageNumber);
		args.add(pageSize);
		sqls.add("LIMIT ?, ?) AS ${classNameLower}2");
		sqls.add("WHERE ${classNameLower}1.ID=${classNameLower}2.ID");
		args.add(pageSize);
		sqls.add("LIMIT ?");

		String sql = StriUtil.join(sqls, " ");
		if (logger.isDebugEnabled())
		{
			logger.debug(sql);
		}

		return this.getJdbcTemplate().query(sql, MAPPER, args.toArray());
	}
}

