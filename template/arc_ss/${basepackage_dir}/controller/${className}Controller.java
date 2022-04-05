<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ${basepackage}.controller.dto.${className}Dto;
import ${basepackage}.model.${className};
import ${basepackage}.model.dyna.${className}Dyna;
import ${basepackage}.service.I${className}Svc;
import com.nsn.web.controller.session.SessionContext;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.laocu.core.exception.CodeException;
import cn.laocu.core.util.CoreUtil;

@Controller
public class ${className}Controller
{
	private static final Logger logger = LoggerFactory.getLogger(${className}Controller.class);

	@Resource(name = "${projectName}.${classNameLower}Svc")
	private I${className}Svc ${classNameLower}Svc;

	@RequestMapping(value = "/${classNameLower}", method = RequestMethod.POST)
	@ResponseBody
	public ${className}Dto create(SessionContext ctxt, HttpServletRequest request, @RequestBody ${className}Dto ${classNameLower}Dto)
	{
		if (ObjectUtil.isNull(${classNameLower}Dto))
		{
			throw new CodeException("-1", "${classNameLower}Dto must no empty");
		}

		${className} ${classNameLower} = ${classNameLower}Dto.get${className}();
		if (ObjectUtil.isNull(${classNameLower}))
		{
			throw new CodeException("-1", "${classNameLower} must no empty");
		}

		${classNameLower}Svc.create(${classNameLower});

		${classNameLower}Dto.set${className}(${classNameLower});
		return ${classNameLower}Dto;
	}
	
	@RequestMapping(value = "/${classNameLower}/{id:\\w{32}}", method = RequestMethod.DELETE)
	@ResponseBody
	public ${className}Dto delete(SessionContext ctxt, @PathVariable String id)
	{
		${className}Dto ${classNameLower}Dto = new ${className}Dto();

		if (StrUtil.isEmpty(id))
		{
			throw new CodeException("-1", "id must no empty");
		} else
		{
			logger.debug("id={}", id);
		}
		
		${classNameLower}Svc.deleteById(id);

		${classNameLower}Dto.set${className}(null);
		return new ${className}Dto();
	}

	@RequestMapping(value = "/${classNameLower}", method = RequestMethod.PUT)
	@ResponseBody
	public ${className}Dto edit(SessionContext ctxt, @RequestBody ${className}Dto ${classNameLower}Dto)
	{
		if (ObjectUtil.isNull(${classNameLower}Dto))
		{
			throw new CodeException("-1", "${classNameLower}Dto must no empty");
		}

		${className} ${classNameLower} = ${classNameLower}Dto.get${className}();
		if (ObjectUtil.isNull(${classNameLower}))
		{
			throw new CodeException("-1", "${classNameLower} must no empty");
		}else if (StrUtil.isEmpty(${classNameLower}.getId()))
		{
			throw new CodeException("-1", "${classNameLower}Id must no empty");
		}

		${classNameLower}Svc.update(${classNameLower});

		${classNameLower}Dto.set${className}(null);
		return ${classNameLower}Dto;
	}

	@RequestMapping(value = "/${classNameLower}/{id:\\w{32}}", method = RequestMethod.GET)
	@ResponseBody
	public ${className}Dto get(SessionContext ctxt, @PathVariable String id)
	{
		${className}Dto ${classNameLower}Dto = new ${className}Dto();

		if (StrUtil.isEmpty(id))
		{
			throw new CodeException("-1", "id must no empty");
		}

		${className} ${classNameLower} = ${classNameLower}Svc.findById(id);
		${classNameLower}Dto.set${className}(${classNameLower});

		return ${classNameLower}Dto;
	}
	
	@RequestMapping(value = "/${classNameLower}/list", method = RequestMethod.POST)
	@ResponseBody
	public ${className}Dto find(SessionContext ctxt, @RequestBody ${className}Dto ${classNameLower}Dto)
	{
		if (ObjectUtil.isNull(${classNameLower}Dto))
		{
			throw new CodeException("-1", "${classNameLower}Dto must no empty");
		}

		${className}Dyna ${classNameLower}Dyna = ${classNameLower}Dto.get${className}Dyna();
		if (ObjectUtil.isNull(${classNameLower}Dyna))
		{
			throw new CodeException("-1", "${classNameLower}Dyna must no empty");
		}

		List<${className}> ${classNameLower}List = this.${classNameLower}Svc.find(${classNameLower}Dyna);
		${classNameLower}Dto.set${className}s(${classNameLower}List);

		return ${classNameLower}Dto;
	}

	@RequestMapping(value = "/${classNameLower}/page", method = RequestMethod.POST)
	@ResponseBody
	public ${className}Dto findPage(SessionContext ctxt, @RequestBody ${className}Dto ${classNameLower}Dto)
	{
		if (ObjectUtil.isNull(${classNameLower}Dto))
		{
			throw new CodeException("-1", "${classNameLower}Dto must no empty");
		}

		${className}Dyna ${classNameLower}Dyna = ${classNameLower}Dto.get${className}Dyna();
		if (ObjectUtil.isNull(${classNameLower}Dyna))
		{
			throw new CodeException("-1", "${classNameLower}Dyna must no empty");
		}

		// Page Number must more than 1
		// Page Size must more than 1
		// between time
		if (ObjectUtil.isNull(${classNameLower}Dto.getPageNum()))
		{
			throw new CodeException("-1", "PageNum input error");
		}else if (ObjectUtil.isNull(${classNameLower}Dto.getPageSize()))
		{
			throw new CodeException("-1", "PageSize input error");
		}else if (CollectionUtil.isNotEmpty(${classNameLower}Dyna.getCreateTimeBetween()))
		{
			List<Timestamp> createTimeList = ${classNameLower}Dyna.getCreateTimeBetween();
			if (CoreUtil.isNotEqual(createTimeList.size(), 2))
			{
				throw new CodeException("CreateTimeBetween input error");
			}

			Timestamp startTime = createTimeList.get(0);
			Timestamp endTime = createTimeList.get(1);
			if (endTime.before(startTime))
			{
				throw new CodeException("CreateTimeBetween input error");
			}
		}

		// find page
		List<${className}> ${classNameLower}List = this.${classNameLower}Svc.find(${classNameLower}Dto.getPageNum(), ${classNameLower}Dto.getPageSize(), ${classNameLower}Dyna);
		${classNameLower}Dto.set${className}s(${classNameLower}List);
		
		// set dto
		${classNameLower}Dto.setPage(this.${classNameLower}Svc.count(${classNameLower}Dyna), ${classNameLower}Dto.getPageNum(), ${classNameLower}Dto.getPageSize());
		${classNameLower}Dto.set${className}(null);
		${classNameLower}Dto.set${className}Dyna(null);
		return ${classNameLower}Dto;
	}
}
