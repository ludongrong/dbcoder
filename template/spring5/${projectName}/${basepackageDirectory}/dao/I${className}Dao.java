package ${basepackage}.dao;

import java.util.Map;
import io.github.ludongrong.dbcoder.sql.DynamicCondition;
import ${basepackage}.entity.${className}Bo;

public interface I${className}Dao {
    
    int create(${className}Bo ${classNameVariable});
    
    int delete(DynamicCondition dyc, Map<String, Object> param);
    
    int update(Map<String, Object> input, DynamicCondition dyc, Map<String, Object> param);
    
    long count(DynamicCondition dyc, Map<String, Object> param);
    
    List<${className}Bo> query(DynamicCondition dyc, Map<String, Object> param);
    
    List<${className}Bo> query(int offset, int limit, DynamicCondition dyc, Map<String, Object> param);
}
