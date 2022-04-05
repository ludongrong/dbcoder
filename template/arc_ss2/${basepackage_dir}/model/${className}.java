<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model;

import java.io.Serializable;
<#list table.notPFkColumns as column>
<#if column.javaType == "Timestamp">
import java.sql.Timestamp;
<#elseif column.javaType == "Date">
import java.util.Date;
</#if>
</#list>

/**
 * 
 * ${className} 实体层
 * 
 * @author 卢冬榕
 * @email 736779458@qq.com
 *
 */
public class ${className} implements Serializable 
{
	<#list table.notPFkColumns as column>
	/**
	 * ${column.columnAlias}
	 */
	private ${column.javaType} ${column.columnNameLower};
	
	</#list>
	
	public ${className}(){
		super();
	}
}