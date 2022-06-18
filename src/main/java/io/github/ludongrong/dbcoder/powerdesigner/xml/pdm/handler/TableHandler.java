package io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.handler;

import cn.hutool.core.util.StrUtil;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.util.OOMXmlUtil;
import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.PDMProject;
import io.github.ludongrong.dbcoder.powerdesigner.xml.util.ObjectsUtil;
import org.dom4j.ElementPath;

import java.util.*;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject.*;

/**
 * TablesHandler
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-23
 */
public class TableHandler extends PDMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Tables/Table";

    // 标记 - 判别Model归属Table
    public static final String TAG_KEY = "Tag";
    public static final String TAG_VALUE = "Table";

    // 字段
    public static final String ARROW_CHILD_TABLE = "ChildTable";
    public static final String ARROW_PARENT_TABLE = "ParentTable";

    public static final String[] NODES = new String[]{
            ELE_OBJECT_ID,
            ELE_OBJECT_NAME,
            ELE_OBJECT_CODE,
            PDMProject.ELE_COMMENT
    };

    TableColumnHandler tableColumnHandler;

    TableKeyHandler keyHandler;

    public TableHandler(TableColumnHandler tableColumnHandler, TableKeyHandler keyHandler) {
        super(HANDLER_PATH, NODES);
        this.tableColumnHandler = tableColumnHandler;
        this.keyHandler = keyHandler;
    }

    @Override
    public void onStart(ElementPath elementPath) {
        super.onStart(elementPath);
        getCurrentModel().put(TAG_KEY, TAG_VALUE);
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        convertCodeToUpper(PDMProject.ELE_OBJECT_CODE);
        convertCodeVariable(ELE_OBJECT_CODE);
        setClassNameAndVariable();

        tableColumnHandler.onParentNodeHandlerEnd(TableColumnHandler.TAG, this);

        annotatePrimaryColumn(elementPath);
        keyHandler.onParentNodeHandlerEnd(TableKeyHandler.TAG);
    }

    public static List<Map<String, Object>> getColumnList(Map<String, Object> model) {
        return (List) model.get(TableColumnHandler.TAG);
    }

    /**
     * 存在主键列
     *
     * @param model
     */
    public static void hasPrimaryKey(Map<String, Object> model) {
        if (!Objects.isNull(model))
            model.put(PDMProject.TABLE_PRIMARY_KEY, "1");
    }

    /**
     * 存在主键列
     *
     * @param model
     */
    public static void hasNotPrimaryKey(Map<String, Object> model) {
        if (!Objects.isNull(model))
            model.put(PDMProject.TABLE_PRIMARY_KEY, "0");
    }

    /**
     * 父级关联表
     *
     * @param selfTable
     * @param parentTable
     * @param arrowColumnList
     */
    public static void addRefParent(Map<String, Object> selfTable, Map<String, Object> parentTable, List<Map<String, Object>> arrowColumnList) {
        HashMap<Object, Object> refTable = new HashMap<>();
        refTable.put(ARROW_PARENT_TABLE, parentTable);
        refTable.put(PDMProject.ARROW_COLUMN_LIST, arrowColumnList);
        ((List) selfTable.computeIfAbsent(PDMProject.ARROW_REF_PARENT, (k) -> {
            return new ArrayList<Map<String, Object>>();
        })).add(refTable);
    }

    /**
     * 子级关联表
     *
     * @param selfTable
     * @param childTable
     * @param arrowColumnList
     */
    public static void addRefChild(Map<String, Object> selfTable, Map<String, Object> childTable, List<Map<String, Object>> arrowColumnList) {
        HashMap<Object, Object> refTable = new HashMap<>();
        refTable.put(TableHandler.ARROW_CHILD_TABLE, childTable);
        refTable.put(PDMProject.ARROW_COLUMN_LIST, arrowColumnList);
        ((List) selfTable.computeIfAbsent(PDMProject.ARROW_REF_CHILD, (k) -> {
            return new ArrayList<Map<String, Object>>();
        })).add(refTable);
    }

    /**
     * 标注哪列是主键
     *
     * @param elementPath
     */
    private void annotatePrimaryColumn(ElementPath elementPath) {
        // 主键映射ID
        String primaryRefId = OOMXmlUtil.getRefId(PDMProject.COLUMN_PRIMARY_KEY, elementPath.getCurrent());
        if (StrUtil.isEmpty(primaryRefId) == false) { // 有主键 -> 标注哪列是主键
            annotatePrimaryColumn(primaryRefId);
            hasPrimaryKey(getCurrentModel());
        } else {// 没有主键
            hasNotPrimaryKey(getCurrentModel());
        }
    }

    /**
     * 标注哪列是主键
     *
     * @param primaryRefId
     */
    private void annotatePrimaryColumn(String primaryRefId) {
        Map<String, Object> keyRef = ObjectsUtil.getMatchModel(primaryRefId, keyHandler.getModelList());
        List<String> columnRefIdList = keyHandler.listRefId(keyRef);
        columnRefIdList.forEach(refId -> {
            Map<String, Object> column = ObjectsUtil.getMatchModel(refId, tableColumnHandler.getModelList());
            TableColumnHandler.setPrimaryKey(column);
        });
    }

}
