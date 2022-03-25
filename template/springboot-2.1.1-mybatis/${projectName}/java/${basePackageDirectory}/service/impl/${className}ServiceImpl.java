package ${basePackage}.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${basePackage}.dao.${className}Mapper;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.service.${className}Service;

/**
* ${className}ServiceImpl
*
* @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
* <#noparse>@since ${currentDate:date('yyyy-MM-dd')}</#noparse>
*/
@Service("${classNameVariable}Service")
public class ${className}ServiceImpl implements ${className}Service {
    
	@Resource
    private ${className}Mapper ${classNameVariable}Mapper;
	
    @Transactional
    @Override
    public List<${className}Entity> queryList(Map<String, Object> paramMap) {
        return ${classNameVariable}Mapper.queryList(paramMap);
    }
    
}
