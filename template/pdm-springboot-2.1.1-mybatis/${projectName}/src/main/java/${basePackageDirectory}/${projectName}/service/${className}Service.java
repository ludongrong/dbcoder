package ${basePackage}.${projectName}.service;

import java.util.List;
import java.util.Map;

import ${basePackage}.${projectName}.entity.${className}Entity;
import com.github.pagehelper.PageInfo;
import org.springframework.scheduling.annotation.Async;

<#--
Service/Dao 层命名规约
1） 获取单个对象的方法用 get 做前缀。
2） 获取多个对象的方法用 list 做前缀。
3） 获取统计值的方法用 count 做前缀。
4） 插入的方法用 save（推荐）或 insert 做前缀。
5） 删除的方法用 remove（推荐）或 delete 做前缀。
6） 修改的方法用 update 做前缀。

扩展：
1） 提审 --- submit
2） 审核 --- audit
3） 退回 --- back --- 退回到流程中的某一步
4） 撤销 --- cancel --- 退回到流程中的某一步
-->

/**
 * ${Name} 业务逻辑层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
public interface ${className}Service {
    
    /**
	 * ${Name} 插入
	 *
	 * @param ${classNameVariable} 入参
	 * @return java.util.List<${basePackage}.${projectName}.entity.${className}>
	 */
    ${className} save(${className} ${classNameVariable});
    
    /**
	 * ${Name} 删除
	 *
	 * @param ${classNameVariable} 入参
	 * @return java.util.List<${basePackage}.${projectName}.entity.${className}>
	 */
    void remove(${className} ${classNameVariable});
    
    /**
	 * ${Name} 修改
	 *
	 * @param ${classNameVariable} 入参
	 * @return java.util.List<${basePackage}.${projectName}.entity.${className}>
	 */
    void update(${className} ${classNameVariable});
    
    <#if HasPrimaryKey == '1'>
    /**
	 * ${Name} 查询 - 主键
	 *
<#list Columns as column>
     <#if column.PrimaryKey == "1">
     * @param ${column.CodeCamelFirstLower} ${column.Name}
     </#if>
</#list>
	 * @return java.util.List<${basePackage}.${projectName}.entity.${className}>
	 */
    ${className}Entity getByPrimary(<#list Columns as column><#if column.PrimaryKey == "1">${column.JavaType} ${column.CodeCamelFirstLower}</#if><#if column_has_next>, </#if></#list>);
    </#if>
	
	/**
	 * ${Name} 查询 - 分页
	 *
	 * @param paramMap 参数集
	 * @return java.util.List<${basePackage}.${projectName}.entity.${className}>
	 */
	PageInfo<${className}Entity> paging(Map<String, Object> paramMap);
    
	/**
     * ${Name} 查询
     * 
     * @param paramMap 查询参数
     * @return java.util.List<${basePackage}.${projectName}.entity.${className}Entity>
     */
    List<${className}Entity> list(Map<String, Object> paramMap);
    
	/**
     * ${Name} 导出
     * 
     * @param paramMap 查询参数
     * @return java.util.List<${basePackage}.${projectName}.entity.${className}Entity>
     */
	@Async
    String listForExport(String fileName, Map<String, Object> paramMap);
    
<#if RefParents?exists>
    <#list RefParents as reference>
    List<${className}> listBy${reference.ParentTable.className}${reference_index}(Map<String, Object> paramMap);
    
    </#list>
    <#list RefParents as reference>
    List<${className}> listRefBy${reference.ParentTable.className}${reference_index}Result(Map<String, Object> paramMap);
    
    </#list>
</#if>
<#if RefChildren?exists>
    <#list RefChildren as reference>
    List<${className}> listRefBy${reference.ChildTable.className}Many${reference_index}(Map<String, Object> paramMap);
    
    </#list>
</#if>
}
