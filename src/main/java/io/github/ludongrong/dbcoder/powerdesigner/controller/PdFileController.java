package io.github.ludongrong.dbcoder.powerdesigner.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import io.github.ludongrong.dbcoder.common.config.ApplicationConfiguration;
import io.github.ludongrong.dbcoder.powerdesigner.controller.dto.PdFileMapper;
import io.github.ludongrong.dbcoder.powerdesigner.controller.dto.PdFileVo;
import io.github.ludongrong.dbcoder.common.exception.BadGatewayException;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMReader;
import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.PDMProject;
import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.PDMReader;
import io.github.ludongrong.dbcoder.util.template.TemplateUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * xxx.
 *
 * @author <a href="mailto:736779458@qq.com">卢冬榕</a>
 * @since 2020-12-18
 */
@Controller
@RequestMapping("/pdfile")
public class PdFileController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/x-msdownload")
    public void create(PdFileVo pdFileVo, @RequestParam(value = "file") MultipartFile uploadFile, HttpServletResponse response) {

        String fileType = checkUploadFile(uploadFile);

        List<Map<String, Object>> modelList = null;
        switch (fileType) {
            case "oom": {
                modelList = parseOOM(pdFileVo, uploadFile);
                break;
            }
            default: {
                modelList = parsePDM(pdFileVo, uploadFile);
            }
        }

        String templatePath = ApplicationConfiguration._template_directory + File.separator + pdFileVo.getName();
        ByteArrayInputStream inBuf = TemplateUtil.generateTemplate(templatePath, modelList);
        responseStream(response, inBuf);
    }

    private List<Map<String, Object>> parsePDM(PdFileVo pdFileVo, MultipartFile file) {
        try {
            PDMProject project = PDMReader.read(PdFileMapper.INSTANCE.vo2bo(pdFileVo), file.getInputStream());
            return project.getModelList();
        } catch (Exception e) {
            throw new BadGatewayException(String.format("Can not parse upload %s file --- %s", "PDM", randomUUID()), e);
        }
    }

    private List<Map<String, Object>> parseOOM(PdFileVo pdFileVo, MultipartFile file) {
        try {
            OOMProject project = OOMReader.read(PdFileMapper.INSTANCE.vo2bo(pdFileVo), file.getInputStream());
            return project.getModelList();
        } catch (Exception e) {
            throw new BadGatewayException(String.format("Can not parse upload %s file --- %s", "OOM", randomUUID()), e);
        }
    }

    /**
     * 检查上传接口入参
     *
     * @param file
     * @return
     */
    private String checkUploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File must have a non-nulls!");
        }

        if (file.getSize() > ApplicationConfiguration._UPLOAD_MAX_SIZE) {
            throw new IllegalStateException("The file size exceeds max upload size("
                    + ApplicationConfiguration._UPLOAD_MAX_SIZE / 1024 / 1024 + "M)!");
        }

        String fileType = FileUtil.extName(file.getOriginalFilename());

        boolean match = Arrays.asList(ApplicationConfiguration._FILE_TYPE).stream().anyMatch(v -> {
            return fileType.equalsIgnoreCase(v);
        });

        if (match == false) {
            throw new IllegalStateException("The current file upload type is not support!");
        }

        return fileType;
    }

    private String randomUUID() {
        return UUID.randomUUID().toString();
    }

    private void responseStream(HttpServletResponse response, ByteArrayInputStream inBuf) {
        response.setContentType("application/x-msdownload");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + "pdfile-" + DateUtil.formatDate(new Date()) + ".zip\"");
        response.setHeader("Content-Length", Integer.toString(inBuf.available()));

        try (OutputStream oStream = response.getOutputStream()) {
            IoUtil.copy(inBuf, oStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
