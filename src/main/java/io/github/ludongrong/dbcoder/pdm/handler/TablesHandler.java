package io.github.ludongrong.dbcoder.pdm.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.ludongrong.dbcoder.util.DomUtil;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import io.github.ludongrong.dbcoder.pdm.Column;
import io.github.ludongrong.dbcoder.pdm.PDMProject;
import io.github.ludongrong.dbcoder.pdm.Table;
import lombok.Getter;

public class TablesHandler implements ElementHandler {

    @Getter
    List<Table> tableList = new ArrayList<Table>();

    ColumnHandler columnHandler = null;
    KeysHandler keysHandler = null;
    PrimaryKeyHandler primaryKeyHandler = null;

    Table table;

    PDMProject project;

    public void onStart(ElementPath path) {
        Element element = path.getCurrent();
        table = new Table();
        table.setId(element.attributeValue("Id"));
        table.setProject(project);

        // 列
        columnHandler = new ColumnHandler(table);
        // 普通键
        keysHandler = new KeysHandler();
        // 主键
        primaryKeyHandler = new PrimaryKeyHandler();

        path.addHandler("Columns/Column", columnHandler);
        path.addHandler("Keys/Key", keysHandler);
        path.addHandler("PrimaryKey", primaryKeyHandler);
    }

    public void onEnd(ElementPath path) {
        Element element = path.getCurrent();
        table.setName(DomUtil.getText(element, "Name"));
        table.setCode(DomUtil.getText(element, "Code"));
        table.setColumns(columnHandler.getColumnList());

        Map<String, String[]> keysMap = keysHandler.getKeysMap();
        if (keysMap.isEmpty() == false) {
            String[] keys = keysMap.get(primaryKeyHandler.getKeyId());
            for (int i = 0; i < table.getColumns().size(); i++) {
                Column column = table.getColumns().get(i);

                // 从
                int l = keys.length;
                for (int j = 0; j < l; j++) {
                    if (column.getId().equals(keys[j])) {
                        column.setPrimaryKey(true);
                        keys[j] = keys[l - 1];
                        l--;
                    }
                }
            }
        }

        tableList.add(table);
        table = null;

        path.removeHandler("Table");
        path.getCurrent().detach();
    }

    public TablesHandler(PDMProject project) {
        super();
        this.project = project;
    }
}
