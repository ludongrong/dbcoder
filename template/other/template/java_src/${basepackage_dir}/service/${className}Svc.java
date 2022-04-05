<#include "/copyright_include/class.header"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

public interface ${className}Svc {

	public ${className} create(${className} model);
	
	public List<${className}> creates(List<${className}> models);
	
	public int destroy(String pk);
	
	public int[] destroys(String[] pks);
	
	public int update(${className} model);
	
	public int[] updates(List<${className}> models);
	
	public ${className} detail(String pk);
	
	public boolean isExist(${className} model);
	
	public List<${className}> findAll();
	
	public List<${className}> find(Map<String, Object> params);
	
	public List<${className}> findPage(Map<String, Object> params);

	/*~~~~~~~~~~~~~~~~~~~~~~~*/
	/*~~~~~~~~~~~~~~~~~~~~~~~*/
}
