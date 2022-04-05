<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUp = className?upper_case>  
package ${basepackage}.service.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import ${basepackage}.entity.${className};
import ${basepackage}.service.dao.I${className}Dao;

public class ${className}DaoImpl extends JdbcDaoSupport implements I${className}Dao 
{
	@Override
    public int save(<#list table.columns as column>${column.javaType} ${column.columnNameLower}<#if column_has_next>,</#if></#list>)
    {
    	final String SQL = "INSERT INTO ${table.sqlName} (<#list table.columns as column> ${column.sqlName}<#if column_has_next>,</#if></#list>) VALUES (<#list table.columns as column>?<#if column_has_next>,</#if></#list>)";
    	return this.getJdbcTemplate().update(SQL,<#list table.columns as column>${column.columnNameLower}<#if column_has_next>,</#if></#list>);
    }
	
	@Override
	public int[] save(final List<${className}> ${classNameLower}s)
	{
		final String SQL = "INSERT INTO ${table.sqlName} (<#list table.columns as column> ${column.sqlName}<#if column_has_next>,</#if></#list>) VALUES (<#list table.columns as column>?<#if column_has_next>,</#if></#list>)";
		return this.getJdbcTemplate().batchUpdate(SQL, new BatchPreparedStatementSetter()
		{
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException
			{
				${className} ${classNameLower} = ${classNameLower}s.get(i);
				<#list table.columns as column>
				ps.set${column.javaType}(${column_index + 1}, ${classNameLower}.get${column.columnName}());
				</#list>
			}

			@Override
			public int getBatchSize()
			{
				return ${classNameLower}s.size();
			}
		});
	}
	
    @Override
	public int destroy(String pk)
    {
    	final String SQL = "DELETE FROM ${table.sqlName} WHERE ID = ?";
    	return this.getJdbcTemplate().update(SQL, pk);
	}
	
    @Override
	public int update(<#list table.columns as column>${column.javaType} ${column.columnNameLower}<#if column_has_next>,</#if></#list>)
    {
    	final String SQL = "UPDATE ${table.sqlName} SET <#list table.columns as column>${column.sqlName} = ?<#if column_has_next>,</#if> </#list> WHERE ID = ?";
    	return this.getJdbcTemplate().update(SQL,<#list table.columns as column>${column.columnNameLower}<#if column_has_next>,</#if></#list>);
	}
    
    @Override
	public int[] update(final List<${className}> ${classNameLower}s)
	{
    	final String SQL = "UPDATE ${table.sqlName} SET <#list table.columns as column>${column.sqlName} = ?<#if column_has_next>,</#if> </#list> WHERE ID = ?";
		return this.getJdbcTemplate().batchUpdate(SQL, new BatchPreparedStatementSetter()
		{
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException
			{
				${className} ${classNameLower} = ${classNameLower}s.get(i);
				<#list table.columns as column>
				ps.set${column.javaType}(${column_index + 1}, ${classNameLower}.get${column.columnName}());
				</#list>
			}

			@Override
			public int getBatchSize()
			{
				return ${classNameLower}s.size();
			}
		});
	}
	
    @Override
	public ${className} detail(String pk)
	{
		final String SQL = "SELECT <#list table.columns as column>${column.sqlName}<#if column_has_next>,</#if></#list> FROM ${table.sqlName} WHERE ID = ?";
		try
		{
			return this.getJdbcTemplate().queryForObject(SQL, MAPPER, pk);
		} catch (DataAccessException e)
		{
			return null;
		}
	}
	
	@Override
	public List<${className}> find(int start,int end)
	{
		final String SQL = "SELECT * FROM (SELECT <#list table.columns as column>${column.sqlName}<#if column_has_next>,</#if></#list>, ROWNUM RN FROM ${table.sqlName} WHERE ROWNUM < ?) WHERE RN > ?";
		return this.getJdbcTemplate().query(SQL, MAPPER,end,start);
	}
	
	@Override
	public int count()
	{
		final String SQL = "SELECT COUNT(ID) FROM ${table.sqlName}";

		try
		{
			return this.getJdbcTemplate().queryForInt(SQL);
		} catch (DataAccessException e)
		{
			return 0;
		}
	}
    
    public static final RowMapper<${className}> MAPPER = new RowMapper<${className}>() 
	{
		public ${className} mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			${className} ${classNameLower} = new ${className}();
			
		<#list table.columns as column>
			${classNameLower}.set${column.columnName}(rs.get${column.javaType}("${column.sqlName}"));
		</#list>
			
			return ${classNameLower};
		}
	};
}

