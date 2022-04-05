<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.dao;

import ${basepackage}.model.${className}Bo;

/**
 * 
 * ${className} DAO 数据层
 * 
 * @author 卢冬榕
 * @email 736779458@qq.com
 *
 */
public interface I${className}Dao
{
	int create(${className}Bo ${classNameLower});
	
	int deleteById(String id);
	
	int delete(DynamicCondition dyc, Map<String, Object> input, boolean safely);

	int update(${className}Bo ${classNameLower});
	
	int update(String id, Map<String, Object> input);
	
	int update(DynamicCondition dyc, Map<String, Object> input, Map<String, Object> param);
	
	long count(DynamicCondition dyc, Map<String, Object> input);
	
	long count(${className}Dyna ${classNameLower}Dyna);

	${className}Bo findById(String id);
	
	List<${className}Bo> find(DynamicCondition dyc, Map<String, Object> input);

	List<${className}Bo> find(${className}Dyna ${classNameLower}Dyna);

	List<${className}Bo> find(int pageNumber, int pageSize, ${className}Dyna ${classNameLower}Dyna);
}
