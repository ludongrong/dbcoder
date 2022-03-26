package io.github.ludongrong.dbcoder.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import io.github.ludongrong.dbcoder.config.ApplicationConfiguration;
import io.github.ludongrong.dbcoder.controller.dto.PdFileMapper;
import io.github.ludongrong.dbcoder.controller.dto.PdFileVo;
import io.github.ludongrong.dbcoder.exception.BadGatewayException;
import io.github.ludongrong.dbcoder.pd.PdmReader;
import io.github.ludongrong.dbcoder.provitor.Project;
import io.github.ludongrong.dbcoder.service.IPdFileService;
import io.github.ludongrong.dbcoder.template.TemplateReader;

/**
 * xxx.
 *
 * @author <a href="mailto:736779458@qq.com">卢冬榕</a>
 * @since 2020-12-18
 */
@Controller
@RequestMapping("/pdfile")
public class PdFileController {

    @Resource(name = "dbcoder-PdFileServiceImpl")
    private IPdFileService pdFileService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/x-msdownload")
    public void create(PdFileVo pdFileVo, @RequestParam(value = "file") MultipartFile file,
            HttpServletResponse response) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("file must have a non-nulls!");
        }

        if (file.getSize() > ApplicationConfiguration._UPLOAD_MAX_SIZE) {
            throw new IllegalStateException("The file size exceeds max upload size("
                    + ApplicationConfiguration._UPLOAD_MAX_SIZE / 1024 / 1024 + "M)!");
        }

        String fileType = FileUtil.extName(file.getOriginalFilename());
        if (fileType.equalsIgnoreCase(ApplicationConfiguration._FILE_TYPE_PDM) == false) {
            throw new IllegalStateException("The current file upload type is not support!");
        }

        String pdFileId = UUID.randomUUID().toString();

        ByteArrayInputStream inBuf = null;

        String templatePath = ApplicationConfiguration._template_directory + File.separator + pdFileVo.getName();
        try {
            Project project = PdmReader.read(PdFileMapper.INSTANCE.vo2bo(pdFileVo), file.getInputStream());
            TemplateReader templateReader = new TemplateReader(templatePath);
            inBuf = templateReader.generate(project);
        } catch (Exception e) {
            throw new BadGatewayException("Can not parse upload files. " + pdFileId, e);
        }

        response.setContentType("application/x-msdownload");
        response.addHeader("Content-Disposition",
                "attachment; filename=\"" + "pdfile-" + DateUtil.formatDate(new Date()) + ".zip\"");
        response.setHeader("Content-Length", Integer.toString(inBuf.available()));

        try (OutputStream oStream = response.getOutputStream()) {
            IoUtil.copy(inBuf, oStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
