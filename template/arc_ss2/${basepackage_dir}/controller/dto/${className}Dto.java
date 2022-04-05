<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.controller.dto;

import ${basepackage}.model.${className};
import com.nsn.web.controller.dto.BaseDto;

/**
 * ${className}Dto
 * 
 * @version 1.0
 * 
 * @author 卢冬榕
 * @email 736779458@qq.com
 * @date 2018年10月17日 下午4:14:19
 */
public class ${className}Dto extends BaseDto
{
	private ${className} ${classNameLower};
	
	private ${className}Dyna ${classNameLower}Dyna;

	private List<${className}> ${classNameLower}s;
	
	public ${className}Dto()
	{
		super();
	}

	public ${className} get${className}()
	{
		return ${classNameLower};
	}

	public void set${className}(${className} ${classNameLower})
	{
		this.${classNameLower} = ${classNameLower};
	}
}
