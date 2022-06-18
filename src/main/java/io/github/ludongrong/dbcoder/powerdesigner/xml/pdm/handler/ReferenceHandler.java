package io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.handler;

import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.util.OOMXmlUtil;
import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.PDMProject;
import io.github.ludongrong.dbcoder.powerdesigner.xml.util.ObjectsUtil;
import org.dom4j.ElementPath;

import java.util.*;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject.*;

/**
 * ReferenceHandler
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-23
 */
public class ReferenceHandler extends PDMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/References/Reference";

    // 标记 - 判别Model归属Reference
    public static final String TAG_KEY = "Tag";
    public static final String TAG_VALUE = "Reference";

    public static final String CARDINALITY = "Cardinality";

    public static final String[] NODES = new String[]{
            ELE_OBJECT_ID,
            ELE_OBJECT_NAME,
            ELE_OBJECT_CODE,
            CARDINALITY
    };
    public static final String NODE_PARENT_TABLE = "ParentTable";
    public static final String NODE_CHILD_TABLE = "ChildTable";
    public static final String NODE_PARENT_KEY = "ParentKey";

    ReferenceJoinHandler referenceJoinHandler;

    public ReferenceHandler(ReferenceJoinHandler referenceJoinHandler) {
        super(HANDLER_PATH, NODES);
        this.referenceJoinHandler = referenceJoinHandler;
    }

    @Override
    public void onStart(ElementPath elementPath) {
        super.onStart(elementPath);
        getCurrentModel().put(TAG_KEY, TAG_VALUE);
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);

        referenceJoinHandler.onParentNodeHandlerEnd(ReferenceJoinHandler.TAG, this);

        Arrays.asList(NODE_PARENT_TABLE, NODE_CHILD_TABLE, NODE_PARENT_KEY).forEach(name -> {
            String refId = OOMXmlUtil.getRefId(name, elementPath.getCurrent());
            getCurrentModel().put(name, refId);
        });
    }

    public static Map<String, Object> getParentTable(Map<String, Object> reference, List<Map<String, Object>> tableList) {
        return ObjectsUtil.getMatchModel(NODE_PARENT_TABLE, reference, tableList);
    }

    public static Map<String, Object> getChildTable(Map<String, Object> reference, List<Map<String, Object>> tableList) {
        return ObjectsUtil.getMatchModel(NODE_CHILD_TABLE, reference, tableList);
    }

    public static List<Map<String, Object>> getReferenceJoin(Map<String, Object> model) {
        return (List) model.get(ReferenceJoinHandler.TAG);
    }

    public static List<Map<String, Object>> listArrowColumn(Map<String, Object> model, Map<String, Object> parentTable, Map<String, Object> childTable) {
        List<Map<String, Object>> arrowColumnList = new ArrayList<>();
        getReferenceJoin(model).forEach(refJoinModel -> {
            Map<String, Object> arrow = new HashMap<>(2);
            arrow.put(PDMProject.ARROW_TAIL_COLUMN, ReferenceJoinHandler.oneRelColumn(refJoinModel, childTable));
            arrow.put(PDMProject.ARROW_HEAD_COLUMN, ReferenceJoinHandler.oneRelColumn(refJoinModel, parentTable));
            arrowColumnList.add(arrow);
        });
        return arrowColumnList;
    }

}
