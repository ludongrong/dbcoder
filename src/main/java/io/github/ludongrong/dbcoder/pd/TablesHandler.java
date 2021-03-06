package io.github.ludongrong.dbcoder.pd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import io.github.ludongrong.dbcoder.provitor.Column;
import io.github.ludongrong.dbcoder.provitor.Project;
import io.github.ludongrong.dbcoder.provitor.Table;
import lombok.Getter;

public class TablesHandler implements ElementHandler {

    @Getter
    List<Table> tableList = new ArrayList<Table>();

    ColumnHandler columnHandler = null;
    KeysHandler keysHandler = null;
    PrimaryKeyHandler primaryKeyHandler = null;

    Table table;

    Project project;

    public void onStart(ElementPath path) {
        Element element = path.getCurrent();
        table = new Table();
        table.setId(element.attributeValue("Id"));
        table.setProject(project);

        columnHandler = new ColumnHandler(table);
        keysHandler = new KeysHandler();
        primaryKeyHandler = new PrimaryKeyHandler();

        path.addHandler("Columns/Column", columnHandler);
        path.addHandler("Keys/Key", keysHandler);
        path.addHandler("PrimaryKey", primaryKeyHandler);
    }

    public void onEnd(ElementPath path) {
        Element element = path.getCurrent();
        table.setName(DomUtil.getText(element, "Code"));
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

    public TablesHandler(Project project) {
        super();
        this.project = project;
    }
}
