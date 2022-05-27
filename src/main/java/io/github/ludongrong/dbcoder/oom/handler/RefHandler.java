package io.github.ludongrong.dbcoder.oom.handler;


import io.github.ludongrong.dbcoder.oom.OOMProject;
import io.github.ludongrong.dbcoder.oom.util.OOMXmlUtil;
import org.dom4j.ElementPath;

import java.util.Map;

/**
 * @author ldr
 * @version 1.0
 */
public class RefHandler extends OOMElementHandler {

    public RefHandler(String path, String[] nodes) {
        super(path, nodes);
    }

    /**
     * 获取映射ID
     *
     * @param elementPath
     */
    @Override
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);

        String ref1Id = OOMXmlUtil.getRefId("Object1", elementPath.getCurrent());
        String ref2Id = OOMXmlUtil.getRefId("Object2", elementPath.getCurrent());

        Map<String, Object> lastModel = getLastModel();

        // 映射ID
        lastModel.put(OOMProject.OOM_REF1_ID, ref1Id);
        lastModel.put(OOMProject.OOM_REF2_ID, ref2Id);
    }

}