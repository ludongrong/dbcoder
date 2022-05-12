package io.github.ludongrong.dbcoder.util;

import org.dom4j.Element;

import java.util.Optional;

public class DomUtil {

    public static String getReferenceJoinKey(Element e, String tag) {
        return Optional.ofNullable(e.element(tag)).map(t1 -> {
            return t1.element("Column");
        }).map(t2 -> {
            return t2.attributeValue("Ref");
        }).orElse(null);
    }

    public static String getText(Element element, String tag) {
        return Optional.ofNullable(element.element(tag)).map(t -> {
            return t.getText();
        }).orElse("Empty");
    }

    public static int getInt(Element element, String tag) {
        return Optional.ofNullable(element.element(tag)).map(t -> {
            String valString = t.getText();
            if (valString == null) {
                return 0;
            }

            try {
                return Integer.parseInt(valString);
            } catch (NumberFormatException e) {
                return 0;
            }
        }).orElse(0);
    }

    public static boolean getBoolean(Element element, String tag) {
        return Optional.ofNullable(element.element(tag)).map(t -> {
            String valString = t.getText();
            if (valString == null) {
                return false;
            }

            try {
                return Boolean.parseBoolean(valString);
            } catch (NumberFormatException e) {
                return false;
            }
        }).orElse(false);
    }
}
