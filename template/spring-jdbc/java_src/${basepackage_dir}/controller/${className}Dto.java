<#include "/copyright_include/class.header"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ${className}Dto implements Serializable
{
	private ${className} ${classNameLower};
	
	private List<${className}> ${classNameLower}s;
}