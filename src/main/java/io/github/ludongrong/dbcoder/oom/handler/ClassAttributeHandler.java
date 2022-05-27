package io.github.ludongrong.dbcoder.oom.handler;

import io.github.ludongrong.dbcoder.oom.OOMProject;
import org.dom4j.ElementPath;

public class ClassAttributeHandler extends OOMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Classes/Class/Attributes/Attribute";

    public static final String TAG = "Attributes";

    public static final String NODE_KEY_ATTRIBUTE_VISIBILITY = "Attribute.Visibility";

    public static final String[] NODES = new String[]{
            "ObjectID", "Name", "Code", "DataType", NODE_KEY_ATTRIBUTE_VISIBILITY, "Volatile", "Static", "InitialValue"
    };

    public ClassAttributeHandler() {
        super(HANDLER_PATH, NODES);
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        convertPropertiesOfVisibility(NODE_KEY_ATTRIBUTE_VISIBILITY, OOMProject.OOM_VISIBILITY);
    }

}
