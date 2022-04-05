<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service;

import ${basepackage}.entity.${className};

public interface I${className}Svc extends BaseSvc<${className}, String>
{
	${className} findById(String id);
}
