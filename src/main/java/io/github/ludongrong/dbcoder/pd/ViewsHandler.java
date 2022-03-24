package io.github.ludongrong.dbcoder.pd;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import io.github.ludongrong.dbcoder.provitor.Project;
import io.github.ludongrong.dbcoder.provitor.Table;
import lombok.Getter;

/** 
* ViewHandler
* 
* <pre>
* 
* </pre>
*
* @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
* @since 2022-03-23
*/
public class ViewsHandler implements ElementHandler {

    @Getter
    List<Table> tableList = new ArrayList<Table>();

    ViewColumnHandler viewColumnHandler = null;

    Table table;

    Project project;

    public void onStart(ElementPath path) {
    	// <o:View Id="o11">
        Element element = path.getCurrent();
        table = new Table();
        table.setId(element.attributeValue("Id"));
        table.setProject(project);

        viewColumnHandler = new ViewColumnHandler(table);

        path.addHandler("Columns/ViewColumn", viewColumnHandler);
    }

    public void onEnd(ElementPath path) {
        Element element = path.getCurrent();
        table.setName(DomUtil.getText(element, "Code"));
        table.setColumns(viewColumnHandler.getColumnList());

        tableList.add(table);
        table = null;

        path.removeHandler("Table");
        path.getCurrent().detach();
    }

    public ViewsHandler(Project project) {
        super();
        this.project = project;
    }
}
