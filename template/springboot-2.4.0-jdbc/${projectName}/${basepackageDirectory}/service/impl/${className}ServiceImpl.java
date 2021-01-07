package ${basepackage}.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mpro.pro.fj.cs5g.dao.ICell4gDao;
import org.mpro.pro.fj.cs5g.svc.IGisZcqSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.ludongrong.dbcoder.entity.Table3Bo;
import io.github.ludongrong.dbcoder.sql.DynamicCondition;

@Service("${projectName}-${className}ServiceImpl")
public class ${className}ServiceImpl implements I${className}Service {
    
    static final public DynamicCondition _DEFAULT_CONDITION = new DynamicCondition(ConditionGroup.AND);
	static {
	<#list columns as column>
	    _DEFAULT_CONDITION.eq(${className}Bo._${column.name?upper_case}, "${column.javaNameVariable}");
	</#list>
	}
	
	private I${className}Dao ${classNameVariable}Dao;
	
	@Autowired(required = true)
    public void setBaseDao(@Qualifier("${projectName}-${className}DaoImpl") I${className}Dao baseDao) {
        ${classNameVariable}Dao = baseDao;
    }
	
	//@Resource(name = "${projectName}-XxxServiceImpl")
    //private IXxxService xxxService;
	
	@Transactional
	@Override
	public boolean create(${className}Bo ${classNameVariable}) {
		
		int result = ${classNameVariable}Dao.create(${classNameVariable});
		if (result != 1 && result != -2) {
			return false;
		}
		
		return true;
	}
	
	@Transactional
	@Override
	public int delete(DynamicCondition dyc, Map<String, Object> param) {
	    
		int result = ${classNameVariable}Dao.delete(dyc, param);
		if (result < 0) {
			return 0;
		}
		return result;
	}
	
	@Transactional
	@Override
    public int delete(Map<String, Object> param) {
        
        return delete(_DEFAULT_CONDITION, param);
    }
	
	@Transactional
	@Override
	public int delete(<#list primaryColumns as column>${column.javaType} ${column.javaNameVariable}<#if column_has_next>, </#if></#list>) {
		
	    Map<String, Object> param = new HashMap<String, Object>();
	    <#list primaryColumns as column>
	    param.put("${column.javaNameVariable}", ${column.javaNameVariable});
	    </#list>
	    return delete(_DEFAULT_CONDITION, param);
	}
	
	@Transactional
	@Override
	public int update(Map<String, Object> input, DynamicCondition dyc, Map<String, Object> param) {
		if (MapUtil.isEmpty(input)) {
			return 0;
		}

		int result = ${classNameVariable}Dao.update(input, dyc, param);
		if (result < 0) {
            return 0;
        }
        return result;
	}
	
	@Transactional
	@Override
    public int update(Map<String, Object> input, Map<String, Object> param) {

        return update(input, _DEFAULT_CONDITION, param);
    }
	
	@Transactional
	@Override
    public int update(${className}Bo ${classNameVariable}, <#list primaryColumns as column>${column.javaType} ${column.javaNameVariable}<#if column_has_next>, </#if></#list>) {
	    Map<String, Object> input = new HashMap<String, Object>();
<#list columns as column>
    <#if column.premaryKey?exists && column.premaryKey == false>
        input.put("_${column.name?upper_case}", ${classNameVariable}.get${column.javaName}());
    </#if>
</#list>

        Map<String, Object> param = new HashMap<String, Object>();
        <#list primaryColumns as column>
        param.put("${column.javaNameVariable}", ${column.javaNameVariable});
        </#list>
        
        return update(input, _DEFAULT_CONDITION, param);
    }
	
	@Override
	public long count(DynamicCondition dyc, Map<String, Object> param) {
		
	    return ${classNameVariable}Dao.count(dyc, param);
	}
	
	@Override
    public long count(Map<String, Object> param) {
        
        return ${classNameVariable}Dao.count(_DEFAULT_CONDITION, param);
    }

	@Override
	public List<${className}Bo> list(DynamicCondition dyc, Map<String, Object> param) {
		
	    return ${classNameVariable}Dao.query(dyc, param);
	}
	
	@Override
    public List<${className}Bo> list(Map<String, Object> param) {
        
        return ${classNameVariable}Dao.query(_DEFAULT_CONDITION, param);
    }
	
	@Override
    public List<${className}Bo> list(int offset, int limit, DynamicCondition dyc, Map<String, Object> param) {
        
        return ${classNameVariable}Dao.query(offset, limit, dyc, param);
    }
	
	@Override
    public List<${className}Bo> list(int offset, int limit, Map<String, Object> param) {
        
        return ${classNameVariable}Dao.query(offset, limit, _DEFAULT_CONDITION, param);
    }
	
	@Override
    public ${className}Bo get(<#list primaryColumns as column>${column.javaType} ${column.javaNameVariable}<#if column_has_next>, </#if></#list>) {
        
        Map<String, Object> param = new HashMap<String, Object>();
    <#list primaryColumns as column>
        param.put("${column.javaNameVariable}", ${column.javaNameVariable});
    </#list>
        
        List<${className}Bo> ${classNameVariable}List = ${classNameVariable}Dao.query(_DEFAULT_CONDITION, param);
        if (${classNameVariable}List.size() > 0) {
            return ${classNameVariable}List.get(0);
        }
        return null;
    }
    
<#list childSelfReferences as reference>
    @Override
    public List<${reference.className}Bo> list${reference.className}Bo(<#list reference.columns as column>${column.javaType} ${column.javaNameVariable}<#if column_has_next>, </#if></#list>) {

        Map<String, Object> param = new HashMap<String, Object>();
    <#list reference.columns as column>
        param.put("${column.javaNameVariable}", ${column.javaNameVariable});
    </#list>
    
        return ${classNameVariable}Dao.query(_DEFAULT_CONDITION, param);
    }

</#list>
}
