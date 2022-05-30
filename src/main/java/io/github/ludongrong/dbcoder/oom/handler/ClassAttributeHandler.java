package io.github.ludongrong.dbcoder.oom.handler;

import io.github.ludongrong.dbcoder.oom.OOMProject;
import org.dom4j.ElementPath;

import static io.github.ludongrong.dbcoder.oom.OOMProject.*;

public class ClassAttributeHandler extends OOMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Classes/Class/Attributes/Attribute";

    public static final String TAG = "Attributes";

    public static final String NODE_KEY_ATTRIBUTE_VISIBILITY = "Attribute.Visibility";

    public static final String[] NODES = new String[]{
            OOM_NODE_OBJECT_ID,
            OOM_OBJECT_NAME,
            OOM_OBJECT_CODE,
            OOM_OBJECT_DATA_TYPE,
            NODE_KEY_ATTRIBUTE_VISIBILITY,
            OOM_OBJECT_VOLATILE,
            OOM_OBJECT_STATIC,
            OOM_OBJECT_INITIAL_VALUE
    };

    public ClassAttributeHandler() {
        super(HANDLER_PATH, NODES);
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        convertPropertiesOfVisibility(NODE_KEY_ATTRIBUTE_VISIBILITY, OOMProject.OOM_OBJECT_VISIBILITY);
        convertCodeVariable(OOM_OBJECT_CODE);
    }

}
