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

@Repository("${projectName}-${className}DaoImpl")
public class ${className}DaoImpl extends NamedParameterJdbcDaoSupport implements I${className}Dao {
    
    static final public RowMapper<${className}Bo> _DEFAULT_MAPPER = new RowMapper<${className}Bo>() {
        
		public ${className}Bo mapRow(ResultSet rs, int rowNum) throws SQLException {
			${className}Bo ${classNameVariable} = new ${className}Bo();
			
		<#list columns as column>
			${classNameVariable}.set${column.javaName}(rs.get<#if column.javaType == "Integer">Int<#else>${column.javaType}</#if>(${className}Bo._${column.name?upper_case}));
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
	public int create(${className}Bo ${classNameVariable}) {
	    
		final String SQL = "INSERT INTO ${tableName} "
				+ "(<#list columns as column>${column.name}<#if column_has_next>, </#if></#list>) "
				+ "VALUES                                "
				+ "(<#list columns as column>:${column.javaNameVariable}<#if column_has_next>, </#if></#list>)";
		return getNamedParameterJdbcTemplate().update(SQL, new BeanPropertySqlParameterSource(${classNameVariable}));
	}
	
	@Override
	public int delete(DynamicCondition dyc, Map<String, Object> param) {
		
	    final String _sql = "DELETE FROM ${tableName}";

	    List<Object> args = new ArrayList<Object>();
	    return getJdbcTemplate().update(DynamicCondition.generate(_sql, dyc, param, args), args.toArray());
	}

	@Override
	public int update(Map<String, Object> input, DynamicCondition dyc, Map<String, Object> param) {
	    
	    List<Object> args = new ArrayList<Object>();

        if (input.isEmpty()) {
            return -2;
        }

		StringBuilder strBuilder = new StringBuilder("UPDATE ${tableName} SET");
        for (Map.Entry<String, Object> entry : input.entrySet()) {
            strBuilder.append(entry.getKey());
            args.add(entry.getValue());
        }
        strBuilder.delete(0, strBuilder.length());
		
        return getJdbcTemplate().update(DynamicCondition.generate(strBuilder.toString(), dyc, param, args),
                args.toArray());
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
	
	@Override
    public List<${className}Bo> query(int offset, int limit, DynamicCondition dyc, Map<String, Object> param) {
        
	    List<Object> args = new ArrayList<Object>();
        
        final String _inside_sql = "select <#list primaryColumns as column>${column.name}<#if column_has_next>, </#if></#list> FROM ${tableName} ";
        StringBuilder inSqlBuf = new StringBuilder(DynamicCondition.generate(_inside_sql, dyc, param, args))
                .append(" LIMIT ?, ? ");
        
        args.add(offset);
        args.add(limit);

        final String _outside_sql = "SELECT <#list columns as column>A.${column.name}<#if column_has_next>, </#if></#list> FROM ${tableName} AS A";
        StringBuilder outSqlBuf = new StringBuilder(_outside_sql)
                .append(" RIGHT JOIN ").append("(").append(inSqlBuf).append(") AS B ON ");
        
    <#list primaryColumns as column>
        outSqlBuf.append(" A.${column.name}=B.${column.name} ");
        <#if column_has_next>outSqlBuf.append(" AND ");</#if>
    </#list>
        outSqlBuf.append(" LIMIT ?");
        
        args.add(limit);

        return getJdbcTemplate().query(_outside_sql.toString(), _DEFAULT_MAPPER, args.toArray());
    }
}

