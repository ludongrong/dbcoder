package ${basepackage}.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import cn.laocu.core.str.StriUtil;
import io.github.ludongrong.dbcoder.sql.DynamicCondition;

@Repository("${className}DaoImpl")
public class ${className}DaoImpl extends NamedParameterJdbcDaoSupport implements I${className}Dao {
    
    static final public RowMapper<${className}Model> _DEFAULT_MAPPER = new RowMapper<${className}Model>() {
        
		public ${className}Model mapRow(ResultSet rs, int rowNum) throws SQLException {
			${className}Model ${classNameVariable} = new ${className}Model();
			
		<#list columns as column>
			${classNameVariable}.set${column.javaName}(rs.get<#if column.javaType == "Integer">Int<#else>${column.javaType}</#if>(${className}Model._${column.name?upper_case}));
		<#if column.javaType != "String">
			if (rs.wasNull()) {
                ${classNameVariable}.set${column.javaName}(null);
            }
		</#if>
		</#list>
			
			return ${classNameVariable};
		}
	};
	
	@Autowired
	${className}DaoImpl(@Qualifier("dataSource") DataSource dataSource) {
        super(dataSource);
    }
	
	@Override
	public int create(${className}Model ${classNameVariable}) {
	    
		final String _sql = "INSERT INTO ${tableName} "
				+ "(<#list columns as column>${column.name}<#if column_has_next>, </#if></#list>) "
				+ "VALUES"
				+ "(<#list columns as column>?<#if column_has_next>, </#if></#list>)";
		return getJdbcTemplate().update(_sql, <#list columns as column>${classNameVariable}.get${column.javaName}<#if column_has_next>, </#if></#list>);
	}
	
	@Override
	public int delete(DynamicCondition dyc, Map<String, Object> param) {
		
	    final String _sql = "DELETE FROM ${tableName}";

	    List<Object> args = new ArrayList<Object>();
	    String sql = DynamicCondition.generate(_sql, dyc, param, args);
	    return getJdbcTemplate().update(sql, args.toArray());
	}

	@Override
	public int update(Map<String, Object> input, DynamicCondition dyc, Map<String, Object> param) {
	    
	    List<Object> args = new ArrayList<Object>();

        if (input.isEmpty()) {
            return -2;
        }
        
        final String _sql = "UPDATE ${tableName} SET";

		StringBuilder strBuilder = new StringBuilder(_sql);
        for (Map.Entry<String, Object> entry : input.entrySet()) {
            strBuilder.append(entry.getKey());
            args.add(entry.getValue());
        }
        strBuilder.delete(0, strBuilder.length());
        
        return getJdbcTemplate().update(DynamicCondition.generate(strBuilder.toString(), dyc, param, args), args.toArray());
	}
	
	@Override
	public long count(DynamicCondition dyc, Map<String, Object> param) {
	    
	    final String _sql = "SELECT COUNT(1) FROM ${tableName}";
	    List<Object> args = new ArrayList<Object>();
	    return getJdbcTemplate().queryForObject(DynamicCondition.generate(_sql, dyc, param, args), Long.class, args.toArray());
	}
	
	@Override
	public List<${className}Bo> query(DynamicCondition dyc, Map<String, Object> param) {
	    
		final String _sql = "SELECT <#list columns as column>${column.name}<#if column_has_next>, </#if></#list> FROM ${tableName}";
		List<Object> args = new ArrayList<Object>();
		return getJdbcTemplate().query(DynamicCondition.generate(_sql, dyc, param, args), _DEFAULT_MAPPER, args.toArray());
	}
}

