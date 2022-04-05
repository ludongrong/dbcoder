<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign classNameUp = className?upper_case>
package com.opencode.tea.test.service;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.opencode.tea.entity.SystemBackstage;

@ContextConfiguration(
{ "classpath:applicationContext.xml" })
public class ${className}SvcTest extends AbstractJUnit4SpringContextTests
{

	@Resource(name = "tea.${classNameLower}Svc")
	private ${className}Svc ${classNameLower}Svc;

	public void set${className}Svc(${className}Svc ${classNameLower}Svc)
	{
		this.${classNameLower}Svc = ${classNameLower}Svc;
	}

	@Test
	@Rollback(true)
	public void saveTest()
	{
		${className} ${classNameLower} = new ${className}();
		
		<#list table.columns as column>
		${classNameLower}.set${column.columnName}("");
		</#list>
		
		String id = ${classNameLower}Svc.save(${classNameLower});

		Assert.assertNotNull(id);
	}
	
	@Test
	@Rollback(true)
	public void deleteTest()
	{
		this.${classNameLower}Svc.delete("");
	}
	
	@Test
	@Rollback(true)
	public void updateTest() {
		${className} ${classNameLower} = this.${classNameLower}Svc.get("");
		Assert.assertNotNull(${classNameLower}.getId());

		this.${classNameLower}Svc.update(${classNameLower});
	}

	@Test
	@Rollback(true)
	public void getTest() {
		${className} ${classNameLower} = this.${classNameLower}Svc.get("");
		Assert.assertNotNull(${classNameLower}.getId());
	}

}
