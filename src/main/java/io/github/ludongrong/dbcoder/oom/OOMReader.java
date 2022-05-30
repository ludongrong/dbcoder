package io.github.ludongrong.dbcoder.oom;

import cn.hutool.core.collection.CollectionUtil;
import io.github.ludongrong.dbcoder.entity.PdFileBo;
import io.github.ludongrong.dbcoder.oom.handler.*;
import org.apache.commons.collections4.MapUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.*;

public class OOMReader {

    static Map<String, String> uris = new HashMap<String, String>();

    static {
        uris.put("o", "fooNamespace");
        uris.put("a", "barNamespace");
    }

    public static OOMProject read(PdFileBo pdFileBo, InputStream in) throws DocumentException {

        // 项目信息 -> 初始
        OOMProject project = onBeforeRead(pdFileBo);

        // 解析 -> DocumentFactory
        DocumentFactory factory = new DocumentFactory();
        factory.setXPathNamespaceURIs(uris);

        // 解析 -> SAXReader
        SAXReader reader = new SAXReader();
        reader.setDocumentFactory(factory);
        InterfaceHandler interfaceHandler = readInterface(reader);
        ClassHandler classHandler = readClass(reader);
        GeneralizationHandler generalizationHandler = readGeneralization(reader);
        RealizationHandler realizationHandler = readRealization(reader);
        reader.read(in);

        mergeObjects(project, interfaceHandler, classHandler, generalizationHandler, realizationHandler);

        project.setInterfaces(interfaceHandler.getModelList());
        project.setClasses(classHandler.getModelList());

        return project;
    }

    private static void mergeObjects(OOMProject project, InterfaceHandler interfaceHandler, ClassHandler classHandler, GeneralizationHandler generalizationHandler, RealizationHandler realizationHandler) {
        List<Map<String, Object>> modelList = new ArrayList<>();
        interfaceHandler.getModelList().forEach(model->{
            project.fillInProjectInformation(model);
            modelList.add(model);
        });
        classHandler.getModelList().forEach(model->{
            project.fillInProjectInformation(model);
            modelList.add(model);
        });

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
            List<Map<String, Object>> implementsList = (List)ref2.computeIfAbsent(OOMProject.OOM_OBJECT_EXTENDS, (k) -> {
                return new ArrayList<String>();
            });
            implementsList.add(ref1);
        });

        realizationHandler.getModelList().forEach(model -> {
            Map<String, Object> ref1 = getMatchModel(OOMProject.OOM_NODE_REF1_ID, model, modelList);
            Map<String, Object> ref2 = getMatchModel(OOMProject.OOM_NODE_REF2_ID, model, modelList);
            // ref1 被 ref2 实现
            List<Map<String, Object>> implementsList = (List)ref2.computeIfAbsent(OOMProject.OOM_OBJECT_IMPLEMENTS, (k) -> {
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