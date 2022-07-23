package ${basePackage}.${projectName}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${basePackage}.${projectName}.entity.${className}Bo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

<#if RefParents?exists || RefChildren?exists>
import java.util.List;
    
</#if>
/**
 * ${Name} 数据操作层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Repository
@Mapper
public interface ${className}Mapper extends BaseMapper<${className}Bo> {
    
<#if HasPrimaryKey == '1'>
    ${className}Bo getByPrimary(<#list Columns?filter(x -> x.PrimaryKey == "1") as column>@Param(${className}Bo.PK_${column.CodeUpper}) ${column.JavaType} ${column.CodeCamelFirstLower}<#sep>, </#sep></#list>);

</#if>
    /**
     * ${Name} 列表
     * 
     * @param paramMap 查询参数
     * @return java.util.List<${basePackage}.${projectName}.entity.${className}Bo>
     */
    List<TestBo> list(Map<String, Object> paramMap);
    
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
