<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ${basepackage}.entity.${className};
import ${basepackage}.service.dao.I${className}Dao;

@Transactional
public class ${className}SvcImpl implements I${className}Svc
{
	private I${className}Dao ${classNameLower}Dao;

	public void set${className}Dao(I${className}Dao ${classNameLower}Dao)
	{
		this.${classNameLower}Dao = ${classNameLower}Dao;
	}

	@Override
	public int save(${className} ${classNameLower})
	{
		return this.${classNameLower}Dao.save(<#list table.columns as column>${classNameLower}.get${column.columnName}()<#if column_has_next>,</#if></#list>);
	}
	
	@Override
	public int[] save(List<${className}> ${classNameLower}s)
	{
		return this.${classNameLower}Dao.save(${classNameLower}s);
	}

	@Override
	public int destroy(String pk)
	{
		return this.${classNameLower}Dao.destroy(pk);
	}

	@Override
	public int update(${className} ${classNameLower})
	{
		return this.${classNameLower}Dao.update(<#list table.columns as column>${classNameLower}.get${column.columnName}()<#if column_has_next>,</#if></#list>);
	}
	
	@Override
	public int[] update(List<${className}> ${classNameLower}s)
	{
		return this.${classNameLower}Dao.update(${classNameLower}s);
	}

	@Override
	public ${className} detail(String pk)
	{
		return this.${classNameLower}Dao.detail(pk);
	}

	@Override
	public List<${className}> find(int start, int end)
	{
		return this.${classNameLower}Dao.find(start, end);
	}

	@Override
	public int count()
	{
		return this.${classNameLower}Dao.count();
	}
}
