package ${basePackage}.service;

import java.util.List;
import java.util.Map;

import ${basePackage}.entity.${Code}Entity;
import com.github.pagehelper.PageInfo;
import org.springframework.scheduling.annotation.Async;

/**
 * ${Name} 业务逻辑层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
public interface ${CodeCamelFirstUpper}Service {
	
	/**
	 * ${Name} 查询分页
	 *
	 * @param paramMap 参数集
	 * @return java.util.List<${basePackage}.entity.${CodeCamelFirstUpper}>
	 */
	PageInfo<${CodeCamelFirstUpper}Entity> queryPage(Map<String, Object> paramMap);

}
