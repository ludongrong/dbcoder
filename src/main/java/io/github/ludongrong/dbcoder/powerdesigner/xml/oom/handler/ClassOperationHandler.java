package io.github.ludongrong.dbcoder.powerdesigner.xml.oom.handler;

import cn.hutool.core.util.StrUtil;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject;
import org.apache.commons.collections4.MapUtils;
import org.dom4j.ElementPath;

import java.util.Arrays;
import java.util.Objects;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject.*;

public class ClassOperationHandler extends OOMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Classes/Class/Operations/Operation";

    public static final String TAG = "Operations";

    public static final String NODE_KEY_OPERATION_VISIBILITY = "Operation.Visibility";

    public static final String NODE_KEY_OPERATION_ABSTRACT = "Operation.Abstract";

    public static final String NODE_KEY_OPERATION_STATIC = "Operation.Static";

    public static final String NODE_KEY_OPERATION_FINAL = "Operation.Final";

    public static final String NODE_KEY_EXTENDED_ATTRIBUTES_TEXT = "ExtendedAttributesText";

    String[] javaExtends = new String[]{OOM_OBJECT_SYNCHRONIZED, OOM_OBJECT_STRICTFP, OOM_OBJECT_NATIVE};

    public static final String[] NODES = new String[]{
            ELE_OBJECT_ID,
            ELE_OBJECT_NAME,
            ELE_OBJECT_CODE,
            OOM_OBJECT_RETURN_TYPE,
            NODE_KEY_OPERATION_VISIBILITY,
            NODE_KEY_OPERATION_ABSTRACT,
            NODE_KEY_OPERATION_STATIC,
            NODE_KEY_OPERATION_FINAL,
            NODE_KEY_EXTENDED_ATTRIBUTES_TEXT
    };

    ParameterHandler parameterHandler;

    public ClassOperationHandler(ParameterHandler parameterHandler) {
        super(HANDLER_PATH, NODES);
        this.parameterHandler = parameterHandler;
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        parameterHandler.onParentNodeHandlerEnd(ParameterHandler.TAG, this);
        convertPropertiesOfVisibility(NODE_KEY_OPERATION_VISIBILITY, OOMProject.OOM_OBJECT_VISIBILITY);
        convertProperties(NODE_KEY_OPERATION_ABSTRACT, OOMProject.OOM_OBJECT_ABSTRACT);
        convertProperties(NODE_KEY_OPERATION_STATIC, OOMProject.OOM_OBJECT_STATIC);
        convertProperties(NODE_KEY_OPERATION_FINAL, OOMProject.OOM_OBJECT_FINAL);
        parseExtendedAttributes();
    }

    /**
     * 解析Java扩展字段
     * synchronized, strictfp, native
     */
    private void parseExtendedAttributes() {
        String text = MapUtils.getString(getCurrentModel(), NODE_KEY_EXTENDED_ATTRIBUTES_TEXT, "");
        Arrays.asList(text.split(StrUtil.LF)).stream().forEach(txt -> {
            String javaExtend = StrUtil.getContainsStrIgnoreCase(txt, javaExtends);
            if (!Objects.isNull(javaExtend)) {
                boolean containsValue = StrUtil.containsAnyIgnoreCase(text, "true");
                getCurrentModel().put(javaExtend, containsValue ? "1" : "0");
            }
        });
    }

}
