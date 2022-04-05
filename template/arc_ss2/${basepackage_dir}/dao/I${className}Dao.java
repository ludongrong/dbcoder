<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.dao;

import ${basepackage}.model.${className};

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
	/**
	 * create
	 * 
	 * @param ${classNameLower}
	 * @return
	 */
	int create(${className} ${classNameLower});
	
	/**
	 * deleteById
	 * 
	 * @param id
	 * @return
	 */
	int deleteById(String id);

	/**
	 * update
	 * 
	 * @param ${classNameLower}
	 * @return
	 */
	int update(${className} ${classNameLower});
	
	/**
	 * update
	 * 
	 * @param id
	 * @param input
	 * @return
	 */
	int update(String id, Map<String, Object> input);
	
	/**
	 * countByDynamicCondition
	 * 
	 * @param dyc
	 * @param input
	 * @return
	 */
	long count(DynamicCondition dyc, Map<String, Object> input);

	/**
	 * findById
	 * 
	 * @param id
	 * @return
	 */
	${className} findById(String id);
	
	/**
	 * find
	 * 
	 * @param dyc
	 * @param input
	 * @return
	 */
	List<${className}> find(DynamicCondition dyc, Map<String, Object> input);

	/**
	 * find
	 * 
	 * @param ${classNameLower}Dyna
	 * @return
	 */
	List<${className}> find(${className}Dyna ${classNameLower}Dyna);

	/**
	 * findByPage
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param ${classNameLower}Dyna
	 * @return
	 */
	List<${className}> find(int pageNumber, int pageSize, ${className}Dyna ${classNameLower}Dyna);
}
