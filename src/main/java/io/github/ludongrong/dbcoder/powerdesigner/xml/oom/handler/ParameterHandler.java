package io.github.ludongrong.dbcoder.powerdesigner.xml.oom.handler;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject.*;

public class ParameterHandler extends OOMElementHandler {

    public static final String PARAMETER_HANDLER_PATH = "/Model/RootObject/Children/Model/Interfaces/Interface/Operations/Operation/Parameters/Parameter";

    public static final String TAG = "Parameters";

    public static final String[] NODES = new String[]{
            ELE_OBJECT_ID,
            ELE_OBJECT_NAME,
            ELE_OBJECT_CODE,
            OOM_NODE_PARAMETER_DATA_TYPE
    };

    public ParameterHandler() {
        super(PARAMETER_HANDLER_PATH, NODES);
    }

}
