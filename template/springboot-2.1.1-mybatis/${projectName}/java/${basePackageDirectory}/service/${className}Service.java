package ${basePackage}.service;

import java.util.List;
import java.util.Map;

import ${basePackage}.entity.${className}Entity;

/**
 * ${tableNameCN} 业务逻辑层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
public interface ${className}Service {
    
	/**
     * 查询
     * 
     * @param paramMap 查询参数
     * @return java.util.List<${basePackage}.entity.${className}Entity>
     */
    List<${className}Entity> queryList(Map<String, Object> paramMap);
    
}
