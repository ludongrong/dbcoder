<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.svc;

import ${basepackage}.model.${className};

/**
 * 
 * ${className} 业务逻辑层
 * 
 * @author 卢冬榕
 * @email 736779458@qq.com
 *
 */
public interface I${className}Svc
{
	/**
	 * create
	 * 
	 * @param sys
	 * @return
	 */
	${className} create(${className} sys);

	/**
	 * deleteById
	 * 
	 * @param id
	 * @return
	 */
	void deleteById(String id);

	/**
	 * deleteByIdOfSafely
	 * 
	 * @param id
	 * @return
	 */
	${className} deleteByIdOfSafely(String id);

	/**
	 * update
	 * 
	 * @param sys
	 * @return
	 */
	void update(${className} sys);
	
	/**
	 * update
	 * 
	 * @param id
	 * @param input
	 * @return
	 */
	void update(String id, Map<String, Object> input);
	
	/**
	 * count
	 * 
	 * @param input
	 * @return
	 */
	long count(Map<String, Object> input);

	/**
	 * findById
	 * 
	 * @param id
	 * @return
	 */
	${className} findById(String id);

	/**
	 * findById
	 * 
	 * @param id
	 * @param defaultVal
	 * @return
	 */
	${className} findById(String id, ${className} defaultVal);
	
	/**
	 * find
	 * 
	 * @param input
	 * @return
	 */
	List<${className}> find(Map<String, Object> input);

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
	 * @param pageNum
	 * @param pageSize
	 * @param ${classNameLower}Dyna
	 * @return
	 */
	List<${className}> find(int pageNum, int pageSize, ${className}Dyna ${classNameLower}Dyna);
}
