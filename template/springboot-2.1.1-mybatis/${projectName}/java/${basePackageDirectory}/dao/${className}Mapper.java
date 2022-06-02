package ${basePackage}.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import ${basePackage}.entity.${className}Entity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * ${tableNameCN} 数据操作层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Mapper
@DS("primary")
public interface ${className}Mapper {
    
	List<${className}Entity> queryList(Map<String, Object> paramMap);
	
}
