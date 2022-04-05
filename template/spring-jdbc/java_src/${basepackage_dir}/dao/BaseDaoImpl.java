<#include "/copyright_include/class.header"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.dao;

import java.sql.Timestamp;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.opencode.tea.jdbc.DynamicSql;

public abstract class BaseDaoImpl extends JdbcDaoSupport {

	protected final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	
	protected final DynamicSql dynamicSql = new DynamicSql()
	{
		@Override
		public void addContion()
		{
			add("cc", "and name = 'xx'");
			add("dd", "and sex = 'xx'");
		}
	};
	
	public enum OPERATE_RESULT
	{
		SUCCEED
		{
			public int getValue()
			{
				return 1;
			}
		},
		FAIL
		{
			public int getValue()
			{
				return 0;
			}
		};

		public abstract int getValue();
	}
}
