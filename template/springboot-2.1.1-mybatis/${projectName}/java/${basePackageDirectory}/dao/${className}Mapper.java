package ${basePackage}.dao;

import java.util.List;
import java.util.Map;

import ${basePackage}.entity.${className}Entity;

/**
 * ${tableNameCN} 数据操作层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${lastUpdated?string("yyyy-MM-dd")}
 */
public interface ${className}Mapper {
    
	List<${className}Entity> queryList(Map<String, Object> paramMap);
	
}
