<#assign proName=projectName>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUp = className?upper_case>  
package ${basepackage}.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import ${basepackage}.model.${className};
import ${basepackage}.dao.I${className}Dao;

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
	/**
	 * logger
	 */
	private Logger logger = LogManager.getLogger(getClass());
	
	public static final RowMapper<${className}> MAPPER = new RowMapper<${className}>() 
	{
		public ${className} mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			${className} ${classNameLower} = new ${className}();
			
		<#list table.columns as column>
			<#assign columnJavaTypeUp = column.javaType?cap_first >
			${classNameLower}.set${column.columnName}(rs.get${columnJavaTypeUp}("${column.sqlName}"));
		</#list>
			
			return ${classNameLower};
		}
	};
	
	@Override
	public int create(${className} ${classNameLower})
	{
		final String SQL = "INSERT INTO ${table.sqlName} "
				+ "(<#list table.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list>) "
				+ "VALUES                                "
				+ "(<#list table.columns as column>:${column.columnNameLower}<#if column_has_next>, </#if></#list>)";
		if(logger.isDebugEnabled())
		{
			logger.debug(SQL);
		}
		return getNamedParameterJdbcTemplate().update(SQL,
				new BeanPropertySqlParameterSource(${classNameLower}));
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
	public int update(${className} ${classNameLower})
	{
		List<String> sqls = new ArrayList<String>();

		sqls.add("UPDATE ${table.sqlName} SET");
		<#list table.columns as column>
		<#if column.columnName != "Id" && column.columnName != "CreateTime">
		if (ObjectUtil.isNotNull(${classNameLower}.get${column.columnName}()))
		{
			if(sqls.size()>1)
			{
				sqls.add(", ${column.sqlName} = :${column.columnNameLower}");
			}else{
				sqls.add("${column.sqlName} = :${column.columnNameLower}");
			}
		}
		</#if>
		</#list>
		sqls.add("WHERE ID = :id");

		// Empty data update
		if (sqls.size() == 2)
		{
			return 0;
		}

		// sql
		String sql = StriUtil.join(sqls, " ");
		if (logger.isDebugEnabled())
		{
			logger.debug(sql);
		}
		return getNamedParameterJdbcTemplate().update(sql,
				new BeanPropertySqlParameterSource(${classNameLower}));
	}
	
	@Override
	public int update(String id, Map<String, Object> input)
	{
		List<String> sqls = new ArrayList<String>();

		sqls.add("UPDATE ${table.sqlName} SET");
		<#list table.columns as column>
		<#if column.columnName != "Id" && column.columnName != "CreateTime">
		if (input.containsKey("${column.columnNameLower}"))
		{
			if(sqls.size()>1)
			{
				sqls.add(", ${column.sqlName} = :${column.columnNameLower}");
			}else{
				sqls.add("${column.sqlName} = :${column.columnNameLower}");
			}
		}
		</#if>
		</#list>
		sqls.add("WHERE ID = :id");

		// Empty data update
		if (sqls.size() == 2)
		{
			return 0;
		}
		
		//put id
		input.put("id", id);

		// sql
		String sql = StriUtil.join(sqls, " ");
		if (logger.isDebugEnabled())
		{
			logger.debug(sql);
		}
		return getNamedParameterJdbcTemplate().update(sql,
				new MapSqlParameterSource(input));
	}
	
	@Override
	public long count(DynamicCondition dyc, Map<String, Object> input)
	{
		String sql = "SELECT COUNT(ID) FROM ${table.sqlName}";
		StringBuilder strBuilder = new StringBuilder(sql);

		List<Object> args = new ArrayList<Object>();
		if (MapUtil.isNotEmpty(input))
		{
			boolean genresult = dyc.preWhere().generateSql(strBuilder, input,
					args);
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
			return this.getJdbcTemplate().queryForObject(sql, Long.class,
					args.toArray());
		} catch (DataAccessException e)
		{
			return 0L;
		}
	}

	@Override
	public ${className} findById(String id)
	{
		final String SQL = "SELECT <#list table.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list> FROM ${table.sqlName} WHERE ID = ?";
		if(logger.isDebugEnabled())
		{
			logger.debug(SQL);
		}
		return this.getJdbcTemplate().queryForObject(SQL, MAPPER, id);
	}
	
	@Override
	public List<${className}> find(DynamicCondition dyc, Map<String, Object> input)
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
	public List<${className}> find(${className}Dyna ${classNameLower}Dyna)
	{
		List<Object> args = new ArrayList<Object>();
		List<String> sqls = new ArrayList<String>();

		final String SQL = "SELECT <#list table.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list> FROM ${table.sqlName} where 1=1";
		sqls.add(SQL.toString());
		<#list table.columns as column>
		<#if column.columnName != "CreateTime"&&column.columnName != "ModifyTime">
		if (CoreUtil.isNotEmpty(${classNameLower}Dyna.get${column.columnName}Eq()))
		{
			args.add(${classNameLower}Dyna.get${column.columnName}Eq());
			sqls.add("and ${column.sqlName} = ?");
		}
		<#else>
		if (CollectionUtil.isNotEmpty(${classNameLower}Dyna.get${column.columnName}Between())
				&& ${classNameLower}Dyna.get${column.columnName}Between().size() == 2)
		{
			args.addAll(${classNameLower}Dyna.get${column.columnName}Between());
			sqls.add("and ${column.sqlName} BETWEEN ? AND ?");
		}
		</#if>
		</#list>

		String sql = StriUtil.join(sqls, " ");
		if (logger.isDebugEnabled())
		{
			logger.debug(sql);
		}

		return this.getJdbcTemplate().query(sql, MAPPER, args.toArray());
	}

	@Override
	public List<${className}> find(int pageNumber, int pageSize, ${className}Dyna ${classNameLower}Dyna)
	{
		List<Object> args = new ArrayList<Object>();
		List<String> sqls = new ArrayList<String>();

		final String SQL = "SELECT <#list table.columns as column>${classNameLower}1.${column.sqlName}<#if column_has_next>, </#if></#list> FROM ${table.sqlName} AS ${classNameLower}1";
		sqls.add(SQL.toString());
		sqls.add("JOIN");
		sqls.add("(SELECT ID FROM ${table.sqlName} WHERE 1=1");
		<#list table.columns as column>
		<#if column.columnName != "CreateTime"&&column.columnName != "ModifyTime">
		if (CoreUtil.isNotEmpty(${classNameLower}Dyna.get${column.columnName}Eq()))
		{
			args.add(${classNameLower}Dyna.get${column.columnName}Eq());
			sqls.add("AND ${column.sqlName} = ?");
		}
		<#else>
		if (CollectionUtil.isNotEmpty(${classNameLower}Dyna.get${column.columnName}Between())
				&& ${classNameLower}Dyna.get${column.columnName}Between().size() == 2)
		{
			args.addAll(${classNameLower}Dyna.get${column.columnName}Between());
			sqls.add("AND ${column.sqlName} BETWEEN ? AND ?");
		}
		</#if>
		</#list>
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

