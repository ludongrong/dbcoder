package ${basePackage}.entity.header;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
* ${className}Header
*
* @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
* <#noparse>@since ${currentDate:date('yyyy-MM-dd')}</#noparse>
*/
@ApiModel(value = "${className}Header", description = "${tableName}")
public class ${className}Header {

<#list columns as column>
	@JsonProperty(value = "${column.name}")
    @Getter
    @Setter
	private final ${column.javaType} ${column.javaNameVariable} = "${column.name}";
	
</#list>	
    private static final ${className}Header ${tableName?upper_case} = new ${className}Header();

    public ${className}Header() {
		super();
	}

    public static ${className}Header getInstance() {
        return ${tableName?upper_case};
    }
}
