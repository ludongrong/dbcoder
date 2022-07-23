package io.github.ludongrong.dbcoder.excel.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.ludongrong.dbcoder.common.controller.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcelFileDto extends BaseDto {

    static final private long serialVersionUID = 9040648716323037363L;

    @Getter
    @Setter
    private List<ExcelFileVo> data;
    
    @Getter
    @Setter
    private Object id;

}
