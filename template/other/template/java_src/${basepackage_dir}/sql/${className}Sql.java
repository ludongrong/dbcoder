<#include "/copyright_include/class.header"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao;

import java.util.List;

import org.apache.velocity.VelocityContext;

public interface ${className}Sql {

	public String create(${className} model);
	
	public String creates(List<${className}> models);
	
	public String destroy(String pk);
	
	public String destroys(String[] pks);
	
	public String update(${className} model);
	
	public String updates(List<${className}> models);
	
	public String detail(String pk);
	
	public String count();
	
	public String findAll();
	
	public String find(Map<String, Object> params);
	
	public String findPage(Map<String, Object> params);

	/*~~~~~~~~~~~~~~~~~~~~~~~*/
	/*~~~~~~~~~~~~~~~~~~~~~~~*/
}
