package ${basepackage}.service.test;

import ${basepackage}.Application;
import ${basepackage}.entity.${className};
import ${basepackage}.service.${className}Service;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

@SpringBootTest(classes = {Application.class})
public class ${className}ServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private ${className}Service ${classNameVariable}Service;
	
	@DataProvider(name = "crud")
    public Object[][] crudDataProvider() {

		${className} ${classNameVariable} = new ${className}();
<#list columns as column>
	<#if column.name != "ID" && column.name != "CREATE_TIME" && column.name != "MODIFY_TIME">
		${classNameVariable}.set${column.javaName}(<#if column.javaType == "Integer">0<#else>""</#if>);
	</#if>
</#list>

        return new Object[][]{{${classNameVariable}}};
    }
	
	@Test(dataProvider = "crud")
    public void mainTest(${className} ${classNameVariable}) {

		${classNameVariable}Service.create(${classNameVariable});
        MatcherAssert.assertThat(${classNameVariable}.getId(), Matchers.hasLength(32));

        ${classNameVariable} = this.${classNameVariable}Service.findById(${classNameVariable}.getId());
        MatcherAssert.assertThat(${classNameVariable}.getId(), Matchers.hasLength(32));

        ${className} ${classNameVariable}FindPageParam = new ${className}();
        ${classNameVariable}FindPageParam.setId(${classNameVariable}.getId());
        Page<${className}> ${classNameVariable}Page = this.${classNameVariable}Service.findPage(${classNameVariable}, 0, 10);
        MatcherAssert.assertThat(${classNameVariable}Page.getContent(), Matchers.hasSize(1));

        ${classNameVariable} = this.${classNameVariable}Service.findById(${classNameVariable}.getId());
        this.${classNameVariable}Service.delete(${classNameVariable}.getId());

        ${className} final${className} = ${classNameVariable};
        Assert.assertThrows(NoSuchElementException.class, () -> {
        	${className}ServiceTest.this.${classNameVariable}Service.findById(final${className}.getId());
        });
    }

    @Test(dataProvider = "crud")
    public void batchDelete(${className} ${classNameVariable}) {

    	${classNameVariable}Service.create(${classNameVariable});
        MatcherAssert.assertThat(${classNameVariable}.getId(), Matchers.hasLength(32));

        ${classNameVariable} = this.${classNameVariable}Service.findById(${classNameVariable}.getId());
        MatcherAssert.assertThat(${classNameVariable}.getId(), Matchers.hasLength(32));

        String[] ids = {${classNameVariable}.getId()};
        this.${classNameVariable}Service.batchDelete(ids);

        ${className} final${className} = ${classNameVariable};
        Assert.assertThrows(NoSuchElementException.class, () -> {
        	${className}ServiceTest.this.${classNameVariable}Service.findById(final${className}.getId());
        });
    }
	
}
