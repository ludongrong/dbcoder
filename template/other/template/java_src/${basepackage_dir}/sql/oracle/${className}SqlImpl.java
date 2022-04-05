<#include "/copyright_include/class.header"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao.jdbc;

import java.io.Serializable;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

public class ${className}SqlImpl implements ${className}Sql {

	private final String createSql = "INSERT INTO ${table.sqlName} " 
		    + " ("
			<#list table.columns as column>
		    +"${column.sqlName}<#if column_has_next>,</#if>"
		    </#list>
		    +") " 
		    + " VALUES "
		    + " (<#list table.columns as column>?<#if column_has_next>,</#if></#list>)";
	
	private final String destroySql = "DELETE FROM ${table.sqlName} WHERE ${table.idColumn.sqlName} = ?";
	
	private final String updateSql = "UPDATE ${table.sqlName} SET "
		    <#list table.columns as column>
		    +"${column.sqlName} = ?<#if column_has_next>,</#if>"
		    </#list>
		    + " WHERE ${table.idColumn.sqlName} = ?";
		    
    public String create(${className} model) {
		return createSql;
    }
    
    public String creates(List<${className}> models) {
		return createSql;
    }
	
	public String destroy(String pk) {
		return destroySql;
	}
	
	public String destroys(String[] pks) {
		return destroySql;
	}
	
	public String update(${className} model) {
		return updateSql;
	}
	
	public String updates(List<${className}> models) {
		return updateSql;
	}
	
	public String detail(String pk) {
		return "SELECT "
			    <#list table.columns as column>
			    +" ${column.sqlName}<#if column_has_next>,</#if>"
			    </#list>
				+ " FROM ${table.sqlName} WHERE ${table.idColumn.sqlName} = ?";
	}
	
	public String findAll() {
		return "SELECT "
			    <#list table.columns as column>
			    +" ${column.sqlName}<#if column_has_next>,</#if>"
			    </#list>
				+ " FROM ${table.sqlName}";
	}
	
	public String find(Map<String, Object> params) {
		return "SELECT " 
		<#list table.columns as column>
        +" ${column.sqlName}<#if column_has_next>,</#if>"
        </#list>
		+ " FROM ${table.sqlName}";
	}
	
	public String findPage(Map<String, Object> params) {
		return null;
	}
	
}
