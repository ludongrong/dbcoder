package ${basePackage}.${projectName}.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import ${basePackage}.${projectName}.entity.${className}Entity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * ${Name} 数据操作层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Mapper
@DS("primary")
public interface ${className}Mapper {
    
    void insert(${className} Map<String, Object> paramMap);
    
    void delete(${className} Map<String, Object> paramMap);
    
    void update(${className} Map<String, Object> paramMap);
    
    <#if HasPrimaryKey == '1'>
    ${className}Entity getByPrimary(<#list Columns as column><#if column.PrimaryKey == "1">@Param("${column.CodeUnderlineUpper}") ${column.JavaType} ${column.CodeCamelFirstLower}</#if><#if column_has_next>, </#if></#list>);
    </#if>
    
    long count(Map<String, Object> paramMap);
    
    List<${className}Entity> list(Map<String, Object> paramMap);

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
