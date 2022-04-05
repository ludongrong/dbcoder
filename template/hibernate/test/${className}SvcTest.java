<#assign proName = projectName>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ${basepackage}.entity.${className};
import ${basepackage}.service.I${className}Svc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/app.xml" })
public class ${className}SvcTest
{
	@Resource(name = "${proName}.${className}Svc")
	private I${className}Svc ${classNameLower}Svc;

	@Test
	public void save()
	{
		${className} ${classNameLower} = new ${className}();
		
<#list table.columns as column>
	<#if column.javaType == "String">
		${classNameLower}.set${column.columnName}("");
	<#elseif column.javaType == "BigDecimal">
		${classNameLower}.set${column.columnName}(0);
	<#elseif column.javaType == "int">
		${classNameLower}.set${column.columnName}(0);
	<#elseif column.javaType == "long">
		${classNameLower}.set${column.columnName}(0L);
	<#elseif column.javaType == "boolean">
		${classNameLower}.set${column.columnName}(false);
	<#elseif column.javaType == "Timestamp">
		${classNameLower}.set${column.columnName}(timestamp);
	<#elseif column.javaType == "Date">
		${classNameLower}.set${column.columnName}(timestamp);
	<#else>
		${classNameLower}.set${column.columnName}();
	</#if>
</#list>
		
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
		System.out.println(${classNameLower}.getId());
	}
}