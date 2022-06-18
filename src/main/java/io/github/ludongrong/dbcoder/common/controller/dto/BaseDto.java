package io.github.ludongrong.dbcoder.common.controller.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDto implements Serializable {

    static final private long serialVersionUID = 9040648716323037363L;

    @Getter
    @Setter
    private String message;

    public BaseDto() {
        super();
    }

    public BaseDto(String message) {
        super();
        this.message = message;
    }

}
