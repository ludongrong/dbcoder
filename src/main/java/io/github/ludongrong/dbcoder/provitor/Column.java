package io.github.ludongrong.dbcoder.provitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.ludongrong.dbcoder.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

/** 
* Column
*
* @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
* @since 2022-03-23
*/
public class Column extends Element {

	private static final long serialVersionUID = 7019859125530766748L;

    /**
	 * 类型 > 数据库
	 */
	@Getter
    @Setter
    private String dataType;

	/**
	 * 长度
	 */
    @Getter
    @Setter
    private int length;

	/**
	 * 尾数
	 */
    @Getter
    @Setter
    private int precision;

	/**
	 * 不为空
	 */
	@Getter
	@Setter
	private boolean mandatory;

	/**
	 * 主键
	 */
	@Getter
	@Setter
	private boolean primaryKey;

	/**
	 * 类型 > JAVA
	 */
    @Getter
    @Setter
    private String javaType;
    
	/**
	 * 名称 > JAVA
	 */
    @Getter
    @Setter
    private String javaName;

	/**
	 * 所属表
	 */
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

	/**
	 * 列 转 模板模型数据
	 * 
	 * @param column
	 * @return
	 */
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

	/**
	 * 列 转 模板模型数据
	 * 
	 * @param columnList
	 * @return
	 */
	public static List<Map<String, Object>> toModel(List<Column> columnList) {
	
	    return columnList.stream().collect(Collectors.mapping(t -> {
	    	String columnType = StringUtil.subStringBeforeParenthesis(t.getDataType());
	
	        String dbType = t.getTable().getProject().getDbType();
	
	        String preferredJavaType = JavaTypes.getPreferredJavaType(dbType,
	                columnType,
	                t.getLength(),
	                t.getPrecision());
	
	        t.setJavaType(preferredJavaType);
	        t.setJavaName(StringUtil.toJavaClassName(t.getName()));
	
	        return toModel(t);
	    }, Collectors.toList()));
	}

	/**
	 * 父表列 <-对应-> 子表列 转 模板模型数据
	 * 
	 * @param columnMappingList
	 * @return
	 */
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
}