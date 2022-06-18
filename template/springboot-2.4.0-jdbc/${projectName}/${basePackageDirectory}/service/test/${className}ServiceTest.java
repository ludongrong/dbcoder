package ${basepackage}.service.test;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import ${basepackage}.Application;
import ${basepackage}.entity.${className};
import ${basepackage}.service.${className}Service;

@SpringBootTest(classes = { Application.class })
public class ${className}ServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private ${className}Service ${classNameVariable}Service;

	@Test
	public void saveTest() {
		${className} ${classNameVariable} = new ${className}();
		<#list columns as column>
		<#if column.name != "ID" && column.name != "CREATE_TIME" && column.name != "MODIFY_TIME">
		${classNameVariable}.set${column.javaName}(<#if column.javaType == "Integer">0<#else>""</#if>);
		</#if>
		</#list>
		${classNameVariable}Service.create(${classNameVariable});
		MatcherAssert.assertThat(${classNameVariable}.getId(), Matchers.hasLength(32));
	}
	
	@Test
	public void deleteTest() {

		${className} ${classNameVariable} = this.${classNameVariable}Service.findById("");
		this.${classNameVariable}Service.delete(${classNameVariable}.getId());
	}

	@Test
	public void batchDelete() {

		String[] ids = {""};
		this.${classNameVariable}Service.batchDelete(ids);
	}

	@Test
	public void findById() {

		${className} ${classNameVariable} = this.${classNameVariable}Service.findById("");
		MatcherAssert.assertThat(${classNameVariable}, Matchers.nullValue());
	}
	
}
