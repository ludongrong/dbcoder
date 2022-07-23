package io.github.ludongrong.dbcoder.excel.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@ApiModel(description = "ExcelFileVo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcelFileVo implements Serializable {

    private static final long serialVersionUID = 308306308587343266L;

    /*标识*/
    @ApiModelProperty(value = "id", required = true, notes = "id")
    @Getter
    @Setter
    private String id;

    /*创建时间*/
    @ApiModelProperty(value = "createTime", required = true, notes = "createTime")
    @Getter
    @Setter
    private Timestamp createTime;

    /*工程目录*/
    @ApiModelProperty(value = "basePackage", required = true, notes = "basePackage")
    @Getter
    @Setter
    private String basePackage;

    /*模块名*/
    @ApiModelProperty(value = "projectName", required = true, notes = "projectName")
    @Getter
    @Setter
    private String projectName;

    /*应用类型*/
    @Deprecated
    @ApiModelProperty(value = "appType", required = true, notes = "appType", allowableValues = "web, app")
    @Getter
    @Setter
    private String appType;

    /*模板名称*/
    @ApiModelProperty(value = "name", required = true, notes = "name")
    @Getter
    @Setter
    private String name;

}