package ${basePackage}.${projectName}.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ${basePackage}.${projectName}.entity.${className}Bo;
import org.springframework.scheduling.annotation.Async;

<#if RefParents?exists || RefChildren?exists>
import java.util.List;    
</#if>
import java.util.Map;
import java.util.List;
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
public interface I${className}Service extends IService<${className}Bo> {

<#if HasPrimaryKey == '1'>
    /**
	 * ${Name} 查询 - 主键
	 *
<#list Columns?filter(x -> x.PrimaryKey == "1") as column>
     * @param ${column.CodeCamelFirstLower} ${column.Name}
</#list>
	 * @return java.util.List<${basePackage}.${projectName}.entity.${className}Bo>
	 */
    ${className}Bo getByPrimary(<#list Columns?filter(x -> x.PrimaryKey == "1") as column>${column.JavaType} ${column.CodeCamelFirstLower}<#sep>, </#sep></#list>);
</#if>

    /**
     * ${Name} 列表
     * 
     * @param paramMap 查询参数
     * @return java.util.List<${basePackage}.${projectName}.entity.${className}Bo>
     */
    List<TestBo> list(Map<String, Object> paramMap);
    
	/**
     * ${Name} 导出
     * 
     * @param paramMap 查询参数
     * @return java.util.List<${basePackage}.${projectName}.entity.${className}Bo>
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
