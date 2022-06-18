package io.github.ludongrong.dbcoder.powerdesigner.xml.oom.handler;


import org.dom4j.ElementPath;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject.*;

/**
 * 关联关系 - 实现
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-05-09
 */
public class RealizationHandler extends RefHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Realizations/Realization";

    public static final String NODES[] = new String[]{
            ELE_OBJECT_ID,
            ELE_OBJECT_NAME,
            ELE_OBJECT_CODE
    };

    public RealizationHandler() {
        super(HANDLER_PATH, NODES);
    }

    @Override
    public void onStart(ElementPath elementPath) {
        super.onStart(elementPath);
    }

}