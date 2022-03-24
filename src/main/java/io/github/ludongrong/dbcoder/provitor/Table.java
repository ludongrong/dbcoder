package io.github.ludongrong.dbcoder.provitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import io.github.ludongrong.dbcoder.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

public class Table extends Element {

    private static final long serialVersionUID = 5683537028079665552L;

    @Getter
    @Setter
    private List<Column> columns;

    @Getter
    @Setter
    private List<Reference> parentReferences;

    @Getter
    @Setter
    private List<Reference> childReferences;

    @Getter
    @Setter
    private Project project;

    public void addChildReference(Reference reference) {
        childReferences = Optional.ofNullable(childReferences).orElse(new ArrayList<Reference>());
        childReferences.add(reference);
    }

    public void addParentReference(Reference reference) {
        parentReferences = Optional.ofNullable(parentReferences).orElse(new ArrayList<Reference>());
        parentReferences.add(reference);
    }

    /**
     * 主键列 Column >转> Map<String, Column>
     * 
     * <pre>
     *   key -> Column.id
     *   key -> Column
     * </pre>
     * 
     * @return
     */
    public Map<String, Column> toPrimaryMap() {
         List<Column> primaryColumns = getColumns().stream().filter(t1 -> {
            return t1.isPrimaryKey();
        }).collect(Collectors.toList());
        		
         return Column.mapping(primaryColumns);
    }

    /**
     * 普通列 Column >转> Map<String, Column>
     * 
     * <pre>
     *   key -> Column.id
     *   key -> Column
     * </pre>
     * 
     * @return
     */
    public Map<String, Column> toColumnMap() {
        return Column.mapping(getColumns());
    }
    
    /**
     * Table >转> Map<String, Column>
     * 
     * <pre>
     *   key -> Table.id
     *   key -> Table
     * </pre>
     * 
     * @return
     */
    public static Map<String, Table> mapping(List<Table> tables) {
        return tables.stream().collect(Collectors.toMap((val) -> {
            return val.getId();
        }, (val) -> {
            return val;
        }, (oldValue, newValue) -> newValue));
    }

	public static Map<String, Object> toTableModel(Table table) {
	
	    Map<String, Object> model = new HashMap<String, Object>();
	
	    Project project = table.getProject();
	    String basePackage = project.getBasePackage() + "." + project.getProjectName();
	
	    List<Map<String, Object>> primaryColumns = Column.toModel(table.getColumns().stream().filter(t -> {
	        return t.isPrimaryKey();
	    }).collect(Collectors.toList()));
	
	    List<Map<String, Object>> parentReferences = Reference.toModel(
	            Optional.ofNullable(table.getParentReferences())
	                    .orElse(new ArrayList<Reference>())
	    );
	
	    List<Map<String, Object>> childReferences = Reference.toModel(
	            Optional.ofNullable(table.getChildReferences())
	                    .orElse(new ArrayList<Reference>())
	    );
	
	    model.put("projectName", project.getProjectName());
	    model.put("basePackage", basePackage);
	    model.put("basePackageDirectory", basePackage.replaceAll("\\.", "/"));
	    model.put("tableName", table.getName());
	    model.put("className", StringUtil.toJavaClassName(table.getName()));
	    model.put("classNameVariable", StringUtil.toJavaVariableName(table.getName()));
	    model.put("columns", Column.toModel(table.getColumns()));
	    model.put("primaryColumns", primaryColumns);
	    model.put("parentReferences", parentReferences);
	    model.put("childReferences", childReferences);
	
	    return model;
	}
}