package io.github.ludongrong.dbcoder.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

public class PdFileDo implements Serializable {

    private static final long serialVersionUID = 4502535932156166087L;

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
    private String dbType;

    static final public String _DB_TYPE = "DB_TYPE";

    @Getter
    @Setter
    private String name;

    static final public String _NAME = "NAME";

    @Getter
    @Setter
    private String generatePath;

    static final public String _GENERATE_PATH = "GENERATE_PATH";

    @Getter
    @Setter
    private String templatePath;

    static final public String _TEMPLATE_PATH = "TEMPLATE_PATH";

    public PdFileDo() {
        super();
    }
}