package io.github.ludongrong.dbcoder.powerdesigner.xml.util;

import cn.hutool.core.collection.CollectionUtil;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject;
import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.PDMProject;
import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.handler.TableColumnHandler;
import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.util.JavaTypes;
import io.github.ludongrong.dbcoder.powerdesigner.xml.common.PDProject;
import io.github.ludongrong.dbcoder.powerdesigner.xml.common.handler.PDElementHandler;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ObjectsUtil
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-23
 */
public class ObjectsUtil {

    /**
     * 合并modelList到一个modelList
     *
     * @param project
     * @param handlers
     * @return
     */
    public static List<Map<String, Object>> mergeObjects(PDProject project, PDElementHandler... handlers) {
        List<Map<String, Object>> modelList = new ArrayList<>();
        for (PDElementHandler handler : handlers) {
            handler.getModelList().forEach(model -> {
                project.fillInProjectInformation(model);
                modelList.add(model);
                model.put("ModelList", modelList);
            });
        }
        project.setModelList(modelList);
        return modelList;
    }

    /**
     * 查找modelList的id等于refModel[refKey]指向值的model
     *
     * @param refKey
     * @param refModel
     * @param modelList
     * @return
     */
    public static Map<String, Object> getMatchModel(String refKey, Map<String, Object> refModel, List<Map<String, Object>> modelList) {
        String refId = MapUtils.getString(refModel, refKey, "");
        return getMatchModel(refId, modelList);
    }

    /**
     * 查找modelList的id等于refId的model
     *
     * @param refId
     * @param modelList
     * @return
     */
    public static Map<String, Object> getMatchModel(String refId, List<Map<String, Object>> modelList) {
        Map<String, Object> matchModel = CollectionUtil.findOne(modelList, (model) -> {
            String eleId = MapUtils.getString(model, OOMProject.ELE_ID, "");
            return refId.equals(eleId);
        });
        return matchModel;
    }

    /**
     * 查找modelList的id值在refIdList里的model
     *
     * @param refIdList
     * @param modelList
     * @return
     */
    public static List<Map<String, Object>> getMatchModel(List<String> refIdList, List<Map<String, Object>> modelList) {
        List<Map<String, Object>> matchModelList = new ArrayList<>();
        refIdList.forEach(refId -> {
            matchModelList.add(getMatchModel(refId, modelList));
        });
        return matchModelList;
    }

    public static void convertDataTypeToJdbcType(Map<String, Object> model, String dbType) {
        String dataType = TableColumnHandler.getDataType(model);

        String javaType = JavaTypes.getJavaType(dbType, dataType,
                TableColumnHandler.getLength(model),
                TableColumnHandler.getPrecision(model));

        model.put(PDMProject.COLUMN_JAVA_TYPE, javaType);
        model.put(PDMProject.COLUMN_JDBC_TYPE, JavaTypes.getJdbcType(dbType, dataType));
    }

}
