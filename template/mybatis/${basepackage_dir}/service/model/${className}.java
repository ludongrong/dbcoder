<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service.model;

import java.io.Serializable;

public class ${className} implements Serializable {
	<@generateOtherFields/>
	
	public ${className}(){
		super();
	}
}

<#macro generatePKFields>
	// 本表有${table.pkCount}个主键，<#if table.singleId>是单主键<#else>是合成主键</#if>。
	<#if table.compositeId>
		<#list table.pkColumns as column>
			private ${column.javaType} ${column.columnNameLower};
		</#list>
	<#else>
		<#assign column = table.pkColumn>
		private ${column.javaType} ${column.columnNameLower};
	</#if>
</#macro>

<#macro generateFKFields>
	<#list table.fkColumns as column>
		/* ${column.columnAlias} */
		private ${column.javaType} ${column.columnNameLower};
	</#list>
</#macro>

<#macro generateOtherFields>
	<#list table.notPFkColumns as column>
		/* ${column.columnAlias} */
		private ${column.javaType} ${column.columnNameLower};
	</#list>
</#macro>
