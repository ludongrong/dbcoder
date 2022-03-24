package io.github.ludongrong.dbcoder.provitor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

/** 
* ReferenceKey
*
* @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
* @since 2022-03-24
*/
public class ReferenceKey {

    /**
     * 关联关系 箭头方向的表
     */
    @Getter
    @Setter
    private String parentTableKeyId;

    /**
     * 关联关系 尾巴方向的表
     */
    @Getter
    @Setter
    private String childTableKeyId;

    /**
     * 箭头方向的表 与 尾巴方向的表 的关联字段
     */
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
		    	columnMapping.setMapping(columnMap2.get(joins[0]));
		    }
        	
            return columnMapping;
        }, Collectors.toList())).stream().filter(c -> {
            return c.getSelf() != null && c.getMapping() != null;
        }).collect(Collectors.toList());
    }
}