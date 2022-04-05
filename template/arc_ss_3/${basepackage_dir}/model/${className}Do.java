<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model;

import java.io.Serializable;

/**
 * 
 * ${table.className}Do
 * 
 * @version 1.0
 * 
 * @author 卢冬榕
 * @email 736779458@qq.com
 *
 */
@ApiModel(value = "${table.className}Do", description = "${table.className}Do")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ${table.className}Do implements Serializable 
{
<#list table.notPFkColumns as column>
	@ApiModelProperty(value = "${column.columnAlias}")
	<#if column.columnName != "Id">
	@DOFeild(name = "${column.sqlName}")
	<#else>
	@DOFeild(name = "${column.sqlName}", ispk = true)
	</#if>
	private ${column.javaType} ${column.columnNameLower};
	
	private Boolean ${column.columnNameLower}IsNull;
	
</#list>
	public ${table.className}Do()
	{
		super();
	}
}