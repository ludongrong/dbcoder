package io.github.ludongrong.dbcoder.oom.handler;

import org.dom4j.ElementPath;

public class InterfaceHandler extends OOMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Interfaces/Interface";

    public static final String INTERFACE_TAG_KEY = "tag";

    public static final String INTERFACE_TAG = "interface";

    public static final String NODE_KEY_CODE = "Code";

    public static final String[] NODES = new String[]{
            "ObjectID", "Name", NODE_KEY_CODE
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

        convertVariable(NODE_KEY_CODE);
    }
    
}
