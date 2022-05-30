package io.github.ludongrong.dbcoder.oom.handler;

import io.github.ludongrong.dbcoder.oom.OOMProject;
import org.dom4j.ElementPath;

import static io.github.ludongrong.dbcoder.oom.OOMProject.*;

public class InterfaceHandler extends OOMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Interfaces/Interface";

    public static final String INTERFACE_TAG_KEY = "tag";

    public static final String INTERFACE_TAG = "interface";

    public static final String[] NODES = new String[]{
            OOM_NODE_OBJECT_ID,
            OOM_OBJECT_NAME,
            OOM_OBJECT_CODE
    };

    InterfaceAttributeHandler attributeHandler;

    InterfaceOperationHandler operationHandler;

    public InterfaceHandler(InterfaceAttributeHandler attributeHandler, InterfaceOperationHandler operationHandler) {
        super(HANDLER_PATH, NODES);
        this.attributeHandler = attributeHandler;
        this.operationHandler = operationHandler;
    }

    @Override
    public void onStart(ElementPath elementPath) {
        super.onStart(elementPath);
        getCurrentModel().put(INTERFACE_TAG_KEY, INTERFACE_TAG);
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);

        attributeHandler.onParentNodeHandlerEnd(InterfaceAttributeHandler.TAG, this);
        operationHandler.onParentNodeHandlerEnd(InterfaceOperationHandler.TAG, this);

        convertCodeVariable(OOM_OBJECT_CODE);
        getCurrentModel().put("className", getCurrentModel().get(OOMProject.CODE_CAMEL_FIRST_UPPER));
        getCurrentModel().put("classNameVariable", getCurrentModel().get(OOMProject.CODE_CAMEL_FIRST_LOWER));
    }
    
}
