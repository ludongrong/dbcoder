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

	@Test
	public void delete()
	{
		${classNameLower}Svc.delete("f1458887a2c3469f8cf74a37cccc1737");
	}

	@Test
	public void update()
	{
		${className} ${classNameLower} = new ${className}();
		${classNameLower}.setId("f1458887a2c3469f8cf74a37cccc1737");
		${classNameLower}Svc.update(${classNameLower});
	}

	@Test
	public void findById()
	{
		${className} ${classNameLower} = ${classNameLower}Svc.findById("f1458887a2c3469f8cf74a37cccc1737");
	}
}