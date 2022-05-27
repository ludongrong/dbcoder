package io.github.ludongrong.dbcoder.oom.handler;


import org.dom4j.ElementPath;

/**
 * @author ldr
 * @version 1.0
 */
public class RealizationHandler extends RefHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Realizations/Realization";

    public static final String NODES[] = new String[]{
            "ObjectID", "Name", "Code"
    };

    public RealizationHandler() {
        super(HANDLER_PATH, NODES);
    }

    @Override
    public void onStart(ElementPath elementPath) {
        super.onStart(elementPath);
    }

}