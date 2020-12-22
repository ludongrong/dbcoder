package io.github.ludongrong.dbcoder.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PdFileDto extends BaseDto {

    static final private long serialVersionUID = 9040648716323037363L;

    @Getter
    @Setter
    private List<PdFileVo> data;
    
    @Getter
    @Setter
    private Object id;

    public PdFileDto() {
        super();
    }

    public PdFileDto(Object id) {
        super();
        this.id = id;
    }

    public PdFileDto(List<PdFileVo> data) {
        super();
        this.data = data;
    }
}
