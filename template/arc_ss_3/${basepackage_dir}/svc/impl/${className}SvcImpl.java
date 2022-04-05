<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.svc.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ${basepackage}.model.${className}Bo;
import ${basepackage}.model.dyna.${className}Dyna;
import ${basepackage}.dao.I${className}Dao;
import ${basepackage}.svc.I${className}Svc;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.laocu.core.exception.CodeException;
import cn.laocu.core.util.IdUtil;
import cn.laocu.db.sql.ConditionGroup;
import cn.laocu.db.sql.DynamicCondition;

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
	private static final DynamicCondition _Dynam = new DynamicCondition(ConditionGroup.AND);
	static
	{
		_Dynam.orderByCreated("CREATE_TIME", "createTime");
	}
	
	private static final Logger logger = LoggerFactory.getLogger(${className}SvcImpl.class);
	
	/**
	 *Dao->${classNameLower}Dao
	 */
	private I${className}Dao ${classNameLower}Dao;
	
	public void set${className}Dao(I${className}Dao ${classNameLower}Dao)
	{
		this.${classNameLower}Dao = ${classNameLower}Dao;
	}
	
	@Override
	public ${className}Bo create(${className}Bo ${classNameLower})
	{
		if (ObjectUtil.isNull(${classNameLower}))
		{
			throw new CodeException("101", "${className} must no empty");
		}
		
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
	public ${className}Bo deleteByIdOfSafely(String id)
	{
		logger.debug(id);
		${className}Bo ${classNameLower} = this.${classNameLower}Dao.findById(id);
		if (ObjectUtil.isNotNull(${classNameLower}))
		{
			deleteById(id);
		}
		return ${classNameLower};
	}
	
	@Override
	public void update(${className}Bo ${classNameLower})
	{
		if (ObjectUtil.isNull(${classNameLower}))
		{
			throw new CodeException("101", "${className} must no empty");
		}else if (StrUtil.isEmpty(${classNameLower}.getId()))
		{
			throw new CodeException("102", "${classNameLower} id must no empty");
		}
		
		// no update data
		${className}Bo ${classNameLower}DB = findById(${classNameLower}.getId(), null);
		if (ObjectUtil.isNull(${classNameLower}DB))
		{
			throw new CodeException("402", "Please enter the correct ${classNameLower} id");
		}
				
		// update data
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
		if (StrUtil.isEmpty(id))
		{
			throw new CodeException("101", "id must no empty");
		}else if (MapUtil.isEmpty(input))
		{
			return;
		}
		
		Timestamp time = new Timestamp(System.currentTimeMillis());
		input.put("modifyTime", time);

		int result = this.${classNameLower}Dao.update(id, input);
		if (result != 1 && result != -2)
		{
			throw new CodeException("0101", "Update size zero, result:" + result);
		}
	}
	
	@Override
	public void update(DynamicCondition dynam, Map<String, Object> input, Map<String, Object> param)
	{
		if (MapUtil.isEmpty(input))
		{
			return;
		}

		Timestamp time = new Timestamp(System.currentTimeMillis());
		input.put("modifyTime", time);

		int result = this.${classNameLower}Dao.update(dynam, input, param);
		if (result != 1 && result != -2)
		{
			throw new CodeException("0101", "Update size zero, result:" + result);
		}
	}
	
	@Override
	public void update(Map<String, Object> input, Map<String, Object> param)
	{
		this.update(_Dynam, input, param);
	}
	
	@Override
	public long count(Map<String, Object> input)
	{
		return this.${classNameLower}Dao.count(_Dynam, input);
	}
	
	@Override
	public long count(${className}Dyna ${classNameLower}Dyna)
	{
		return this.${classNameLower}Dao.count(${classNameLower}Dyna);
	}

	@Override
	public ${className}Bo findById(String id)
	{
		if (StrUtil.isEmpty(id))
		{
			throw new CodeException("101", "id must no empty");
		}
		return this.${classNameLower}Dao.findById(id);
	}

	@Override
	public ${className}Bo findById(String id, ${className}Bo defaultVal)
	{
		try
		{
			return this.${classNameLower}Dao.findById(id);
		} catch (EmptyResultDataAccessException e)
		{
			return defaultVal;
		}catch (CodeException e)
		{
			return defaultVal;
		}
	}
	
	@Override
	public List<${className}Bo> find(Map<String, Object> input)
	{
		if (MapUtil.isEmpty(input))
		{
			return new ArrayList<${className}Bo>();
		}
		
		return this.${classNameLower}Dao.find(_Dynam, input);
	}

	@Override
	public List<${className}Bo> find(${className}Dyna ${classNameLower}Dyna)
	{
		if (ObjectUtil.isNull(${classNameLower}Dyna))
		{
			return new ArrayList<${className}Bo>();
		}
		
		return this.${classNameLower}Dao.find(${classNameLower}Dyna);
	}

	@Override
	public List<${className}Bo> find(int pageNum, int pageSize, ${className}Dyna ${classNameLower}Dyna)
	{
		if (ObjectUtil.isNull(${classNameLower}Dyna))
		{
			return new ArrayList<${className}Bo>();
		}
		
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

		int sp = (pageNum - 1) * pageSize;
		return this.${classNameLower}Dao.find(sp, pageSize, ${classNameLower}Dyna);
	}
}
