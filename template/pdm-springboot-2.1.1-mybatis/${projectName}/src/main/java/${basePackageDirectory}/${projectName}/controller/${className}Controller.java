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
@RequestMapping("/${projectName}/${CodeKebabCaseLower}")
public class ${className}Controller extends BaseController {

    @Resource("${classNameVariable}Service")
    private I${className}Service ${classNameVariable}Service;

<#--

    // -----------------------
    // 内部跳转 --- 路径
    // -----------------------

    public static final String PAGE_LIST = "/account/${CodeKebabCaseLower}/listpage";
    public static final String PAGE_LIST_DATA = "/account/${CodeKebabCaseLower}/listpage/data"
    public static final String PAGE_ADD = "/account/${CodeKebabCaseLower}/addpage";
    public static final String PAGE_ADD_DATA = "/account/${CodeKebabCaseLower}/addpage/data"
    public static final String PAGE_ADD_SUBMIT = "/account/${CodeKebabCaseLower}/addpage/submit"
    public static final String PAGE_EDIT = "/account/${CodeKebabCaseLower}/editpage";
    public static final String PAGE_EDIT_DATA = "/account/${CodeKebabCaseLower}/editpage/data";
    public static final String PAGE_EDIT_UPDATE = "/account/${CodeKebabCaseLower}/editpage/update";

    // -----------------------
    // 内部跳转 --- 接口
    // -----------------------

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

<#--
1、方式1：直接返回业务对象，错误通过HTTPCODE标记
  => 内置
    => ConversionNotSupportException -> 500(Internal Server Error)
    => HttpMediaTypeNotAcceptableException -> 406(Not Acceptable)
    => HttpMediaTypeNotSupportedException -> 415(Unsupported Media Type)
    => HttpMessageNotWritableException -> 500(Internal Server Error)
    => HttpRequestMethodNotSupportedException -> 405(Method Not Allowed)
    => MissingServletRequestParamerException -> 400(Bad Request)
    => NoSuchRequestHandlingMethodException -> 404(Not found)
    => TypeMismatchException -> 400(Bad Request)
  => 直接设置
    => response.setStatus(HttpServletResponse.SC_NOT_FOUND);
2、方式2：HttpCode = 200，消息体中包含错误信息 {"code": "password error"}
  => 采用方式2，可以解决调用方不知道错误在哪里，很懵逼的痛点
-->
    /**
     * 新增资源 - 单个.
     * 
     * <pre>
     * URI 样例
     * 
     * POST /animals  // 新增动物
     * POST /zoos/1/employees // 为id为1的动物园雇佣员工
     * </pre>
     * 
     * @param ${classNameVariable}Vo {@code ${className}Vo}
     * @return {@code ${className}Dto}
     */
    @ApiOperation(value = "新增", notes = "单对象的保存接口。")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ${className}Dto save(@RequestBody ${className}Vo ${classNameVariable}Vo) {

        ${className}Bo ${classNameVariable}Bo = ${className}Mapper.INSTANCE.toBo(${classNameVariable}Vo);
        boolean success = ${classNameVariable}Service.save(${classNameVariable}Bo);
        if(!success) {
            return BaseDto.of(TestDto.class).fail();
        }
        
        ${className}Dto ${classNameVariable}Dto = BaseDto.of(${className}Dto.class).success();
        ${classNameVariable}Dto.set${className}Vo(${className}Mapper.INSTANCE.toVo(${classNameVariable}Bo));
        return ${classNameVariable}Dto;
    }
    
