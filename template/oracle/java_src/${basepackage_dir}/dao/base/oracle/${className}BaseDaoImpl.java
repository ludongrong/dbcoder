<#include "/copyright_include/class.header"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUp = className?upper_case>  
package ${basepackage}.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.internal.OracleCallableStatement;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.util.Assert;

import com.opencode.tea.backend.dao.BaseDaoImpl;
import com.opencode.tea.backend.dao.OPERATE_RESULT;
import com.opencode.tea.backend.dao.StructDescMapper;
import com.opencode.tea.backend.dao.StructMapper;
import com.opencode.tea.backend.exception.ExecuteErrorException;
import com.opencode.tea.backend.exception.ParamErrorException;
import com.opencode.tea.backend.module.ListModel;
import com.opencode.tea.backend.module.OBJECT_STATE;
import com.opencode.tea.backend.module.base.OrgType;
import com.opencode.tea.backend.module.user.LoginLog;
import com.opencode.tea.backend.module.user.UserRole;
import com.opencode.tea.backend.module.website.type.oracle.OSystem;
import com.opencode.tea.common.dao.RowMapperResultSetExtractor;
import com.opencode.tea.util.Identifier;

public class ${className}BaseDaoImpl extends BaseDaoImpl implements ${className}BaseDao 
{

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
	
	public static final StructMapper<${className}> STRUCT_S = new StructMapper<${className}>()
	{
		@Override
		public ${className} mapStruct(STRUCT st) throws SQLException
		{
			// Initialize an instance object
			${className} ${classNameLower} = new ${className}();

			// Read the values in turn
			// This value is the same as the data returned list order
			Datum[] oj = st.getOracleAttributes();

			<#list table.columns as column>
			if (oj[${column_index}] != null)
			{
				${classNameLower}.set${column.columnName}(oj[${column_index}].${column.javaType}Value());
			}
			</#list>
					
			return ${classNameLower};
		}
	};
	
	public static final StructDescMapper<${className}> STRUCT_DESC_MAPPER_S = new StructDescMapper<${className}>()
	{
		@Override
		public Map<String, Object> mapping(${className} obj) throws SQLException 
		{
			Map<String, Object> map = new HashMap<String, Object>();

			<#list table.columns as column>
			map.put("${column.sqlName}", obj.get${column.columnName}());
		    </#list>

			return map;
		}
	};
			
	public static final StructDescMapper<${className}> STRUCT_DESC_MAPPER_C = new StructDescMapper<${className}>()
	{
		@Override
		public Map<String, Object> mapping(${className} obj) throws SQLException 
		{
			Map<String, Object> map = new HashMap<String, Object>();

			<#list table.columns as column>
			map.put("${column.sqlName}", obj.get${column.columnName}());
		    </#list>

			return map;
		}
	};
	
	@Override
    public ${className} create(${className} ${classNameLower}) throws ExecuteErrorException
    {
    	// Validate the input parameters
    	Assert.notNull(${classNameLower});
    	
    	// Dim time test As String
    	this.timestamp.setTime(System.currentTimeMillis());
    	
    	// Set the necessary properties
		${classNameLower}.setId(Identifier.stringUUID());
    	${classNameLower}.setCreateTime(this.timestamp);
    	${classNameLower}.setModifyTime(this.timestamp);
    	
    	//Validate the input information is compatible with the requirements of a table
    	if (${classNameLower}.getState() == null)
    	{
    		${classNameLower}.setState(OBJECT_STATE.ACTIVATE.getValue());
    	}
    	
    	// Final value type[Only change the reference variables]
    	final ${className} ${classNameUp} = ${classNameLower};
    	
    	try
		{
			return this.getJdbcTemplate().execute(
					new ConnectionCallback<${className}>()
					{

						@Override
						public ${className} doInConnection(Connection con)
								throws SQLException, DataAccessException
						{
							// The database data object interface behavior
							String storedProc = "{? = call o_${classNameLower}.add_self(?)}";

							// For a link object
							con = con.getMetaData().getConnection();
							OracleCallableStatement stmt = (OracleCallableStatement) con
									.prepareCall(storedProc);

							// With the database object mapping
							StructDescriptor desc = StructDescriptor.createDescriptor("O_${classNameLower}", con);
		    				STRUCT struct = new STRUCT(desc, con, STRUCT_DESC_MAPPER_S.mapping(${classNameUp}));

							// input parameter
							stmt.registerOutParameter(1, OracleTypes.INTEGER);
							stmt.setObject(2, struct);

							try
							{
								stmt.execute();
							} catch (SQLException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							} catch (DataAccessException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							}

							// Achieved good results in
							int state = OE_FAIL.getValue();
							try
							{
								state = stmt.getInt(1);

								if (state == OE_FAIL.getValue())
								{
									throw new SQLException();
								} else if (state != OE_SUCCEED.getValue())
								{
									return null;
								}
							} catch (SQLException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							}

							//Object to create success
							// If you create failure it returns null
							return ${classNameUp};
						}
					});
		} catch (DataAccessException e)
		{
			throw new ExecuteErrorException("DataAccess exception");
		} catch (Exception e)
		{
			throw new ExecuteErrorException("excute exception");
		}
    }
	
