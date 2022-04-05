package com.opencode.tea.backend.module;

import java.io.Serializable;

public class BasePage implements Serializable
{

	private static final long serialVersionUID = 9113505967548724758L;

	// 默认当前第几页
	private final static int DEFAULT_PAGE_NO = 1;

	// 默认页的条数
	private int DEFAULT_PAGE_SIZE;

	private int pageNo;

	private int pageSize;
	
	private int total;

	public int getPageNo()
	{
		return pageNo;
	}

	public void setPageNo(int pageNo)
	{
		this.pageNo = pageNo;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

}
