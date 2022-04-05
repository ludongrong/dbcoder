/**
 * 实体映射类 - {输入实体中文名-请与物理模型中Name对应}
 * ============================================================================
 * 版权所有 2012-2015 福州起源软件工作室，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得 QiYuan 商业授权之前，您不能将本软件应用于商业用途，否则 QiYuan 将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.open-code.cn
 * ----------------------------------------------------------------------------
 * KEY: 5a16981b-8c2b-4172-a36d-40bcd11031f9
 * ============================================================================
 */
package com.opencode.tea.backend.xx.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.sql.Timestamp;

public class BaseTable extends BasePage
{
	private static final long serialVersionUID = 3148176768559230877L;

	private String id;

	private Timestamp createTime;

	private Timestamp modifyTime;

	private String methodType;

	private String operateType;

	public enum METHOD_TYPE_ENUM
	{
		// 不可以覆盖：{UPDATE}
		// 可以覆盖：{DONOT}
		CREATE
		{
			public String getValue()
			{
				return "CREATE";/* 新创建 */
			}
		},
		// 不可以覆盖：{}
		// 可以覆盖：{DONOT}
		UPDATE
		{
			public String getValue()
			{
				return "UPDATE";/* 值发生改变 */
			}
		},
		DONOT
		{
			public String getValue()
			{
				return "DONOT";/* 本身就是来自于数据库，并且每发生改变 */
			}
		};

		public abstract String getValue();
	}

	public enum OPERATE_TYPE_ENUM
	{
		SUCCESS
		{
			public String getValue()
			{
				return "SUCCESS";
			}
		},
		FAIL
		{
			public String getValue()
			{
				return "FAIL";
			}
		};

		public abstract String getValue();
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Timestamp getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime)
	{
		this.createTime = createTime;
	}

	public Timestamp getModifyTime()
	{
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime)
	{
		this.modifyTime = modifyTime;
	}

	public String getMethodType()
	{
		return methodType;
	}

	public void setMethodType(String methodType)
	{
		this.methodType = methodType;
	}

	public String getOperateType()
	{
		return operateType;
	}

	public void setOperateType(String operateType)
	{
		this.operateType = operateType;
	}

	public int hashCode()
	{
		return ((this.id == null) ? System.identityHashCode(this) : this.id
				.hashCode());
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (super.getClass().getPackage() != obj.getClass().getPackage())
			return false;

		BaseTable other = (BaseTable) obj;
		if (this.id == null)
		{
			if (other.getId() != null)
			{
				return false;
			}
		}
		return (this.id.equals(other.getId()));
	}

	public String toString()
	{
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}