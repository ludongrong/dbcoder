<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.laocu.spring.jdbc.anno.DOFeild;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
	
	public static String ${column.columnNameLower}FieldName = "${column.columnNameLower}";
	
</#list>
	public ${table.className}Do()
	{
		super();
	}
}