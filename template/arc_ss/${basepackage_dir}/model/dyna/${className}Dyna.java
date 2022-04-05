<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model.dyna;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ${className}Dyna implements Serializable
{
<#list table.notPFkColumns as column>
<#if column.columnName != "CreateTime"&&column.columnName != "ModifyTime">
	private ${column.javaType} ${column.columnNameLower}Eq;
<#else>
	private List<${column.javaType}> ${column.columnNameLower}Between;
</#if>

</#list>
	public ${className}Dyna()
	{
		super();
	}
}