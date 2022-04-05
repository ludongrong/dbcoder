package ${basePackage}.controller;

import ${basePackage}.entity.${className}Entity;
import ${basePackage}.entity.header.${className}Header;
import ${basePackage}.service.${className}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ${tableNameCN} 接口控制层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Api(value = "${tableNameCN}", tags = "${tableNameCN}")
@RestController
@RequestMapping("/projectName/${tableName?lower_case}")
public class ${className}Controller extends BaseController {

    @Resource
    private ${className}Service ${classNameVariable}Service;
	
	/**
     * ${tableNameCN} 查询分页
     * 
     * <pre>
     * URI 样例
     *   POST /${tableName?lower_case}/query.action
     * </pre>
     * 
     * @param jsonStr {@code {}} 入参
     * @return java.util.List<${basePackage}.entity.${className}Entity>
     */
    @ApiOperation(value = "${tableNameCN} 查询分页", notes = "入参 <#list columns as column>“${column.nameCN}”<#if column_has_next>, </#if></#list>...")
    @PostMapping(value = "/query.action", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<PageInfo<${className}Entity>, ${className}Header> queryPage(@RequestBody String jsonStr) {

        try {
            Map<String, Object> paramMap = queryParam(jsonStr);
            PageInfo<${className}Entity> ${classNameVariable}Page = ${classNameVariable}Service.queryPage(paramMap);
            return successJson(${classNameVariable}Page, ${className}Header.getInstance());
        } catch (Exception e) {
            return handleException(e);
        }
    }

    /**
     * ${tableNameCN} 查询列表
     * 
     * <pre>
     * URI 样例
     *   POST /${tableName?lower_case}/list.action
     * </pre>
     * 
     * @param jsonStr {@code {}} 入参
     * @return java.util.List<${basePackage}.entity.${className}Entity>
     */
    @ApiOperation(value = "${tableNameCN} 查询列表", notes = "入参 <#list columns as column>“${column.nameCN}”<#if column_has_next>, </#if></#list>...")
    @PostMapping(value = "/list.action", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<List<${className}Entity>, ${className}Header> queryForList(@RequestBody String jsonStr) {

        try {
            Map<String, Object> paramMap = queryParam(jsonStr);
            List<${className}Entity> ${classNameVariable}List = ${classNameVariable}Service.queryList(paramMap);
            return successJson(${classNameVariable}List, ${className}Header.getInstance());
        } catch (Exception e) {
            return handleException(e);
        }
    }
	
	/**
     * ${tableNameCN} 导出列表
     * 
     * <pre>
     * URI 样例
     *   POST /${tableName?lower_case}/export.action
     * </pre>
     * 
     * @param jsonStr {@code {}} 入参
     * @return java.util.List<${basePackage}.entity.${className}Entity>
     */
	@ApiOperation(value = "${tableNameCN} 导出列表", notes = "入参 <#list columns as column>“${column.nameCN}”<#if column_has_next>, </#if></#list>...")
    @PostMapping(value = "/export.action", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<Download, DownloadHeader> queryForExport(@RequestBody String jsonStr) {

        //设置文件名
        String sheetName = "${tableNameCN}";

        try {
            Map<String, Object> paramMap = queryParam(jsonStr);
            String fileName = ${classNameVariable}Service.queryListForExport(sheetName, paramMap);
            return successJson(new Download(fileName), DownloadHeader.getInstance());
        } catch (Exception e) {
            return handleException(e);
        }
    }
	
	/**
     * ${tableNameCN} 获取参数 -> 查询分页、查询列表、导出列表
     * 
     * <pre>
     * URI 样例
     *   POST /${tableName?lower_case}/export.action
     * </pre>
     * 
     * @param jsonStr {@code {}} 入参
     * @return java.util.List<${basePackage}.entity.${className}Entity>
     */
	private Map<String, Object> queryParam(String jsonStr) {
        return getJsonValues(jsonStr, "PAGE_NUM", "PAGE_SIZE",
			<#list columns as column>
				"${column.name?upper_case}"<#if column_has_next>, </#if>
			</#list>);
    }
    	
}
