<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.test.service;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:conf/applicationContext.xml" })
public class ${className}SvcTest
{
	@Resource(name = "${classNameLower}Svc")
	private ${className}Svc ${classNameLower}Svc;

	@Test
	public void save()
	{
		${className} ${classNameLower} = new ${className}();
		
		<#list table.columns as column>
		${classNameLower}.set${column.columnName}("");
		</#list>
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		${classNameLower}.setCreateTime(timestamp);
		${classNameLower}.setModifyTime(timestamp);
		
		${classNameLower}Svc.save(${classNameLower});
	}

}