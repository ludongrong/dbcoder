<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model;

/**
 * 
 * ${table.className}Bo
 * 
 * @version 1.0
 * 
 * @author 卢冬榕
 * @email 736779458@qq.com
 *
 */
@ApiModel(value = "${table.className}Bo", description = "${table.className}Bo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ${table.className}Bo extends ${table.className}Do 
{	
	public ${className}Bo() 
	{
		super();
	}
}