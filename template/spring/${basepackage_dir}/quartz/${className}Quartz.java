<#assign proName = projectName>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ${className}Quartz 
{
	private final Log logger = LogFactory.getLog(getClass());
	
	private I${className}Svc ${classNameLower}Svc;

	public void init() {
		logger.info("init");
	}

	public void execute() {
		logger.info("xxxxxxxxxxxxxxxxxxxxxxxxxxx");
	}
	
	public void set${className}Svc(I${className}Svc ${classNameLower}Svc)
	{
		this.${classNameLower}Svc = ${classNameLower}Svc;
	}
}
