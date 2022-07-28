<#include "/copyright_include/class.header"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ${className} extends BaseModel {
	<@generatePKFields/>
	<@generateFKFields/>
	<@generateOtherFields/>
	<@generateFKTables/>
	<@generateProperties/>
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
		<#if column.columnNameLower == "id">
		<#else>
			private ${column.javaType} ${column.columnNameLower};
		</#if>
	</#if>

</#macro>

<#macro generateFKFields>

	<#list table.fkColumns as column>
	<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
		<#if column.columnNameLower != "createDate" && column.columnNameLower != "modifyDate">
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
			<#if column.columnNameLower != "createDate" && column.columnNameLower != "modifyDate">
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

<#macro generateFKTables>


</#macro>

<#macro generateProperties>
	
	<#if table.compositeId>
		<#list table.pkColumns as column>
			public ${column.javaType} get${column.columnName}Begin() {
				return this.${column.columnNameLower}Begin;
			}
			
			public void set${column.columnName}Begin(${column.javaType} value) {
				this.${column.columnNameLower}Begin = value;
			}
		</#list>
	<#else>
		<#assign column = table.pkColumn>
		
		<#if column.columnNameLower != "id">
			public ${column.javaType} get${column.columnName}Begin() {
				return this.${column.columnNameLower}Begin;
			}
			
			public void set${column.columnName}Begin(${column.javaType} value) {
				this.${column.columnNameLower}Begin = value;
			}
		</#if>
	</#if>

	<#list table.notPkColumns as column>
		<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
			<#if column.columnNameLower != "createDate" && column.columnNameLower != "modifyDate">
				public ${column.javaType} get${column.columnName}Begin() {
					return this.${column.columnNameLower}Begin;
				}
				
				public void set${column.columnName}Begin(${column.javaType} value) {
					this.${column.columnNameLower}Begin = value;
				}	
				
				public ${column.javaType} get${column.columnName}End() {
					return this.${column.columnNameLower}End;
				}
				
				public void set${column.columnName}End(${column.javaType} value) {
					this.${column.columnNameLower}End = value;
				}
			</#if>
		<#else>
			<#if column.columnNameLower != "id">
				public ${column.javaType} get${column.columnName}() {
					return this.${column.columnNameLower};
				}
				
				public void set${column.columnName}(${column.javaType} value) {
					this.${column.columnNameLower} = value;
				}
			</#if>
		</#if>
	</#list>
	
</#macro>




