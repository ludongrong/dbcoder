<#include "/copyright_include/class.header"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao.jdbc;

public class ${className}BaseSqlImpl implements ${className}BaseSql {

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
		
    @Override
    public String create(${className} ${classNameLower}) {
		return createSql;
    }
    
    @Override
	public String destroy(String pk) {
		return destroySql;
	}
	
	@Override
	public String update(${className} ${classNameLower}) {
		return updateSql;
	}
	
	@Override
	public String detail(String pk) {
		return "SELECT "
			    <#list table.columns as column>
			    +" ${column.sqlName}<#if column_has_next>,</#if>"
			    </#list>
				+ " FROM ${table.sqlName} WHERE ${table.idColumn.sqlName} = ?";
	}
	
	@Override
	public String find(${className} ${classNameLower}) {
		return "SELECT " 
		<#list table.columns as column>
        +" ${column.sqlName}<#if column_has_next>,</#if>"
        </#list>
		+ " FROM ${table.sqlName}";
	}
	
	@Override
	public String count(${className} ${classNameLower})
	{
		return "SELECT COUNT(ID) FROM ${table.sqlName}";
	}
	
	@Override
	public String creates(List<${className}> ${classNameLower}s) {
		return createSql;
    }
	
	@Override
	public String destroys(String[] pks) {
		return destroySql;
	}
	
	@Override
	public String updates(List<${className}> ${classNameLower}s) {
		return updateSql;
	}
	
}
