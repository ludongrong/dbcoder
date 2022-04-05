package ${basePackage}.entity;

import java.io.Serializable;

import com.alibaba.excel.annotation.format.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * ${tableNameCN} 实体
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@ApiModel(value = "${className}Entity", description = "${tableNameCN}")
public class ${className}Entity implements Serializable {
	
<#list columns as column>
    @ExcelProperty("${column.nameCN}")
<#if column.javaType == "java.sql.Date" || column.javaType == "java.sql.Timestamp" || column.javaType == "java.sql.Time">
	@DateTimeFormat("yyyy-MM-dd HH:mm:ss")
</#if>
	@JsonProperty(value = "${column.name?upper_case}")
<#if column.javaType == "java.sql.Date" || column.javaType == "java.sql.Timestamp">
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
<#elseif column.javaType == "java.sql.Time">
	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
</#if>
	@ApiModelProperty(value = "${column.nameCN}", name = "${column.name?upper_case}")
    @Getter
    @Setter
	private ${column.javaType} ${column.javaNameVariable};
	
</#list>
	public ${className}Entity() {
		super();
	}
	
}