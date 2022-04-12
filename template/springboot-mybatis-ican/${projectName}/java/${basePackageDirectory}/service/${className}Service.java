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
	
	/**
     * ${tableNameCN} 查询列表
     * 
     * @param ${classNameVariable} 查询参数
     * @return java.util.List<${basePackage}.entity.${className}>
     */
    RestResult<VuePage<${className}>> list${className}(${className} ${classNameVariable});
    
}
