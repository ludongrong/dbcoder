<#include "/copyright_include/class.header"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ${className}SvcImpl extends ${className}BaseSvcImpl implements ${className}Svc {
    
}
