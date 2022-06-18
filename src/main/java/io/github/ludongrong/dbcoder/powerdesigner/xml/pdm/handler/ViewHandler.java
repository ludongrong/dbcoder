package io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.handler;

import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.PDMProject;
import org.dom4j.ElementPath;

import java.util.List;
import java.util.Map;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject.*;

/**
 * ViewHandler
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-23
 */
public class ViewHandler extends PDMElementHandler {

    public static final String HANDLER_PATH = "/Model/RootObject/Children/Model/Views/View";

    // 标记 - 判别Model归属View
    public static final String TAG_KEY = "Tag";
    public static final String TAG_VALUE = "View";

    public static final String[] NODES = new String[]{
            ELE_OBJECT_ID,
            ELE_OBJECT_NAME,
            ELE_OBJECT_CODE,
            PDMProject.ELE_COMMENT
    };

    ViewColumnHandler viewColumnHandler;

    public ViewHandler(ViewColumnHandler viewColumnHandler) {
        super(HANDLER_PATH, NODES);
        this.viewColumnHandler = viewColumnHandler;
    }

    @Override
    public void onStart(ElementPath elementPath) {
        super.onStart(elementPath);
        getCurrentModel().put(TAG_KEY, TAG_VALUE);
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        viewColumnHandler.onParentNodeHandlerEnd(ViewColumnHandler.TAG, this);
        convertCodeToUpper(PDMProject.ELE_OBJECT_CODE);
        convertCodeVariable(ELE_OBJECT_CODE);
        setClassNameAndVariable();
    }

    public static List<Map<String, Object>> getColumnList(Map<String, Object> model) {
        return (List) model.get(ViewColumnHandler.TAG);
    }

}
