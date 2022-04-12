package io.github.ludongrong.dbcoder.pd;

import java.util.Optional;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import com.alibaba.druid.util.JdbcUtils;

import lombok.Getter;

/** 
* DbmsHandler
*
*<c:DBMS>
<o:Shortcut Id="o6">
<a:ObjectID>8EFCD1A1-B9BC-44ED-9B27-DECA3D292DF0</a:ObjectID>
<a:Name>MySQL 5.0</a:Name>
<a:Code>MYSQL50</a:Code>
<a:CreationDate>1525679047</a:CreationDate>
<a:Creator>Don.ron</a:Creator>
<a:ModificationDate>1525679048</a:ModificationDate>
<a:Modifier>Don.ron</a:Modifier>
<a:TargetStereotype/>
<a:TargetID>F4F16ECD-F2F1-4006-AF6F-638D5C65F35E</a:TargetID>
<a:TargetClassID>4BA9F647-DAB1-11D1-9944-006097355D9B</a:TargetClassID>
</o:Shortcut>
</c:DBMS>
*
* @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
* @since 2022-03-23
*/
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
            } else if (code.startsWith("pgsql")) {
                dbType = JdbcUtils.POSTGRESQL;
            }
        }

        code = dbType;

        element.detach();
    }
}