<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.svc;

import java.util.List;
import java.util.Map;

import ${basepackage}.model.${className}Bo;
import ${basepackage}.model.dyna.${className}Dyna;

import cn.laocu.db.sql.DynamicCondition;

public interface I${className}Svc
{
	${className}Bo create(${className}Bo ${classNameLower});

	void delete(String id);

	void update(${className}Bo ${classNameLower});
	
	int update(${className}Bo ${classNameLower}, DynamicCondition dyc, Map<String, Object> param);
	
	long count(Map<String, Object> param);
	
	long count(${className}Dyna ${classNameLower}Dyna);

	${className}Bo findById(String id);

	${className}Bo findById(String id, ${className}Bo defaultVal);
	
	List<${className}Bo> find(Map<String, Object> param);

	List<${className}Bo> find(${className}Dyna ${classNameLower}Dyna);

	List<${className}Bo> find(int pageNum, int pageSize, ${className}Dyna ${classNameLower}Dyna);
	
	${className}Bo onGet(String ${classNameLower}Id);

	${className}Bo onGet(${className}Bo ${classNameLower});

	${className}Bo buzCreate(${className}Bo ${classNameLower});
}
