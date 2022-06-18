package io.github.ludongrong.dbcoder.powerdesigner.xml.oom.util;

import org.dom4j.io.SAXReader;

public interface ReaderHandler {

    void onStart(SAXReader reader);

    void onEnd();
}
