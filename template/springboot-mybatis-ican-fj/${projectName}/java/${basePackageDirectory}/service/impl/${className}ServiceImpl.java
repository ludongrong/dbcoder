package ${basePackage}.service.impl;

import ${basePackage}.entity.${className};
import ${basePackage}.mapper.${className}Dao;
import ${basePackage}.service.${className}Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ${tableNameCN} 业务逻辑实现层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Slf4j
@Service("${classNameVariable}Service")
public class ${className}ServiceImpl implements ${className}Service {
    
	@Resource
    private ${className}Dao ${classNameVariable}Dao;

}
