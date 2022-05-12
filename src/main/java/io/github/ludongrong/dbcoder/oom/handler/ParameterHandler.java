package io.github.ludongrong.dbcoder.oom.handler;

import cn.hutool.core.collection.CollectionUtil;

import java.util.List;
import java.util.Map;

public class ParameterHandler extends OOMElementHandler {

    public static final String PARAMETER_HANDLER_PATH = "/Model/RootObject/Children/Model/Interfaces/Interface/Operations/Operation/Parameters/Parameter";

    public static final String PARAMETER_LIST_TAG = "parameters";

    public static final String[] NODES = new String[]{
            "ObjectID", "Name", "Code", "Parameter.DataType"
    };

    public ParameterHandler() {
        super(PARAMETER_HANDLER_PATH, NODES);
    }

    public void onOperationHandlerEnd(OperationHandler operationHandler) {
        List<Map<String, Object>> modelList = getModelList();

        Map<String, Object> lastModel = CollectionUtil.getLast(operationHandler.getModelList());
        lastModel.put(PARAMETER_LIST_TAG, modelList);

        setModelList(null);
    }
}