    /**
     * 新增资源 - 多个.
     *
     * ${classNameVariable}VoList {@link java.util.List<${className}Vo>}
     * @return {@link ${className}Dto}
     */
    @ApiOperation(value = "新增", notes = "多对象的保存接口。")
    @PostMapping(value = "/save/batch", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ${className}Dto save(@RequestBody List<${className}Vo> ${classNameVariable}VoList) {

        List<${className}Bo> ${classNameVariable}BoList = ${className}Mapper.INSTANCE.toBo(${classNameVariable}VoList);

        boolean success = ${classNameVariable}Service.saveBatch(${classNameVariable}BoList);
        if (!success) {
            return BaseDto.of(${className}Dto.class).fail();
        }

        ${className}Dto ${classNameVariable}Dto = BaseDto.of(${className}Dto.class).success();
        ${classNameVariable}Dto.set${className}VoList(${className}Mapper.INSTANCE.toVo(${classNameVariable}BoList));
        return ${classNameVariable}Dto;
    }
    
    /**
     * 删除资源 - 单个.
     * 
     * <pre>
     * DELETE /zoos/1/employees/2
     * DELETE /zoos/1/employees/2;4;5
     * DELETE /zoos/1/animals  //删除id为1的动物园内的所有动物
     * </pre>
     * 
     */
    @ApiOperation(value = "删除", notes = "单对象的删除接口。")
    @RequestMapping(value = "<#list Columns?filter(x -> x.PrimaryKey == "1") as column>/<#noparse>{</#noparse>${column.CodeCamelFirstLower}<#noparse>}</#noparse></#list>", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TestDto remove(<#list Columns?filter(x -> x.PrimaryKey == "1") as column>@PathVariable(name = "${column.CodeCamelFirstLower}") ${column.JavaType} ${column.CodeCamelFirstLower}<#sep>, </#sep></#list>) {
        ${className}Bo ${classNameVariable}Bo = new ${className}Bo();
        ${classNameVariable}Bo.setId(id);
        return removeBatchByPrimary(Arrays.asList(${classNameVariable}Bo));
    }
    
    /**
     * 删除资源 - 单个.
     *
     * @param ${classNameVariable}Vo {@link ${className}Vo}
     * @return {@link ${className}Dto}
     */
    @ApiOperation(value = "删除", notes = "单对象的删除接口。")
    @PostMapping(value = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public ${className}Dto remove(@RequestBody ${className}Vo ${classNameVariable}Vo) {
        ${className}Bo ${classNameVariable}Bo = ${className}Mapper.INSTANCE.toBo(${classNameVariable}Vo);
        return removeBatchByPrimary(Arrays.asList(${classNameVariable}Bo));
    }

    /**
     * 删除资源 - 多个.
     *
     * @param ${classNameVariable}Vo {@link ${className}Vo}
     * @return {@link ${className}Dto}
     */
    @ApiOperation(value = "删除", notes = "多对象的删除接口。")
    @PostMapping(value = "/remove/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ${className}Dto remove(@RequestBody List<${className}Vo> ${classNameVariable}VoList) {
        List<${className}Bo> ${classNameVariable}BoList = ${className}Mapper.INSTANCE.toBo(${classNameVariable}VoList);
        return removeBatchByPrimary(${classNameVariable}BoList);
    }

    private ${className}Dto removeBatchByPrimary(List<${className}Bo> ${classNameVariable}BoList) {
        boolean success = ${classNameVariable}Service.removeBatchByPrimary(${classNameVariable}BoList);
        return BaseDto.of(${className}Dto.class).result(success);
    }
    
    /**
     * 更新资源 - 单个.
     *
     * <pre>
     * URI 样例
     *
     * PUT /animals/1
     * PUT /zoos/1
     * </pre>
     *
     */
    @ApiOperation(value = "更新", notes = "单对象的更新接口。")
    @RequestMapping(value = "<#list Columns?filter(x -> x.PrimaryKey == "1") as column>/<#noparse>{</#noparse>${column.CodeCamelFirstLower}<#noparse>}</#noparse></#list>", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(<#list Columns?filter(x -> x.PrimaryKey == "1") as column>@PathVariable(name = "${column.CodeCamelFirstLower}") ${column.JavaType} ${column.CodeCamelFirstLower}<#sep>, </#sep></#list>, @RequestBody ${className}Vo ${classNameVariable}Vo) {
        ${className}Bo ${classNameVariable}Bo = ${className}Mapper.INSTANCE.toBo(${classNameVariable}Vo);
    <#list Columns?filter(x -> x.PrimaryKey == "1") as column>
        ${classNameVariable}Bo.set${column.CodeCamelFirstUpper}(${column.CodeCamelFirstLower});
    </#list>
        ${classNameVariable}Service.update(${classNameVariable}Bo, <#list Columns?filter(x -> x.PrimaryKey == "1") as column>${column.CodeCamelFirstLower}<#sep>, </#sep></#list>);
    }
    
    /**
     * 更新
     *
     * @param ${classNameVariable}Vo {@code {}} 入参
     * @return io.github.ludongrong.test.entity.Test
     */
    @ApiOperation(value = "Test 更新", notes = "其他")
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ${className}Dto update(@RequestBody ${className}Vo ${classNameVariable}Vo) {
        ${className}Bo ${classNameVariable}Bo = ${className}Mapper.INSTANCE.toBo(${classNameVariable}Vo);
        boolean success = ${classNameVariable}Service.updateById(${classNameVariable}Bo);
        return BaseDto.of(${className}Dto.class).result(success);
    }

    /**
     * 更新
     *
     * @param ${classNameVariable}VoList {@code {}} 入参
     * @return io.github.ludongrong.test.entity.Test
     */
    @ApiOperation(value = "Test 更新", notes = "其他")
    @PostMapping(value = "/update/batch", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ${className}Dto update(@RequestBody List<${className}Vo> ${classNameVariable}VoList) {
        List<${className}Bo> ${classNameVariable}BoList = ${className}Mapper.INSTANCE.toBo(${classNameVariable}VoList);
        boolean success = ${classNameVariable}Service.updateBatchById(${classNameVariable}BoList);
        return BaseDto.of(${className}Dto.class).result(success);
    }

<#if HasPrimaryKey == '1'>
    @Override
    public RestResult<${className}Vo> getByPrimary(<#list Columns?filter(x -> x.PrimaryKey == "1") as column>${column.JavaType} ${column.CodeCamelFirstLower}<#sep>, </#sep></#list>) {
        RestResult<${className}Vo> result = new RestResult<>();
        try {
            ${className}Entity entity = ${classNameVariable}Service.getByPrimary(<#list Columns?filter(x -> x.PrimaryKey == "1") as column>${column.JavaType} ${column.CodeCamelFirstLower}<#sep>, </#sep></#list>);
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
     * 查询
     *
     * @param ${classNameVariable}Vo {@code {${className}Vo}}
     * @return java.util.List<io.github.ludongrong.test.entity.TestEntity>
     */
    @ApiOperation(value = "Test 查询列表", notes = "其他")
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<${className}Vo> list(@RequestBody ${className}Vo ${classNameVariable}Vo) {

        Map paramMap = objectMapper.convertValue(${classNameVariable}Vo, Map.class);
        List<${className}Bo> ${classNameVariable}BoList = ${classNameVariable}Service.list(paramMap);
        return ${className}Mapper.INSTANCE.toVo(${classNameVariable}BoList);
    }
    
    /**
     * 导出 - 异步
     *
     * @param ${classNameVariable}Vo {@code {${className}Vo}} 入参
     * @return java.util.List<io.github.ludongrong.test.entity.TestEntity>
     */
    @ApiOperation(value = "Test 导出列表", notes = "其他")
    @PostMapping(value = "/list/export", produces = MediaType.APPLICATION_JSON_VALUE)
    public DownloadBo listForExport(@RequestBody ${className}Vo ${classNameVariable}Vo) {

        String sheetName = "Test";
        Map paramMap = objectMapper.convertValue(${classNameVariable}Vo, Map.class);
        String fileName = ${classNameVariable}Service.listForExport(sheetName, paramMap);
        return new DownloadBo(fileName);
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
    
    /**
     * 新增.
     * 
     * <p>
     * 夹带文件流.
     * 
     * <pre>
     * {@code 
     * 
     * &#64;ApiOperation(value = "create", notes = "create")
     * &#64;RequestMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     * &#64;ResponseBody
     * public ${className}Dto create(@RequestParam("files") MultipartFile[] files) {
     *
     *    List<String> idList = new ArrayList<String>();
     *
     *    for (MultipartFile file : files) {
     *        idList.add(upload(file).getId());
     *    }
     *
     *    return new ${className}Dto(idList);
     * }
     * 
     * }
     * </pre>
     *
     * @param ${classNameVariable}Vo {@code ${className}Vo}
     * @param file     {@code MultipartFile}
     * @return {@code ${className}Dto}
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ${className}Dto create(${className}Vo ${classNameVariable}Vo, @RequestParam(value = "file") MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("file must have a non-nulls!");
        }

        if (file.getSize() > ApplicationConfiguration._UPLOAD_MAX_SIZE) {
            throw new IllegalStateException("The file size exceeds max upload size("
                    + ApplicationConfiguration._UPLOAD_MAX_SIZE / 1024 / 1024 + "M)!");
        }

        String fileType = FileUtil.extName(file.getOriginalFilename());
        if (fileType.equalsIgnoreCase(ApplicationConfiguration._FILE_TYPE_PDM) == false) {
            throw new IllegalStateException("The current file upload type is not support!");
        }

        return new ${className}Dto(UUID.randomUUID().toString());
    }

    /**
     * 查询
     * 
     * <p>
     * 查询单个资源.
     * 
     * <pre>
     * GET /zoos 
     * GET /zoos/1 
     * GET /zoos/1/employees
     * </pre>
     * 
     * <pre>
     *  分页返回内容
     * { 
     *     "data":[{},{},{}...]
     * }
     * </pre>
     * 
     * @return {@code Object}
     */
    @ApiOperation(value = "get", notes = "get")
    @RequestMapping(value = "<#list Columns?filter(x -> x.PrimaryKey == "1") as column>/<#noparse>{</#noparse>${column.CodeCamelFirstLower}<#noparse>}</#noparse></#list>", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object get(<#list Columns?filter(x -> x.PrimaryKey == "1") as column>@PathVariable(name = "${column.CodeCamelFirstLower}") ${column.JavaType} ${column.CodeCamelFirstLower}<#sep>, </#sep></#list>) {

        return ${classNameVariable}Service.get(<#list Columns?filter(x -> x.PrimaryKey == "1") as column>${column.CodeCamelFirstLower}<#sep>, </#sep></#list>);
    }

    /**
     * 查询
     * 
     * <p>
     * 查询复杂资源.
     * 
     * <pre>
     * URI 样例
     * 
     * GET /zoos 
     * GET /zoos/1 
     * GET /zoos/1/employees
     * </pre>
     * 
     * <pre>
     * 查询标签化
     * 
     * GET /trades?status=closed&sort=created,desc 可以写成 GET /trades/recently-closed
     * </pre>
     * 
     * <pre>
     * 查询参数
     * 过滤条件 ?type=1&age=16
     * 排序  ?sort=age,desc
     * 投影  ?whitelist=id,name,email     
     * 分页  ?limit=10&offset=3
     * </pre>
     * 
     * <pre>
     *  分页返回内容
     * { 
     *     "paging":{"limit":10,"offset":0,"total":729},
     *     "data":[{},{},{}...]
     * }
     * </pre>
     * 
     * @param param {@code Map<String, Object>}
     * @return {@code ${className}Dto}
     */
    @ApiOperation(value = "get", notes = "get")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ${className}Dto get(@RequestParam Map<String, Object> param) {

        Integer[] pageParam = Optional.ofNullable(MapUtil.getInt(param, "offset")).filter(t -> {
            return t >= 0;
        }).map(t -> {
            return new Integer[] { t, Optional.ofNullable(MapUtil.getInt(param, "limit")).orElse(10) };
        }).orElse(null);

        if (pageParam == null) {
            pageParam = Optional.ofNullable(MapUtil.getInt(param, "limit")).filter(t -> {
                return t > 0;
            }).map(t -> {
                return new Integer[] { Optional.ofNullable(MapUtil.getInt(param, "offset")).orElse(0), t };
            }).orElse(null);
        }

        if (pageParam == null) {
            return new ${className}Dto(${className}Mapper.INSTANCE.bo2vo(${classNameVariable}Service.list(param)));
        } else {
            ${className}Dto dto = new ${className}Dto(
                    ${className}Mapper.INSTANCE.bo2vo(${classNameVariable}Service.list(pageParam[0], pageParam[1], param)));
            dto.setOffset(pageParam[0]).setLimit(pageParam[1]).setTotal(${classNameVariable}Service.count(param));
            return dto;
        }
    }

    /**
     * 下载
     * 
     * <p>
     * 下载查询结果.
     */
    @ApiOperation(value = "xlsx", notes = "xlsx")
    @RequestMapping(value = "/xlsx", method = RequestMethod.GET, produces = "application/x-msdownload")
    public void get(HttpServletResponse response, @RequestParam Map<String, Object> param) {

        ${className}Dto dto = get(param);

        ByteArrayOutputStream byteOutbuf = new ByteArrayOutputStream();

        ExcelWriter writer = ExcelUtil.getWriter();
        for (${className}Vo ${classNameVariable}Vo : dto.getData()) {
            writer.write(CollUtil.newArrayList(<#list Columns as column>${classNameVariable}Vo.get${column.CodeCamelFirstUpper}())<#sep>, </#sep></#list>);
        }
        writer.flush(byteOutbuf);
        writer.close();

        ByteArrayInputStream byteInBuf = new ByteArrayInputStream(byteOutbuf.toByteArray());

        response.setContentType("application/x-msdownload");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + "${classNameVariable?lower_case}-" + DateUtil.formatDate(new Date()) + ".xlsx\"");
        response.setHeader("Content-Length", Integer.toString(byteInBuf.available()));

        try (OutputStream oStream = response.getOutputStream()) {
            IoUtil.copy(byteInBuf, oStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
