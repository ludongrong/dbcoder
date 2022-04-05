<#include "/copyright_include/class.header"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUp = className?upper_case>  
package ${basepackage}.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import com.opencode.tea.backend.dao.BaseDaoImpl;
import com.opencode.tea.backend.dao.OPERATE_RESULT;
import com.opencode.tea.backend.dao.user.base.UserBaseDao;
import com.opencode.tea.backend.exception.ExecuteErrorException;
import com.opencode.tea.backend.exception.PageIndexException;
import com.opencode.tea.backend.exception.ParamErrorException;
import com.opencode.tea.backend.module.OBJECT_STATE;
import com.opencode.tea.backend.module.user.User;
import com.opencode.tea.common.dynamic.Analysis;
import com.opencode.tea.common.dynamic.AnalysisException;
import com.opencode.tea.common.dynamic.Convert;
import com.opencode.tea.common.dynamic.DynamicSql;
import com.opencode.tea.common.dynamic.Fragment;
import com.opencode.tea.util.Identifier;

public class ${className}BaseDaoImpl extends BaseDaoImpl implements ${className}BaseDao 
{

	public static final RowMapper<${className}> MAPPER_SELF = new RowMapper<${className}>() 
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
	
	public static final RowMapper<${className}> MAPPER_PREFIX = new RowMapper<${className}>() 
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
	
	private static final Fragment FRAGMENT_S = new Fragment()
	{
		@Override
		public void initDefaultCondition()
		{
			this.add("AND_NAME_LIKE", "and {0}name like %{1}%");
		}
	};
	
	private static final Analysis<${className}> ANALYSIS_SQL_S = new Analysis<${className}>(
			FRAGMENT_S)
	{
		@Override
		public void analysis(${className} ctObj) throws AnalysisException
		{
			if (ctObj.getId() != null)
			{
				this.setVal("AND_NAME_LIKE", "", ctObj.getId());

				if (this.isFirst() == false)
				{
					this.setPosition(3);
					this.setFirst(true);
				}
			}
		}
	};

	private static final DynamicSql<${className}> DYNAMIC_SQL = new DynamicSql<${className}>(
			ANALYSIS_SQL_S);
	
	private final String CREATE = "INSERT INTO ${table.sqlName} " 
		    + " ("
			<#list table.columns as column>
		    +"${column.sqlName}<#if column_has_next>,</#if>"
		    </#list>
		    +") " 
		    + " VALUES "
		    + " (<#list table.columns as column>?<#if column_has_next>,</#if></#list>)";
	
	private final String DESTROY = "DELETE FROM ${table.sqlName} WHERE ${table.idColumn.sqlName} = ?";
	
	
	private final String UPDATE = "UPDATE ${table.sqlName} SET "
		    <#list table.columns as column>
		    +"${column.sqlName} = ?<#if column_has_next>,</#if>"
		    </#list>
		    + " WHERE ${table.idColumn.sqlName} = ?";
	
