<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUp = className?upper_case> 
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