    @Override
	public OPERATE_RESULT destroy(String pk) throws ExecuteErrorException
    {
    	// Validate the input parameters
    	Assert.hasText(pk);
    	
    	// Final value type[Only change the reference variables]
    	final String PK = pk;
    	
    	try
		{
			return this.getJdbcTemplate().execute(
					new ConnectionCallback<OPERATE_RESULT>()
					{

						@Override
						public OPERATE_RESULT doInConnection(Connection con)
								throws SQLException, DataAccessException
						{
							// The database data object interface behavior
							String storedProc = "{? = call o_${classNameLower}.delete_self(?)}";

							// For a link object
							con = con.getMetaData().getConnection();
							CallableStatement stmt = con
									.prepareCall(storedProc);

							// input parameter
							stmt.registerOutParameter(1, OracleTypes.INTEGER);
							stmt.setString(2, PK);

							try
							{
								stmt.execute();
							} catch (SQLException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							} catch (DataAccessException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							}

							// Achieved good results in
							int state = OE_FAIL.getValue();
							try
							{
								state = stmt.getInt(1);

								if (state == OE_FAIL.getValue())
								{
									throw new SQLException();
								} else if (state != OE_SUCCEED.getValue())
								{
									return OE_FAIL;
								}
							} catch (SQLException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							}

							return OE_SUCCEED;
						}
					});
		} catch (DataAccessException e)
		{
			throw new ExecuteErrorException("DataAccess exception");
		} catch (Exception e)
		{
			throw new ExecuteErrorException("excute exception");
		}
	}
	
    @Override
	public OPERATE_RESULT update(${className} ${classNameLower}) throws ExecuteErrorException
    {
    	// Validate the input parameters
    	Assert.notNull(${classNameLower});
    	Assert.hasText(${classNameLower}.getId());
    	
    	// Dim time test As String
    	this.timestamp.setTime(System.currentTimeMillis());
    	
    	// Set the necessary properties
    	${classNameLower}.setModifyTime(this.timestamp);
		
    	//Validate the input information is compatible with the requirements of a table
		if (${classNameLower}.getState() == null)
		{
			${classNameLower}.setState(OBJECT_STATE.ACTIVATE.getValue());
		}
    	
    	// Final value type[Only change the reference variables]
    	final ${className} ${classNameUp} = ${classNameLower};
    	
    	try
		{
			return this.getJdbcTemplate().execute(
					new ConnectionCallback<OPERATE_RESULT>()
					{

						@Override
						public OPERATE_RESULT doInConnection(Connection con)
								throws SQLException, DataAccessException
						{
							// The database data object interface behavior
							String storedProc = "{? = call o_${classNameLower}.update_self(?)}";

							// For a link object
							con = con.getMetaData().getConnection();
							OracleCallableStatement stmt = (OracleCallableStatement) con
									.prepareCall(storedProc);

							// With the database object mapping
							StructDescriptor desc = StructDescriptor.createDescriptor("O_${classNameLower}", con);
		    				STRUCT struct = new STRUCT(desc, con, STRUCT_DESC_MAPPER_S.mapping(${classNameUp}));

							// input parameter
							stmt.registerOutParameter(1, OracleTypes.INTEGER);
							stmt.setObject(2, struct);

							try
							{
								stmt.execute();
							} catch (SQLException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							} catch (DataAccessException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							}

							// Achieved good results in
							int state = OE_FAIL.getValue();
							try
							{
								state = stmt.getInt(1);

								if (state == OE_FAIL.getValue())
								{
									throw new SQLException();
								} else if (state != OE_SUCCEED.getValue())
								{
									return OE_FAIL;
								}
							} catch (SQLException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							}

							return OE_SUCCEED;
						}
					});
		} catch (DataAccessException e)
		{
			throw new ExecuteErrorException("DataAccess exception");
		} catch (Exception e)
		{
			throw new ExecuteErrorException("excute exception");
		}
	}
	
