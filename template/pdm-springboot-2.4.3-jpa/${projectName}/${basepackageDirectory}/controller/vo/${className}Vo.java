package ${basepackage}.controller.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onemysoft.common.validation.BatchDeleteGroup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "${className}Vo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ${className}Vo implements Serializable {

	<#list columns as column>
	<#if column.name != "ID" && column.name != "CREATE_TIME" && column.name != "MODIFY_TIME">
	@ApiModelProperty(value = "${column.javaNameVariable}", required = true, notes = "${column.javaNameVariable}")
	<#else>
	@ApiModelProperty(value = "${column.javaNameVariable}", required = false, notes = "${column.javaNameVariable}")
	</#if>
    @Getter
    @Setter
    private <#if column.javaType == "Timestamp">Date<#else>${column.javaType}</#if> ${column.javaNameVariable};
	
    </#list>
    @ApiModelProperty(value = "ids[]", required = false, notes = "ids[]")
    @NotNull(groups = {BatchDeleteGroup.class}, message = "主键不能为空")
    @Valid
    @Size(min = 1, message = "主键个数不能小于 1")
    @Getter
    @Setter
    private <#noparse>List<@Length(min = 36, max = 36, message = "主键字符个数为 36 位") String></#noparse> idList;
    
}