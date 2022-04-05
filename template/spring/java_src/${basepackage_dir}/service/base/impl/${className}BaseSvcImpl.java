<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

<#include "/copyright_include/service.header"/>
package ${basepackage}.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.opencode.tea.backend.dao.OPERATE_RESULT;
import com.opencode.tea.backend.exception.ExecuteErrorException;
import com.opencode.tea.backend.exception.OperatorErrorException;
import com.opencode.tea.backend.exception.ParamErrorException;
import com.opencode.tea.backend.module.ListModel;

@Service
@Transactional
public class ${className}BaseSvcImpl implements ${className}BaseSvc {
    
	private ${className}Dao ${classNameLower}Dao;
	
	public void set${className}Dao(${className}Dao dao) {
		this.${classNameLower}Dao = dao;
	}
	
	public ${className}Dao get${className}Dao() {
		return this.${classNameLower}Dao;
	}

	@Override
	public ${className} create(${className} ${classNameLower}) throws OperatorErrorException
	{
		try
		{
			return this.get${className}Dao().create(${classNameLower});
		} catch (ExecuteErrorException e)
		{
			throw new OperatorErrorException();
		}
    }
    
    @Override
    public OPERATE_RESULT destroy(String pk) throws OperatorErrorException
    {
    	try
		{
    		return this.get${className}Dao().destroy(pk);
		} catch (ExecuteErrorException e)
		{
			throw new OperatorErrorException();
		}
	}
	
    @Override
    public OPERATE_RESULT update(${className} ${classNameLower}) throws OperatorErrorException
    {
    	try
		{
    		return this.get${className}Dao().update(${classNameLower});
		} catch (ExecuteErrorException e)
		{
			throw new OperatorErrorException();
		}
	}
	
	@Transactional(readOnly=true)
	@Override
	public ${className} detail(String pk) throws OperatorErrorException
	{
		try
		{
			return this.get${className}Dao().detail(pk);
		} catch (ExecuteErrorException e)
		{
			throw new OperatorErrorException();
		}
	}
	
	@Transactional(readOnly=true)
	@Override
	public ListModel<${className}> find(${className} ${classNameLower})throws PageIndexException, OperatorErrorException
	{
		int total = 0;
		List<${className}> ${classNameLower}s = null;

		try
		{
			${classNameLower}s = this.get${className}Dao().find(${classNameLower});

			total = this.get${className}Dao().count(${classNameLower});
		} catch (ExecuteErrorException e)
		{
			throw new OperatorErrorException();
		}

		// 逻辑语句异常
		if (${classNameLower}s == null || ${classNameLower}s.size() == 0)
		{
			return null;
		} else if (total < ${classNameLower}s.size())
		{
			throw new OperatorErrorException();
		}

		return new ListModel<${className}>(${classNameLower}s);
	}
	
	@Transactional(readOnly=true)
	@Override
	public int count(${className} ${classNameLower}) throws OperatorErrorException
	{
		try
		{
			return this.get${className}Dao().count(${classNameLower});
		} catch (ExecuteErrorException e)
		{
			throw new OperatorErrorException();
		}
	}
	
	@Override
	public List<${className}> creates(List<${className}> ${classNameLower}s) throws OperatorErrorException
	{
		if (${classNameLower}s == null)
		{
			return null;
		}

		try
		{
			return this.get${className}Dao().creates(${classNameLower}s);
		} catch (ParamErrorException e)
		{
			throw new OperatorErrorException();
		} catch (ExecuteErrorException e)
		{
			throw new OperatorErrorException();
		}
    }
    
    @Override
    public OPERATE_RESULT destroys(List<String> pks) throws OperatorErrorException
    {
    	if (pks == null)
		{
			return OPERATE_RESULT.FAIL;
		}

		try
		{
			return this.get${className}Dao().destroys(pks);
		} catch (ParamErrorException e)
		{
			throw new OperatorErrorException();
		} catch (ExecuteErrorException e)
		{
			throw new OperatorErrorException();
		}
	}
    
    @Override
    public OPERATE_RESULT updates(List<${className}> ${classNameLower}s) throws OperatorErrorException
    {
    	if (${classNameLower}s == null)
		{
			return OPERATE_RESULT.FAIL;
		}

		try
		{
			return this.get${className}Dao().updates(${classNameLower}s);
		} catch (ParamErrorException e)
		{
			throw new OperatorErrorException();
		} catch (ExecuteErrorException e)
		{
			throw new OperatorErrorException();
		}
	}
    
    @Override
    public boolean isExist(${className} ${classNameLower})
    {
//		int count =  this.${classNameLower}Dao.count(model.getId());
//		if(count > 0){
//			return true;
//		}
		
		return false;
	}
	
}
