package io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.handler;

import com.alibaba.druid.util.JdbcUtils;
import lombok.Getter;
import org.apache.commons.collections4.MapUtils;
import org.dom4j.ElementPath;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject.*;

/**
 * DbmsHandler
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-23
 */
public class DbmsHandler extends PDMElementHandler {

    @Getter
    String dbType;

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/DBMS/Shortcut";

    public static final String TAG = "DBMS";

    public static final String[] NODES = new String[]{
            ELE_OBJECT_ID,
            ELE_OBJECT_NAME,
            ELE_OBJECT_CODE
    };

    public DbmsHandler() {
        super(HANDLER_PATH, NODES);
    }

    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        String name = MapUtils.getString(getCurrentModel(), ELE_OBJECT_NAME, "");
        this.dbType = toDbType(name.toLowerCase());
    }

    /**
     * 通过名称判断出数据库类型
     *
     * @param name PDM描述的数据库名称
     * @return
     */
    private String toDbType(String name) {
        String dbType = null;
        if (name != null) {
            if (name.startsWith(JdbcUtils.ORACLE)) {
                dbType = JdbcUtils.ORACLE;
            } else if (name.startsWith(JdbcUtils.SQL_SERVER)) {
                dbType = JdbcUtils.SQL_SERVER;
            } else if (name.startsWith(JdbcUtils.MYSQL)) {
                dbType = JdbcUtils.MYSQL;
            } else if (name.startsWith(JdbcUtils.POSTGRESQL)) {
                dbType = JdbcUtils.POSTGRESQL;
            }
        }
        return dbType;
    }

}