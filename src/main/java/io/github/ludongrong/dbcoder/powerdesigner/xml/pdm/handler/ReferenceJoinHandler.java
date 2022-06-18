package io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.handler;

import cn.hutool.core.util.StrUtil;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.util.OOMXmlUtil;
import io.github.ludongrong.dbcoder.powerdesigner.xml.util.ObjectsUtil;
import org.dom4j.ElementPath;

import java.util.*;
import java.util.stream.Collectors;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject.ELE_OBJECT_ID;

/**
 * ReferenceJoinHandler
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-23
 */
public class ReferenceJoinHandler extends PDMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/References/Reference/Joins/ReferenceJoin";

    public static final String TAG = "ReferenceJoin";

    public static final String[] NODES = new String[]{
            ELE_OBJECT_ID,
    };

    public ReferenceJoinHandler() {
        super(HANDLER_PATH, NODES);
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);

        Arrays.asList("Object1", "Object2").forEach(name -> {
            String refId = OOMXmlUtil.getRefId(name, elementPath.getCurrent());
            getCurrentModel().put(name, refId);
        });
    }

    /**
     * ReferenceJoin层级的列映射ID
     *
     * @param model
     * @return
     */
    public static List<String> listColumnRefId(Map<String, Object> model) {
        ArrayList<String> refIdList = new ArrayList<>();
        Arrays.asList("Object1", "Object2").forEach(name -> {
            refIdList.add(Optional.ofNullable(model.get(name)).map(refId -> {
                return refId.toString();
            }).orElse(StrUtil.EMPTY));
        });
        return refIdList;
    }

    /**
     * 关系线的箭头和箭尾指向的俩个列
     *
     * @param refJoinModel A表的A列与B表的B列的对应关系
     * @param table        某个表
     * @return
     */
    public static Map<String, Object> oneRelColumn(Map<String, Object> refJoinModel, Map<String, Object> table) {
        List<String> colJoinRefIdList = listColumnRefId(refJoinModel);
        List<Map<String, Object>> columnList = TableHandler.getColumnList(table);
        List<Map<String, Object>> relColumnList = ObjectsUtil.getMatchModel(colJoinRefIdList, columnList).stream().filter(m -> {
            return !Objects.isNull(m);
        }).collect(Collectors.toList());
        return relColumnList.isEmpty() ? null : relColumnList.get(0);
    }

}
