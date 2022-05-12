package ${basePackage}.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 * ${tableNameCN} 集中参数引擎扩展表
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Data
@ApiModel(value = "${className}", description = "${tableNameCN}")
@ToString(exclude = {})
public class ${className} implements Serializable {
	
<#list columns as column>
	@ApiModelProperty(value = "${column.nameCN}", name = "${column.javaNameVariable}")
	private ${column.javaType} ${column.javaNameVariable};
	
</#list>

	public ${className}() {
		super();
	}

<#list columns as column>
	public static String PK_${column.name?upper_case} = "${column.name?upper_case}";
</#list>

	public static Map<String, Object> toMap(${className} ${classNameVariable}) {
		Map<String, Object> resutl = new HashMap<>();
	<#list columns as column>
		resutl.put(PK_${column.name?upper_case}, ${classNameVariable}.get${column.javaName}());
	</#list>
		return resutl;
	}

	public static Map<String, Object> toBean(Map<String, Object> paramMap) {
		${className} ${classNameVariable} = new ${className};
<#list columns as column>
	<#if column.javaType == "java.sql.Date" || column.javaType == "java.sql.Timestamp" || column.javaType == "java.sql.Time">
		${classNameVariable}.set${column.javaName}(getDate(PK_${column.name?upper_case}, paramMap));
	<#elseif column.javaType == "java.lang.Integer">
		${classNameVariable}.set${column.javaName}(getInteger(PK_${column.name?upper_case}, paramMap));
	<#else>
		${classNameVariable}.set${column.javaName}(getString(PK_${column.name?upper_case}, paramMap));
	</#if>
</#list>
		return ${classNameVariable};
	}
	
}