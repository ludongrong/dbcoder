package io.github.ludongrong.dbcoder.oom.handler;

import cn.hutool.core.collection.CollectionUtil;

import java.util.List;
import java.util.Map;

public class AttributeHandler extends OOMElementHandler {

    public static final String ATTRIBUTE_HANDLER_PATH = "/Model/RootObject/Children/Model/Interfaces/Interface/Attributes/Attribute";

    public static final String ATTRIBUTE_LIST_TAG = "attributes";

    public static final String[] NODES = new String[]{
            "ObjectID", "Name", "Code", "DataType"
    };

    public AttributeHandler() {
        super(ATTRIBUTE_HANDLER_PATH, NODES);
    }

    public void onInterfaceHandlerEnd(InterfaceHandler interfaceHandler) {
        List<Map<String, Object>> modelList = getModelList();

        Map<String, Object> lastModel = CollectionUtil.getLast(interfaceHandler.getModelList());
        lastModel.put(ATTRIBUTE_LIST_TAG, modelList);

        setModelList(null);
    }
}