    @Override
	public ${className} detail(String pk) throws ExecuteErrorException
	{
		// Validate the input parameters
		Assert.hasText(pk);

		// Final value type[Only change the reference variables]
		final String PK = pk;
    	
		try
		{
			return this.getJdbcTemplate().execute(
					new ConnectionCallback<${className}>()
					{
						@Override
						public ${className} doInConnection(Connection con)
								throws SQLException, DataAccessException
						{
							// The database data object interface behavior
							String storedProc = "{? = call o_${classNameLower}.detail(?,?)}";

							// For a link object
							con = con.getMetaData().getConnection();
							CallableStatement stmt = con
									.prepareCall(storedProc);

							// input parameter
							stmt.registerOutParameter(1, OracleTypes.INTEGER);
							stmt.setString(2, PK);
							stmt.registerOutParameter(3, Types.STRUCT,"O_${classNameLower}");

							try
							{
								stmt.execute();
							} catch (SQLException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							} catch (DataAccessException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							}

							// Achieved good results in
							int state = OE_FAIL.getValue();
							try
							{
								state = stmt.getInt(1);

								if (state == OE_FAIL.getValue())
								{
									throw new SQLException();
								} else if (state != OE_SUCCEED.getValue())
								{
									return null;
								}
							} catch (SQLException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							}

							// Return to find the object or null
							try
							{
								STRUCT struct = (STRUCT) stmt.getObject(3);

								// If it is null, return null values directly
								// It is not necessary to do further conversion
								if (struct != null)
								{
									return STRUCT_S.mapStruct(struct);
								} else
								{
									return null;
								}
							} catch (SQLException e)
							{
								throw e;
							} finally
							{
								if (stmt != null)
								{
									stmt.close();
								}
							}

						}
					});
		} catch (DataAccessException e)
		{
			throw new ExecuteErrorException("DataAccess exception");
		} catch (Exception e)
		{
			throw new ExecuteErrorException("excute exception");
		}
	}
	
