<#include "/copyright_include/class.header"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao;

import java.util.List;

public interface ${className}BaseSql {

	String create(${className} ${classNameLower});
	
	String destroy(String pk);
	
	String update(${className} ${classNameLower});
	
	String detail(String pk);
	
	String count(${className} ${classNameLower});
	
	String find(${className} ${classNameLower});
	
	String creates(List<${className}> ${classNameLower}s);
	
	String destroys(String[] pks);
	
	String updates(List<${className}> ${classNameLower}s);
	
}
