package io.github.ludongrong.dbcoder.powerdesigner.xml.oom.util;

import cn.hutool.core.util.StrUtil;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class OOMXmlUtil {

    static Map<String, String> uris = new HashMap();

    static {
        uris.put("o", "fooNamespace");
        uris.put("a", "barNamespace");
    }

    public static void read(InputStream in, ReaderHandler consumer) throws DocumentException {

        // 解析 -> DocumentFactory
        DocumentFactory factory = new DocumentFactory();
        factory.setXPathNamespaceURIs(uris);

        // 解析 -> SAXReader
        SAXReader reader = new SAXReader();
        reader.setDocumentFactory(factory);

        // 读取前
        consumer.onStart(reader);
        // 读取xml
        reader.read(in);
        // 读取后
        consumer.onEnd();
    }

    public static String getRefId(String elementName, Element element) {
        List<String> refIdList = getRefIdList(elementName, element);
        if (refIdList.isEmpty()) {
            return StrUtil.EMPTY;
        } else {
            return refIdList.get(0);
        }
    }

    public static List<String> getRefIdList(String elementName, Element element) {
        return Optional.ofNullable(element.element(elementName)).map(t -> {
            return t.elements().stream().map(e -> {
                return e.attributeValue("Ref");
            }).collect(Collectors.toList());
        }).orElse(Collections.emptyList());
    }

}
