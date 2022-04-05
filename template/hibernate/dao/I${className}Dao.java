<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.dao;

import ${basepackage}.entity.${className};

public interface I${className}Dao extends BaseDao<${className}, String>
{
	${className} findById(String id);
}
