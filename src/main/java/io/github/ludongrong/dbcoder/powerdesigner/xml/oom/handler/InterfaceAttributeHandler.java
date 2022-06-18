package io.github.ludongrong.dbcoder.powerdesigner.xml.oom.handler;

import org.dom4j.ElementPath;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject.*;

/**
 * 接口 - 属性
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-05-09
 */
public class InterfaceAttributeHandler extends OOMElementHandler {

    public static final String ATTRIBUTE_HANDLER_PATH = "/Model/RootObject/Children/Model/Interfaces/Interface/Attributes/Attribute";

    public static final String TAG = "Attributes";

    public static final String[] NODES = new String[]{
            ELE_OBJECT_ID,
            ELE_OBJECT_NAME,
            ELE_OBJECT_CODE,
            OOM_OBJECT_DATA_TYPE
    };

    public InterfaceAttributeHandler() {
        super(ATTRIBUTE_HANDLER_PATH, NODES);
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        convertCodeVariable(ELE_OBJECT_CODE);
    }

}
