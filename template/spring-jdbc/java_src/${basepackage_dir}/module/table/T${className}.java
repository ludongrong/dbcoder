<#include "/copyright_include/class.header"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class T${className} extends O${className} {
	<@generatePKFields/>
	<@generateFKFields/>
	<@generateOtherFields/>
}

<#macro generatePKFields>

		// 该表有 ${table.pkCount} 个主键，<#if table.singleId>是单主键<#else>是合成主键</#if>。
	<#if table.compositeId>
		// 组成主键的联合字段，继承的父类 id 作为附属单主键。
		<#list table.pkColumns as column>
			private ${column.javaType} ${column.columnNameLower};
		</#list>
	<#else>
		<#assign column = table.pkColumn>
		<#if column.columnNameLower != "id">
			private ${column.javaType} ${column.columnNameLower};
		</#if>
	</#if>

</#macro>

<#macro generateFKFields>

	<#list table.fkColumns as column>
	<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
		<#if column.columnNameLower != "createTime" && column.columnNameLower != "modifyTime">
			/* ${column.columnAlias} */
			private ${column.javaType} ${column.columnNameLower}Begin;
			/* ${column.columnAlias} */
			private ${column.javaType} ${column.columnNameLower}End;
		</#if>
	<#else>
			/* ${column.columnAlias} */
			private ${column.javaType} ${column.columnNameLower};
	</#if>
	</#list>

</#macro>

<#macro generateOtherFields>
	
	<#list table.notPFkColumns as column>
		<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
			<#if column.columnNameLower != "createTime" && column.columnNameLower != "modifyTime">
				/* ${column.columnAlias} */
				private ${column.javaType} ${column.columnNameLower}Begin;
				/* ${column.columnAlias} */
				private ${column.javaType} ${column.columnNameLower}End;
			</#if>
		<#else>
			<#if column.columnNameLower != "id">
				/* ${column.columnAlias} */
				private ${column.javaType} ${column.columnNameLower};
			</#if>
		</#if>
	</#list>

</#macro>