	@Override
    public ${className} create(${className} ${classNameLower}) throws ParamErrorException,
	ExecuteErrorException
    {
    	// 需要插入的数据参数不能为空
    	// 否则抛出参数异常
    	try
    	{
    		Assert.notNull(${classNameLower});
    	} catch (IllegalArgumentException e)
    	{
    		throw new ParamErrorException("not null");
    	}

    	try
    	{
    		Assert.hasText(${classNameLower}.getState());

    		if (${classNameLower}.getState().length() > 1)
    		{
    			throw new ExecuteErrorException("state");
    		}
    	} catch (IllegalArgumentException e)
    	{
    		${classNameLower}.setState(OBJECT_STATE.ACTIVATE.getValue());
    	}

    	// 初始化相关属性
    	this.timestamp.setTime(System.currentTimeMillis());

    	// 设置唯一标示符
    	${classNameLower}.setId(Identifier.stringUUID());
    	${classNameLower}.setCreateTime(timestamp);
    	${classNameLower}.setModifyTime(timestamp);

    	// 单条插入操作
    	// 如果数据库是oracle返回的结果是{0:失败/1：成功}
    	// 这里有判断单个对象执行情况，只要没抛出异常或者操作结果返回{1}，则都认为操作成功
    	try
    	{
    		int result = this
    				.getJdbcTemplate()
    				.update(CREATE,
    						<#list table.columns as column>
    			            ${classNameLower}.get${column.columnName}()<#if column_has_next>,</#if>
    			            </#list>);
   			if (result == 0)
   			{
   				return null;
   			}
		} catch (DataAccessException e)
		{
			throw new ExecuteErrorException();
		}

		// 如果能顺利执行到这步而认为操作成功
		// 外面只要判断返回的结果不为空则就可以判断是否操作成功
    	return ${classNameLower};
    }
	
    @Override
	public OPERATE_RESULT destroy(String pk) throws ParamErrorException,
	ExecuteErrorException
    {
    	// 需要插入的数据参数不能为空
    	// 否则抛出参数异常
    	try
    	{
    		Assert.hasText(pk);
    	} catch (IllegalArgumentException e)
    	{
    		throw new ParamErrorException("not null");
    	}

    	// 单条插入操作
    	// 如果数据库是oracle返回的结果是{0:失败/1：成功}
    	// 这里有判断单个对象执行情况，只要没抛出异常或者操作结果返回{1}，则都认为操作成功
    	try
    	{
    		int result = this.getJdbcTemplate().update(DESTROY, pk);

    		if (result == 0)
    		{
    			return null;
    		}
    	} catch (DataAccessException e)
    	{
    		throw new ExecuteErrorException();
    	}

    	// 如果能顺利执行到这步而认为操作成功
    	return OPERATE_RESULT.SUCCEED;
	}
	
    @Override
	public OPERATE_RESULT update(${className} ${classNameLower}) throws ParamErrorException,
	ExecuteErrorException
    {
    	// 需要插入的数据参数不能为空
    	// 否则抛出参数异常
    	try
    	{
    		Assert.notNull(${classNameLower});
    	} catch (IllegalArgumentException e)
    	{
    		throw new ParamErrorException("not null");
    	}

    	try
    	{
    		Assert.hasText(${classNameLower}.getState());

    		if (${classNameLower}.getState().length() > 1)
    		{
    			throw new ParamErrorException("userRole state");
    		}
    	} catch (IllegalArgumentException e)
    	{
    		throw new ParamErrorException("userRole state");
    	}

    	// 初始化相关属性
    	this.timestamp.setTime(System.currentTimeMillis());
    	${classNameLower}.setModifyTime(this.timestamp);

    	// 适合执行小批量操作
    	// 如果数据库是oracle返回的结果是{0:失败/1：成功}
    	// 这里有判断单个对象执行情况，只要没抛出异常或者操作结果返回{1}，则都认为操作成功
    	try
    	{
    		int result = this
    				.getJdbcTemplate()
    				.update(UPDATE,
    						<#list table.columns as column>
		                    ${classNameLower}.get${column.columnName}()<#if column_has_next>,</#if>
		                    </#list>);

    		if (result == 0)
    		{
    			return null;
    		}
    	} catch (DataAccessException e)
    	{
    		throw new ExecuteErrorException();
    	}

    	// 如果能顺利执行到这步而认为操作成功
    	return OPERATE_RESULT.SUCCEED;
	}
	
    @Override
	public ${className} detail(String pk) throws ParamErrorException,
	ExecuteErrorException
	{
		// 需要插入的数据参数不能为空
		// 否则抛出参数异常
		try
		{
			Assert.hasText(pk);
		} catch (IllegalArgumentException e)
		{
			throw new ParamErrorException("not null");
		}

		// 单条插入操作
		${className} ${classNameLower} = null;
		try
		{
			${classNameLower} = this.getJdbcTemplate().queryForObject("", MAPPER_SELF,
					pk);
		} catch (EmptyResultDataAccessException e)
		{
			return null;
		} catch (IncorrectResultSizeDataAccessException e)
		{
			throw new ExecuteErrorException(">1");
		} catch (DataAccessException e)
		{
			throw new ExecuteErrorException();
		}

		// 如果能顺利执行到这步而认为操作成功
		// 如果是空值会捕获空值异常然后返回空值
		// 所以调用者只要判断为空值说明没有值，如果不为空说明有值。
		// 如果异常说明不只一个值，或者操作错误
		return ${classNameLower};
	}
	
	@Override
	public List<${className}> find(${className} ${classNameLower})throws PageIndexException,ParamErrorException, ExecuteErrorException
	{
		// 验证条件
		// 需要插入的数据参数不能为空
		// 否则抛出参数异常
		try
		{
			Assert.notNull(${classNameLower});
		} catch (IllegalArgumentException e)
		{
			throw new ParamErrorException("not null");
		}

		// 特定的语句
		// {0}是占位符的意思，如果包含占位符，则需要被替换成可执行的语句
		String sql = "select * from (select <#list table.columns as column> ${column.sqlName}<#if column_has_next>,</#if> </#list>, rownum rn  FROM ${table.sqlName} where rownum < ? {0}) where rn > ?";

		// 根据page number 和page size
		// 计算出oracle的分页的起始位置和结束位置
		${classNameLower} = (${className}) ${classNameLower}.initIndex();

		// 使用文本转换器把动态条件插入到SQL语句
		try
		{
			sql = Convert.convert(sql,
					DYNAMIC_SQL.getSqlIncludePrefix(${classNameLower}, true));
		} catch (AnalysisException e)
		{
			throw new ExecuteErrorException(">1");
		}

		try
		{
			return this.getJdbcTemplate().query(sql, MAPPER_SELF,
					${classNameLower}.getLastIndex(), ${classNameLower}.getStartIndex());
		} catch (DataAccessException e)
		{
			throw new ExecuteErrorException();
		}
	}
	
	@Override
	public int count(${className} ${classNameLower}) throws ParamErrorException,ExecuteErrorException
	{
		// 验证条件
		// 需要插入的数据参数不能为空
		// 否则抛出参数异常
		try
		{
			Assert.notNull(${classNameLower});
		} catch (IllegalArgumentException e)
		{
			throw new ParamErrorException("not null");
		}

		// 特定的语句
		// {0}是占位符的意思，如果包含占位符，则需要被替换成可执行的语句
		String sql = "SELECT COUNT(ID) FROM ${table.sqlName} {0}";

		// 使用文本转换器把动态条件插入到SQL语句
		try
		{
			sql = Convert.convert(sql,
					DYNAMIC_SQL.getSqlIncludeWhere(${classNameLower}, true));
		} catch (AnalysisException e)
		{
			throw new ExecuteErrorException(">1");
		}

		try
		{
			return this.getJdbcTemplate().queryForInt(sql);
		} catch (DataAccessException e)
		{
			throw new ExecuteErrorException();
		}
	}
	
	@Override
    public List<${className}> creates(List<${className}> ${classNameLower}s) 
    		throws ParamErrorException, ExecuteErrorException
    {
    	// 需要插入的数据参数不能为空
    	// 否则抛出参数异常
    	try
    	{
    		Assert.notNull(${classNameLower}s);
    	} catch (IllegalArgumentException e)
    	{
    		throw new ParamErrorException("not null");
    	}

    	// 初始化相关属性
    	this.timestamp.setTime(System.currentTimeMillis());

    	// 要更新对象中的字段需要与数据库中表的字段要求一致
    	// 否则抛出与表中字段不一致异常
    	for (${className} ${classNameLower} : ${classNameLower}s)
    	{
    		try
    		{
    			Assert.notNull(${classNameLower});

    			${classNameLower}.setId(Identifier.stringUUID());
    			${classNameLower}.setCreateTime(this.timestamp);
    			${classNameLower}.setModifyTime(this.timestamp);
    		} catch (IllegalArgumentException e)
    		{
    			throw new ParamErrorException("userRole state");
    		}

    		try
    		{
    			Assert.hasText(${classNameLower}.getState());

    			if (${classNameLower}.getState().length() > 1)
    			{
    				throw new ExecuteErrorException("userRole state");
    			}
    		} catch (IllegalArgumentException e)
    		{
    			${classNameLower}.setState(OBJECT_STATE.ACTIVATE.getValue());
    		}
    	}

    	// 作为常量变量
    	// 指针不变，但是值可以改变
    	final List<${className}> OBJS = ${classNameLower}s;

    	// 适合执行小批量操作
    	// 如果数据库是oracle返回的结果是{-2}，驱动问题
    	// 这里没有判断单个对象执行情况，而是整理执行情况，只要没抛出异常，则都认为操作成功
    	try
    	{
    		this.getJdbcTemplate()
    				.batchUpdate(
    						CREATE,
    						new BatchPreparedStatementSetter()
    						{
    							@Override
    							public void setValues(PreparedStatement pps,
    									int pi) throws SQLException
    							{
    								${className} obj = OBJS.get(pi);

    								<#list table.columns as column>
    								pps.set${column.javaType}(${column_index + 1}, obj.get${column.columnName}());
    								</#list>
    							}

    							@Override
    							public int getBatchSize()
    							{
    								return OBJS.size();
    							}
    						});
    	} catch (DataAccessException e)
    	{
    		throw new ExecuteErrorException();
    	}

    	// 如果能顺利执行到这步而认为操作成功
    	// 外面只要判断返回的结果不为空则就可以判断是否操作成功
    	return ${classNameLower}s;
    }
    
    @Override
	public OPERATE_RESULT destroys(List<String> pks) 
			throws ParamErrorException, ExecuteErrorException
    {
    	// 需要插入的数据参数不能为空
    	// 否则抛出参数异常
    	try
    	{
    		Assert.notNull(pks);
    	} catch (IllegalArgumentException e)
    	{
    		throw new ParamErrorException("not null");
    	}

    	// 要更新对象中的字段需要与数据库中表的字段要求一致
    	// 否则抛出与表中字段不一致异常
    	for (String pk : pks)
    	{
    		try
    		{
    			Assert.hasText(pk);
    		} catch (IllegalArgumentException e)
    		{
    			throw new ParamErrorException("not null");
    		}
    	}

    	// 作为常量变量
    	// 指针不变，但是值可以改变
    	final List<String> PKS = pks;

    	// 适合执行小批量操作
    	// 如果数据库是oracle返回的结果是{-2}，驱动问题
    	// 这里没有判断单个对象执行情况，而是整理执行情况，只要没抛出异常，则都认为操作成功
    	try
    	{
    		this.getJdbcTemplate().batchUpdate(
    				DESTROY,
    				new BatchPreparedStatementSetter()
    				{
    					@Override
    					public void setValues(PreparedStatement pps, int pi)
    							throws SQLException
    					{
    						String pk = PKS.get(pi);
							pps.setString(1, pk);
						}

    					@Override
    					public int getBatchSize()
    					{
    						return PKS.size();
    					}
    				});
    	} catch (DataAccessException e)
    	{
    		throw new ExecuteErrorException();
    	}

    	// 如果能顺利执行到这步而认为操作成功
    	return OPERATE_RESULT.SUCCEED;
	}
    
    @Override
	public OPERATE_RESULT updates(List<${className}> ${classNameLower}s) 
			throws ParamErrorException, ExecuteErrorException
    {
    	// 需要插入的数据参数不能为空
    	// 否则抛出参数异常
    	try
    	{
    		Assert.notNull(${classNameLower}s);
    	} catch (IllegalArgumentException e)
    	{
    		throw new ParamErrorException("not null");
    	}

		// 初始化相关属性
		this.timestamp.setTime(System.currentTimeMillis());

    	// 要更新对象中的字段需要与数据库中表的字段要求一致
    	// 否则抛出与表中字段不一致异常
    	for (${className} ${classNameLower} : ${classNameLower}s)
    	{
    		try
    		{
    			Assert.notNull(${classNameLower});

    			${classNameLower}.setModifyTime(this.timestamp);
    		} catch (IllegalArgumentException e)
    		{
    			throw new ParamErrorException("not null");
    		}
    	}

    	// 作为常量变量
    	// 指针不变，但是值可以改变
    	final List<${className}> OBJS = ${classNameLower}s;

    	// 适合执行小批量操作
    	// 如果数据库是oracle返回的结果是{-2}，驱动问题
    	// 这里没有判断单个对象执行情况，而是整理执行情况，只要没抛出异常，则都认为操作成功
    	try
    	{
    		this.getJdbcTemplate()
    				.batchUpdate(
    						UPDATE,
    						new BatchPreparedStatementSetter()
    						{
    							@Override
    							public void setValues(PreparedStatement pps,
    									int pi) throws SQLException
    							{
    								${className} obj = OBJS.get(pi);

    								<#list table.columns as column>
    								pps.set${column.javaType}(${column_index + 1}, obj.get${column.columnName}());
    								</#list>
    							}

    							@Override
    							public int getBatchSize()
    							{
    								return OBJS.size();
    							}
    						});
    	} catch (DataAccessException e)
    	{
    		throw new ExecuteErrorException();
    	}

    	// 如果能顺利执行到这步而认为操作成功
    	return OPERATE_RESULT.SUCCEED;
	}
    
}
