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
	 * 日志
	 */
	private Logger logger = LogManager.getLogger(getClass());
	
	/**
	 * 所有字段的行映射
	 */
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
}

