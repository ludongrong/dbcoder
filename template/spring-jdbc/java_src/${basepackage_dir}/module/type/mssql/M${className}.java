<#include "/copyright_include/class.header"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class M${className} extends MssqlType {
}