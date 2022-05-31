package io.github.ludongrong.dbcoder.oom.handler;

import org.dom4j.ElementPath;

import static io.github.ludongrong.dbcoder.oom.OOMProject.*;

public class InterfaceAttributeHandler extends OOMElementHandler {

    public static final String ATTRIBUTE_HANDLER_PATH = "/Model/RootObject/Children/Model/Interfaces/Interface/Attributes/Attribute";

    public static final String TAG = "Attributes";

    public static final String[] NODES = new String[]{
            OOM_NODE_OBJECT_ID,
            OOM_OBJECT_NAME,
            OOM_OBJECT_CODE,
            OOM_OBJECT_DATA_TYPE
    };

    public InterfaceAttributeHandler() {
        super(ATTRIBUTE_HANDLER_PATH, NODES);
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        convertCodeVariable(OOM_OBJECT_CODE);
    }

}
