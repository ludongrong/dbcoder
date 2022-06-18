package io.github.ludongrong.dbcoder.powerdesigner.xml.oom.handler;

import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject;
import org.dom4j.ElementPath;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject.*;

public class ClassAttributeHandler extends OOMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Classes/Class/Attributes/Attribute";

    public static final String TAG = "Attributes";

    public static final String NODE_KEY_ATTRIBUTE_VISIBILITY = "Attribute.Visibility";

    public static final String[] NODES = new String[]{
            ELE_OBJECT_ID,
            ELE_OBJECT_NAME,
            ELE_OBJECT_CODE,
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
        convertCodeVariable(ELE_OBJECT_CODE);
    }

}
