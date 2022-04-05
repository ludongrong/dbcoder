package com.opencode.tea.backend.module;

import com.opencode.tea.backend.exception.PageIndexException;

public class OracleType extends BaseTable
{
	
	private static final long serialVersionUID = -5623534037111390821L;

	private boolean isFirst;
	
	private int startIndex;
	
	private int lastIndex;

	public int getStartIndex()
	{
		return startIndex;
	}

	public void setStartIndex(int startIndex)
	{
		this.startIndex = startIndex;
	}

	public int getLastIndex()
	{
		return lastIndex;
	}

	public void setLastIndex(int lastIndex)
	{
		this.lastIndex = lastIndex;
	}
	
	public boolean getIsFirst()
	{
		return isFirst;
	}

	public void setIsFirst(boolean isFirst)
	{
		this.isFirst = isFirst;
	}

	public Object initIndex() throws PageIndexException{
		if (this.isFirst)
		{
			this.setPageSize(7);
		}

		this.setStartIndex((this.getPageNo() - 1) * this.getPageSize());
		this.setLastIndex(this.getStartIndex() + this.getPageSize() + 1);

		if (startIndex < 0 || lastIndex < 0)
		{
			throw new PageIndexException("分页条数设置错误");
		}

		if (lastIndex < startIndex)
		{
			throw new PageIndexException("分页条数设置错误");
		}
		
		return this;
	}
		
}
