package io.github.ludongrong.dbcoder.pdm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.github.ludongrong.dbcoder.pdm.handler.DbmsHandler;
import io.github.ludongrong.dbcoder.pdm.handler.ReferenceHandler;
import io.github.ludongrong.dbcoder.pdm.handler.TablesHandler;
import io.github.ludongrong.dbcoder.pdm.handler.ViewsHandler;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;

import io.github.ludongrong.dbcoder.entity.PdFileBo;

public class PDMReader {

    static Map<String, String> uris = new HashMap<String, String>();
    static {
        uris.put("o", "fooNamespace");
        uris.put("a", "barNamespace");
    }

    public static PDMProject read(PdFileBo pdFileBo, InputStream in) throws DocumentException {

        DocumentFactory factory = new DocumentFactory();
        factory.setXPathNamespaceURIs(uris);

        PDMProject project = new PDMProject();
        project.setBasePackage(pdFileBo.getBasePackage());
        project.setProjectName(pdFileBo.getProjectName());
        project.setCurrentDate(new Date());

        DbmsHandler dbmsHandler = new DbmsHandler();
        TablesHandler tablesHandler = new TablesHandler(project);
        ViewsHandler viewsHandler = new ViewsHandler(project);
        ReferenceHandler referenceHandler = new ReferenceHandler();

        // parse or create a document
        SAXReader reader = new SAXReader();
        reader.setDocumentFactory(factory);
        reader.addHandler("/Model/RootObject/Children/Model/Tables/Table", tablesHandler);
        reader.addHandler("/Model/RootObject/Children/Model/Views/View", viewsHandler);
        reader.addHandler("/Model/RootObject/Children/Model/References/Reference", referenceHandler);
        reader.addHandler("/Model/RootObject/Children/Model/DBMS/Shortcut", dbmsHandler);
        reader.read(in);

        ArrayList<Table> tables = new ArrayList<>();
        tables.addAll(tablesHandler.getTableList());
        tables.addAll(viewsHandler.getTableList());

        project.setTables(tables);
        project.setDbType(dbmsHandler.getCode());
        if (project.getDbType() == null) {
            throw new IllegalStateException("support dbtype mysql oracle sqlserver");
        }

        // 关联字段 绑定到 表
        referenceBingTable(project, referenceHandler);

        return project;
    }

	private static void referenceBingTable(PDMProject project, ReferenceHandler referenceHandler) {
		
		Map<String, Table> tableMap = Table.mapping(project.getTables());

        referenceHandler.getReferenceList().stream().forEach(t -> {

        	// 相当于关联的箭头
            Table parentTable = tableMap.get(t.getParentTableKeyId());
            // 相当于关联的末尾
            Table childTable = tableMap.get(t.getChildTableKeyId());

			Map<String, Column> primaryMap = parentTable.toPrimaryMap();
			if (primaryMap.isEmpty()) {
				return;
			}

			Map<String, Column> childMap = childTable.toColumnMap();
			if (childMap.isEmpty()) {
				return;
			}
            
            // 父表的主键个数
            int primaryColumnSize = primaryMap.size();

            // 子表外键
            // 父表的主键 对应 子表的外键
            // 跳过 > 主键与外键不一致的案例
            if (t.getJoin().size() != primaryColumnSize) {
                return;
            }

            // 子表 指向 父表 的关联
            // 子表的外键字段 对应 父表的主键字段
            Reference reference = new Reference();
            reference.setReferenceTable(parentTable);
            reference.setColumnMappingList(t.toColumnMapping(childMap, primaryMap));
            childTable.addParentReference(reference);

            // 父表 被 子表 指向 的关联
            // 父表的主键字段 对应 子表的外键字段
            reference = new Reference();
            reference.setReferenceTable(childTable);
            reference.setColumnMappingList(t.toColumnMapping(primaryMap, childMap));
            parentTable.addChildReference(reference);
        });
	}
}