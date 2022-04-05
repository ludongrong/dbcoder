<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model.dyna;

import java.io.Serializable;

/**
 * 
 * ${className}Dyna
 * 
 * @version 1.0
 * 
 * @author 卢冬榕
 * @email 736779458@qq.com
 *
 */
@ApiModel(value = "${className}Dyna", description = "${className}Dyna")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ${className}Dyna implements Serializable
{
<#list table.notPFkColumns as column>
<#if column.columnName != "CreateTime"&&column.columnName != "ModifyTime">
	@ApiModelProperty(value = "${column.columnAlias}")
	private ${column.javaType} ${column.columnNameLower}Eq;
<#else>
	@ApiModelProperty(value = "${column.columnAlias}")
	private List<${column.javaType}> ${column.columnNameLower}Between;
</#if>

</#list>
	
	public ${className}Dyna()
	{
		super();
	}
}