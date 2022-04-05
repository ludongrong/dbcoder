<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
<#list table.notPFkColumns as column>
	Map<String, String> ${column.sqlName} = new HashMap<>();
	${column.sqlName}.put("COLTYPE", "${column.sqlTypeName}");
	${column.sqlName}.put("COLNAME", "${column.sqlName}");
	${column.sqlName}.put("COLVALUE", emosSource.get${column.columnName}());
	dataList.add(${column.sqlName});	
</#list>