package ${basePackage}.entity;

import java.io.Serializable;

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
	@JsonProperty(value = "${column.name?upper_case}")
	@ApiModelProperty(value = "${column.nameCN}", name = "${column.name?upper_case}")
    @Getter
    @Setter
	private ${column.javaType} ${column.javaNameVariable};
	
</#list>
	public ${className}Entity() {
		super();
	}
	
}