/**    
* @Title: PageBean.java
* @Package com.coe.wms.common.web.page
* @author yechao
* @date 2017年2月7日 下午2:26:59
* @version V1.0
* @Description: TODO   
*/
package com.coe.wms.common.web.page;

import java.util.List;

/**
 * @ClassName: PageBean
 * @author yechao
 * @date 2017年2月7日 下午2:26:59
 * @Description: TODO
 */
public class PageBean<T> {
	private int page; // 当前页数
	private int totalCount; // 总记录数
	private int totalPage; // 总页数
	private int limit; // 每页显示的记录数
	private List<T> list; // 每页显示数据记录的集合；

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}