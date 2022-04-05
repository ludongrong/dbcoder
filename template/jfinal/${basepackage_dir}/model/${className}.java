<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.model;

import java.sql.Timestamp;
import java.util.List;

import com.eova.common.base.BaseModel;
import com.jfinal.plugin.activerecord.Db;
import com.oss.session.SessionConstant;

public class ${className} extends BaseModel<${className}>
{
	static public final String Config_Name = "user_mg";
	
	static public final ${className} DAO = new ${className}();

	static public ${className} build(String id)
	{
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		${className} ${classNameLower} = new ${className}();
		${classNameLower}.setId(id);
		${classNameLower}.setCreateTime(createTime);
		${classNameLower}.setModifyTime(createTime);
		return ${classNameLower};
	}

	static public ${className} build(<#list table.columns as column>${column.javaType} ${column.columnNameLower}<#if column_has_next>,</#if></#list>)
	{
		${className} ${classNameLower} = new ${className}();
	<#list table.notPFkColumns as column>
		${classNameLower}.set("${column.sqlName}", ${column.columnNameLower});
	</#list>
		return ${classNameLower};
	}
	
	public ${className}()
	{
		this(Config_Name);
	}

	public ${className}(String configName)
	{
		super();
		this.use(configName);
	}

	public int deleteByUid(String uid)
	{
		final String sql = "delete from ${table.sqlName} u where u.id = ?";
		return Db.use(SessionConstant.Config_Name_User_Mg).update(sql, uid);
	}

	public ${className} findByUid(String uid)
	{
		final String sql = "select * from ${table.sqlName} u where u.id = ?";
		return DAO.findFirst(sql, uid);
	}

<#list table.notPFkColumns as column>
	public ${column.javaType} get${column.columnName}()
	{
		return get("${column.sqlName}", null);
	}

	public ${className} remove${column.columnName}()
	{
		return remove("${column.sqlName}");
	}

	public ${className} set${column.columnName}(${column.javaType} ${column.columnNameLower})
	{
		return set("${column.sqlName}", ${column.columnNameLower});
	}
</#list>
}