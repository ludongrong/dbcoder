<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service;

import ${basepackage}.entity.${className};
import java.util.List;

public interface I${className}Svc
{
	int save(${className} ${classNameLower});
	
	int[] save(List<${className}> ${classNameLower}s);

	int destroy(String pk);

	int update(${className} ${classNameLower});
	
	int[] update(List<${className}> ${classNameLower}s);

	${className} detail(String pk);

	List<${className}> find(int start,int end);

	int count();
}
