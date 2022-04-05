<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUp = className?upper_case>  
package ${basepackage}.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import com.nsn.xx;
import java.util.ArrayList;
import com.nsn.framework.sql.DynamicCondition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import ${basepackage}.model.${className};
import ${basepackage}.dao.I${className}Dao;

/**
 * 
 * ${className} DAO 数据实现层
 * 
 * @author 卢冬榕
 * @email 736779458@qq.com
 *
 */
public class ${className}DaoImpl extends JdbcDaoSupport implements I${className}Dao 
{
	private Logger logger = LogManager.getLogger(getClass());
}

static final Map<String, String> CREATE_FILTER = new HashMap<String, String>();
static
{
	CREATE_FILTER.put("过滤", "不再拼凑SQL");
}

@Override
public int create(${className} ${classNameLower})
{
	final String SQL = "INSERT INTO ${table.sqlName} (<#list table.columns as column> ${column.sqlName}<#if column_has_next>,</#if></#list>) VALUES (<#list table.columns as column>?<#if column_has_next>,</#if></#list>)";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	return this.getJdbcTemplate().update(SQL, <#list table.columns as column>${classNameLower}.get${column.columnName}()<#if column_has_next>,</#if></#list>);
}

@Override
public int create(Map<String, String> ${classNameLower}Map)
{
	final String SQL = "INSERT INTO ${table.sqlName} ({0}) VALUES ({1})";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	
	// 用来拼凑字段
	StringBuffer filStrBuf = new StringBuffer();
	// 用来拼凑字段值
	StringBuffer valStrBuf = new StringBuffer();

	// 遍历Map,拼凑出所有字段和字段值
	for (Map.Entry<String, String> entry : ${classNameLower}Map.entrySet())
	{
		if (CREATE_FILTER.containsKey(entry.getKey()))
		{
			continue;
		}
		
		filStrBuf.append(entry.getKey());
		filStrBuf.append(",");

		valStrBuf.append(entry.getValue());
		valStrBuf.append(",");
	}

	// 删除最后面的逗号
	if (xx.isMoreThan(filStrBuf.length(), 0))
	{
		filStrBuf.deleteCharAt(filStrBuf.length() - 1);/* 删除最后一位 */
		valStrBuf.deleteCharAt(valStrBuf.length() - 1);/* 删除最后一位 */
	}

	String sql = MessageFormat.format(SQL.toString(), filStrBuf.toString(), valStrBuf.toString());
	if (logger.isDebugEnabled())
	{
		logger.debug("sql:" + sql);
	}

	return this.getJdbcTemplate().update(SQL);
}

@Override
public int[] create(final List<${className}> ${classNameLower}s)
{
	final String SQL = "INSERT INTO ${table.sqlName} (<#list table.columns as column> ${column.sqlName}<#if column_has_next>,</#if></#list>) VALUES (<#list table.columns as column>?<#if column_has_next>,</#if></#list>)";
	logger.info(SQL);
	
	return this.getJdbcTemplate().batchUpdate(SQL, new BatchPreparedStatementSetter()
	{
		@Override
		public void setValues(PreparedStatement ps, int i) throws SQLException
		{
			${className} ${classNameLower} = ${classNameLower}s.get(i);
			<#list table.columns as column>
			<#assign columnJavaTypeUp = column.javaType?cap_first >
			ps.set${columnJavaTypeUp}(${column_index + 1}, ${classNameLower}.get${column.columnName}());
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
public int del()
{
   	final String SQL = "DELETE FROM ${table.sqlName}";
   	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
   	return this.getJdbcTemplate().update(SQL);
}

<#list table.columns as column>
@Override
public int delBy${column.columnName}(${column.javaType} ${column.columnNameLower})
{
	final String SQL = "DELETE FROM ${table.sqlName} WHERE ${column.sqlName} = ?";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	return this.getJdbcTemplate().update(SQL, ${column.columnNameLower});
}
</#list>

@Override
public int update(${className} ${classNameLower})
{
	final String SQL = "UPDATE ${table.sqlName} SET <#list table.columns as column>${column.sqlName} = ?<#if column_has_next>,</#if> </#list> WHERE ID = ?";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	return getJdbcTemplate().update(SQL, <#list table.columns as column>${classNameLower}.get${column.columnName}()<#if column_has_next>,</#if></#list>);
}

<#list table.columns as column>
@Override
public int update${column.columnName}(String id, ${column.javaType} ${column.columnNameLower})
{
	final String SQL = "UPDATE ${table.sqlName} SET ${column.sqlName} = ? WHERE ID = ?";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	return getJdbcTemplate().update(SQL, ${column.columnNameLower}, id);
}
</#list>

@Override
public int update(String pk, Map<String, Object> ${classNameLower}Map)
{
	final String SQL = "UPDATE ${table.sqlName} SET {0} WHERE ID = ?";
	
	// 用来拼凑字段值
	StringBuffer valStrBuf = new StringBuffer();

	// 参数
	List<Object> args = new ArrayList<Object>();

	// 遍历Map,拼凑出所有字段和字段值
	for (Map.Entry<String, Object> entry : ${classNameLower}Map.entrySet())
	{
		// 剔除过滤字段
		if (CREATE_FILTER.containsKey(entry.getKey()))
		{
			continue;
		}
		
		// 拼更新字段
		valStrBuf.append(entry.getKey());
		valStrBuf.append(" ");
		valStrBuf.append("=");
		valStrBuf.append(" ");
		valStrBuf.append("?");
		valStrBuf.append(",");

		// 添加到参数列表
		args.add(entry.getValue());
	}

	// 删除最后面的逗号
	if (xx.isMoreThan(valStrBuf.length(), 0))
	{
		// 删除最后一位
		valStrBuf.deleteCharAt(valStrBuf.length() - 1);
		
		// 得出spring jdbc格式sql
		String sql = MessageFormat.format(SQL.toString(), valStrBuf.toString());
		if (logger.isInfoEnabled())
		{
			logger.debug("sql:" + sql);
		}

		if(logger.isDebugEnabled())
		{
			logger.debug(sql);
		}
		
		args.add(pk);
		
		// 执行sql
		return this.getJdbcTemplate().update(sql, args.toArray());
	}

	// 执行异常
	return 0;
}

@Override
public int[] update(final List<${className}> ${classNameLower}s)
{
	final String SQL = "UPDATE ${table.sqlName} SET <#list table.columns as column><#if column.columnName != "Id">${column.sqlName} = ?<#if column_has_next>,</#if></#if></#list> WHERE ID = ?";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	
	return getJdbcTemplate().batchUpdate(SQL, new BatchPreparedStatementSetter()
	{
		@Override
		public void setValues(PreparedStatement ps, int i) throws SQLException
		{
			${className} ${classNameLower} = ${classNameLower}s.get(i);
	
	<#assign xx_index = 1>
	<#list table.columns as column>
		<#if column.columnName != "Id">
		<#assign columnJavaTypeUp = column.javaType?cap_first >
			ps.set${columnJavaTypeUp}(${xx_index}, ${classNameLower}.get${column.columnName}());
			<#assign xx_index = xx_index + 1>
		</#if>
	</#list>
	        ps.setString(${xx_index}, ${classNameLower}.getId());
		}

		@Override
		public int getBatchSize()
		{
			return ${classNameLower}s.size();
		}
	});
}

//#################
//find
@Override
public List<${className}> find()
{
	final String SQL = "SELECT <#list table.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list> FROM ${table.sqlName}";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	
	return getJdbcTemplate().query(SQL, MAPPER);
}

//#################
//find column
<#list table.columns as column>
@Override
public List<${className}> findBy${column.columnName}(${column.javaType} ${column.columnNameLower})
{
	final String SQL = "SELECT <#list table.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list> FROM ${table.sqlName} WHERE ${column.sqlName} = ?";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	return getJdbcTemplate().query(SQL, MAPPER, ${column.columnNameLower});
}
</#list>

//#################
//find object
<#list table.columns as column>
@Override
public ${className} findBy${column.columnName}Obj(${column.javaType} ${column.columnNameLower})
{
	final String SQL = "SELECT <#list table.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list> FROM ${table.sqlName} WHERE ${column.sqlName} = ?";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	
	try
	{
		return getJdbcTemplate().queryForObject(SQL, MAPPER, ${column.columnNameLower});
	} catch (DataAccessException e)
	{
		return null;
	}
}
</#list>

//#################
//count
@Override
public int count()
{
	final String SQL = "SELECT COUNT(ID) FROM ${table.sqlName}";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	
	try
	{
		return getJdbcTemplate().queryForObject(SQL, Integer.class);
	} catch (DataAccessException e)
	{
		return 0;
	}
}

<#list table.columns as column>
@Override
public ${className} countBy${column.columnName}(${column.javaType} ${column.columnNameLower})
{
	final String SQL = "SELECT COUNT(${column.sqlName}) WHERE ${column.sqlName} = ?";
	if(logger.isDebugEnabled())
	{
		logger.debug(SQL);
	}
	
	try
	{
		return getJdbcTemplate().queryForObject(SQL, Integer.class);
	} catch (DataAccessException e)
	{
		return 0;
	}
}
</#list>