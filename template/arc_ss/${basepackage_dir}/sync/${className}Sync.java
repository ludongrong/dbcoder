<#assign proName = projectName>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.sync;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ${basepackage}.model.${className};
import ${basepackage}.service.I${className}Svc;
import com.nsn.support.spring.jdbc.DataSourceContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:conf/module${projectName}.xml")
public class ${className}Sync
{
	protected Logger logger = LogManager.getLogger(${className}Sync.class);

	@Resource(name = "${projectName}.${classNameLower}Svc")
	private I${className}Svc ${classNameLower}Svc;

	@Test
	public void execute()
	{
		DataSourceContext.setDbType(DataSourceContext.DATA_SOURCE_MAIN);
		List<${className}> ${classNameLower}s = ${classNameLower}Svc.find();
		logger.info("${classNameLower}s size {}", ${classNameLower}s.size());

		DataSourceContext.setDbType(DataSourceContext.DATA_SOURCE_ORACLE);
		${classNameLower}Svc.create(${classNameLower}s, 1000);
		logger.info("${classNameLower}s sync ok");
	}
}
