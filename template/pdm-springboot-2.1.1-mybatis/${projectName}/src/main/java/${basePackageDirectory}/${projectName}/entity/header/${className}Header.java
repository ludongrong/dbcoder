package ${basePackage}.${projectName}.entity.header;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * ${Name} 实体字段说明
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@ApiModel(value = "${className}Header", description = "${Name}")
public class ${className}Header implements Serializable {

<#list Columns as column>
	@JsonProperty(value = "${column.CodeUnderlineUpper}")
	private final String ${column.CodeCamelFirstLower} = "${column.Name}";
	
</#list>	
    private static final ${className}Header ${CodeUnderlineUpper} = new ${className}Header();

    public ${className}Header() {
		super();
	}

    public static ${className}Header getInstance() {
        return ${CodeUnderlineUpper};
    }
}
