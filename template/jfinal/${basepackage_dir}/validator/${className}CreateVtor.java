<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.validator;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.oss.base.validator.BaseValidator;

public class ${className}CreateVtor extends BaseValidator
{
	static private Log log = Log.getLog(${className}CreateVtor.class);

	@Override
	protected void validate(Controller controller)
	{
	<#list table.notPFkColumns as column>
		validateRequiredString("${column.columnNameLower}", "${column.columnNameLower}Error", "${column.columnNameLower} is null");
	</#list>
	}
}
