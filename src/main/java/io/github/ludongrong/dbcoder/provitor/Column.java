package io.github.ludongrong.dbcoder.provitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.ludongrong.dbcoder.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

public class Column extends Element {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7019859125530766748L;

	@Getter
    @Setter
    private String dataType;

    @Getter
    @Setter
    private int length;

    @Getter
    @Setter
    private int precision;

    @Getter
    @Setter
    private boolean mandatory;

    @Getter
    @Setter
    private boolean primaryKey;

    @Getter
    @Setter
    private String javaType;
    
    @Getter
    @Setter
    private String javaName;

    @Getter
    @Setter
    private Table table;
    
    /**
     * Column >转> Map<String, Column>
     * 
     * <pre>
     *   key -> Column.id
     *   key -> Column
     * </pre>
     * 
     * @return
     */
    public static Map<String, Column> mapping(List<Column> columns) {
		return columns.stream().collect(Collectors.toMap(val1 -> {
            return val1.getId();
        }, val2 -> {
            return val2;
        }, (oldValue, newValue) -> newValue));
	}

	static Map<String, Object> toModel(Column column) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("javaName", StringUtil.toJavaClassName(column.getName()));
		model.put("javaNameVariable", StringUtil.toJavaVariableName(column.getName()));
		model.put("javaType", column.getJavaType());
		model.put("name", column.getName());
		model.put("dataType", column.getDataType());
		model.put("length", column.getLength());
		model.put("precision", column.getPrecision());
		model.put("mandatory", column.isMandatory());
		model.put("primaryKey", column.isPrimaryKey());
	
		return model;
	}

	static List<Map<String, Object>> toModelForJoin(List<ColumnMapping> columnMappingList) {
		
		List<Map<String, Object>> columnModelList = new ArrayList<Map<String, Object>>();
		
		for (ColumnMapping columnMapping : columnMappingList) {
			Map<String, Object> model = new HashMap<String, Object>();
		    model.put("self", toModel(columnMapping.getSelf()));
		    model.put("mapping", toModel(columnMapping.getMapping()));
		    columnModelList.add(model);
		}
		return columnModelList;
	}

	public static List<Map<String, Object>> toModel(List<Column> columnList) {
	
	    return columnList.stream().collect(Collectors.mapping(t -> {
	    	String columnType = StringUtil.subStringBeforeParenthesis(t.getDataType());
	
	        String dbType = t.getTable().getProject().getDbType();
	
	        String preferredJavaType = JavaTypesUtils.getPreferredJavaType(dbType,
	                columnType,
	                t.getLength(),
	                t.getPrecision());
	
	        t.setJavaType(preferredJavaType);
	        t.setJavaName(StringUtil.toJavaClassName(t.getName()));
	
	        return toModel(t);
	    }, Collectors.toList()));
	}
}