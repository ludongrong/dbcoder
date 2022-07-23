package io.github.ludongrong.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文件下载 实体
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-26
 */
@Data
@NoArgsConstructor
public class DownloadBo implements Serializable {

    private static final long serialVersionUID = -8296808804648326778L;

    @JsonProperty(value = "FILE_CODE")
    private Integer fileCode;

    @JsonProperty(value = "FILE_MESSAGE")
    private String fileMessage;

    @JsonProperty(value = "FILE_NAME")
    private String fileName;

    public DownloadBo(Integer fileCode, String fileMessage) {
        this.fileCode = fileCode;
        this.fileMessage = fileMessage;
    }

}
