package ${basePackage}.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import io.github.ludongrong.dbcoder.common.config.ApplicationConfiguration;
import io.github.ludongrong.dbcoder.common.config.ProjectConfiguration;
import io.github.ludongrong.dbcoder.powerdesigner.controller.Table3Controller.UploadReponseDto;
import io.github.ludongrong.dbcoder.powerdesigner.controller.dto.ResponseDto;
import io.github.ludongrong.dbcoder.powerdesigner.controller.dto.Table3Dto;
import io.github.ludongrong.dbcoder.powerdesigner.controller.dto.Table3Mapper;
import io.github.ludongrong.dbcoder.powerdesigner.controller.dto.Table3Vo;
import io.github.ludongrong.dbcoder.common.exception.BadGatewayException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

/**
 * xxx.
 *
 * @author <a href="mailto:736779458@qq.com">卢冬榕</a>
 * @since 2020-12-18
 */
@Controller
@RequestMapping("/${classNameVariable?lower_case}")
public class ${className}Controller {

    @Resource(name="${projectName}-${className}ServiceImpl")
    private I${className}Service ${classNameVariable}Service;

    /**
     * 新增
     * 
     * <p>
     * 创建单个资源.
     * 
     * <pre>
     * URI 样例
     * 
     * POST /animals  //新增动物
     * POST /zoos/1/employees //为id为1的动物园雇佣员工
     * </pre>
     * 
     * <pre>
     * response 的 body 直接就是数据，不要做多余的包装。
     * </pre>
     * 
     * @param ${classNameVariable}Vo {@code ${className}Vo}
     * @return {@code Object}
     */
	@ApiOperation(value = "create", notes = "create")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object create(@RequestBody ${className}Vo ${classNameVariable}Vo) {

	    ${className}Bo ${classNameVariable}Bo=${className}Mapper.INSTANCE.vo2bo(${classNameVariable}Vo);
	    boolean createResult = ${classNameVariable}Service.create(${classNameVariable}Bo);
	    if(createResult==false) {
	        throw new BadGatewayException("未创建成功");
	    }
	    return ${className}Mapper.INSTANCE.bo2vo(${classNameVariable}Bo);
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
     * 删除
     * 
     * <p>
     * 删除资源.
     * 
     * <pre>
     * DELETE /zoos/1/employees/2
     * DELETE /zoos/1/employees/2;4;5
     * DELETE /zoos/1/animals  //删除id为1的动物园内的所有动物
     * </pre>
     * 
<#list primaryColumns as column>
     * @param ${column.javaNameVariable} {@code ${column.javaType}}}
</#list>
     */
	@ApiOperation(value = "delete", notes = "delete")
	@RequestMapping(value = "<#list primaryColumns as column>/{${column.javaNameVariable}}</#list>", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void delete(<#list primaryColumns as column>@PathVariable(name = "${column.javaNameVariable}") ${column.javaType} ${column.javaNameVariable}<#if column_has_next>, </#if></#list>) {
	    
	    ${classNameVariable}Service.delete(<#list primaryColumns as column>${column.javaNameVariable}<#if column_has_next>, </#if></#list>);
	}

    /**
     * 更新
     * 
     * <p>
     * 更新单个资源.
     * 
     * <pre>
     * URI 样例
     * 
     * PUT /animals/1
     * PUT /zoos/1
     * </pre>
     * 
<#list primaryColumns as column>
     * @param ${column.javaNameVariable} {@code ${column.javaType}}}
</#list>
     */
	@ApiOperation(value = "edit", notes = "edit")
	@RequestMapping(value = "<#list primaryColumns as column>/{${column.javaNameVariable}}</#list>", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void edit(<#list primaryColumns as column>@PathVariable(name = "${column.javaNameVariable}") ${column.javaType} ${column.javaNameVariable}<#if column_has_next>, </#if></#list>, @RequestBody ${className}Vo ${classNameVariable}Vo) {

		${className}Bo ${classNameVariable}Bo = ${className}Mapper.INSTANCE.vo2bo(${classNameVariable}Vo);
		${classNameVariable}Service.update(${classNameVariable}Bo, <#list primaryColumns as column>${column.javaNameVariable}<#if column_has_next>, </#if></#list>);
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
<#list primaryColumns as column>
     * @param ${column.javaNameVariable} {@code ${column.javaType}}}
</#list>
     * @return {@code Object}
     */
	@ApiOperation(value = "get", notes = "get")
	@RequestMapping(value = "<#list primaryColumns as column>/{${column.javaNameVariable}}</#list>", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object get(<#list primaryColumns as column>@PathVariable(name = "${column.javaNameVariable}") ${column.javaType} ${column.javaNameVariable}<#if column_has_next>, </#if></#list>) {

        return ${classNameVariable}Service.get(<#list primaryColumns as column>${column.javaNameVariable}<#if column_has_next>, </#if></#list>);
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
     * 
<#list primaryColumns as column>
     * @param ${column.javaNameVariable} {@code ${column.javaType}}}
</#list>
     */
    @ApiOperation(value = "xlsx", notes = "xlsx")
    @RequestMapping(value = "/xlsx", method = RequestMethod.GET, produces = "application/x-msdownload")
    public void get(HttpServletResponse response, @RequestParam Map<String, Object> param) {

        ${className}Dto dto = get(param);

        ByteArrayOutputStream byteOutbuf = new ByteArrayOutputStream();

        ExcelWriter writer = ExcelUtil.getWriter();
        for (${className}Vo ${classNameVariable}Vo : dto.getData()) {
            writer.write(CollUtil.newArrayList(<#list columns as column>${classNameVariable}Vo.get${column.javaName}())<#if column_has_next>, </#if></#list>);
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
