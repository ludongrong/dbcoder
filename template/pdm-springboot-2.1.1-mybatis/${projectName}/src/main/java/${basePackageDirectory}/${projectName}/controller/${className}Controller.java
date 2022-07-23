package ${basePackage}.${projectName}.controller;

import ${basePackage}.${projectName}.entity.${className}Entity;
import ${basePackage}.${projectName}.entity.header.${className}Header;
import ${basePackage}.${projectName}.service.${className}Service;
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
<#--
“数据对象”可统称为资源，“业务领域”就是业务相近的“资源”的集合。

“资源”一定是业务抽象后的对象：
  1、可以是具体的数据对象：商品、订单...
  2、可以是抽象的对象概念：租户、用户...
“业务领域”与“业务领域”之间的依赖，可理解为是对“资源”操作(读、写、通知)的依赖。
所以，API作为“业务领域”间沟通的手段，其应该以面向资源角度进行命名。
例如，修改-订单-商品：updateOrderItem。

命名术语
1） 商品 -> item
2） 特性 -> feature
3） 金额 -> amount
4） 校验 -> check
5） 分页 -> paging
6） 创建 -> save
7） 删除 -> remove
8） 搜索 -> query
-->

/**
 * ${Name} 接口控制层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Slf4j
@Api(value = "${Name}", tags = "${Name}")
@RestController
<#-- apiv1：充当接口的版本 -->
<#-- 驼峰：不建议（在URL上不美观）-->
<#-- @RequestMapping("/apiv1/${projectName}/${CodeCamelFirstLower}") -->
<#-- 全小写： -->
<#-- @RequestMapping("/apiv1/${projectName}/${CodeLower}") -->
<#-- 下划线： -->
<#-- @RequestMapping("/apiv1/${projectName}/${CodeUnderlineLower}") -->
<#-- 短横线： -->
@RequestMapping("/apiv1/${projectName}/${CodeKebabCaseLower}")
public class ${className}Controller extends BaseController {

    @Resource("${classNameVariable}Service")
    private I${className}Service ${classNameVariable}Service;

<#-- 内部跳转 --- 路径
    public static final String PAGE_LIST = "/account/${CodeKebabCaseLower}/listpage";
    public static final String PAGE_LIST_DATA = "/account/${CodeKebabCaseLower}/listpage/data"
	public static final String PAGE_ADD = "/account/${CodeKebabCaseLower}/addpage";
	public static final String PAGE_ADD_DATA = "/account/${CodeKebabCaseLower}/addpage/data"
    public static final String PAGE_ADD_SUBMIT = "/account/${CodeKebabCaseLower}/addpage/submit"
    public static final String PAGE_EDIT = "/account/${CodeKebabCaseLower}/editpage";
    public static final String PAGE_EDIT_DATA = "/account/${CodeKebabCaseLower}/editpage/data";
    public static final String PAGE_EDIT_UPDATE = "/account/${CodeKebabCaseLower}/editpage/update";
-->
<#-- 内部跳转 --- 接口
	@RequestMapping(value = "list")
	public String list(Model model) {
		model.addAttribute("pageName", "tea.account.${classNameVariable}.list");
		model.addAttribute("readUrl", PAGE_LIST_DATA);
		model.addAttribute("rootAddr", "http://127.0.0.1:8080/tea/");
		return this.PAGE_LIST;
	}

	@RequestMapping(value = "add")
	public String add(Model model) {
		model.addAttribute("pageName", "tea.account.${classNameVariable}.add");
		model.addAttribute("readUrl", PAGE_ADD_DATA);
		model.addAttribute("submitUrl", PAGE_ADD_SUBMIT);
		model.addAttribute("rootAddr", "http://127.0.0.1:8080/tea/");
		return this.PAGE_ADD;
	}

	@RequestMapping(value = "edit")
	public String edit(Model model) {
		model.addAttribute("pageName", "tea.account.${classNameVariable}.edit");
		model.addAttribute("readUrl", PAGE_EDIT_DATA);
		model.addAttribute("submitUrl", PAGE_EDIT_UPDATE);
		model.addAttribute("rootAddr", "http://127.0.0.1:8080/tea/");
		return this.PAGE_ADD;
	}
-->
	
    /**
     * ${Name} 创建
     * 
     * @param ${classNameVariable} {@code {}} 入参
     * @return ${basePackage}.${projectName}.entity.${className}
     */
    @ApiOperation(value = "${Name} 创建", notes = "其他")
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<${className}Vo> save(@RequestBody ${className}Vo ${classNameVariable}Vo) {
        RestResult<${className}Vo> result = new RestResult<${className}Vo>();
        try {
            ${classNameVariable}Service.save(${classNameVariable});
            result.setMessage("新增 “${Name}” 成功");
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setMessage("新增 “${Name}” 失败 ---> " + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * ${Name} 删除
     * 
     * @param ${classNameVariable} {@code {}} 入参
     * @return ${basePackage}.${projectName}.entity.${className}
     */
    @ApiOperation(value = "${Name} 删除", notes = "其他")
    @PostMapping(value = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<${className}Vo> remove(@RequestBody ${className}Vo ${classNameVariable}Vo) {
        RestResult<${className}Vo> result = new RestResult<${className}Vo>();
        try {
            ${classNameVariable}Service.remove(${classNameVariable});
            result.setMessage("删除 “${Name}” 成功");
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setMessage("删除 “${Name}” 失败 ---> " + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * ${Name} 更新
     * 
     * @param ${classNameVariable} {@code {}} 入参
     * @return ${basePackage}.${projectName}.entity.${className}
     */
    @ApiOperation(value = "${Name} 更新", notes = "其他")
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<${className}Vo> update(@RequestBody ${className}Vo ${classNameVariable}Vo) {
        RestResult<${className}Vo> result = new RestResult<${className}Vo>();
        try {
            ${classNameVariable}Service.update(${classNameVariable});
            result.setMessage("更新 “${Name}” 成功");
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setMessage("更新 “${Name}” 失败 ---> " + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    
    <#if HasPrimaryKey == '1'>
    @Override
    public RestResult<${className}Vo> getByPrimary(<#list Columns as column><#if column.PrimaryKey == "1">${column.JavaType} ${column.CodeCamelFirstLower}</#if><#if column_has_next>, </#if></#list>) {
        RestResult<${className}Vo> result = new RestResult<>();
        try {
            ${className}Entity entity = ${classNameVariable}Service.getByPrimary(<#list Columns as column><#if column.PrimaryKey == "1">${column.JavaType} ${column.CodeCamelFirstLower}</#if><#if column_has_next>, </#if></#list>);
            result.setData(entity);
            result.setMessage("获取 “${Name}” 成功");
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setMessage("获取 “${Name}” 失败 ---> " + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    </#if>
    
	/**
     * ${Name} 查询 - 分页
     * 
     * @param jsonStr {@code {}} 入参
     * @return java.util.List<${basePackage}.${projectName}.entity.${className}Entity>
     */
    @ApiOperation(value = "${Name} 查询分页", notes = "其他")
    @PostMapping(value = "/paging", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<PageInfo<${className}Vo>, ${className}Header> paging(@RequestBody String jsonStr) {

        try {
            Map<String, Object> paramMap = queryParam(jsonStr);
            PageInfo<${className}Entity> ${classNameVariable}Page = ${classNameVariable}Service.paging(paramMap);
            return successJson(${classNameVariable}Page, ${className}Header.getInstance());
        } catch (Exception e) {
            return handleException(e);
        }
    }

    /**
     * ${Name} 查询
     * 
     * @param jsonStr {@code {}} 入参
     * @return java.util.List<${basePackage}.${projectName}.entity.${className}Entity>
     */
    @ApiOperation(value = "${Name} 查询列表", notes = "其他")
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<List<${className}Vo>, ${className}Header> list(@RequestBody String jsonStr) {

        try {
            Map<String, Object> paramMap = queryParam(jsonStr);
            List<${className}Entity> ${classNameVariable}List = ${classNameVariable}Service.list(paramMap);
            return successJson(${classNameVariable}List, ${className}Header.getInstance());
        } catch (Exception e) {
            return handleException(e);
        }
    }
    
    /**
     * ${Name} 查询
     * 
     * @param ${classNameVariable} {@code {}} 入参
     * @return java.util.List<${basePackage}.${projectName}.entity.${className}>
     */
    @ApiOperation(value = "${Name} 查询列表", notes = "其他")
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<VuePage<${className}>> list(@RequestBody ${className} ${classNameVariable}) {

        return ${classNameVariable}Service.list${className}(${classNameVariable});
    }
	
	/**
     * ${Name} 导出
     * 
     * @param jsonStr {@code {}} 入参
     * @return java.util.List<${basePackage}.${projectName}.entity.${className}Entity>
     */
	@ApiOperation(value = "${Name} 导出列表", notes = "其他")
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<Download, DownloadHeader> listForExport(@RequestBody String jsonStr) {

        //设置文件名
        String sheetName = "${Name}";

        try {
            Map<String, Object> paramMap = queryParam(jsonStr);
            String fileName = ${classNameVariable}Service.listForExport(sheetName, paramMap);
            return successJson(new Download(fileName), DownloadHeader.getInstance());
        } catch (Exception e) {
            return handleException(e);
        }
    }
	
	/**
     * ${Name} 获取参数 -> 查询分页、查询列表、导出列表
     * 
     * @param jsonStr {@code {}} 入参
     * @return java.util.List<${basePackage}.${projectName}.entity.${className}Entity>
     */
	private Map<String, Object> queryParam(String jsonStr) {
        return getJsonValues(jsonStr, "PAGE_NUM", "PAGE_SIZE",
			<#list Columns as column>
				"${column.CodeUpper}"<#if column_has_next>, </#if>
			</#list>);
    }

}
