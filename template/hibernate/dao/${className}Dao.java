<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>

public interface ${className}Dao extends BaseDao<${className},String>
{
}
