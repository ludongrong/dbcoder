package io.github.ludongrong.dbcoder.powerdesigner.xml.oom;

import io.github.ludongrong.dbcoder.powerdesigner.entity.PdFileBo;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.handler.*;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.util.OOMXmlUtil;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.util.ReaderHandler;
import io.github.ludongrong.dbcoder.powerdesigner.xml.util.ObjectsUtil;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OOMReader {

    public static OOMProject read(PdFileBo pdFileBo, InputStream in) throws DocumentException {

        // 项目信息 -> 初始
        OOMProject project = OOMProject.onBeforeRead(pdFileBo);

        OOMXmlUtil.read(in, new ReaderHandler() {

            @Override
            public void onStart(SAXReader reader) {
                onReadStart(reader, project);
            }

            @Override
            public void onEnd() {
                onReadEnd(project);
            }
        });

        return project;
    }

    private static void onReadEnd(OOMProject project) {
        List<Map<String, Object>> modelList = ObjectsUtil.mergeObjects(project, project.getInterfaceHandler(), project.getClassHandler());
        connectWithRef(project.getGeneralizationHandler(), project.getRealizationHandler(), modelList);
    }

    private static void onReadStart(SAXReader reader, OOMProject project) {
        project.setInterfaceHandler(readInterface(reader));
        project.setClassHandler(readClass(reader));
        project.setGeneralizationHandler(readGeneralization(reader));
        project.setRealizationHandler(readRealization(reader));
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
            Map<String, Object> ref1 = ObjectsUtil.getMatchModel(OOMProject.OOM_NODE_REF1_ID, model, modelList);
            Map<String, Object> ref2 = ObjectsUtil.getMatchModel(OOMProject.OOM_NODE_REF2_ID, model, modelList);
            // ref1 被 ref2 继承
            List<Map<String, Object>> implementsList = (List) ref2.computeIfAbsent(OOMProject.OOM_OBJECT_EXTENDS, (k) -> {
                return new ArrayList<Map<String, Object>>();
            });
            implementsList.add(ref1);
        });

        realizationHandler.getModelList().forEach(model -> {
            Map<String, Object> ref1 = ObjectsUtil.getMatchModel(OOMProject.OOM_NODE_REF1_ID, model, modelList);
            Map<String, Object> ref2 = ObjectsUtil.getMatchModel(OOMProject.OOM_NODE_REF2_ID, model, modelList);
            // ref1 被 ref2 实现
            List<Map<String, Object>> implementsList = (List) ref2.computeIfAbsent(OOMProject.OOM_OBJECT_IMPLEMENTS, (k) -> {
                return new ArrayList<Map<String, Object>>();
            });
            implementsList.add(ref1);
        });
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