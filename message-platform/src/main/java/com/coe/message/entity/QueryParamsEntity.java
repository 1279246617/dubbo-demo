package com.coe.message.entity;

/**分页查询参数实体*/
public class QueryParamsEntity {
	/**开始时间*/
	private String startTime;
	/**结束时间*/
	private String endTime;
	/**时间类型,1:预报接收时间，2:预审时间，3:复审时间，4:报关时间,5:清关时间*/
	private String timeType;
	/**精准查询（true:是，false:模糊查询）*/
	private boolean preciseQuery;
	/**开始索引*/
	private String startIndex;
	/**当前页码*/
	private int page;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public boolean isPreciseQuery() {
		return preciseQuery;
	}

	public void setPreciseQuery(boolean preciseQuery) {
		this.preciseQuery = preciseQuery;
	}

	public String getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(String startIndex) {
		this.startIndex = startIndex;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	/**每页显示记录数*/
	private int rows;
}
