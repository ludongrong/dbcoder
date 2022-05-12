package ${basePackage}.service;

import ${basePackage}.entity.${className};

/**
 * ${tableNameCN} 业务逻辑层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
public interface ${className}Service {
    
    /**
     * ${tableNameCN} 创建
     * 
     * @param processInstanceId 引擎实例ID
     * @param taskId 任务ID
     * @param ${classNameVariable} 集中参数引擎扩展表
     * @return 
     */
    RestResult<${className}> create(String processInstanceId, String taskId, ${className} ${classNameVariable});
    
    RestResult<${className}> modify(String taskId, ${className} ${classNameVariable});
    
    RestResult<${className}> delete(${className} ${classNameVariable});
    
    RestResult<Object> query(String userId, String todo, ${className} ${classNameVariable});

    RestResult<Object> count(String userId, ${className} ${classNameVariable});

}
