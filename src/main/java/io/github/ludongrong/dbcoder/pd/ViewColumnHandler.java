package io.github.ludongrong.dbcoder.pd;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import io.github.ludongrong.dbcoder.provitor.Column;
import io.github.ludongrong.dbcoder.provitor.Table;
import lombok.Getter;

/** 
* ColumnHandler
* 
* <o:ViewColumn Id="o61">
*   <a:ObjectID>137D9E6D-6AE2-47CC-B6E6-43A754EB8A70</a:ObjectID>
*   <a:Name>ID</a:Name>
*   <a:Code>ID</a:Code>
*   <a:DataType>varchar(%n)</a:DataType>
*   <a:CustomDataType>varchar(%n)</a:CustomDataType>
*   <a:ExpressionEx>dsadfds</a:ExpressionEx>
* </o:ViewColumn>
*
* @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
* @since 2022-03-23
*/
public class ViewColumnHandler implements ElementHandler {

    @Getter
    List<Column> columnList = new ArrayList<Column>();

    Table table;

    public ViewColumnHandler(Table table) {
        this.table = table;
    }

    public void onStart(ElementPath path) {
    }

    public void onEnd(ElementPath path) {
        
    	Element element = path.getCurrent();
        
        Column column = new Column();
        column.setId(element.attributeValue("Id"));
        column.setName(DomUtil.getText(element, "Name"));
        column.setCode(DomUtil.getText(element, "Code"));
        column.setDataType(DomUtil.getText(element, "DataType"));
        column.setLength(0);
        column.setPrecision(0);
        column.setMandatory(false);
        column.setTable(table);
        columnList.add(column);
        
        element.detach();
    }
}
