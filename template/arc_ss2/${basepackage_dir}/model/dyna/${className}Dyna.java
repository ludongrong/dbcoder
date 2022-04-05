<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model.dyna;

<#list table.notPFkColumns as column>
<#if column.javaType == "Timestamp">
import java.sql.Timestamp;
<#elseif column.javaType == "Date">
import java.util.Date;
</#if>
</#list>

/**
 * 
 * ${className} 动态查询模板层
 * 
 * @author 卢冬榕
 * @email 736779458@qq.com
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ${className}Dyna implements Serializable
{
	<#list table.notPFkColumns as column>
	<#if column.columnName != "CreateTime"&&column.columnName != "ModifyTime">
	/**
	 * ${column.columnAlias}
	 */
	private ${column.javaType} ${column.columnNameLower}Eq;
	<#else>
	/**
	 * ${column.columnAlias}
	 */
	private List<${column.javaType}> ${column.columnNameLower}Between;
	</#if>
	</#list>
	
	public ${className}Dyna(){
		super();
	}
}