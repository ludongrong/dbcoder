<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUp = className?upper_case>  
//##################################
//#delete
//##################################
<#list table.columns as column>
@Override
public void deleteBy${column.columnName}(${column.javaType} ${column.columnNameLower}){
	final String SQL = "DELETE FROM ${table.sqlName} WHERE ${column.sqlName} = ?";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	return this.getJdbcTemplate().update(SQL, ${column.columnNameLower});
}
</#list>

//##################################
//#update
//##################################
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

##################################
find
##################################
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