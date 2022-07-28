package io.github.ludongrong.dbcoder.powerdesigner.xml.common.handler;

import cn.hutool.core.collection.CollectionUtil;
import io.github.ludongrong.dbcoder.powerdesigner.xml.common.PDProject;
import io.github.ludongrong.dbcoder.util.DomUtil;
import io.github.ludongrong.dbcoder.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.MapUtils;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static io.github.ludongrong.dbcoder.powerdesigner.xml.common.PDProject.CODE_CLASS_NAME;
import static io.github.ludongrong.dbcoder.powerdesigner.xml.common.PDProject.CODE_CLASS_NAME_VARIABLE;

/**
 * PDElementHandler
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-05-09
 */
public class PDElementHandler implements ElementHandler {

    @Getter
    @Setter
    List<Map<String, Object>> modelList = new ArrayList<>();

    @Getter
    String path;

    @Getter
    String[] nodes;

    @Getter
    Map<String, Object> currentModel;

    public PDElementHandler(String path, String[] nodes) {
        super();
        this.path = path;
        this.nodes = nodes;
    }

    public void onStart(ElementPath elementPath) {
        Element element = elementPath.getCurrent();

        if (null == modelList)
            modelList = new ArrayList<>();

        this.currentModel = new HashMap<>();
        this.currentModel.put(PDProject.ELE_ID, element.attributeValue(PDProject.ELE_ID));
    }

    public void onEnd(ElementPath elementPath) {
        Element element = elementPath.getCurrent();

        // 属性值 -> 读取
        IntStream.range(0, nodes.length)
                .mapToObj(i -> nodes[i])
                .forEach(tag -> {
                    this.currentModel.put(tag, DomUtil.getText(element, tag));
                });

        // 内存 -> 列表
        modelList.add(this.currentModel);

        // xml元素 -> 销毁
        elementPath.getCurrent().detach();
    }

    public Map<String, Object> getLastModel() {
        return CollectionUtil.getLast(getModelList());
    }

    /**
     * 当父级节点解析结束
     * @param currentNodeTag 自身节点标签名。如Operations、parameters
     * @param oomElementHandler
     */
    public void onParentNodeHandlerEnd(String currentNodeTag, PDElementHandler oomElementHandler) {
        List<Map<String, Object>> modelList = getModelList();

        Map<String, Object> lastModel = CollectionUtil.getLast(oomElementHandler.getModelList());
        lastModel.put(currentNodeTag, modelList);

        setModelList(null);
    }

    /**
     * 当父级节点解析结束
     * @param currentNodeTag 自身节点标签名。如Operations、parameters
     */
    public void onParentNodeHandlerEnd(String currentNodeTag) {
        setModelList(null);
    }

    /**
     * 修改 final、static修饰符
     * @param nodeKey 原有标记
     * @param oomKey final、static修饰符
     */
    protected void convertProperties(String nodeKey, String oomKey) {
        String properties = MapUtils.getString(currentModel, nodeKey);
        currentModel.put(oomKey, properties);
    }

    /**
     * 类名、接口、属性名转变量名
     * @param codeNodeKey 原有标记
     */
    protected void convertCodeVariable(String codeNodeKey) {
        Map<String, Object> lastModel = getLastModel();
        String code = MapUtils.getString(lastModel, codeNodeKey, "");

        /*下划线*/
        String codeUnderLine = StringUtil.toUnderscoreName(code);
        lastModel.put(PDProject.CODE_UNDERLINE_UPPER, codeUnderLine.toUpperCase());
        lastModel.put(PDProject.CODE_UNDERLINE_LOWER, codeUnderLine.toLowerCase());
        /*驼峰*/
        String codeClassName = StringUtil.toJavaClassName(codeUnderLine);
        lastModel.put(PDProject.CODE_CAMEL_FIRST_UPPER, codeClassName);
        lastModel.put(PDProject.CODE_CAMEL_FIRST_LOWER, StringUtil.uncapitalize(codeClassName));
        /*全大写或小写*/;
        String codeUpper = codeUnderLine.toUpperCase();
        lastModel.put(PDProject.CODE_UPPER, codeUpper);
        lastModel.put(PDProject.CODE_LOWER, codeUpper.toLowerCase());
        /*脊柱*/
        String codeKebabCase = codeUnderLine.replaceAll("_", "-");
        lastModel.put(PDProject.CODE_KEBAB_CASE_UPPER, codeKebabCase.toUpperCase());
        lastModel.put(PDProject.CODE_KEBAB_CASE_LOWER, codeKebabCase.toLowerCase());
    }

    /**
     * 类名、接口、属性名转变量名
     */
    protected void setClassNameAndVariable() {
        getCurrentModel().put(CODE_CLASS_NAME, getCurrentModel().get(PDProject.CODE_CAMEL_FIRST_UPPER));
        getCurrentModel().put(CODE_CLASS_NAME_VARIABLE, getCurrentModel().get(PDProject.CODE_CAMEL_FIRST_LOWER));
    }

}
