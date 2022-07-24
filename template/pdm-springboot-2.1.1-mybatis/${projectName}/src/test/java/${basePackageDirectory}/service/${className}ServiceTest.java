package ${basePackage}.${projectName}.service;

import java.util.List;
import java.util.Map;

import ${basePackage}.${projectName}.entity.${className}Entity;
import com.github.pagehelper.PageInfo;
import org.springframework.scheduling.annotation.Async;

/**
 * ${Name} 业务测试层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@SpringBootTest(classes = { Application.class })
public interface ${className}ServiceTest {
    
    @Autowired
	private ${className}Service ${classNameVariable}Service;
    
    /**
	 * ${Name} 插入测试
	 */
    @Test
    public void saveTest() {
        ${className} ${classNameVariable} = new ${className}();
<#list Columns as column>
    <#if column.JavaType == "java.sql.Date" || column.JavaType == "java.sql.Timestamp">
        ${classNameVariable}.set${column.CodeCamelFirstUpper}(new Date());
    <#elseif column.JavaType == "java.lang.String">
        ${classNameVariable}.set${column.CodeCamelFirstUpper}("");
    <#else>
        ${classNameVariable}.set${column.CodeCamelFirstUpper}(null);
    </#if>
		${classNameVariable}Service.save(${classNameVariable});
		//MatcherAssert.assertThat(${classNameVariable}.getId(), Matchers.hasLength(32));
    }
</#list>
    
    /**
	 * ${Name} 删除测试
	 */
    @Test
    public void remove() {
        // ${classNameVariable}Service.remove(${classNameVariable});
    }
    
    /**
	 * ${Name} 修改测试
	 */
    @Test
    public void update() {
        // ${classNameVariable}Service.update(${classNameVariable});
    }
    
    <#if HasPrimaryKey == '1'>
    /**
	 * ${Name} 查询测试 - 主键
	 */
    @Test
    public void getByPrimary() {
        
    }
    </#if>
	
	/**
	 * ${Name} 查询测试 - 分页
	 */
	@Test
    public void paging() {
        
    }
    
	/**
     * ${Name} 查询
     */
    @Test
    public void list() {
        
    }

}
