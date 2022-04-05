<#include "/copyright_include/class.header"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model;

import java.util.List;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "account/${classNameLower}/")
public class ${className}Controller
{
	private final Log LOGGER = LogFactory.getLog(this.getClass());

	private final String PAGE_LIST = "/account/${classNameLower}/list";

	private final String PAGE_ADD = "/account/${classNameLower}/add";

	private final String PAGE_EDIT = "/account/${classNameLower}/add";

	@Resource(name = "tea.${classNameLower}Svc")
	private ${className}Svc ${classNameLower}Svc;

	@RequestMapping(value = "list")
	public String list(Model model)
	{
		model.addAttribute("pageName", "tea.account.${classNameLower}.list");
		model.addAttribute("readUrl", "account/${classNameLower}/list/getData.do");

		model.addAttribute("rootAddr", "http://127.0.0.1:8080/tea/");

		return this.PAGE_LIST;
	}

	@RequestMapping(value = "add")
	public String add(Model model)
	{
		model.addAttribute("pageName", "tea.account.${classNameLower}.add");
		model.addAttribute("readUrl", "account/${classNameLower}/add/getData.do");
		model.addAttribute("submitUrl", "account/${classNameLower}/create.do");

		model.addAttribute("rootAddr", "http://127.0.0.1:8080/tea/");

		return this.PAGE_ADD;
	}

	@RequestMapping(value = "edit")
	public String edit(Model model)
	{
		model.addAttribute("pageName", "tea.account.${classNameLower}.edit");
		model.addAttribute("readUrl", "account/${classNameLower}/edit/getData.do");
		model.addAttribute("submitUrl", "account/${classNameLower}/update.do");

		model.addAttribute("rootAddr", "http://127.0.0.1:8080/tea/");

		return this.PAGE_ADD;
	}

	@RequestMapping(value = "list/data")
	@ResponseBody
	public ${className}Dto listData(@RequestBody ${className}Dto ${classNameLower}Dto) throws ParamErrorException
	{
//		AppSystem appSystem = listDto.getAppSystem();
//
//		if (appSystem == null)
//		{
//			listDto.setOperate(false);
//			return listDto;
//		}
//
//		
//		try
//		{
//			ListModel<Account> accounts = this.accountSvc.getAdmins(appSystem.getId(),cAccount);
//			// 返回操作结果
//			listDto.setResults(accounts);
//			// 设置结果个数
//			cAccount.setTotal(accounts.getTotal());
//		} catch (PageIndexException e)
//		{
//			// 告诉前台操作失败
//			listDto.setOperate(false);
//		}
//
//		cAccount.setIsFirst(false);
//
//		return listDto;
		
		return null;
	}

	@RequestMapping(value = "add/data")
	@ResponseBody
	public ${className}Dto addData(@RequestBody ${className}Dto ${classNameLower}Dto)
	{
//		AppSystem appSystem = userDto.getAppSystem();
//
//		if (appSystem == null)
//		{
//			userDto.setOperate(false);
//			return userDto;
//		}

//		try
//		{
//			List<Org> orgs = this.orgSvc.findToAdmin(appSystem.getId());
//			
//			if (orgs == null)
//			{
//				userDto.setOperate(false);
//				return userDto;
//			} else
//			{
//				userDto.setOperate(true);
//				userDto.setOrgs(orgs);
//			}
//		} catch (Exception e)
//		{
//			userDto.setOperate(false);
//			return userDto;
//		}
//
//		return userDto;
		
		return null;
	}

	@RequestMapping(value = "edit/data")
	@ResponseBody
	public ${className}Dto editData(@RequestBody ${className}Dto ${classNameLower}Dto)
	{
		// 验证数据
//		Account account = userDto.getAccount();
//		AppSystem appSystem = userDto.getAppSystem();
//
//		if (account == null)
//		{
//			userDto.setOperate(false);
//			return userDto;
//		}
//
//		if (appSystem == null)
//		{
//			userDto.setOperate(false);
//			return userDto;
//		}
//
//		try
//		{
//			// 获取组织机构
//			List<Org> orgs = this.orgSvc.findToAdmin(appSystem.getId());
//
//			if (orgs == null)
//			{
//				userDto.setOperate(false);
//				return userDto;
//			} else
//			{
//				userDto.setOperate(true);
//				userDto.setOrgs(orgs);
//			}
//		} catch (Exception e)
//		{
//			userDto.setOperate(false);
//			return userDto;
//		}
//
//		try
//		{
//			// 获取账户信息
//			account = this.accountSvc.getDetailAdmin(account);
//
//			if (account == null)
//			{
//				userDto.setOperate(false);
//				return userDto;
//			} else
//			{
//				userDto.setOperate(true);
//				userDto.setAccount(account);
//			}
//		} catch (Exception e)
//		{
//			userDto.setOperate(false);
//			return userDto;
//		}
//
//		// 操作成功
//		userDto.setOperate(true);
//
//		// 返回信息
//		return userDto;
		
		return null;
	}

	@RequestMapping(value = "create")
	@ResponseBody
	public ${className}Dto create(SessionContext ctxt, @RequestBody ${className}Dto ${classNameLower}Dto)
	{
//		Account account = accountDto.getAccount();
//
//		try
//		{
//			this.accountSvc.createAdmin(account);
//		} catch (Exception e)
//		{
//			accountDto.setOperate(false);
//		}
//
//		return accountDto;
		return null;
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public ${className}Dto update(SessionContext ctxt, @RequestBody ${className}Dto ${classNameLower}Dto)
	{
//		Account account = userDto.getAccount();
//
//		if (account == null)
//		{
//			userDto.setOperate(false);
//			return userDto;
//		}
//
//		try
//		{
//			int state = this.accountSvc.updateAdmin(account);
//
//			if (state != 1)
//			{
//				userDto.setOperate(false);
//				return userDto;
//			} else
//			{
//				userDto.setOperate(true);
//			}
//		} catch (Exception e)
//		{
//			userDto.setOperate(false);
//			return userDto;
//		}
//
//		return userDto;
		return null;
	}
}