package ${basePackage}.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ${className}Dto extends BaseDto {

    static final private long serialVersionUID = 9040648716323037363L;

    @Getter
    @Setter
    private List<${className}Vo> data;
    
    @Getter
    @Setter
    private Object id;
    
    public ${className}Dto() {
        super();
    }

    public ${className}Dto(Object id) {
        super();
        this.id = id;
    }

    public ${className}Dto(List<${className}Vo> data) {
        super();
        this.data = data;
    }
}
