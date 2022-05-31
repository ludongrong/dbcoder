package io.github.ludongrong.dbcoder.oom;

import cn.hutool.core.collection.CollectionUtil;
import io.github.ludongrong.dbcoder.entity.PdFileBo;
import io.github.ludongrong.dbcoder.oom.handler.*;
import io.github.ludongrong.dbcoder.oom.util.OOMXmlUtil;
import io.github.ludongrong.dbcoder.oom.util.ReaderHandler;
import org.apache.commons.collections4.MapUtils;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OOMReader {

    public static OOMProject read(PdFileBo pdFileBo, InputStream in) throws DocumentException {

        // 项目信息 -> 初始
        OOMProject project = onBeforeRead(pdFileBo);

        OOMXmlUtil.read(in, new ReaderHandler() {

            InterfaceHandler interfaceHandler = null;
            ClassHandler classHandler = null;
            GeneralizationHandler generalizationHandler = null;
            RealizationHandler realizationHandler = null;

            @Override
            public void onStart(SAXReader reader) {
                interfaceHandler = readInterface(reader);
                classHandler = readClass(reader);
                generalizationHandler = readGeneralization(reader);
                realizationHandler = readRealization(reader);
            }

            @Override
            public void onEnd() {
                mergeObjects(project, interfaceHandler, classHandler, generalizationHandler, realizationHandler);
            }
        });

        return project;
    }

    private static void mergeObjects(OOMProject project, InterfaceHandler interfaceHandler, ClassHandler classHandler, GeneralizationHandler generalizationHandler, RealizationHandler realizationHandler) {
        List<Map<String, Object>> modelList = new ArrayList<>();
        interfaceHandler.getModelList().forEach(model -> {
            project.fillInProjectInformation(model);
            modelList.add(model);
        });
        classHandler.getModelList().forEach(model -> {
            project.fillInProjectInformation(model);
            modelList.add(model);
        });

        project.setModelList(modelList);

        connectWithRef(generalizationHandler, realizationHandler, modelList);
    }

    private static OOMProject onBeforeRead(PdFileBo pdFileBo) {
        OOMProject project = new OOMProject();
        project.setBasePackage(pdFileBo.getBasePackage());
        project.setProjectName(pdFileBo.getProjectName());
        project.setCurrentDate(new Date());
        return project;
    }

    /**
     * 关联类的关系（继承、实现）
     *
     * @param generalizationHandler
     * @param realizationHandler
     * @param modelList
     */
    private static void connectWithRef(GeneralizationHandler generalizationHandler, RealizationHandler realizationHandler, List<Map<String, Object>> modelList) {
        generalizationHandler.getModelList().forEach(model -> {
            Map<String, Object> ref1 = getMatchModel(OOMProject.OOM_NODE_REF1_ID, model, modelList);
            Map<String, Object> ref2 = getMatchModel(OOMProject.OOM_NODE_REF2_ID, model, modelList);
            // ref1 被 ref2 继承
            List<Map<String, Object>> implementsList = (List) ref2.computeIfAbsent(OOMProject.OOM_OBJECT_EXTENDS, (k) -> {
                return new ArrayList<String>();
            });
            implementsList.add(ref1);
        });

        realizationHandler.getModelList().forEach(model -> {
            Map<String, Object> ref1 = getMatchModel(OOMProject.OOM_NODE_REF1_ID, model, modelList);
            Map<String, Object> ref2 = getMatchModel(OOMProject.OOM_NODE_REF2_ID, model, modelList);
            // ref1 被 ref2 实现
            List<Map<String, Object>> implementsList = (List) ref2.computeIfAbsent(OOMProject.OOM_OBJECT_IMPLEMENTS, (k) -> {
                return new ArrayList<String>();
            });
            implementsList.add(ref1);
        });
    }

    private static Map<String, Object> getMatchModel(String refKey, Map<String, Object> refModel, List<Map<String, Object>> modelList) {
        String refId = MapUtils.getString(refModel, refKey, "");
        Map<String, Object> matchModel = CollectionUtil.findOne(modelList, (model) -> {
            String eleId = MapUtils.getString(model, OOMProject.OOM_NODE_ELE_ID, "");
            return refId.equals(eleId);
        });
        return matchModel;
    }

    private static InterfaceHandler readInterface(SAXReader reader) {
        ParameterHandler parameterHandler = new ParameterHandler();
        InterfaceOperationHandler operationHandler = new InterfaceOperationHandler(parameterHandler);
        InterfaceAttributeHandler attributeHandler = new InterfaceAttributeHandler();
        InterfaceHandler interfaceHandler = new InterfaceHandler(attributeHandler, operationHandler);

        reader.addHandler(interfaceHandler.getPath(), interfaceHandler);
        reader.addHandler(attributeHandler.getPath(), attributeHandler);
        reader.addHandler(operationHandler.getPath(), operationHandler);
        reader.addHandler(parameterHandler.getPath(), parameterHandler);

        return interfaceHandler;
    }

    private static ClassHandler readClass(SAXReader reader) {
        ParameterHandler parameterHandler = new ParameterHandler();
        ClassOperationHandler operationHandler = new ClassOperationHandler(parameterHandler);
        ClassAttributeHandler attributeHandler = new ClassAttributeHandler();
        ClassHandler classHandler = new ClassHandler(attributeHandler, operationHandler);

        reader.addHandler(classHandler.getPath(), classHandler);
        reader.addHandler(attributeHandler.getPath(), attributeHandler);
        reader.addHandler(operationHandler.getPath(), operationHandler);
        reader.addHandler(parameterHandler.getPath(), parameterHandler);

        return classHandler;
    }

    private static GeneralizationHandler readGeneralization(SAXReader reader) {
        GeneralizationHandler generalizationHandler = new GeneralizationHandler();
        reader.addHandler(generalizationHandler.getPath(), generalizationHandler);
        return generalizationHandler;
    }

    private static RealizationHandler readRealization(SAXReader reader) {
        RealizationHandler realizationHandler = new RealizationHandler();
        reader.addHandler(realizationHandler.getPath(), realizationHandler);
        return realizationHandler;
    }

}