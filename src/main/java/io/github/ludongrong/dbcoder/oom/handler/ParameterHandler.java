package io.github.ludongrong.dbcoder.oom.handler;

public class ParameterHandler extends OOMElementHandler {

    public static final String PARAMETER_HANDLER_PATH = "/Model/RootObject/Children/Model/Interfaces/Interface/Operations/Operation/Parameters/Parameter";

    public static final String TAG = "Parameters";

    public static final String[] NODES = new String[]{
            "ObjectID", "Name", "Code", "Parameter.DataType"
    };

    public ParameterHandler() {
        super(PARAMETER_HANDLER_PATH, NODES);
    }

}
