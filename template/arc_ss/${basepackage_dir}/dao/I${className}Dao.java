<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.dao;

import ${basepackage}.model.${className}Bo;

public interface I${className}Dao
{
	int create(${className}Bo ${classNameLower});
	
	int delete(String id);
	
	int delete(DynamicCondition dyc, Map<String, Object> input, boolean safely);

	int update(${className}Bo ${classNameLower});
	
	int update(${className}Bo ${classNameLower}, DynamicCondition dyc, Map<String, Object> param);
	
	long count(DynamicCondition dyc, Map<String, Object> input);
	
	long count(${className}Dyna ${classNameLower}Dyna);

	${className}Bo findById(String id);
	
	List<${className}Bo> find(DynamicCondition dyc, Map<String, Object> input);

	List<${className}Bo> find(${className}Dyna ${classNameLower}Dyna);

	List<${className}Bo> find(int pageNumber, int pageSize, ${className}Dyna ${classNameLower}Dyna);
}
