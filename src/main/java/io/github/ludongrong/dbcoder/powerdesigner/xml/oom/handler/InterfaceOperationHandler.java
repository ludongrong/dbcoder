package io.github.ludongrong.dbcoder.powerdesigner.xml.oom.handler;

import org.dom4j.ElementPath;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject.*;

/**
 * 接口 - 函数
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-05-09
 */
public class InterfaceOperationHandler extends OOMElementHandler {

    public static final String OPERATION_HANDLER_PATH = "/Model/RootObject/Children/Model/Interfaces/Interface/Operations/Operation";

    public static final String TAG = "Operations";

    public static final String[] NODES = new String[]{
            ELE_OBJECT_ID,
            ELE_OBJECT_NAME,
            ELE_OBJECT_CODE,
            OOM_OBJECT_RETURN_TYPE
    };

    ParameterHandler parameterHandler;

    public InterfaceOperationHandler(ParameterHandler parameterHandler) {
        super(OPERATION_HANDLER_PATH, NODES);
        this.parameterHandler = parameterHandler;
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        parameterHandler.onParentNodeHandlerEnd(ParameterHandler.TAG, this);
    }

}
