<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "${table.className}Bo", description = "${table.className}Bo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ${table.className}Bo extends ${table.className}Do 
{	
	public ${className}Bo() 
	{
		super();
	}
}