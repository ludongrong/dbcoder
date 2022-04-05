<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUp = className?upper_case>  
static final Map<String, String> CREATE_FILTER = new HashMap<String, String>();
static
{
	CREATE_FILTER.put("过滤", "不再拼凑SQL");
}

@Override
public List<${className}> findByColmun(String colmun, String value)
{
	final String SQL = "SELECT <#list table.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list> FROM ${table.sqlName} WHERE {0} = ?";
	String sql = MessageFormat.format(SQL, colmun);
	
	if(logger.isDebugEnabled())
	{
		logger.debug(sql);
	}
	
	return this.getJdbcTemplate().query(sql, MAPPER, value);
}

@Override
public int delByDynamicCondition(DynamicCondition dyc, Map<String, Object> input)
{
	final String SQL = "DELETE FROM ${table.sqlName}";
	StringBuilder strBuilder = new StringBuilder(SQL);

	List<Object> args = new ArrayList<Object>();
	boolean genresult = dyc.preWhere().generateSql(strBuilder, input, args);
	if (!genresult)
	{
		String msg = "动态条件生成失败";
		logger.error(msg);
		throw new DataAccessException(msg)
		{
			private static final long serialVersionUID = -352254045672223128L;
		};
	}
	
	String sql = strBuilder.toString();
	if(logger.isDebugEnabled())
	{
		logger.debug(sql);
	}

	return this.getJdbcTemplate().update(sql, args.toArray());
}

@Override
public List<${className}> findByDynamicCondition(DynamicCondition dyc, Map<String, Object> input)
{
	final String SQL = "SELECT <#list table.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list> FROM ${table.sqlName}";
	StringBuilder strBuilder = new StringBuilder(SQL);

	List<Object> args = new ArrayList<Object>();
	boolean genresult = dyc.preWhere().generateSql(strBuilder, input, args);
	if (!genresult)
	{
		String msg = "动态条件生成失败";
		logger.error(msg);
		throw new DataAccessException(msg)
		{
			private static final long serialVersionUID = -352254045672223128L;
		};
	}
	
	String sql = strBuilder.toString();
	if(logger.isDebugEnabled())
	{
		logger.debug(sql);
	}

	return this.getJdbcTemplate().query(sql, MAPPER, args.toArray());
}

@Override
public int countByDynamicCondition(DynamicCondition dyc, Map<String, Object> input)
{
	final String SQL = "SELECT COUNT(ID) FROM ${table.sqlName}";
	StringBuilder strBuilder = new StringBuilder(SQL);

	List<Object> args = new ArrayList<Object>();
	boolean genresult = dyc.preWhere().generateSql(strBuilder, input, args);
	if (!genresult)
	{
		String msg = "动态条件生成失败";
		logger.error(msg);
		throw new DataAccessException(msg)
		{
			private static final long serialVersionUID = -352254045672223128L;
		};
	}
	
	String sql = strBuilder.toString();
	if(logger.isDebugEnabled())
	{
		logger.debug(sql);
	}

	try
	{
		return this.getJdbcTemplate().queryForObject(sql, Integer.class, args.toArray());
	} catch (DataAccessException e)
	{
		return 0;
	}
}

