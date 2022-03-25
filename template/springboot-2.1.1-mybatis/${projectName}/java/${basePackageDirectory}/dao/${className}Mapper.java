package ${basePackage}.dao;

import java.util.List;
import java.util.Map;

import ${basePackage}.entity.${className}Entity;

/**
* ${className}Mapper
*
* @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
* <#noparse>@since ${currentDate:date('yyyy-MM-dd')}</#noparse>
*/
public interface ${className}Mapper {
    
	List<${className}Entity> queryList(Map<String, Object> paramMap);
	
}
