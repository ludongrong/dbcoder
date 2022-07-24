package ${basePackage}.${projectName}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${basePackage}.${projectName}.entity.${className}Bo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

<#if RefParents?exists || RefChildren?exists>
import java.util.List;
    
</#if>
<#--
命名约定：动作前缀_意图_条件1_条件2_...
  => 插入前缀：insert
  => 更新前缀：update
  => 删除前缀：delete
  => 查询前缀：select

例子：
  => 查询主键对象 -> select_primary
-->
/**
 * ${Name} 数据操作层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Repository
@Mapper
public interface ${className}Mapper extends BaseMapper<${className}Bo> {

<#--
// Mybatis 返回单个对象
// org.apache.ibatis.executor.ResultExtractor
//   => 大于1 抛出 ExecutorException
//   => 小于1 返回 null
-->
<#if HasPrimaryKey == '1'>
    ${className}Bo select_primary(<#list Columns?filter(x -> x.PrimaryKey == "1") as column>@Param(${className}Bo.PK_${column.CodeUpper}) ${column.JavaType} ${column.CodeCamelFirstLower}<#sep>, </#sep></#list>);

</#if>
    /**
     * ${Name} 列表
     * 
     * @param paramMap 查询参数
     * @return java.util.List<${basePackage}.${projectName}.entity.${className}Bo>
     */
    List<${className}Bo> select(Map<String, Object> paramMap);

<#-- 存在父级 -->
<#if RefParents?exists>
    <#list RefParents as reference>
    List<${className}Bo> select_${reference.ParentTable.className}_${reference_index}(Map<String, Object> paramMap);
    
    </#list>
    <#list RefParents as reference>
    List<${className}Bo> select_ref_${reference.ParentTable.className}_${reference_index}(Map<String, Object> paramMap);
    
    </#list>
</#if>
<#-- 存在子级 -->
<#if RefChildren?exists>
    <#list RefChildren as reference>
    List<${className}Bo> select_ref_${reference.ChildTable.className}_many${reference_index}(Map<String, Object> paramMap);
    
    </#list>
</#if>
}
