<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUp = className?upper_case>  

<#include "/copyright_include/dao.header"/>
package ${basepackage}.dao.jdbc;

public class ${className}DaoImpl extends ${className}BaseDaoImpl implements ${className}Dao 
{
}
