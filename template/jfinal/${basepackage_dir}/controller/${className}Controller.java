<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.activerecord.tx.TxConfig;
import com.oss.base.dto.BaseDto;
import com.oss.base.validator.IdValidator;

public class ${className}Controller extends Controller
{
	static private Log log = Log.getLog(${className}Controller.class);

	@TxConfig(value = "user_mg")
	@Before({ ${className}CreateVtor.class, Tx.class })
	public void create()
	{
		log.debug("create........start");
		${className} ${classNameLower} = ${className}Dto.parse(this);
		renderJson(BaseDto.buildResult(true, ${className}Svc.SVC.save(${classNameLower})));
	}

	@TxConfig(value = "user_mg")
	@Before({ IdValidator.class, Tx.class })
	public void delete()
	{
		log.debug("delete........start");
		String id = getPara("id");
		renderJson(BaseDto.buildSuccess(${className}Svc.SVC.delete(id)));
	}

	@TxConfig(value = "user_mg")
	@Before({ IdValidator.class, Tx.class })
	public void queryById()
	{
		log.debug("queryById........start");
		String id = getPara("id");
		renderJson(BaseDto.buildResult(true, ${className}.DAO.findByUid(id)));
	}

	@TxConfig(value = "user_mg")
	@Before({ IdValidator.class, ${className}UpdateVtor.class, Tx.class })
	public void update()
	{
		log.debug("update........start");
		${className} ${classNameLower} = ${className}Dto.parse(this);
		renderJson(BaseDto.buildResult(true, ${className}Svc.SVC.update(${classNameLower})));
	}
}
