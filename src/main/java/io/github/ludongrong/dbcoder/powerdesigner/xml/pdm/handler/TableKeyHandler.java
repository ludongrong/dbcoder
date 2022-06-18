package io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.handler;

import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.util.OOMXmlUtil;
import org.dom4j.ElementPath;

import java.util.List;
import java.util.Map;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject.*;

/**
 * KeysHandler
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-23
 */
public class TableKeyHandler extends PDMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Tables/Table/Keys/Key";

    public static final String TAG = "Keys";

    public static final String[] NODES = new String[]{
            ELE_OBJECT_ID,
            ELE_OBJECT_NAME,
            ELE_OBJECT_CODE
    };

    // 节点
    public static final String NODE_KEY_COLUMNS = "Key.Columns";

    // 字段
    public static final String FIELD_REF_ID_LIST = "RefIdList";

    public TableKeyHandler() {
        super(HANDLER_PATH, NODES);
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        parseColumnRefId(elementPath);
    }

    /**
     * 列的映射ID列表
     *
     * @param elementPath
     * @return
     */
    private void parseColumnRefId(ElementPath elementPath) {
        List<String> refIdList = OOMXmlUtil.getRefIdList(NODE_KEY_COLUMNS, elementPath.getCurrent());
        getCurrentModel().put(FIELD_REF_ID_LIST, refIdList);
    }

    public static List<String> listRefId(Map<String, Object> model) {
        return (List) model.get(FIELD_REF_ID_LIST);
    }

}