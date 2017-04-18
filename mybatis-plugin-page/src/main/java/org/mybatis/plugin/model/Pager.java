package org.mybatis.plugin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pager<T> implements Serializable{

	/**  
	* @Fields serialVersionUID : TODO  
	*/   
	private static final long serialVersionUID = 5858226881390560587L;

	/** 数据集合 */
	private List<T> list;

	/** 当前页码 */
	private int page;

	/** 总页数 */
	private int totalPage;

	/** 总记录数 */
	private int total;
	
	public Pager() {
	}

	public Pager(List<T> list, int page, int totalPage, int total) {
		this.list = list;
		this.page = page;
		this.totalPage = totalPage;
		this.total = total;
	}

	public List<T> getList() {
		if (list == null) {
			return new ArrayList<T>();
		}
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
