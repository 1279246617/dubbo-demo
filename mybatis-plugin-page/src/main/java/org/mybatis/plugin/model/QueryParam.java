package org.mybatis.plugin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 查询条件类，分页查询条件类需要继承该类
 * @author shiw
 *
 */
public class QueryParam {
	
	/** 记录页码 */
	@JsonIgnore
	private int page = 1;
	
	/** 限制纪录条数 */
	@JsonIgnore
	private int limit = 10;
	
	/** 总纪录数 */
	@JsonIgnore
	private int count;
	
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	@JsonIgnore
	public int getStart() {
		int start = (page-1) * limit;
		return start;
	}
	
	@JsonIgnore
	public int getTotalPage(){
		if(this.count == 0){
			return 0;
		}
		int totalPage = this.count / this.limit;
		if(this.count % this.limit != 0){
			totalPage ++;
		}
		return totalPage;
	}
}
