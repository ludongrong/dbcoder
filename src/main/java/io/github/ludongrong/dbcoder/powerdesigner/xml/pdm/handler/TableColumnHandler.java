package io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.handler;

import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.PDMProject;
import org.apache.commons.collections4.MapUtils;
import org.dom4j.ElementPath;

import java.util.Map;
import java.util.Objects;

/**
 * ColumnHandler
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-23
 */
public class TableColumnHandler extends PDMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Tables/Table/Columns/Column";

    public static final String TAG = "Columns";

    // 节点
    public static final String NODE_DATA_TYPE = "DataType";
    public static final String NODE_IDENTITY = "Identity";
    public static final String NODE_AUTO_MIGRATED = "AutoMigrated";
    public static final String NODE_COLUMN_MANDATORY = "Column.Mandatory";
    public static final String NODE_COLUMN_COMPUTED = "Column.Computed";

    public static final String[] NODES = new String[]{
            PDMProject.ELE_OBJECT_ID,
            PDMProject.ELE_OBJECT_NAME,
            PDMProject.ELE_OBJECT_CODE,
            PDMProject.ELE_COMMENT,
            NODE_DATA_TYPE,
            PDMProject.COLUMN_LENGTH,
            PDMProject.COLUMN_PRECISION,
            NODE_IDENTITY,
            NODE_AUTO_MIGRATED,
            NODE_COLUMN_MANDATORY,
            NODE_COLUMN_COMPUTED
    };

    public TableColumnHandler() {
        super(HANDLER_PATH, NODES);
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        convertCodeToUpper(PDMProject.ELE_OBJECT_CODE);
        convertCodeVariable(PDMProject.ELE_OBJECT_CODE);
        convertProperties(NODE_COLUMN_MANDATORY, PDMProject.COLUMN_MANDATORY);
        setNotPrimaryKey();
    }

    public static String getDataType(Map<String, Object> model) {
        return MapUtils.getString(model, NODE_DATA_TYPE, "");
    }

    public static int getLength(Map<String, Object> model) {
        return MapUtils.getIntValue(model, PDMProject.COLUMN_LENGTH, 0);
    }

    public static int getPrecision(Map<String, Object> model) {
        return MapUtils.getIntValue(model, PDMProject.COLUMN_PRECISION, 0);
    }

    /**
     * 标注是主键列
     *
     * @param model
     */
    public static void setPrimaryKey(Map<String, Object> model) {
        if (!Objects.isNull(model))
            model.put(PDMProject.COLUMN_PRIMARY_KEY, "1");
    }

}
