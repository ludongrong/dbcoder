<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.svc;

import java.util.List;
import java.util.Map;

import ${basepackage}.model.${className}Bo;
import ${basepackage}.model.dyna.${className}Dyna;

import cn.laocu.db.sql.DynamicCondition;

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
	${className}Bo create(${className}Bo ${classNameLower});

	void deleteById(String id);

	${className}Bo deleteByIdOfSafely(String id);

	void update(${className}Bo sys);
	
	void update(String id, Map<String, Object> input);
	
	void update(DynamicCondition dynam, Map<String, Object> input, Map<String, Object> param);
	
	void update(Map<String, Object> input, Map<String, Object> param);
	
	long count(Map<String, Object> input);
	
	long count(${className}Dyna ${classNameLower}Dyna);

	${className}Bo findById(String id);

	${className}Bo findById(String id, ${className}Bo defaultVal);
	
	List<${className}Bo> find(Map<String, Object> input);

	List<${className}Bo> find(${className}Dyna ${classNameLower}Dyna);

	List<${className}Bo> find(int pageNum, int pageSize, ${className}Dyna ${classNameLower}Dyna);
}
