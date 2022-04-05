<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUp = className?upper_case> 
@Override
public int update(${className} ${classNameLower})
{
	final String SQL = "UPDATE ${table.sqlName} SET <#list table.columns as column>${column.sqlName} = :${column.columnNameLower}<#if column_has_next>, </#if></#list> WHERE ID = ?";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	return getNamedParameterJdbcTemplate().update(SQL,
			new BeanPropertySqlParameterSource(${classNameLower}));
}

@Override
public int update(List<${className}> ${classNameLower}s)
{
	final String SQL = "UPDATE ${table.sqlName} SET <#list table.columns as column>${column.sqlName} = :${column.columnNameLower}<#if column_has_next>, </#if></#list> WHERE ID = ?";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	return getNamedParameterJdbcTemplate().batchUpdate(SQL, SqlParameterSourceUtils.createBatch(${classNameLower}s.toArray()));
}


<#list table.columns as column>
@Override
public int update${column.columnName}ById(String id, ${column.javaType} ${column.columnNameLower})
{
	final String SQL = "UPDATE ${table.sqlName} SET ${column.sqlName} = ? WHERE ID = ?";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	return getJdbcTemplate().update(SQL, ${column.columnNameLower}, id);
}
</#list>