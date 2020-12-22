package io.github.ludongrong.dbcoder.pd;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import io.github.ludongrong.dbcoder.provitor.Column;
import io.github.ludongrong.dbcoder.provitor.Table;
import lombok.Getter;

public class ColumnHandler implements ElementHandler {

    @Getter
    List<Column> columnList = new ArrayList<Column>();

    Table table;

    public ColumnHandler(Table table) {
        this.table = table;
    }

    public void onStart(ElementPath path) {
    }

    public void onEnd(ElementPath path) {
        Element element = path.getCurrent();
        Column column = new Column();
        column.setId(element.attributeValue("Id"));
        column.setName(DomUtil.getText(element, "Code"));
        column.setDataType(DomUtil.getText(element, "DataType"));
        column.setLength(DomUtil.getInt(element, "Length"));
        column.setPrecision(DomUtil.getInt(element, "Precision"));
        column.setMandatory(DomUtil.getBoolean(element, "Column.Mandatory"));
        column.setTable(table);
        columnList.add(column);
        element.detach();
    }
}
