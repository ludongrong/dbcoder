package ${basePackage}.${projectName}.controller.header;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ${Name} 视图实体实体字段说明
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@ApiModel(value = "${className}Header", description = "${Name}")
@NoArgsConstructor
public class ${className}Header implements Serializable {

<#list Columns as column>
	@JsonProperty(value = "${column.CodeUpper}")
	private final String ${column.CodeCamelFirstLower} = "${column.Name}";
	
</#list>	
    private static final ${className}Header ${CodeUpper} = new ${className}Header();

    public static final ${className}Header getInstance() {
        return ${CodeUpper};
    }
    
}
