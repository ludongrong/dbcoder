package ${basePackage}.service;

import java.util.List;
import java.util.Map;

import io.github.ludongrong.dbcoder.entity.Table3Bo;
import io.github.ludongrong.dbcoder.sql.DynamicCondition;

public interface I${className}Service {
    
	boolean create(${className}Bo ${classNameVariable});

	int delete(DynamicCondition dyc, Map<String, Object> param);
	
	int delete(Map<String, Object> param);
	
	int delete(<#list primaryColumns as column>${column.javaType} ${column.javaName}<#if column_has_next>, </#if></#list>);

	int update(Map<String, Object> input, DynamicCondition dyc, Map<String, Object> param);

	int update(Map<String, Object> input, Map<String, Object> param);
	
	int update(${className}Bo ${classNameVariable}, <#list primaryColumns as column>${column.javaType} ${column.javaName}<#if column_has_next>, </#if></#list>);
	
	long count(DynamicCondition dyc, Map<String, Object> param);
	
	long count(Map<String, Object> param);
	
	List<${className}Bo> list(DynamicCondition dyc, Map<String, Object> param);
	
	List<${className}Bo> list(Map<String, Object> param);

	List<${className}Bo> list(int offset, int limit, DynamicCondition dyc, Map<String, Object> param);
	
	List<${className}Bo> list(int offset, int limit, Map<String, Object> param);
	
	${className}Bo get(<#list primaryColumns as column>${column.javaType} ${column.javaName}<#if column_has_next>, </#if></#list>);
    
<#list childReferences as reference>
    List<${reference.className}Bo> list${reference.className}Bo(<#list reference.columnMappings as columnMapping>${columnMapping.mapping.javaType} ${columnMapping.mapping.javaName}<#if columnMapping_has_next>, </#if></#list>);

</#list>
}
