package ${basepackage}.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onemysoft.common.context.SystemContext;
import com.onemysoft.common.springmvc.intercept.annotation.JsonDataRequestBody;
import com.onemysoft.common.utils.OMAssert;
import com.onemysoft.common.validation.BatchDeleteGroup;
import com.onemysoft.common.validation.CreateGroup;
import com.onemysoft.common.validation.DeleteGroup;
import com.onemysoft.common.validation.EditGroup;
import com.onemysoft.common.validation.GetGroup;
import com.onemysoft.common.validation.ListGroup;
import com.onemysoft.common.web.DataTransferObject;
import com.onemysoft.common.web.Result;
import ${basepackage}.controller.mapper.${className}Mapper;
import ${basepackage}.controller.vo.${className}Vo;
import ${basepackage}.entity.${className};
import ${basepackage}.service.${className}Service;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api/${classNameVariable?lower_case}")
public class ${className}Controller {

    private final ${className}Service ${classNameVariable}Service;
    
    @Autowired
	public ${className}Controller(${className}Service ${classNameVariable}Service) {
		this.${classNameVariable}Service = ${classNameVariable}Service;
	}

	@ApiOperation(value = "create", notes = "create")
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Result create(@Validated({CreateGroup.class}) ${className}Vo ${classNameVariable}Vo) {

		${className} ${classNameVariable} = ${className}Mapper.voToEntity(${classNameVariable}Vo);
		this.${classNameVariable}Service.create(${classNameVariable});
		${classNameVariable}Vo = ${className}Mapper.entityToVo(${classNameVariable});
		return Result.ok().data(${classNameVariable}Vo);
    }
	
	@ApiOperation(value = "edit", notes = "edit")
	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Result edit(@Validated({EditGroup.class}) ${className}Vo ${classNameVariable}Vo) {

		${className} ${classNameVariable} = ${className}Mapper.voToEntity(${classNameVariable}Vo);
		${classNameVariable} = this.${classNameVariable}Service.update(${classNameVariable});
		${classNameVariable}Vo = ${className}Mapper.entityToVo(${classNameVariable});
		return Result.ok().data(${classNameVariable}Vo);
	}
	
    @ApiOperation(value = "delete", notes = "delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Result delete(@Validated({DeleteGroup.class}) ${className}Vo ${classNameVariable}Vo) {
	    
    	this.${classNameVariable}Service.delete(${classNameVariable}Vo.getId());
	    return Result.ok();
	}
    
    @ApiOperation(value = "batchDelete", notes = "batchDelete")
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result batchDelete(@Validated({BatchDeleteGroup.class}) ${className}Vo ${classNameVariable}Vo) {

    	String[] ids = new String[${classNameVariable}Vo.getIdList().size()];
    	this.${classNameVariable}Service.batchDelete(${classNameVariable}Vo.getIdList().toArray(ids));
        return Result.ok();
    }

    @ApiOperation(value = "get", notes = "get")
	@RequestMapping(value = "get", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result get(@Validated({GetGroup.class}) ${className}Vo ${classNameVariable}Vo) {

    	${className} ${classNameVariable} = this.${classNameVariable}Service.findById(${classNameVariable}Vo.getId());
    	${classNameVariable}Vo = ${className}Mapper.entityToVo(${classNameVariable});
		return Result.ok().data(${classNameVariable}Vo);
    }
	
	@ApiOperation(value = "list", notes = "list")
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonDataRequestBody(limit="limit")
	@ResponseBody
	public Result list(@Validated({ListGroup.class}) ${className}Vo ${classNameVariable}Vo) {

		OMAssert.notNull(SystemContext.getRequestTransferData());

		int page = SystemContext.getRequestTransferData().getPage();
		int limit = SystemContext.getRequestTransferData().getLimit();

		${className} ${classNameVariable} = ${className}Mapper.voToEntity(${classNameVariable}Vo);
		Page<${className}> page${className} = this.${classNameVariable}Service.findPage(${classNameVariable}, page, limit);

		List<${className}Vo> ${classNameVariable}List = ${className}Mapper.entityToVo(page${className}.getContent());

		DataTransferObject<List<${className}Vo>> dto = new DataTransferObject<>();
		dto.setData(${classNameVariable}List);
		dto.setTotalRecordNums(page${className}.getTotalElements());
		dto.setPage(page);
		dto.setLimit(limit);

		return Result.ok().data(dto);
	}

}
