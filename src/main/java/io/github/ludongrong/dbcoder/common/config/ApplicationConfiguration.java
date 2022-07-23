package io.github.ludongrong.dbcoder.common.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import cn.hutool.core.util.StrUtil;

@Configuration
public class ApplicationConfiguration {

    static final public int _DEFAULT_PAGE_SIZE = 10;

    static final public int _UPLOAD_MAX_SIZE = 10 * 1024 * 1024;

    static final public String[] _PD_FILE_TYPE = {"pdm","oom"};

    static final public String _FILE_STORE = "pdm";

    static public String _template_directory;

    static public String _generate_directory;

    @Value("${project.template-directory}")
    public void setTemplateDirectory(String templateDirectory) {
        if (StrUtil.isBlank(templateDirectory)) {
            templateDirectory = System.getProperty("user.dir");
            if (templateDirectory.endsWith(File.separator) == false) {
                templateDirectory += File.separator;
            }
            templateDirectory += "template";
        }
        _template_directory = templateDirectory;
    }

    @Value("${project.generate-directory}")
    public void setGenerateDirectory(String generateDirectory) {
        if (StrUtil.isBlank(generateDirectory)) {
            generateDirectory = System.getProperty("user.dir");
            if (generateDirectory.endsWith(File.separator) == false) {
                generateDirectory += File.separator;
            }
            generateDirectory += "generate";
        }
        _generate_directory = generateDirectory;
    }

}
