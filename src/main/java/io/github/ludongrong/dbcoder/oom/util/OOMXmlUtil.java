package io.github.ludongrong.dbcoder.oom.util;

import org.dom4j.Element;

import java.util.Optional;

public class OOMXmlUtil {

    public static String getRefId(String elementName, Element element) {
        return Optional.ofNullable(element.element(elementName)).map(t -> {
            return t.elements().get(0);
        }).map(t -> {
            return t.attributeValue("Ref");
        }).orElse(null);
    }
}
