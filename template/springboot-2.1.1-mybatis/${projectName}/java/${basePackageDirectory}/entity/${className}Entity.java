package ${basePackage}.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
* ${className}Entity
*
* @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
* <#noparse>@since ${currentDate:date('yyyy-MM-dd')}</#noparse>
*/
@ApiModel(value = "${className}Entity", description = "${tableName}")
public class ${className}Entity implements Serializable {
	
<#list columns as column>
	@JsonProperty(value = "${column.name}")
	@ApiModelProperty(value = "${column.name}", name = "${column.name}")
    @Getter
    @Setter
	private ${column.javaType} ${column.javaNameVariable};
	
</#list>
	public ${className}Entity() {
		super();
	}
	
}