package io.github.ludongrong.dbcoder.oom.handler;

import cn.hutool.core.collection.CollectionUtil;
import io.github.ludongrong.dbcoder.util.StringUtil;
import org.apache.commons.collections4.MapUtils;
import org.dom4j.ElementPath;

import java.util.Map;

public class InterfaceHandler extends OOMElementHandler {

    public static final String INTERFACE_HANDLER_PATH = "/Model/RootObject/Children/Model/Interfaces/Interface";

    public static final String TAG = "tag";

    public static final String INTERFACE_TAG = "interface";

    public static final String KEY_CODE = "Code";

    public static final String[] NODES = new String[]{
            "ObjectID", "Name", KEY_CODE
    };

    AttributeHandler attributeHandler;

    OperationHandler operationHandler;

    public InterfaceHandler(AttributeHandler attributeHandler, OperationHandler operationHandler) {
        super(INTERFACE_HANDLER_PATH, NODES);
        this.attributeHandler = attributeHandler;
        this.operationHandler = operationHandler;
    }

    @Override
    public void onStart(ElementPath elementPath) {
        super.onStart(elementPath);
        getCurrentModel().put(TAG, INTERFACE_TAG);
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        attributeHandler.onInterfaceHandlerEnd(this);
        operationHandler.onInterfaceHandlerEnd(this);

        Map<String, Object> lastModel = CollectionUtil.getLast(getModelList());
        String code = MapUtils.getString(lastModel, KEY_CODE, "");
        lastModel.put("className", StringUtil.toJavaClassName(code));
        lastModel.put("classNameVariable", StringUtil.toJavaVariableName(code));
    }
}
