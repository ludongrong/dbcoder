<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUp = className?upper_case>  

@Repository
public class ${className}DaoImpl extends BaseDaoImpl<${className},String> implements ${className}Dao 
{

}
