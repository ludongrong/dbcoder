package io.github.ludongrong.dbcoder.oom.handler;

public class InterfaceAttributeHandler extends OOMElementHandler {

    public static final String ATTRIBUTE_HANDLER_PATH = "/Model/RootObject/Children/Model/Interfaces/Interface/Attributes/Attribute";

    public static final String TAG = "Attributes";

    public static final String[] NODES = new String[]{
            "ObjectID", "Name", "Code", "DataType"
    };

    public InterfaceAttributeHandler() {
        super(ATTRIBUTE_HANDLER_PATH, NODES);
    }

}
