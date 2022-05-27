package io.github.ludongrong.dbcoder.oom.handler;

import org.dom4j.ElementPath;

public class ClassHandler extends OOMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Classes/Class";

    public static final String CLASS_TAG_KEY = "Tag";

    public static final String TAG = "Class";

    public static final String NODE_KEY_CODE = "Code";

    public static final String[] NODES = new String[]{
            "ObjectID", "Name", NODE_KEY_CODE
    };

    ClassAttributeHandler attributeHandler;

    ClassOperationHandler operationHandler;

    public ClassHandler(ClassAttributeHandler attributeHandler, ClassOperationHandler operationHandler) {
        super(HANDLER_PATH, NODES);
        this.attributeHandler = attributeHandler;
        this.operationHandler = operationHandler;
    }

    @Override
    public void onStart(ElementPath elementPath) {
        super.onStart(elementPath);
        getCurrentModel().put(CLASS_TAG_KEY, TAG);
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);

        attributeHandler.onParentNodeHandlerEnd(ClassAttributeHandler.TAG, this);
        operationHandler.onParentNodeHandlerEnd(ClassOperationHandler.TAG, this);

        convertVariable(NODE_KEY_CODE);
    }

}
