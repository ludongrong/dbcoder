package io.github.ludongrong.dbcoder.powerdesigner.xml.oom.handler;

import io.github.ludongrong.dbcoder.powerdesigner.xml.common.handler.PDElementHandler;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.OOMProject;
import org.apache.commons.collections4.MapUtils;

/**
 * OOMElementHandler
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-05-09
 */
public class OOMElementHandler extends PDElementHandler {

    public OOMElementHandler(String path, String[] nodes) {
        super(path, nodes);
    }

    /**
     * 修改 public、private...修饰符
     * @param nodeKey 原有标记
     * @param oomKey public、private修饰符
     */
    protected void convertPropertiesOfVisibility(String nodeKey, String oomKey) {
        String visibility = MapUtils.getString(getCurrentModel(), nodeKey);
        OOMProject.VISIBILITY_ENUM visibilityEnum = OOMProject.VISIBILITY_ENUM.convertEnum(visibility, OOMProject.VISIBILITY_ENUM.PUBLIC);
        getCurrentModel().put(oomKey, visibilityEnum.name());
    }

}
