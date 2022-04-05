<#include "/copyright_include/class.header"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import java.util.List;

import com.opencode.tea.backend.exception.OperatorErrorException;

public interface ${className}BaseSvc 
{
	${className} create(${className} ${classNameLower}) throws OperatorErrorException;
	
	OPERATE_RESULT destroy(String pk) throws OperatorErrorException;
	
	OPERATE_RESULT update(${className} ${classNameLower}) throws OperatorErrorException;
	
	${className} detail(String pk) throws OperatorErrorException;
	
	ListModel<${className}> find(${className} ${classNameLower})throws PageIndexException, OperatorErrorException;
	
	int count(${className} ${classNameLower}) throws OperatorErrorException;
	
	List<${className}> creates(List<${className}> ${classNameLower}s) throws OperatorErrorException;
	
	OPERATE_RESULT destroys(List<String> pks) throws OperatorErrorException;
	
	OPERATE_RESULT updates(List<${className}> ${classNameLower}s) throws OperatorErrorException;
	
	boolean isExist(${className} ${classNameLower});
}
