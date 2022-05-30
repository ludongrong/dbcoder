package io.github.ludongrong.dbcoder.oom.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.ludongrong.dbcoder.oom.OOMProject;
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

public class OOMElementHandler implements ElementHandler {

    @Getter
    @Setter
    List<Map<String, Object>> modelList = new ArrayList<>();

    @Getter
    String path;

    @Getter
    String[] nodes;

    @Getter
    Map<String, Object> currentModel;

    public OOMElementHandler(String path, String[] nodes) {
        super();
        this.path = path;
        this.nodes = nodes;
    }

    public void onStart(ElementPath elementPath) {
        Element element = elementPath.getCurrent();

        if (null == modelList)
            modelList = new ArrayList<>();

        this.currentModel = new HashMap<>();
        this.currentModel.put(OOMProject.OOM_NODE_ELE_ID, element.attributeValue(OOMProject.OOM_NODE_ELE_ID));
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
    public void onParentNodeHandlerEnd(String currentNodeTag, OOMElementHandler oomElementHandler) {
        List<Map<String, Object>> modelList = getModelList();

        Map<String, Object> lastModel = CollectionUtil.getLast(oomElementHandler.getModelList());
        lastModel.put(currentNodeTag, modelList);

        setModelList(null);
    }

    /**
     * 修改 public、private...修饰符
     * @param nodeKey 原有标记
     * @param oomKey public、private修饰符
     */
    protected void convertPropertiesOfVisibility(String nodeKey, String oomKey) {
        String visibility = MapUtils.getString(currentModel, nodeKey);
        OOMProject.VISIBILITY_ENUM visibilityEnum = OOMProject.VISIBILITY_ENUM.convertEnum(visibility, OOMProject.VISIBILITY_ENUM.PUBLIC);
        currentModel.put(oomKey, visibilityEnum.name());
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

        String codeUnderLine = StrUtil.toUnderlineCase(code);
        lastModel.put(OOMProject.CODE_UNDERLINE_UPPER, codeUnderLine.toUpperCase());
        lastModel.put(OOMProject.CODE_UNDERLINE_LOWER, codeUnderLine.toLowerCase());
        lastModel.put(OOMProject.CODE_CAMEL_FIRST_UPPER, StringUtil.toJavaClassName(codeUnderLine));
        lastModel.put(OOMProject.CODE_CAMEL_FIRST_LOWER, StringUtil.toJavaVariableName(codeUnderLine));
    }

}
