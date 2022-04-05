<#include "/copyright_include/class.header"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.opencode.tea.backend.dao.OPERATE_RESULT;
import com.opencode.tea.backend.exception.ExecuteErrorException;
import com.opencode.tea.backend.exception.OperatorErrorException;
import com.opencode.tea.backend.exception.ParamErrorException;

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
			return this.${classNameLower}Dao.create(${classNameLower});
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
    		return this.${classNameLower}Dao.destroy(pk);
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
    		return this.${classNameLower}Dao.update(${classNameLower});
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
			return this.${classNameLower}Dao.detail(pk);
		} catch (ExecuteErrorException e)
		{
			throw new OperatorErrorException();
		}
	}
	
	@Transactional(readOnly=true)
	@Override
	public ListModel<${className}> find(${className} ${classNameLower})throws PageIndexException, OperatorErrorException
	{
		try
		{
			return this.${classNameLower}Dao.find(${classNameLower});
		} catch (ExecuteErrorException e)
		{
			throw new OperatorErrorException();
		}
	}
	
	@Transactional(readOnly=true)
	@Override
	public int count(${className} ${classNameLower}) throws OperatorErrorException
	{
		try
		{
			return this.${classNameLower}Dao.count(${classNameLower});
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
			return this.${classNameLower}Dao.creates(${classNameLower}s);
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
			return this.${classNameLower}Dao.destroys(pks);
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
			return this.${classNameLower}Dao.updates(${classNameLower}s);
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
