package io.github.ludongrong.dbcoder.powerdesigner.xml.oom.handler;

import org.dom4j.ElementPath;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject.*;

/**
 * 接口
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-05-09
 */
public class InterfaceHandler extends OOMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Interfaces/Interface";

    public static final String INTERFACE_TAG_KEY = "tag";

    public static final String INTERFACE_TAG = "interface";

    public static final String[] NODES = new String[]{
            ELE_OBJECT_ID,
            ELE_OBJECT_NAME,
            ELE_OBJECT_CODE
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

        convertCodeVariable(ELE_OBJECT_CODE);
        setClassNameAndVariable();
    }
    
}
