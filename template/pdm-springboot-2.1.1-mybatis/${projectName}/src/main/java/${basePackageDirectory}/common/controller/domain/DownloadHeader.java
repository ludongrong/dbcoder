package io.github.ludongrong.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件下载 视图实体实体字段说明
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-26
 */
@Data
@NoArgsConstructor
public class DownloadHeader {

	@JsonProperty(value = "FILE_CODE")
	private final String fileCode = "文件状态";

	@JsonProperty(value = "FILE_MESSAGE")
	private final String fileMessage = "状态描述";

	@JsonProperty(value = "FILE_NAME")
	private final String fileName = "文件名称";

	private static final DownloadHeader DOWNLOAD_HEADER = new DownloadHeader();

	public static DownloadHeader getInstance() {
		return DOWNLOAD_HEADER;
	}

}
