package ${basePackage}.service;

import ${basePackage}.entity.${className};
import com.hiz.system.entity.RestResult;
import com.hiz.system.entity.VuePage;

/**
 * ${tableNameCN} 业务逻辑层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
public interface ${className}Service {
    
    RestResult<${className}> insert(${className} ${classNameVariable});
    
    RestResult<${className}> update(${className} ${classNameVariable});
    
    RestResult<${className}> delete(${className} ${classNameVariable});
    
    /**
     * ${tableNameCN} 查询列表
     * 
     * @param ${classNameVariable} 查询参数
     * @return java.util.List<${basePackage}.entity.${className}>
     */
    RestResult<VuePage<${className}>> list${className}(${className} ${classNameVariable});
    
    <#list parentReferences as reference>
    RestResult<VuePage<${className}>> list${className}${reference.className}ToOne${reference_index}(${className} ${classNameVariable});
    
    </#list>
    <#list childReferences as reference>
    RestResult<VuePage<${className}>> list${className}${reference.className}ToMany${reference_index}(${className} ${classNameVariable});
    
    </#list>
}
