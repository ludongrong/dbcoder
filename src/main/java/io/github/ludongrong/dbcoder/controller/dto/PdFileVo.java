package io.github.ludongrong.dbcoder.controller.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "PdFileVo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PdFileVo implements Serializable {

    private static final long serialVersionUID = 308306308587343266L;

    @ApiModelProperty(value = "id", required = true, notes = "id")
    @Getter
    @Setter
    private String id;

    @ApiModelProperty(value = "createTime", required = true, notes = "createTime")
    @Getter
    @Setter
    private Timestamp createTime;

    @ApiModelProperty(value = "basePackage", required = true, notes = "basePackage")
    @Getter
    @Setter
    private String basePackage;

    @ApiModelProperty(value = "projectName", required = true, notes = "projectName")
    @Getter
    @Setter
    private String projectName;

    @ApiModelProperty(value = "dbType", required = true, notes = "dbType")
    @Getter
    @Setter
    private String dbType;

    @ApiModelProperty(value = "name", required = true, notes = "name")
    @Getter
    @Setter
    private String name;
}