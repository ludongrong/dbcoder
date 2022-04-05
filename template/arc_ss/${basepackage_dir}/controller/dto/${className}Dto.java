<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.controller.dto;

import java.util.List;

import ${basepackage}.model.${className}Bo;
import ${basepackage}.model.dyna.${className}Dyna;
import com.nsn.web.controller.dto.BaseDto;

public class ${className}Dto extends BaseDto
{
	private ${className}Bo ${classNameLower};
	
	private ${className}Dyna ${classNameLower}Dyna;

	private List<${className}Bo> ${classNameLower}s;
	
	public ${className}Dto()
	{
		super();
	}
}
