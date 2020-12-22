package io.github.ludongrong.dbcoder.pd;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;

import io.github.ludongrong.dbcoder.entity.PdFileBo;
import io.github.ludongrong.dbcoder.provitor.Column;
import io.github.ludongrong.dbcoder.provitor.Project;
import io.github.ludongrong.dbcoder.provitor.Reference;
import io.github.ludongrong.dbcoder.provitor.Table;

public class PdmReader {

    static Map<String, String> uris = new HashMap<String, String>();
    static {
        uris.put("o", "fooNamespace");
        uris.put("a", "barNamespace");
    }

    public static Project read(PdFileBo pdFileBo, InputStream in) throws DocumentException {

        DocumentFactory factory = new DocumentFactory();
        factory.setXPathNamespaceURIs(uris);

        Project project = new Project();
        project.setBasePackage(pdFileBo.getBasePackage());
        project.setProjectName(pdFileBo.getProjectName());

        DbmsHandler dbmsHandler = new DbmsHandler();
        TablesHandler tablesHandler = new TablesHandler(project);
        ReferenceHandler referenceHandler = new ReferenceHandler();

        // parse or create a document
        SAXReader reader = new SAXReader();
        reader.setDocumentFactory(factory);
        reader.addHandler("/Model/RootObject/Children/Model/Tables/Table", tablesHandler);
        reader.addHandler("/Model/RootObject/Children/Model/References/Reference", referenceHandler);
        reader.addHandler("/Model/RootObject/Children/Model/DBMS/Shortcut", dbmsHandler);
        reader.read(in);

        project.setTables(tablesHandler.getTableList());
        project.setDbType(dbmsHandler.getCode());
        if (project.getDbType() == null) {
            throw new IllegalStateException("support dbtype mysql oracle sqlserver");
        }

        Map<String, Table> tableMap = project.toTableMap();

        referenceHandler.getReferenceList().stream().forEach(t -> {

            Table parantTable = tableMap.get(t.getParentTableKeyId());
            Table childTable = tableMap.get(t.getChildTableKeyId());

            Map<String, Column> primaryMap = parantTable.toPrimaryMap();
            if (primaryMap.isEmpty()) {
                return;
            }

            Map<String, Column> childMap = childTable.toColumnMap();
            if (childMap.isEmpty()) {
                return;
            }

            List<Column> childReferenceColumnList = t.mappingColumn(primaryMap, childMap);
            if (childReferenceColumnList.size() != primaryMap.size()) {
                return;
            }

            Column[] parentJoinColumns = primaryMap.values().toArray(new Column[primaryMap.values().size()]);
            Column[] childJoinColumns = childReferenceColumnList.toArray(new Column[childReferenceColumnList.size()]);

            Reference reference = new Reference();
            reference.setReferenceTable(parantTable);
            reference.setJoinColumns(parentJoinColumns);
            childTable.addParentReference(reference);

            reference = new Reference();
            reference.setReferenceTable(childTable);
            reference.setJoinColumns(childJoinColumns);
            childTable.addChildSelfReference(reference);

            reference = new Reference();
            reference.setReferenceTable(childTable);
            reference.setJoinColumns(childJoinColumns);
            parantTable.addChildReference(reference);

            reference = new Reference();
            reference.setReferenceTable(parantTable);
            reference.setJoinColumns(parentJoinColumns);
            parantTable.addParentSelfReference(reference);
        });

        return project;
    }
}