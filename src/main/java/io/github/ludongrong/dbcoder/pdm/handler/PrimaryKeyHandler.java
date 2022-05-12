package io.github.ludongrong.dbcoder.pdm.handler;

import java.util.Optional;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import lombok.Getter;

public class PrimaryKeyHandler implements ElementHandler {

    @Getter
    String keyId;

    public void onStart(ElementPath path) {
    }

    public void onEnd(ElementPath path) {
        Element element = path.getCurrent();
        keyId = Optional.ofNullable(element.element("Key")).map(t -> {
            return t.attributeValue("Ref");
        }).orElse(null);
        element.detach();
    }
}
