package io.github.ludongrong.dbcoder.controller.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public @JsonIgnoreProperties(ignoreUnknown = true) class IndexResponseDto implements Serializable {

    private static final long serialVersionUID = 4723021637236394970L;

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}