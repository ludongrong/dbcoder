package io.github.ludongrong.dbcoder.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import io.github.ludongrong.dbcoder.common.config.ApplicationConfiguration;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

public class ControllerUtil {

    public static void checkUploadFileSize(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File must have a non-nulls!");
        }

        if (file.getSize() > ApplicationConfiguration._UPLOAD_MAX_SIZE) {
            throw new IllegalStateException("The file size exceeds max upload size("
                    + ApplicationConfiguration._UPLOAD_MAX_SIZE / 1024 / 1024 + "M)!");
        }
    }

    public static void checkFileSuffix(String fileType, List<String> fileSuffice) {
        boolean match = fileSuffice.stream().anyMatch(v -> {
            return fileType.equalsIgnoreCase(v);
        });

        if (match == false) {
            throw new IllegalStateException("The current file upload type is not support!");
        }
    }

    public static void responseStream(HttpServletResponse response, ByteArrayInputStream inBuf, String filePrefix) {
        response.setContentType("application/x-msdownload");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + filePrefix + "-" + DateUtil.formatDate(new Date()) + ".zip\"");
        response.setHeader("Content-Length", Integer.toString(inBuf.available()));

        try (OutputStream oStream = response.getOutputStream()) {
            IoUtil.copy(inBuf, oStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
