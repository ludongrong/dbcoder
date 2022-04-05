<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.opencode.tea.dao.BaseDao;
import com.opencode.tea.entity.User;

@Service("tea.${classNameLower}Svc")
@Transactional
public class ${className}SvcImpl extends BaseSvcImpl<${className},String> implements ${className}Svc 
{
	
	@Resource(name = "tea.${classNameLower}Dao")
	private ${className}Dao ${classNameLower}Dao;
	
	@Resource(name = "tea.${classNameLower}Dao")
	@Override
	public void setBaseDao(BaseDao<${className}, String> baseDao)
	{
		super.setBaseDao(baseDao);
	}
	
}
