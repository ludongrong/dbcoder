package ${basepackage}.controller.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "${className}Vo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ${className}Vo implements Serializable {

<#list columns as column>
    @ApiModelProperty(value = "${column.javaNameVariable}", required = true, notes = "${column.javaNameVariable}")
    @Getter
    @Setter
    private ${column.javaType} ${column.javaNameVariable};
    
</#list>
}