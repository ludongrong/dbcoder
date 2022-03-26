package ${basePackage}.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import ${basePackage}.entity.${className}Entity;
import ${basePackage}.entity.${className}Header;
import ${basePackage}.service.${className}Service;

import lombok.Getter;
import lombok.Setter;

/**
 * ${tableNameCN} 接口控制层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${lastUpdated?string("yyyy-MM-dd")}
 */
@Api(value = "${tableNameCN}", tags = "${tableNameCN}")
@RestController
@RequestMapping("/${tableName?lower_case}")
public class ${className}Controller extends BaseController {

    @Resource
    private ${className}Service ${classNameVariable}Service;

    /**
     * 查询
     * 
     * <pre>
     * URI 样例
     *   POST /${tableName?lower_case}/query.action
     * </pre>
     * 
     * @param jsonStr {@code {}} 入参
     * @return java.util.List<${basePackage}.entity.${className}Entity>
     */
    @ApiOperation(value = "查询", notes = "入参 <#list columns as column>“${column.nameCN}”<#if column_has_next>, </#if></#list>...")
    @PostMapping(value = "/query.action", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<List<${className}Entity>, ${className}Header> queryWx(@RequestBody String jsonStr) {

        try {
            Map<String, Object> paramMap = getJsonValues(jsonStr, <#list columns as column>"${column.name?upper_case}"<#if column_has_next>, </#if></#list>);
            List<${className}Entity> ${classNameVariable}list = ${className}Service.queryList(paramMap);
            return successJson(${classNameVariable}List, className}Header.getInstance());
        } catch (Exception e) {
            return handleException(e);
        }
    }
    	
}