	@Override
	public ListModel<${className}> find(${className} ${classNameLower})throws PageIndexException, ExecuteErrorException
	{
		// Validate the input parameters
		Assert.notNull(${classNameLower});
		Assert.notNull(${classNameLower}.getStartIndex());
		Assert.notNull(${classNameLower}.getLastIndex());

		// Final value type[Only change the reference variables]
		final ${className} ${classNameUp} = (${className}) ${classNameLower}.initIndex();

		try
		{
			return this.getJdbcTemplate().execute(
					new ConnectionCallback<ListModel<${className}>>()
					{
						@Override
						public ListModel<${className}> doInConnection(Connection con)
								throws SQLException, DataAccessException
						{
							// The database data object interface behavior
							String storedProc = "{? = call p_${classNameLower}.get(?,?,?,?,?)}";

							// For a link object
							con = con.getMetaData().getConnection();
							OracleCallableStatement stmt = (OracleCallableStatement) con
									.prepareCall(storedProc);

							// With the database object mapping
							StructDescriptor uc = StructDescriptor
									.createDescriptor("C_${classNameLower}", con);
							STRUCT ucs = new STRUCT(uc, con,
									STRUCT_DESC_MAPPER_C.mapping(${classNameUp}));

							// input parameter
							stmt.registerOutParameter(1, OracleTypes.INTEGER);
							stmt.setInt(2, ${classNameUp}.getStartIndex());
							stmt.setInt(3, ${classNameUp}.getLastIndex());
							stmt.setObject(4, ucs);
							stmt.registerOutParameter(5, OracleTypes.INTEGER);
							stmt.registerOutParameter(6, OracleTypes.CURSOR);

							try
							{
								stmt.execute();
							} catch (SQLException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							} catch (DataAccessException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							}

							// Achieved good results in
							int state = OE_FAIL.getValue();
							try
							{
								state = stmt.getInt(1);

								if (state == OE_FAIL.getValue())
								{
									throw new SQLException();
								} else if (state != OE_SUCCEED.getValue())
								{
									return null;
								}
							} catch (SQLException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							}

							// Return to find the object or null
							ResultSet rs = null;
							try
							{
								int count = stmt.getInt(5);

								rs = (ResultSet) stmt.getObject(6);

								if (rs != null)
								{
									ListModel<${className}> ${classNameLower}s = new ListModel<${className}>();

									${classNameLower}s.setTotal(count);

									while (rs.next())
									{
										${className} ${classNameLower} = MAPPER_SELF.mapRow(
												rs, 0);

										${classNameLower}s.add(${classNameLower});
									}

									return ${classNameLower}s;
								} else
								{
									return null;
								}
							} catch (SQLException e)
							{
								throw e;
							} finally
							{
								if (rs != null)
								{
									rs.close();
								}

								if (stmt != null)
								{
									stmt.close();
								}
							}
						}
					});
		} catch (DataAccessException e)
		{
			throw new ExecuteErrorException("DataAccess exception");
		} catch (Exception e)
		{
			throw new ExecuteErrorException("excute exception");
		}
	}
	
	@Override
	public int count(${className} ${classNameLower}) throws ExecuteErrorException
	{
		// Validate the input parameters
		Assert.notNull(${classNameLower});
		
		// Final value type[Only change the reference variables]
		final ${className} ${classNameUp} = ${classNameLower};

		try
		{
			return this.getJdbcTemplate().execute(
					new ConnectionCallback<Integer>()
					{
						@Override
						public Integer doInConnection(Connection con)
								throws SQLException, DataAccessException
						{
							// The database data object interface behavior
							String storedProc = "{? = call p_${classNameLower}.count(?,?)}";

							// For a link object
							con = con.getMetaData().getConnection();
							OracleCallableStatement stmt = (OracleCallableStatement) con
									.prepareCall(storedProc);

							// With the database object mapping
							StructDescriptor uc = StructDescriptor
									.createDescriptor("C_${classNameLower}", con);
							STRUCT ucs = new STRUCT(uc, con,
									STRUCT_DESC_MAPPER_C.mapping(${classNameUp}));

							// input parameter
							stmt.registerOutParameter(1, OracleTypes.INTEGER);
							stmt.setObject(2, ucs);
							stmt.registerOutParameter(3, OracleTypes.INTEGER);

							try
							{
								stmt.execute();
							} catch (SQLException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							} catch (DataAccessException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							}

							// Achieved good results in
							int state = OE_FAIL.getValue();
							try
							{
								state = stmt.getInt(1);

								if (state == OE_FAIL.getValue())
								{
									throw new SQLException();
								} else if (state != OE_SUCCEED.getValue())
								{
									return null;
								}
							} catch (SQLException e)
							{
								if (stmt != null)
								{
									stmt.close();
								}

								throw e;
							}

							// Return to find the object or null
							try
							{
								return stmt.getInt(3);
							} catch (SQLException e)
							{
								return -1;
							} finally
							{
								if (stmt != null)
								{
									stmt.close();
								}
							}
						}
					});
		} catch (DataAccessException e)
		{
			throw new ExecuteErrorException("DataAccess exception");
		} catch (Exception e)
		{
			throw new ExecuteErrorException("excute exception");
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
    								pps.set${column.javaType}(${column_index}, obj.get${column.columnName}());
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
    	final List<${classNameLower}> OBJS = ${classNameLower}s;

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
    								pps.set${column.javaType}(${column_index}, obj.get${column.columnName}());
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
