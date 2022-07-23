package io.github.ludongrong.dbcoder.excel.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import io.github.ludongrong.dbcoder.common.config.ApplicationConfiguration;
import io.github.ludongrong.dbcoder.common.exception.BadGatewayException;
import io.github.ludongrong.dbcoder.common.util.ControllerUtil;
import io.github.ludongrong.dbcoder.excel.OneLIfeCycleIndex;
import io.github.ludongrong.dbcoder.excel.OneLIfeCycleUUID;
import io.github.ludongrong.dbcoder.excel.controller.dto.ExcelFileMapper;
import io.github.ludongrong.dbcoder.excel.controller.dto.ExcelFileVo;
import io.github.ludongrong.dbcoder.excel.entity.ExcelFileBo;
import io.github.ludongrong.dbcoder.excel.poi.ExcelReader;
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
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xxx.
 *
 * @author <a href="mailto:736779458@qq.com">卢冬榕</a>
 * @since 2022-06-27
 */
@Controller
@RequestMapping("/excelfile")
public class ExcelFileController {

    // 导出文件的名称前缀
    private static final String EXCEL_FILE = "excelfile";

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/x-msdownload")
    public void create(ExcelFileVo excelFileVo, @RequestParam(value = "file") MultipartFile uploadFile, HttpServletResponse response) {

        String fileType = checkUploadFile(uploadFile);

        ExcelFileBo excelFileBo = ExcelFileMapper.INSTANCE.vo2bo(excelFileVo);

        List<Map<String, Object>> modelList = null;
        switch (fileType) {
            case ExcelReader.OFFICE_EXCEL_2003_POSTFIX: {
                modelList = parseExcel2003(uploadFile);
                break;
            }
            default: {
                modelList = parseExcel2007(uploadFile);
            }
        }

        Map<String, Object> model = new HashMap<>();
        model.put("modelList", modelList);
        model.put("uuid7", new OneLIfeCycleUUID());
        model.put("index", new OneLIfeCycleIndex(-1));

        String templatePath = ApplicationConfiguration._template_directory + File.separator + excelFileVo.getName();
        ByteArrayInputStream inBuf = TemplateUtil.generateTemplate(templatePath, model);
        ControllerUtil.responseStream(response, inBuf, EXCEL_FILE);
    }

    private List<Map<String, Object>> parseExcel2003(MultipartFile uploadFile) {

        try (InputStream inputStream = uploadFile.getInputStream()) {
            return new ExcelReader().readExcel2003(inputStream).getDataList();
        } catch (IOException e) {
            throw new BadGatewayException(String.format("Can not parse upload %s file --- %s", "PDM", IdUtil.randomUUID()), e);
        }
    }

    private List<Map<String, Object>> parseExcel2007(MultipartFile uploadFile) {

        try (InputStream inputStream = uploadFile.getInputStream()) {
            return new ExcelReader().readExcel2007(inputStream).getDataList();
        } catch (IOException e) {
            throw new BadGatewayException(String.format("Can not parse upload %s file --- %s", "PDM", IdUtil.randomUUID()), e);
        }
    }

    /**
     * 检查上传接口入参
     *
     * @param file
     * @return
     */
    private String checkUploadFile(MultipartFile file) {
        ControllerUtil.checkUploadFileSize(file);

        String fileType = FileUtil.extName(file.getOriginalFilename());

        List<String> fileSuffice = Arrays.asList(ExcelReader.OFFICE_EXCEL_2003_POSTFIX, ExcelReader.OFFICE_EXCEL_2007_POSTFIX);
        ControllerUtil.checkFileSuffix(fileType, fileSuffice);

        return fileType;
    }

}
