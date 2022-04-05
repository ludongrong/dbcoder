<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUp = className?upper_case> 
@Override
public List<${className}> find()
{
	final String SQL = "SELECT <#list table.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list> FROM ${table.sqlName}";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	
	return this.getJdbcTemplate().query(SQL, MAPPER, MAPPER);
}


<#list table.columns as column>
@Override
public List<${className}> findBy${column.columnName}(${column.javaType} ${column.columnNameLower})
{
	final String SQL = "SELECT <#list table.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list> FROM ${table.sqlName} WHERE ${column.sqlName} = ?";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	return this.getJdbcTemplate().query(SQL, MAPPER, ${column.columnNameLower});
}
</#list>