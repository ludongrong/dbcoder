package ${basePackage}.${projectName}.entity;

import java.io.Serializable;

import com.alibaba.excel.annotation.format.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * ${Name} 实体
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@ApiModel(value = "${className}Entity", description = "${Name}")
public class ${className}Entity implements Serializable {
	
<#list Columns as column>
    @ExcelProperty("${column.Name}")
<#if column.JavaType == "java.sql.Date" || column.JavaType == "java.sql.Timestamp" || column.JavaType == "java.sql.Time">
	@DateTimeFormat("yyyy-MM-dd HH:mm:ss")
</#if>
	@JsonProperty(value = "${column.CodeUnderlineUpper}")
<#if column.JavaType == "java.sql.Date" || column.JavaType == "java.sql.Timestamp">
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
<#elseif column.JavaType == "java.sql.Time">
	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
</#if>
	@ApiModelProperty(value = "${column.Name}", name = "${column.CodeUnderlineUpper}")
    @Getter
    @Setter
	private ${column.JavaType} ${column.CodeCamelFirstLower};
	
</#list>
	public ${className}Entity() {
		super();
	}
	
}