<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model;

import java.io.Serializable;

/**
 * 
 * ${table.className}Column
 * 
 * @version 1.0
 * 
 * @author 卢冬榕
 * @email 736779458@qq.com
 *
 */
@ApiModel(value = "${table.className}Column", description = "${table.className}Column")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ${table.className}Column implements Serializable 
{
<#list table.notPFkColumns as column>
	@ApiModelProperty(value = "${column.columnAlias}")
	public static String ${column.columnNameLower} = "${column.columnNameLower}";

	@ApiModelProperty(value = "${column.columnAlias}")
	public static String ${column.columnNameLower}Sql = "${column.sqlName}";
	
</#list>
	
	public ${table.className}Column()
	{
		super();
	}
}