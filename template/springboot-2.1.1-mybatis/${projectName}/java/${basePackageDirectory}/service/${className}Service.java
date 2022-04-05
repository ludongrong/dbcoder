package ${basePackage}.service;

import java.util.List;
import java.util.Map;

import ${basePackage}.entity.${className}Entity;
import com.github.pagehelper.PageInfo;
import org.springframework.scheduling.annotation.Async;

/**
 * ${tableNameCN} 业务逻辑层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
public interface ${className}Service {
	
	/**
	 * ${tableNameCN} 查询分页
	 *
	 * @param paramMap 参数集
	 * @return java.util.List<${basePackage}.entity.${className}>
	 */
	PageInfo<${className}Entity> queryPage(Map<String, Object> paramMap);
    
	/**
     * ${tableNameCN} 查询列表
     * 
     * @param paramMap 查询参数
     * @return java.util.List<${basePackage}.entity.${className}Entity>
     */
    List<${className}Entity> queryList(Map<String, Object> paramMap);
    
	/**
     * ${tableNameCN} 导出列表
     * 
     * @param paramMap 查询参数
     * @return java.util.List<${basePackage}.entity.${className}Entity>
     */
	@Async
    String queryListForExport(String fileName, Map<String, Object> paramMap);
}
