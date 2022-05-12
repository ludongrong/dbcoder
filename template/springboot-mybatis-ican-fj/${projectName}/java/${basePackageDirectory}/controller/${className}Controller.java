package ${basePackage}.controller;

import com.hiz.frame.controller.BaseController;
import ${basePackage}.entity.${className};
import ${basePackage}.service.${className}Service;
import com.hiz.system.entity.RestResult;
import com.hiz.system.entity.VuePage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * ${tableNameCN} 接口控制层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Api(value = "${tableNameCN}", tags = "${tableNameCN}")
@RestController
@RequestMapping("/${classNameVariable}")
public class ${className}Controller extends BaseController {

    @Resource
    private ${className}Service ${classNameVariable}Service;
    
    /**
     * ${tableNameCN} 创建
     * 
     * <pre>
     * URI 样例
     *   POST /${classNameVariable}/create
     * </pre>
     * 
     * @param ${classNameVariable} {@code {}} 入参
     * @return ${basePackage}.entity.${className}
     */
    @ApiOperation(value = "${tableNameCN} 创建", notes = "入参 <#list columns as column>“${column.nameCN}”<#if column_has_next>, </#if></#list>...")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<${className}> create(@RequestBody ${className} ${classNameVariable}) {

        return ${classNameVariable}Service.create(${classNameVariable});
    }
    
    /**
     * ${tableNameCN} 更新
     * 
     * <pre>
     * URI 样例
     *   POST /${classNameVariable}/update
     * </pre>
     * 
     * @param ${classNameVariable} {@code {}} 入参
     * @return ${basePackage}.entity.${className}
     */
    @ApiOperation(value = "${tableNameCN} 更新", notes = "入参 <#list columns as column>“${column.nameCN}”<#if column_has_next>, </#if></#list>...")
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<${className}> update(@RequestBody ${className} ${classNameVariable}) {

        return ${classNameVariable}Service.update(${classNameVariable});
    }
    
    /**
     * ${tableNameCN} 查询列表
     * 
     * <pre>
     * URI 样例
     *   POST /${classNameVariable}/list
     * </pre>
     * 
     * @param ${classNameVariable} {@code {}} 入参
     * @return java.util.List<${basePackage}.entity.${className}>
     */
    @ApiOperation(value = "${tableNameCN} 查询列表", notes = "入参 <#list columns as column>“${column.nameCN}”<#if column_has_next>, </#if></#list>...")
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<VuePage<${className}>> list(@RequestBody ${className} ${classNameVariable}) {

        return ${classNameVariable}Service.list${className}(${classNameVariable});
    }

}
