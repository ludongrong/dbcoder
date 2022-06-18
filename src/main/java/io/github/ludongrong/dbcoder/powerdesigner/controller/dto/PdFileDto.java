package io.github.ludongrong.dbcoder.powerdesigner.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.github.ludongrong.dbcoder.common.controller.dto.BaseDto;
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

}
