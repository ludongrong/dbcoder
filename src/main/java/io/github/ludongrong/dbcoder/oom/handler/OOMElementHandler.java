package io.github.ludongrong.dbcoder.oom.handler;

import io.github.ludongrong.dbcoder.util.DomUtil;
import lombok.Getter;
import lombok.Setter;
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

        if (null == modelList) {
            modelList = new ArrayList<>();
        }
    }

    public void onStart(ElementPath elementPath) {
        Element element = elementPath.getCurrent();

        this.currentModel = new HashMap<>();
        this.currentModel.put("Id", element.attributeValue("Id"));
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
        this.currentModel = null;

        // xml元素 -> 销毁
        elementPath.getCurrent().detach();
    }
}
