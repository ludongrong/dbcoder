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

/** 
* Table
*
* @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
* @since 2022-03-24
*/
public class Table extends Element {

    private static final long serialVersionUID = 5683537028079665552L;

    /**
     * 列
     */
    @Getter
    @Setter
    private List<Column> columns;

    /**
     * 本表关联的父表 尾巴朝向本表的其他表
     */
    @Getter
    @Setter
    private List<Reference> parentReferences;

    /**
     * 本表关联的子表 箭头朝向本表的其他表
     */
    @Getter
    @Setter
    private List<Reference> childReferences;

    /**
     * 工程
     */
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

	/**
	 * 表 转 模板模型数据
	 * @param table
	 * @return
	 */
	public static Map<String, Object> toModel(Table table) {
	
	    Map<String, Object> model = new HashMap<String, Object>();
	
	    Project project = table.getProject();
	    String basePackage = project.getBasePackage() + "." + project.getProjectName();
	
	    List<Map<String, Object>> primaryColumns = Column.toModel(table.getColumns().stream().filter(t -> {
	        return t.isPrimaryKey();
	    }).collect(Collectors.toList()));

	    // 模型的列表不能是NULL值
	    List<Map<String, Object>> parentReferences = Reference.toModel(
	            Optional.ofNullable(table.getParentReferences())
	                    .orElse(new ArrayList<Reference>())
	    );

        // 模型的列表不能是NULL值
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