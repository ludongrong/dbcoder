package io.github.ludongrong.dbcoder.pd;

import java.util.Optional;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import com.alibaba.druid.util.JdbcUtils;

import lombok.Getter;

public class DbmsHandler implements ElementHandler {

    @Getter
    String code;

    public void onStart(ElementPath path) {
    }

    public void onEnd(ElementPath path) {
        Element element = path.getCurrent();
        code = Optional.ofNullable(element.element("Code")).map(t -> {
            return t.getText();
        }).orElse(null);

        code = code.toLowerCase();

        String dbType = null;
        if (code != null) {
            if (code.startsWith(JdbcUtils.ORACLE)) {
                dbType = JdbcUtils.ORACLE;
            } else if (code.startsWith(JdbcUtils.SQL_SERVER)) {
                dbType = JdbcUtils.SQL_SERVER;
            } else if (code.startsWith(JdbcUtils.MYSQL)) {
                dbType = JdbcUtils.MYSQL;
            }
        }

        code = dbType;

        element.detach();
    }
}