package io.github.ludongrong.dbcoder.powerdesigner.xml.pdm;

import io.github.ludongrong.dbcoder.powerdesigner.entity.PdFileBo;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.util.OOMXmlUtil;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.util.ReaderHandler;
import io.github.ludongrong.dbcoder.powerdesigner.xml.pdm.handler.*;
import io.github.ludongrong.dbcoder.powerdesigner.xml.util.ObjectsUtil;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.*;

/**
 * PDMReader
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-24
 */
public class PDMReader {

    public static PDMProject read(PdFileBo pdFileBo, InputStream in) throws DocumentException {

        // 项目信息 -> 初始
        PDMProject project = PDMProject.onBeforeRead(pdFileBo);

        OOMXmlUtil.read(in, new ReaderHandler() {
            @Override
            public void onStart(SAXReader reader) {
                onReadStart(reader, project);
            }

            @Override
            public void onEnd() {
                onReadEnd(project);
            }
        });

        return project;
    }

    private static void onReadEnd(PDMProject project) {
        project.setDbType(project.getDbmsHandler().getDbType());
        if (project.getDbType() == null) {
            throw new IllegalStateException("Currently Support dbType mysql/oracle/sqlserver");
        }
        // Table的DataType转JdbcType
        project.getTableHandler().getModelList().forEach(tableModel -> {
            TableHandler.getColumnList(tableModel).forEach(columnModel -> {
                ObjectsUtil.convertDataTypeToJdbcType(columnModel, project.getDbType());
            });
            tableModel.put("dbType", project.getDbType());
        });
        // View的DataType转JdbcType
        project.getViewHandler().getModelList().forEach(viewModel -> {
            ViewHandler.getColumnList(viewModel).forEach(columnModel -> {
                ObjectsUtil.convertDataTypeToJdbcType(columnModel, project.getDbType());
            });
            viewModel.put("dbType", project.getDbType());
        });
        // 关联表和表的关系
        List<Map<String, Object>> modelList = ObjectsUtil.mergeObjects(project, project.getTableHandler(), project.getViewHandler());
        connectWithRef(project.getReferenceHandler(), modelList);
    }

    private static void onReadStart(SAXReader reader, PDMProject project) {
        project.setDbmsHandler(readDbms(reader));
        project.setTableHandler(readTable(reader));
        project.setViewHandler(readView(reader));
        project.setReferenceHandler(readReference(reader));
    }

    /**
     * 表的关联关系
     *
     * @param referenceHandler
     * @param modelList
     */
    private static void connectWithRef(ReferenceHandler referenceHandler, List<Map<String, Object>> modelList) {
        referenceHandler.getModelList().forEach(model -> {
            Map<String, Object> parentTable = ReferenceHandler.getParentTable(model, modelList);
            Map<String, Object> childTable = ReferenceHandler.getChildTable(model, modelList);

            // model表示关系
            List<Map<String, Object>> arrowColumnList = ReferenceHandler.listArrowColumn(model, parentTable, childTable);

            // childTable 的父级关系
            TableHandler.addRefParent(childTable, parentTable, arrowColumnList);

            // parentTable 的子级关系
            TableHandler.addRefChild(parentTable, childTable, arrowColumnList);
        });
    }

    private static DbmsHandler readDbms(SAXReader reader) {
        DbmsHandler dbmsHandler = new DbmsHandler();
        reader.addHandler(dbmsHandler.getPath(), dbmsHandler);
        return dbmsHandler;
    }

    private static TableHandler readTable(SAXReader reader) {
        TableColumnHandler tableColumnHandler = new TableColumnHandler();
        TableKeyHandler keysHandler = new TableKeyHandler();
        TableHandler tableHandler = new TableHandler(tableColumnHandler, keysHandler);

        reader.addHandler(tableHandler.getPath(), tableHandler);
        reader.addHandler(tableColumnHandler.getPath(), tableColumnHandler);
        reader.addHandler(keysHandler.getPath(), keysHandler);

        return tableHandler;
    }

    private static ReferenceHandler readReference(SAXReader reader) {
        ReferenceJoinHandler joinsHandler = new ReferenceJoinHandler();
        ReferenceHandler referenceHandler = new ReferenceHandler(joinsHandler);

        reader.addHandler(referenceHandler.getPath(), referenceHandler);
        reader.addHandler(joinsHandler.getPath(), joinsHandler);

        return referenceHandler;
    }

    private static ViewHandler readView(SAXReader reader) {
        ViewColumnHandler viewColumnHandler = new ViewColumnHandler();
        ViewHandler viewHandler = new ViewHandler(viewColumnHandler);

        reader.addHandler(viewHandler.getPath(), viewHandler);
        reader.addHandler(viewColumnHandler.getPath(), viewColumnHandler);

        return viewHandler;
    }

}