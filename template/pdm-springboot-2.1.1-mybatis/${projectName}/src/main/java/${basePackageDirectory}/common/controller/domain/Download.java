package io.github.ludongrong.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文件下载 实体
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-26
 */
@ApiModel(value = "文件下载", description = "文件下载")
@Data
@NoArgsConstructor
public class Download implements Serializable {

    private static final long serialVersionUID = -8296808804648326778L;

    @JsonProperty(value = "FILE_CODE")
    @ApiModelProperty(value = "文件状态", name = "FILE_CODE")
    private Integer fileCode;

    @JsonProperty(value = "FILE_MESSAGE")
    @ApiModelProperty(value = "状态描述", name = "FILE_MESSAGE")
    private String fileMessage;

    @JsonProperty(value = "FILE_NAME")
    @ApiModelProperty(value = "文件名称", name = "FILE_NAME")
    private String fileName;

    public Download(Integer fileCode, String fileMessage) {
        this.fileCode = fileCode;
        this.fileMessage = fileMessage;
    }

}
