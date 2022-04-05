<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.svc.impl;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;

import ${basepackage}.model.${className};
import ${basepackage}.dao.I${className}Dao;
import ${basepackage}.svc.I${className}Svc;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.laocu.core.util.IdUtil;
import cn.laocu.db.exception.DBException;

/**
 * 
 * ${className} 业务逻辑实现层
 * 
 * @author 卢冬榕
 * @email 736779458@qq.com
 *
 */
public class ${className}SvcImpl implements I${className}Svc
{
	private static final DynamicCondition _Dynam = new DynamicCondition(
			ConditionGroup.AND);
	static
	{
		_Dynam.orderByCreated("CREATE_TIME", "createTime");
	}
	
	/**
	 * logger
	 */
	private Logger logger = LogManager.getLogger(getClass());
	
	/**
	 *Dao->${classNameLower}Dao
	 */
	private I${className}Dao ${classNameLower}Dao;
	public void set${className}Dao(I${className}Dao ${classNameLower}Dao)
	{
		this.${classNameLower}Dao = ${classNameLower}Dao;
	}
	
	@Override
	public ${className} create(${className} ${classNameLower})
	{
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		${classNameLower}.setCreateTime(createTime);
		${classNameLower}.setModifyTime(createTime);
		
		if (StrUtil.isEmpty(${classNameLower}.getId()))
		{
			${classNameLower}.setId(IdUtil.stringUUID32());
		}

		int result = this.${classNameLower}Dao.create(${classNameLower});
		if (result != 1 && result != -2)
		{
			throw new CodeException("0101", "Create size zero, result:" + result);
		}
		
		return ${classNameLower};
	}
	
	@Override
	public void deleteById(String id)
	{
		logger.debug(id);
		int result = this.${classNameLower}Dao.deleteById(id);
		if (result != 1 && result != -2)
		{
			throw new CodeException("0101", "Delete size zero, result:" + result);
		}
	}
	
	@Override
	public ${className} deleteByIdOfSafely(String id)
	{
		logger.debug(id);
		${className} ${classNameLower} = this.${classNameLower}Dao.findById(id);
		if (ObjectUtil.isNotNull(${classNameLower}))
		{
			int result = this.${classNameLower}Dao.deleteById(id);
			if (result != 1 && result != -2)
			{
				throw new CodeException("0101", "Delete size zero, result:" + result);
			}

		}
		return ${classNameLower};
	}
	
	@Override
	public void update(${className} ${classNameLower})
	{
		Timestamp time = new Timestamp(System.currentTimeMillis());
		${classNameLower}.setModifyTime(time);

		int result = this.${classNameLower}Dao.update(${classNameLower});
		if (result != 1 && result != -2)
		{
			throw new CodeException("0101", "Update size zero, result:" + result);
		}
	}
	
	@Override
	public void update(String id, Map<String, Object> input)
	{
		Timestamp time = new Timestamp(System.currentTimeMillis());
		input.put("modifyTime", time);

		int result = this.${classNameLower}Dao.update(id, input);
		if (result != 1 && result != -2)
		{
			throw new CodeException("0101", "Update size zero, result:" + result);
		}
	}
	
	@Override
	public long count(Map<String, Object> input)
	{
		return this.${classNameLower}Dao.count(_Dynam, input);
	}

	@Override
	public ${className} findById(String id)
	{
		return this.${classNameLower}Dao.findById(id);
	}

	@Override
	public ${className} findById(String id, ${className} defaultVal)
	{
		try
		{
			return this.${classNameLower}Dao.findById(id);
		} catch (EmptyResultDataAccessException e)
		{
			return defaultVal;
		}
	}
	
	@Override
	public List<${className}> find(Map<String, Object> input)
	{
		return this.${classNameLower}Dao.find(_Dynam, input);
	}

	@Override
	public List<${className}> find(${className}Dyna ${classNameLower}Dyna)
	{
		return this.${classNameLower}Dao.find(${classNameLower}Dyna);
	}

	@Override
	public List<${className}> find(int pageNum, int pageSize, ${className}Dyna ${classNameLower}Dyna)
	{
		// default pageNum equal 1
		if (pageNum < 1)
		{
			pageNum = 1;
		}

		// default pageSize equal 1
		if (pageSize < 1)
		{
			pageSize = 1;
		}

		if (ObjectUtil.isNull(${classNameLower}Dyna))
		{
			${classNameLower}Dyna = new ${className}Dyna();
		}

		return this.${classNameLower}Dao.find(pageNum - 1, pageSize, ${classNameLower}Dyna);
	}
}
