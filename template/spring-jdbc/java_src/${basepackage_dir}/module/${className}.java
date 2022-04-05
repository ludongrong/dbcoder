<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 

<#include "/copyright_include/class.header"/>
package ${basepackage}.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ${className} extends T${className}
{
}