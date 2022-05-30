package io.github.ludongrong.dbcoder.oom.handler;

import org.dom4j.ElementPath;

import static io.github.ludongrong.dbcoder.oom.OOMProject.*;

public class InterfaceOperationHandler extends OOMElementHandler {

    public static final String OPERATION_HANDLER_PATH = "/Model/RootObject/Children/Model/Interfaces/Interface/Operations/Operation";

    public static final String TAG = "Operations";

    public static final String[] NODES = new String[]{
            OOM_NODE_OBJECT_ID,
            OOM_OBJECT_NAME,
            OOM_OBJECT_CODE,
            OOM_OBJECT_RETURN_TYPE
    };

    ParameterHandler parameterHandler;

    public InterfaceOperationHandler(ParameterHandler parameterHandler) {
        super(OPERATION_HANDLER_PATH, NODES);
        this.parameterHandler = parameterHandler;
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        parameterHandler.onParentNodeHandlerEnd(ParameterHandler.TAG, this);
    }

}
