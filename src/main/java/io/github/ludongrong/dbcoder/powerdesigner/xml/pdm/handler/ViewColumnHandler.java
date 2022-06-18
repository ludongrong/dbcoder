package io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.handler;

import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.PDMProject;
import org.dom4j.ElementPath;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject.*;

/**
 * ColumnHandler
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-23
 */
public class ViewColumnHandler extends PDMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Views/View/Columns/ViewColumn";

    public static final String TAG = "Columns";

    // 节点
    public static final String NODE_DATA_TYPE = "DataType";

    public static final String[] NODES = new String[]{
            ELE_OBJECT_ID,
            ELE_OBJECT_NAME,
            ELE_OBJECT_CODE,
            PDMProject.ELE_COMMENT,
            NODE_DATA_TYPE
    };

    public ViewColumnHandler() {
        super(HANDLER_PATH, NODES);
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        convertCodeToUpper(PDMProject.ELE_OBJECT_CODE);
        convertCodeVariable(ELE_OBJECT_CODE);
        setNotPrimaryKey();
    }

}
