<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
<#assign parentId = false>

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.opencode.tea.entity.BaseEntity;

<@generateAot/>

@Entity
@Table(name = "${className}")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ${className} extends BaseEntity {
	<@generateFKFields/>
	<@generateOtherFields/>
	<@generateOtherGetSetMethod/>
}

<#macro generateAot>
	<#if table.compositeId>
		/** *该表有 2 个主键，是合成主键。[
		<#list table.pkColumns as column>
			${column.columnNameLower}|
		</#list>
		]*/
	</#if>
</#macro>

<#macro generatePKFields>
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
		<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
			<#if column.columnNameLower != "createTime" && column.columnNameLower != "modifyTime">
				private ${column.javaType} ${column.columnNameLower};
			</#if>
		<#else>
				private ${column.javaType} ${column.columnNameLower};
		</#if>
	</#list>
</#macro>

<#macro generateOtherFields>
	<#list table.notPFkColumns as column>
		<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
			<#if column.columnNameLower != "createTime" && column.columnNameLower != "modifyTime">
				private ${column.javaType} ${column.columnNameLower};
			</#if>
		<#else>
			<#if column.columnNameLower != "id">
				private ${column.javaType} ${column.columnNameLower};
			</#if>
		</#if>
		
		<#if column.columnNameLower == "parentId">
			<#assign parentId = true>
		</#if>
	</#list>
</#macro>

<#macro generateOtherGetSetMethod>
	<#list table.notPFkColumns as column>
		<#if column.columnNameLower != "createTime" && column.columnNameLower != "modifyTime" && column.columnNameLower != "id">
			<#if column.javaType == "java.lang.String">
			@Column(name = "${column.sqlName}", nullable = false, length = 32)
			<#elseif column.javaType == "java.lang.Integer">
			@Column(name = "${column.sqlName}", nullable = false)
			<#elseif column.javaType == "java.util.Date">
			@Column(name = "${column.sqlName}", nullable = false)
			</#if>
			public ${column.javaType} get${column.columnName}(){
				return this.${column.columnNameLower};
			};
			
			public void set${column.columnName}(${column.javaType} ${column.columnNameLower}){
				this.${column.columnNameLower} = ${column.columnNameLower};
			};
		</#if>
	</#list>
	
	<#if parentId == true>
		private ${className} parent;
		
		@Transient
		public ${className} getParent(){
			return this.parent;
		}
		
		public void setParent(${className} parent){
			if (parent == null)
			{
				this.setParentId(null);
			} else
			{
				this.setParentId(parent.getId());
			}
			this.parent = parent;
		}
	</#if>
</#macro>




