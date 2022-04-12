package ${basePackage}.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 * ${tableNameCN} 实体
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Data
@ApiModel(value = "${className}", description = "${tableNameCN}")
@ToString(exclude = {})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ${className} implements Serializable {
	
<#list columns as column>
<#if column.javaType == "java.sql.Date" || column.javaType == "java.sql.Timestamp">
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
<#elseif column.javaType == "java.sql.Time">
	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
</#if>
	@ApiModelProperty(value = "${column.nameCN}", name = "${column.javaNameVariable}")
	private ${column.javaType} ${column.javaNameVariable};
	
</#list>
<#list parentReferences as reference>
	@ApiModelProperty(value = "${reference.tableNameCN}", name = "${reference.classNameVariable}")
	private ${reference.className} ${reference.classNameVariable};

</#list>
<#list childReferences as reference>
	@ApiModelProperty(value = "${reference.tableNameCN}", name = "${reference.classNameVariable}List")
	private List<${reference.className}> ${reference.classNameVariable}List;

</#list>
	public ${className}() {
		super();
	}
	
}