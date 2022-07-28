<#include "/copyright_include/class.header"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao.jdbc;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.opencode.tea.backend.limit.sqltemplate.oracle.UserRoleSqlImpl;
import com.opencode.tea.backend.resource.model.NavigationType;
import com.opencode.tea.backend.website.dao.jdbc.AppSystemDaoImpl;
import com.opencode.tea.backend.website.model.AppSystem;
import com.opencode.tea.backend.website.model.BackupDatabase;
import com.opencode.tea.backend.website.model.ConfigType;
import com.opencode.tea.common.dao.RowMapperResultSetExtractor;
import com.opencode.tea.common.util.Identifier;
import com.opencode.tea.jdbc.DynamicSql;

public class ${className}DaoImpl extends JdbcDaoSupport implements ${className}Dao {

	private ${className}Sql sqlTemplate;

	public void setSqlTemplate(${className}Sql sqlTemplate) {
		this.sqlTemplate = sqlTemplate;
	}
	
	private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	
	private static final DynamicSql dynamicSql = new DynamicSql()
	{
		@Override
		public void addContion()
		{
			add("cc", "and name = 'xx'");
			add("dd", "and sex = 'xx'");
		}
	};
	
	/* ~~~~~~~~~~~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~~~~~~~~~~~~~~ */
	
	public static final RowMapper<${className}> MAPPER_ONESELF = new RowMapper<${className}>() {

		public ${className} mapRow(ResultSet rs, int rowNum) throws SQLException {

			${className} model = new ${className}();
			
			<#list table.columns as column>
			model.set${column.columnName}(rs.getString("${column.sqlName}"));
			</#list>
			
			return model;
		}
		
	};
	
	//new RowMapperResultSetExtractor<${className}>(MAPPER_CT_C)
	public static final com.opencode.tea.common.dao.RowMapper<${className}> MAPPER_APP_TYPE = new com.opencode.tea.common.dao.RowMapper<${className}>()
	{
		@Override
		public ${className} mapRow(ResultSet rs, int rowNum, ${className} ${className})
				throws SQLException
		{
			NavigationType nType = MAPPER_ONESELF.mapRow(rs, rowNum);

			if (appSystem == null
					|| !appSystem.getId().equals(nType.getUpAppSystemId()))
			{
				appSystem = AppSystemDaoImpl.MAPPER_SIMPLE.mapRow(rs, rowNum);

				if (appSystem.getNavigationTypeList() == null)
				{
					appSystem
							.setNavigationTypeList(new ArrayList<NavigationType>());
				}

				appSystem.getNavigationTypeList().add(nType);

				return appSystem;
			} else
			{
				if (appSystem.getNavigationTypeList() == null)
				{
					appSystem
							.setNavigationTypeList(new ArrayList<NavigationType>());
				}

				appSystem.getNavigationTypeList().add(nType);
			}

			return null;
		}
	};
	
	/* ~~~~~~~~~~~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~~~~~~~~~~~~~~ */
	
	@Override
    public ${className} create(${className} model) {
		String sql = this.sqlTemplate.create(model);
		
    	model.setId(Identifier.stringUUID());
    	
		this.getJdbcTemplate().update(sql,
			<#list table.columns as column>
		    model.get${column.columnName}()<#if column_has_next>,</#if>
		    </#list>
		);
		
		return model;
    }
	
    @Override
    public List<${className}> creates(List<${className}> models) {
		String sql = this.sqlTemplate.creates(models);
		
		final List<${className}> bds = models;

		int[] success = this.getJdbcTemplate().batchUpdate(sql,new BatchPreparedStatementSetter()
		{
			private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			
			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException
			{
				bds.get(i).setId(Identifier.stringUUID());
				bds.get(i).setCreateDate(this.timestamp);
				bds.get(i).setModifyDate(timestamp);
				
				<#list table.columns as column>
				ps.setString(1, bds.get(i).get${column.columnName}());
			    </#list>
			}

			@Override
			public int getBatchSize()
			{
				return bds.size();
			}
		});

		for (int i : success)
		{
			System.out.println(i);
		}
		return null;
    }
    
    @Override
	public int destroy(String pk) {
		String sql = this.sqlTemplate.destroy(pk);
		
		return this.getJdbcTemplate().update(sql,pk);
	}
	
    @Override
	public int[] destroys(String[] pks) {
		String sql = this.sqlTemplate.destroys(pks);
		
		return null;
	}
	
    @Override
	public int update(${className} model) {
		String sql = this.sqlTemplate.update(model);
		
		return this.getJdbcTemplate().update(sql, 
			<#list table.columns as column>
	        model.get${column.columnName}()<#if column_has_next>,</#if>
	        </#list>
		);
	}
	
    @Override
	public int[] updates(List<${className}> models) {
		String sql = this.sqlTemplate.updates(models);
		
		return null;
	}
	
    @Override
	public ${className} detail(String pk) {
		String sql = this.sqlTemplate.detail(pk);
		
		try{
			return this.getJdbcTemplate().queryForObject(sql, MAPPER_ONESELF,pk);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public int count(String pk){
		String sql = this.sqlTemplate.detail(pk);
		
		return this.getJdbcTemplate().queryForInt(sql,pk);
	}
	
	@Override
	public List<${className}> findAll() {
		String sql = this.sqlTemplate.findAll();
		
		return this.getJdbcTemplate().query(sql, MAPPER_ONESELF);
	}
	
	@Override
	public List<${className}> find(Map<String, Object> params) {
		String sql = this.sqlTemplate.find(params);
		
		return null;
	}
	
	@Override
	public List<${className}> findPage(Map<String, Object> params) {
		String sql = this.sqlTemplate.findPage(params);
		
		return null;
	}
}
