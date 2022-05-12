package io.github.ludongrong.dbcoder.oom.handler;

import cn.hutool.core.collection.CollectionUtil;
import org.dom4j.ElementPath;

import java.util.List;
import java.util.Map;

public class OperationHandler extends OOMElementHandler {

    public static final String OPERATION_HANDLER_PATH = "/Model/RootObject/Children/Model/Interfaces/Interface/Operations/Operation";

    public static final String OPERATION_LIST_TAG = "operations";

    public static final String[] NODES = new String[]{
            "ObjectID", "Name", "Code", "ReturnType"
    };

    ParameterHandler parameterHandler;

    public OperationHandler(ParameterHandler parameterHandler) {
        super(OPERATION_HANDLER_PATH, NODES);
        this.parameterHandler = parameterHandler;
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        parameterHandler.onOperationHandlerEnd(this);
    }

    public void onInterfaceHandlerEnd(InterfaceHandler interfaceHandler) {
        List<Map<String, Object>> modelList = getModelList();

        Map<String, Object> lastModel = CollectionUtil.getLast(interfaceHandler.getModelList());
        lastModel.put(OPERATION_LIST_TAG, modelList);

        setModelList(null);
    }
}
