package com.coe.message.entity;

/**分页实体类*/
public class Page {
	/**当前页码*/
	private int pageNo;
	/**总记录数*/
	private int totalRecord;
	/**每页显示记录数*/
	private int pageSize;
	/**总页数*/
	private int totalPage;
	/**记录开始索引(从0开始)*/
	private int startIndex;

	public Page() {

	}

	public Page(int pageNo, int totalRecord, int pageSize, int totalPage, int startIndex) {
		super();
		this.pageNo = pageNo;
		this.totalRecord = totalRecord;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.startIndex = startIndex;
	}

	/**
	 * 初始化Page
	 * @param totalRecord 总记录数
	 * @param pageNo 当前页码
	 * @return Page
	 */
	public static Page init(int totalRecord, int pageNo) {
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setTotalRecord(totalRecord);
		return page;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**获取总页数*/
	public int getTotalPage() {
		int result = totalRecord % pageSize;
		if (result == 0) {
			totalPage = totalRecord / pageSize;
		} else {
			totalPage = totalRecord / pageSize + 1;
		}
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**获取开始索引*/
	public int getStartIndex() {
		if (pageNo <= 1) {
			startIndex = 0;
		} else {
			startIndex = (pageNo - 1) * pageSize;
		}
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
}
