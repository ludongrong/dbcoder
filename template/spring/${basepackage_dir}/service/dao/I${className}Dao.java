<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service.dao;

import ${basepackage}.entity.${className};
import java.util.List;

public interface I${className}Dao
{
	int save(<#list table.columns as column>${column.javaType} ${column.columnNameLower}<#if column_has_next>,</#if></#list>);
	
	int[] save(final List<${className}> ${classNameLower}s);
	
	int destroy(String pk);
	
	int update(<#list table.columns as column>${column.javaType} ${column.columnNameLower}<#if column_has_next>,</#if></#list>);
	
	int[] update(final List<${className}> ${classNameLower}s);
	
	${className} detail(String pk);
	
	List<${className}> find(int start, int end);
	
	int count();
}
