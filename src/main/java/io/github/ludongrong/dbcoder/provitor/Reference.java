package io.github.ludongrong.dbcoder.provitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.ludongrong.dbcoder.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

/** 
* Reference
*
* @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
* @since 2022-03-24
*/
public class Reference {

    /**
     * 关联表
     */
    @Getter
    @Setter
    private Table referenceTable;

    /**
     * 关联列
     * 
     * <pre>
     *   Column[0] -> 本身表列
     *   Column[1] -> 关联表列
     * </pre>
     */
    @Getter
    @Setter
    private List<ColumnMapping> columnMappingList;

	/**
	 * 表的关联 转 模板模型数据
	 * @param referenceList
	 * @return
	 */
	public static List<Map<String, Object>> toModel(List<Reference> referenceList) {
	
	    return referenceList.stream().collect(Collectors.mapping(t -> {
	        Map<String, Object> referenceModel = new HashMap<String, Object>();
	        
	        String tableName = t.getReferenceTable().getName();
	        
	        referenceModel.put("tableName", tableName);
	        referenceModel.put("className", StringUtil.toJavaClassName(tableName));
	        referenceModel.put("classNameVariable", StringUtil.toJavaVariableName(tableName));
	        referenceModel.put("columnMappings", Column.toModelForJoin(t.getColumnMappingList()));
	        
	        return referenceModel;
	    }, Collectors.toList()));
	}
}