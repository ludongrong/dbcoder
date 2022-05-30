package io.github.ludongrong.dbcoder.oom.handler;

import io.github.ludongrong.dbcoder.oom.OOMProject;
import org.dom4j.ElementPath;

import static io.github.ludongrong.dbcoder.oom.OOMProject.*;

public class ClassHandler extends OOMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Classes/Class";

    public static final String CLASS_TAG_KEY = "Tag";

    public static final String TAG = "Class";

    public static final String[] NODES = new String[]{
            OOM_NODE_OBJECT_ID,
            OOM_OBJECT_NAME,
            OOM_OBJECT_CODE
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

        convertCodeVariable(OOM_OBJECT_CODE);
        getCurrentModel().put("className", getCurrentModel().get(OOMProject.CODE_CAMEL_FIRST_UPPER));
        getCurrentModel().put("classNameVariable", getCurrentModel().get(OOMProject.CODE_CAMEL_FIRST_LOWER));
    }

}
