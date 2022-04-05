<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.dto;

import com.oss.base.dto.BaseDto;
import ${basepackage}.controller.${className}Controller;
import ${basepackage}.model.${className};

public class ${className}Dto extends BaseDto
{
	static public ${className} parse(${className}Controller ctr)
	{
		${className} ${classNameLower} = new ${className}();
		
	<#list table.notPFkColumns as column>
		if (ctr.isParaExists("${column.columnNameLower}"))
		{
			${classNameLower}.set${column.columnName}(ctr.getPara("${column.columnNameLower}"));
		}
	</#list>

		return ${classNameLower};
	}
}
