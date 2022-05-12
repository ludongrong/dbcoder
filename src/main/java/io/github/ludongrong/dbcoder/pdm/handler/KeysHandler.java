package io.github.ludongrong.dbcoder.pdm.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import lombok.Getter;

public class KeysHandler implements ElementHandler {

    @Getter
    Map<String, String[]> keysMap = new HashMap<String, String[]>();

    public void onStart(ElementPath path) {
    }

    public void onEnd(ElementPath path) {
        Element element = path.getCurrent();

        String keyId = element.attributeValue("Id");

        String[] keys = Optional.ofNullable(element.element("Key.Columns")).map(t -> {
            return t.elements("Column");
        }).map(t -> {
            String[] k = new String[t.size()];
            for (int i = 0; i < t.size(); i++) {
                k[i] = t.get(i).attributeValue("Ref");
            }
            return k;
        }).orElse(new String[] {});

        keysMap.put(keyId, keys);

        element.detach();
    }
}