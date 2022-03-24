package io.github.ludongrong.dbcoder.provitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.ludongrong.dbcoder.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

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

	public static List<Map<String, Object>> toModel(List<Reference> referenceList) {
	
	    return referenceList.stream().collect(Collectors.mapping(t -> {
	        Map<String, Object> referenceModel = new HashMap<String, Object>();
	        
	        String tableName = t.getReferenceTable().getName();
	        
	        referenceModel.put("tableName", tableName);
	        referenceModel.put("className", StringUtil.toJavaClassName(tableName));
	        referenceModel.put("classNameVariable", StringUtil.toJavaVariableName(tableName));
	        referenceModel.put("joinColumns", Column.toModelForJoin(t.getColumnMappingList()));
	        
	        return referenceModel;
	    }, Collectors.toList()));
	}
}