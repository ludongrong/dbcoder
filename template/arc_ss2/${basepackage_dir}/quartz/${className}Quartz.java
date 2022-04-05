<#assign proName = projectName>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.quartz;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ${basepackage}.service.I${className}Svc;

/**
 * 
 * ${className} quartz 定时器层
 * 
 * @author 卢冬榕
 * @email 736779458@qq.com
 *
 */
public class ${className}Quartz 
{
	private Log logger = LogFactory.getLog(getClass());
	
	@Resource(name="${proName}.${classNameLower}Svc")
	private I${className}Svc ${classNameLower}Svc;

	public void init() {
		logger.info("init");
	}

	public void execute() {
		logger.info("xxxxxxxxxxxxxxxxxxxxxxxxxxx");
		logger.debug(${classNameLower}Svc);
	}
}
