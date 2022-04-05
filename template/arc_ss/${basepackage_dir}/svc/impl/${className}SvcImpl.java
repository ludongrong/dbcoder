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

@Service("${projectName}${className}Svc")
public class ${className}SvcImpl implements I${className}Svc
{
	private static final DynamicCondition _Dynam = new DynamicCondition(ConditionGroup.AND) {{
		orderByCreated("CREATE_TIME", "createTime");
	}};
	
	private Logger logger = LoggerFactory.getLogger(${className}SvcImpl.class);
	
	@Resource(name = "${projectName}${className}Dao")
	private I${className}Dao ${classNameLower}Dao;
	
	@Override
	public ${className}Bo create(${className}Bo ${classNameLower})
	{
		if (ObjectUtil.isNull(${classNameLower})) {
			throw new CodeException("101", "${className} must no empty");
		}
		
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		${classNameLower}.setCreateTime(createTime);
		${classNameLower}.setModifyTime(createTime);
		
		if (StrUtil.isEmpty(${classNameLower}.getId())) {
			${classNameLower}.setId(IdUtil.stringUUID32());
		}

		int result = this.${classNameLower}Dao.create(${classNameLower});
		if (result == 0) {
			throw new CodeException("0101", "Create size zero, result:" + result);
		}
		
		return ${classNameLower};
	}
	
	@Override
	public void delete(String id)
	{
		${className}Bo ${classNameLower} = this.${classNameLower}Dao.findById(id);
		if (ObjectUtil.isNull(${classNameLower})) {
			return;
		}
		
		int result = this.${classNameLower}Dao.delete(id);
		logger.debug("id={}; result={}", id, result);
		if (result == 0) {
			throw new CodeException("0101", "Delete size zero, result:" + result);
		}
	}
	
	@Override
	public void update(${className}Bo ${classNameLower})
	{	
		if (ObjectUtil.isNull(${classNameLower})) {
			throw new CodeException("101", "${classNameLower} must no empty");
		} else if (StrUtil.isEmpty(${classNameLower}.getId())) {
			throw new CodeException("102", "${classNameLower} id must no empty");
		}
		
		${className}Bo ${classNameLower}DB = findById(${classNameLower}.getId(), null);
		if (ObjectUtil.isNull(${classNameLower}DB)) {
			throw new CodeException("402", "Please enter the correct ${classNameLower} id");
		}
		
		Timestamp time = new Timestamp(System.currentTimeMillis());
		${classNameLower}.setModifyTime(time);

		int result = ${classNameLower}Dao.update(${classNameLower}, 1);
		if (result == 0) {
			throw new CodeException("0101", "Update size zero, result:" + result);
		}
	}
	
	@Override
	public int update(OrderTestBo ${classNameLower}, DynamicCondition dyc, Map<String, Object> param)
	{
		if (ObjectUtil.isNull(${classNameLower})) {
			throw new CodeException("101", "${classNameLower} must no empty");
		}
		return ${classNameLower}Dao.update(${classNameLower}, dyc, param);
	}
	
	@Override
	public long count(Map<String, Object> param)
	{
		return this.${classNameLower}Dao.count(_Dynam, param);
	}
	
	@Override
	public long count(${className}Dyna ${classNameLower}Dyna)
	{
		return this.${classNameLower}Dao.count(${classNameLower}Dyna);
	}

	@Override
	public ${className}Bo findById(String id)
	{
		if (StrUtil.isEmpty(id)) {
			throw new CodeException("101", "id must no empty");
		}
		return this.${classNameLower}Dao.findById(id);
	}

	@Override
	public ${className}Bo findById(String id, ${className}Bo defaultVal)
	{
		try {
			return this.${classNameLower}Dao.findById(id);
		} catch (EmptyResultDataAccessException e) {
			return defaultVal;
		} catch (CodeException e) {
			return defaultVal;
		}
	}
	
	@Override
	public List<${className}Bo> find(Map<String, Object> param)
	{
		if (MapUtil.isEmpty(param)) {
			return new ArrayList<${className}Bo>();
		}
		
		return this.${classNameLower}Dao.find(_Dynam, param);
	}

	@Override
	public List<${className}Bo> find(${className}Dyna ${classNameLower}Dyna)
	{
		if (ObjectUtil.isNull(${classNameLower}Dyna)) {
			return new ArrayList<${className}Bo>();
		}
		
		return this.${classNameLower}Dao.find(${classNameLower}Dyna);
	}

	@Override
	public List<${className}Bo> find(int pageNum, int pageSize, ${className}Dyna ${classNameLower}Dyna)
	{
		if (ObjectUtil.isNull(${classNameLower}Dyna)) {
			return new ArrayList<${className}Bo>();
		}
		
		if (pageNum < 1) {
			pageNum = 1;
		}
		
		if (pageSize < 1) {
			pageSize = 1;
		}

		if (ObjectUtil.isNull(${classNameLower}Dyna)) {
			${classNameLower}Dyna = new ${className}Dyna();
		}

		int sp = (pageNum - 1) * pageSize;
		return this.${classNameLower}Dao.find(sp, pageSize, ${classNameLower}Dyna);
	}
	
	@Override
	public boolean isAllowUpdate(${className}Bo ${classNameLower}, boolean safely)
	{
		if (ObjectUtil.isNull(${classNameLower})) {
			if (safely == false) {
				throw new CodeException("101", "${className} must no empty");
			} else {
				return false;
			}
		} else if (StrUtil.isEmpty(${classNameLower}.getId())) {
			if (safely == false) {
				throw new CodeException("102", "${classNameLower} id must no empty");	
			} else {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public ${className}Bo onGet(String ${classNameLower}Id)
	{
		if (StrUtil.isEmpty(${classNameLower}Id)) {
			throw new CodeException("106", "Missing ${classNameLower}Id");
		}
		
		${className}Bo ${classNameLower}DB = findById(${classNameLower}Id, null);
		if (ObjectUtil.isNull(${classNameLower}DB)) {
			throw new CodeException("106", "Missing ${classNameLower}DB");
		}
		
		return ${classNameLower}DB;
	}
	
	@Override
	public ${className}Bo onGet(${className}Bo ${classNameLower})
	{
		if (ObjectUtil.isNull(${classNameLower})) {
			throw new CodeException("101", "${classNameLower} must no empty");
		}
		
		${className}Bo ${classNameLower}DB = onGet(${classNameLower}.getId());
		
		return ${classNameLower}DB;
	}
	
	private ${className}Bo onBuzCreate(${className}Bo ${classNameLower}) 
	{
		if (ObjectUtil.isNull(${classNameLower})) {
			throw new CodeException(CodeException.PARAM_MISSING, "${classNameLower} must no empty");
		}
		
		return ${classNameLower};
	}

	@Transactional
	@Override
	public ${className}Bo buzCreate(${className}Bo ${classNameLower})
	{
		${classNameLower} = onBuzCreate(${classNameLower});
		return create(${classNameLower});
	}
}
