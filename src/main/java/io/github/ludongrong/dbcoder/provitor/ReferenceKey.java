package io.github.ludongrong.dbcoder.provitor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

public class ReferenceKey {

    @Getter
    @Setter
    private String parentTableKeyId;

    @Getter
    @Setter
    private String childTableKeyId;

    @Getter
    @Setter
    private List<String[]> join;

    /**
     * 列标记对应 转 列对应
     * 
     * <pre>
     *   String[0] -> 或父表主键列 或 子表外键列
     *   String[1] -> 或父表主键列 或 子表外键列
     *   
     *   对父表而言
     *   Column[0] -> 父表主键列
     *   Column[1] -> 子表外键列
     *   
     *   对子表而言
     *   Column[0] -> 子表外键列
     *   Column[1] -> 父表主键列
     * </pre>
     * 
     * @param columnMap1
     * @param columnMap2
     * @return
     */
    public List<ColumnMapping> toColumnMapping(Map<String, Column> columnMap1, Map<String, Column> columnMap2) {
        return getJoin().stream().collect(Collectors.mapping((joins) -> {
        	if(joins.length != 2) {
            	return null;
            }
        	
        	ColumnMapping columnMapping = new ColumnMapping();
        	
        	Column column = columnMap1.get(joins[0]);
		    if (column != null) {
		    	columnMapping.setSelf(column);
		    	columnMapping.setMapping(columnMap2.get(joins[1]));
		    } else {
		    	columnMapping.setSelf(columnMap1.get(joins[1]));
		    	columnMapping.setMapping(column);
		    }
        	
            return columnMapping;
        }, Collectors.toList())).stream().filter(c -> {
            return c.getSelf() != null && c.getMapping() != null;
        }).collect(Collectors.toList());
    }
}