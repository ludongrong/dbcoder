package io.github.ludongrong.dbcoder.powerdesigner.xml.oom.handler;

import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject;
import org.dom4j.ElementPath;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject.*;

public class ClassHandler extends OOMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Classes/Class";

    public static final String TAG_KEY = "Tag";

    public static final String TAG_VALUE = "Class";

    public static final String[] NODES = new String[]{
            ELE_OBJECT_ID,
            ELE_OBJECT_NAME,
            ELE_OBJECT_CODE
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
        getCurrentModel().put(TAG_KEY, TAG_VALUE);
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);

        attributeHandler.onParentNodeHandlerEnd(ClassAttributeHandler.TAG, this);
        operationHandler.onParentNodeHandlerEnd(ClassOperationHandler.TAG, this);

        convertCodeVariable(ELE_OBJECT_CODE);
        setClassNameAndVariable();
    }

}
