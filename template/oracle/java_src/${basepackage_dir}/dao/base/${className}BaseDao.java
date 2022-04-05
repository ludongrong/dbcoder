<#include "/copyright_include/class.header"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.dao;

import java.util.List;

import javax.swing.ListModel;

import com.opencode.tea.backend.dao.OPERATE_RESULT;
import com.opencode.tea.backend.exception.ExecuteErrorException;
import com.opencode.tea.backend.exception.PageIndexException;
import com.opencode.tea.backend.exception.ParamErrorException;
import com.opencode.tea.backend.module.user.UserRole;

public interface ${className}BaseDao 
{
	${className} create(${className} ${classNameLower}) throws ExecuteErrorException;
	
	OPERATE_RESULT destroy(String pk) throws ExecuteErrorException;
	
	OPERATE_RESULT update(${className} ${classNameLower}) throws ExecuteErrorException;
	
	${className} detail(String pk) throws ExecuteErrorException;
	
	ListModel<${className}> find(${className} ${classNameLower})throws PageIndexException, ExecuteErrorException;
	
	int count(${className} ${classNameLower}) throws ExecuteErrorException;
	
	List<${className}> creates(List<${className}> ${classNameLower}s) 
			throws ParamErrorException, ExecuteErrorException;
	
	OPERATE_RESULT destroys(List<String> pks) 
			throws ParamErrorException, ExecuteErrorException;
	
	OPERATE_RESULT updates(List<${className}> ${classNameLower}s) 
			throws ParamErrorException, ExecuteErrorException;
}
