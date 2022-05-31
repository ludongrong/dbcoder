package io.github.ludongrong.dbcoder.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import io.github.ludongrong.dbcoder.config.ApplicationConfiguration;
import io.github.ludongrong.dbcoder.controller.dto.PdFileMapper;
import io.github.ludongrong.dbcoder.controller.dto.PdFileVo;
import io.github.ludongrong.dbcoder.exception.BadGatewayException;
import io.github.ludongrong.dbcoder.oom.OOMProject;
import io.github.ludongrong.dbcoder.oom.OOMReader;
import io.github.ludongrong.dbcoder.pdm.PDMProject;
import io.github.ludongrong.dbcoder.pdm.PDMReader;
import io.github.ludongrong.dbcoder.pdm.Table;
import io.github.ludongrong.dbcoder.template.TemplateReader;
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
        switch (fileType){
            case"oom":{
                modelList = parseOOM(pdFileVo, uploadFile);
                break;
            }
            default:{
                modelList = parsePDM(pdFileVo, uploadFile);
            }
        }

        String templatePath = ApplicationConfiguration._template_directory + File.separator + pdFileVo.getName();
        ByteArrayInputStream inBuf = generateTemplate(templatePath, modelList);

        responseStream(response, inBuf);
    }

    private List<Map<String, Object>> parsePDM(PdFileVo pdFileVo, MultipartFile file) {
        try {
            PDMProject project = PDMReader.read(PdFileMapper.INSTANCE.vo2bo(pdFileVo), file.getInputStream());

            List<Map<String, Object>> modelList = new ArrayList<>();

            Iterator<Table> iterable = project.getTables().iterator();
            while (iterable.hasNext()) {
                modelList.add(Table.toModel(iterable.next()));
            }

            return modelList;
        } catch (Exception e) {
            throw new BadGatewayException("Can not parse upload files. " + randomUUID(), e);
        }
    }

    private List<Map<String, Object>> parseOOM(PdFileVo pdFileVo, MultipartFile file) {
        try {
            OOMProject project = OOMReader.read(PdFileMapper.INSTANCE.vo2bo(pdFileVo), file.getInputStream());
            return project.getModelList();
        } catch (Exception e) {
            throw new BadGatewayException("Can not parse upload files. " + randomUUID(), e);
        }
    }

    private ByteArrayInputStream generateTemplate(String templatePath, List<Map<String, Object>> modelList) {
        try {
            TemplateReader templateReader = new TemplateReader(templatePath);

            Iterator<Map<String, Object>> iterable = modelList.iterator();
            while (iterable.hasNext()) {
                templateReader.generate(iterable.next());
            }

            return templateReader.finish();
        } catch (Exception e) {
            throw new BadGatewayException("Can not generate template. " + randomUUID(), e);
        }
    }

    private String checkUploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("file must have a non-nulls!");
        }

        if (file.getSize() > ApplicationConfiguration._UPLOAD_MAX_SIZE) {
            throw new IllegalStateException("The file size exceeds max upload size("
                    + ApplicationConfiguration._UPLOAD_MAX_SIZE / 1024 / 1024 + "M)!");
        }

        String fileType = FileUtil.extName(file.getOriginalFilename());

        boolean match = Arrays.asList(ApplicationConfiguration._FILE_TYPE).stream().anyMatch(v->{
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
