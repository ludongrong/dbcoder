<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("${classNameLower}Svc")
@Transactional
public class ${className}Svc
{
	private static Logger logger = Logger.getLogger(${className}Svc.class);
	
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;
	
	public int save(${className} ${classNameLower})
	{
		if (xx.isEmpty(${classNameLower}))
		{
			return 0;
		}

		${classNameLower}.setId(UuidUtil.get32UUID());

		Timestamp startTimestamp = new Timestamp(System.currentTimeMillis());
		${classNameLower}.setCreateTime(startTimestamp);
		${classNameLower}.setModifyTime(startTimestamp);

		try
		{
			daoSupport.save("${className}Mapper.save", ${classNameLower});
		} catch (Exception e)
		{
			logger.error(e.getMessage());
			return -1;
		}

		return 1;
	}

	public int delete(String id)
	{
		if (StringUtils.isEmpty(id))
		{
			return 0;
		}

		try
		{
			daoSupport.delete("${className}Mapper.delete", id);
		} catch (Exception e)
		{
			logger.error(e.getMessage());
			return -1;
		}

		return 1;
	}

	public int update(${className} ${classNameLower})
	{
		if (xx.isEmpty(${classNameLower}))
		{
			return 0;
		}

		if (StringUtils.isEmpty(${classNameLower}.getId()))
		{
			return 0;
		}

		${classNameLower}.setModifyTime(new Timestamp(System.currentTimeMillis()));

		try
		{
			daoSupport.update("${className}Mapper.update", ${classNameLower});
		} catch (Exception e)
		{
			logger.error(e.getMessage());
			return -1;
		}

		return 1;
	}

	public ${className} findById(String id)
	{
		if (StringUtils.isEmpty(id))
		{
			return null;
		}

		try
		{
			return (${className}) daoSupport.findForObject("${className}Mapper.findById", id);
		} catch (Exception e)
		{
			logger.error(e.getMessage());
			throw new DaoException(MessageCodes.E_SYSTEM_ERROR);
		}
	}
}
