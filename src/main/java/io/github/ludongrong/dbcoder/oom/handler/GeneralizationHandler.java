package io.github.ludongrong.dbcoder.oom.handler;


import org.dom4j.ElementPath;

/**
 * @author ldr
 * @version 1.0
 */
public class GeneralizationHandler extends RefHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Generalizations/Generalization";

    public static final String[] NODES = new String[]{
            "ObjectID", "Name", "Code"
    };

    public GeneralizationHandler() {
        super(HANDLER_PATH, NODES);
    }

    @Override
    public void onStart(ElementPath elementPath) {
        super.onStart(elementPath);
    }

}