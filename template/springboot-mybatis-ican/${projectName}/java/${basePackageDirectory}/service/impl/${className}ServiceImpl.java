package ${basePackage}.service.impl;

import ${basePackage}.entity.${className};
import ${basePackage}.mapper.${className}Dao;
import ${basePackage}.service.${className}Service;
import com.hiz.system.entity.RestResult;
import com.hiz.system.entity.VuePage;
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
    
    @Override
    public RestResult<${className}> insert(${className} ${classNameVariable}) {
        RestResult<${className}> result = new RestResult<${className}>();
        try {
            ${classNameVariable}Dao.insert(${classNameVariable});
            result.setMessage("新增 “${tableNameCN}” 成功");
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setMessage("新增 “${tableNameCN}” 失败" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    
    @Override
    public RestResult<${className}> update(${className} ${classNameVariable}) {
        RestResult<${className}> result = new RestResult<${className}>();
        try {
            ${classNameVariable}Dao.update(${classNameVariable});
            result.setMessage("更新 “${tableNameCN}” 成功");
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setMessage("更新 “${tableNameCN}” 失败" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    
    @Override
    public RestResult<${className}> delete(${className} ${classNameVariable}) {
        RestResult<${className}> result = new RestResult<${className}>();
        try {
            ${classNameVariable}Dao.delete(${classNameVariable});
            result.setMessage("更新 “${tableNameCN}” 成功");
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setMessage("更新 “${tableNameCN}” 失败" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
	
    @Override
    public RestResult<VuePage<${className}>> list${className}(${className} ${classNameVariable}) {
        VuePage<${className}> result = null;
        
        try {
            List<${className}> dataList = ${classNameVariable}Dao.list${className}(${classNameVariable});
            result = new VuePage<${className}>(dataList);
        } catch (Exception e) {
            log.error("${className}ServiceImpl", e);
            return RestResult.buildErrorResult("查询 “${tableNameCN}” 失败");
        }
        return RestResult.buildSuccessResult(result);
    }
	
    <#list parentReferences as reference>
    @Override
    public RestResult<VuePage<${className}>> list${className}${reference.className}ToOne${reference_index}(${className} ${classNameVariable}) {
        VuePage<${className}> result = null;
        
        try {
            List<${className}> dataList = ${classNameVariable}Dao.list${className}${reference.className}ToOne${reference_index}(${classNameVariable});
            result = new VuePage<${className}>(dataList);
        } catch (Exception e) {
            log.error("${className}ServiceImpl", e);
            return RestResult.buildErrorResult("查询 “${tableNameCN}” 的 “list${className}${reference.className}ToOne${reference_index}” 失败");
        }
        return RestResult.buildSuccessResult(result);
    }
    
    </#list>
    <#list childReferences as reference>
    @Override
    public RestResult<VuePage<${className}>> list${className}${reference.className}ToMany${reference_index}(${className} ${classNameVariable}) {
        VuePage<${className}> result = null;
        
        try {
            List<${className}> dataList = ${classNameVariable}Dao.list${className}${reference.className}ToMany${reference_index}(${classNameVariable});
            result = new VuePage<${className}>(dataList);
        } catch (Exception e) {
            log.error("${className}ServiceImpl", e);
            return RestResult.buildErrorResult("查询 “${tableNameCN}” 的 “list${className}${reference.className}ToMany${reference_index}” 失败");
        }
        return RestResult.buildSuccessResult(result);
    }
    
    </#list>
}
