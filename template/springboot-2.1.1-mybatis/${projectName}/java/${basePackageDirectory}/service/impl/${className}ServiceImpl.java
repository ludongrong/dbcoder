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
 * ${tableNameCN} 业务逻辑实现层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
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
