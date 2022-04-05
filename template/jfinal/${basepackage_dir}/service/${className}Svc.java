<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service;

import java.sql.Timestamp;

import com.util.Identifier;

import ${basepackage}.model.${className};

public class ${className}Svc
{
	static public final ${className}Svc SVC = new ${className}Svc();

	public boolean delete(String id)
	{
		${className} ${classNameLower} = ${className}.build(id);
		if (${classNameLower}.delete())
		{
			return true;
		}

		return false;
	}

	public ${className} save(${className} ${classNameLower})
	{
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		
		${classNameLower}.setId(Identifier.stringUUID());
		${classNameLower}.setCreateTime(createTime);
		${classNameLower}.setModifyTime(createTime);
		if (${classNameLower}.save())
		{
			return ${classNameLower};
		}
		return null;
	}

	public ${className} update(${className} ${classNameLower})
	{
		${classNameLower}.removeCreateTime();
		${classNameLower}.setModifyTime(new Timestamp(System.currentTimeMillis()));
		if (${classNameLower}.update())
		{
			return ${classNameLower};
		}

		return null;
	}
}
