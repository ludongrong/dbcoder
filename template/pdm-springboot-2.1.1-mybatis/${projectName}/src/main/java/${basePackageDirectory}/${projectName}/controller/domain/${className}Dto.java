package ${basePackage}.${projectName}.controller.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ${basePackage}.common.controller.domain.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

<#--
Dto 包含 Vo
-->
/**
 * ${Name} 数据传输对象
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "${CodeUpper}_DTO", description = "${Name}")
public class ${className}Dto extends BaseDto<${className}Dto, ${className}Vo> {
    
    private static final String PK_VO_HEADER = "VO_HEADER";

    @JsonProperty(value = PK_VO_HEADER)
    @ApiModelProperty(value = "${Name}-视图实体实体字段说明", name = PK_VO_HEADER)
    private ${className}VoHeader voHeader;

    private ${className}Dto() {
        super();
    }

    public static ${className}Dto of() {
        return new ${className}Dto();
    }
}
