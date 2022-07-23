package io.github.ludongrong.dbcoder.excel.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

public class ExcelFileDo implements Serializable {

    @Getter
    @Setter
    private String id;

    static final public String _ID = "ID";

    @Getter
    @Setter
    private Timestamp createTime;

    static final public String _CREATE_TIME = "CREATE_TIME";

    @Getter
    @Setter
    private String basePackage;

    static final public String _BASE_PACKAGE = "BASE_PACKAGE";

    @Getter
    @Setter
    private String projectName;

    static final public String _PROJECT_NAME = "PROJECT_NAME";

    @Getter
    @Setter
    private String appType;

    static final public String _APP_TYPE = "APP_TYPE";

    @Getter
    @Setter
    private String name;

    static final public String _NAME = "NAME";

    @Getter
    @Setter
    private String templatePath;

    static final public String _TEMPLATE_PATH = "TEMPLATE_PATH";

    public ExcelFileDo() {
        super();
    }

}
