package io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.handler;

import io.github.ludongrong.dbcoder.powerdesigner.xml.common.handler.PDElementHandler;
import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.PDMProject;
import org.apache.commons.collections4.MapUtils;

import java.util.Optional;

/**
 * PDMElementHandler
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-05-09
 */
public class PDMElementHandler extends PDElementHandler {

    public PDMElementHandler(String path, String[] nodes) {
        super(path, nodes);
    }

    /**
     * code 转大写
     *
     * @param codeNodeKey
     */
    protected void convertCodeToUpper(String codeNodeKey) {
        String code = MapUtils.getString(getCurrentModel(), codeNodeKey);
        getCurrentModel().put(codeNodeKey, Optional.ofNullable(code)
                .orElse("")
                .toUpperCase());
    }

    /**
     * 标注是主键列
     */
    protected void setNotPrimaryKey() {
        getCurrentModel().put(PDMProject.COLUMN_PRIMARY_KEY, "0");
    }

}
