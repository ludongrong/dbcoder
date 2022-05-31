package io.github.ludongrong.dbcoder.oom.handler;


import org.dom4j.ElementPath;

import static io.github.ludongrong.dbcoder.oom.OOMProject.*;

/**
 * 关联关系 - 继承
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-05-09
 */
public class GeneralizationHandler extends RefHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Generalizations/Generalization";

    public static final String[] NODES = new String[]{
            OOM_NODE_OBJECT_ID,
            OOM_OBJECT_NAME,
            OOM_OBJECT_CODE
    };

    public GeneralizationHandler() {
        super(HANDLER_PATH, NODES);
    }

    @Override
    public void onStart(ElementPath elementPath) {
        super.onStart(elementPath);
    }

}