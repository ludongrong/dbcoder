package io.github.ludongrong.dbcoder.powerdesigner.xml.pdm;

import io.github.ludongrong.dbcoder.powerdesigner.entity.PdFileBo;
import io.github.ludongrong.dbcoder.powerdesigner.xml.common.PDProject;
import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.handler.DbmsHandler;
import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.handler.ReferenceHandler;
import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.handler.TableHandler;
import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.handler.ViewHandler;
import lombok.Getter;
import lombok.Setter;

/**
 * PDMProject
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-24
 */
public class PDMProject extends PDProject {

    /**
     * 数据库类型
     */
    @Getter
    @Setter
    private String dbType;

    @Getter
    @Setter
    private DbmsHandler dbmsHandler;

    @Getter
    @Setter
    private TableHandler tableHandler;

    @Getter
    @Setter
    private ViewHandler viewHandler;

    @Getter
    @Setter
    private ReferenceHandler referenceHandler;

    // 存在主键
    public static final String TABLE_PRIMARY_KEY = "HasPrimaryKey";

    // 注释
    public static final String ELE_COMMENT = "Comment";
    // 长度
    public static final String COLUMN_LENGTH = "Length";
    // 精度
    public static final String COLUMN_PRECISION = "Precision";
    // 主键(映射/列)
    public static final String COLUMN_PRIMARY_KEY = "PrimaryKey";
    // 强制不空(列)
    public static final String COLUMN_MANDATORY = "Mandatory";
    // java类型
    public static final String COLUMN_JAVA_TYPE = "JavaType";
    // jdbc类型
    public static final String COLUMN_JDBC_TYPE = "JdbcType";

    public static final String ARROW_REF_CHILD = "RefChildren";
    public static final String ARROW_REF_PARENT = "RefParents";
    public static final String ARROW_COLUMN_LIST = "ArrowColumnList";
    public static final String ARROW_TAIL_COLUMN = "ArrowTailColumn";
    public static final String ARROW_HEAD_COLUMN = "ArrowHeadColumn";

    public static PDMProject onBeforeRead(PdFileBo pdFileBo) {
        PDMProject project = new PDMProject();
        project.readFromSubmitRequest(pdFileBo);
        return project;
    }

}
