package ${basePackage}.${projectName}.controller.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * ${Name} 视图实体实体字段说明
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@ApiModel(value = "${CodeUpper}_VO_HEADER", description = "${Name}")
public class ${className}VoHeader implements Serializable {

<#list Columns as column>
    @JsonProperty(value = ${className}Vo.PK_${column.CodeUpper})
    private final String ${column.CodeCamelFirstLower} = "${column.Name}";

</#list>
    private ${className}VoHeader() {
        super();
    }
    
    private static final ${className}VoHeader ${CodeUpper}_VO_HEADER = new ${className}VoHeader();

    public static final ${className}VoHeader getInstance() {
        return ${CodeUpper}_VO_HEADER;
    }
}
