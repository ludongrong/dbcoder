package io.github.ludongrong.dbcoder.oom;

import io.github.ludongrong.dbcoder.entity.PdFileBo;
import io.github.ludongrong.dbcoder.oom.handler.AttributeHandler;
import io.github.ludongrong.dbcoder.oom.handler.InterfaceHandler;
import io.github.ludongrong.dbcoder.oom.handler.OperationHandler;
import io.github.ludongrong.dbcoder.oom.handler.ParameterHandler;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OOMReader {

    static Map<String, String> uris = new HashMap<String, String>();

    static {
        uris.put("o", "fooNamespace");
        uris.put("a", "barNamespace");
    }

    public static OOMProject read(PdFileBo pdFileBo, InputStream in) throws DocumentException {

        ParameterHandler parameterHandler = new ParameterHandler();
        OperationHandler operationHandler = new OperationHandler(parameterHandler);
        AttributeHandler attributeHandler = new AttributeHandler();
        InterfaceHandler interfaceHandler = new InterfaceHandler(attributeHandler, operationHandler);

        // 解析 -> DocumentFactory
        DocumentFactory factory = new DocumentFactory();
        factory.setXPathNamespaceURIs(uris);

        // 解析 -> SAXReader
        SAXReader reader = new SAXReader();
        reader.setDocumentFactory(factory);
        reader.addHandler(interfaceHandler.getPath(), interfaceHandler);
        reader.addHandler(attributeHandler.getPath(), attributeHandler);
        reader.addHandler(operationHandler.getPath(), operationHandler);
        reader.addHandler(parameterHandler.getPath(), parameterHandler);
        reader.read(in);

        OOMProject project = new OOMProject();
        project.setBasePackage(pdFileBo.getBasePackage());
        project.setProjectName(pdFileBo.getProjectName());
        project.setCurrentDate(new Date());
        project.setInterfaces(interfaceHandler.getModelList());

        return project;
    }

}