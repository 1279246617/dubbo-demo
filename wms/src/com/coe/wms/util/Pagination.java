package com.coe.wms.util;

import java.util.List;

/**
 * 分页类，所有需要分页的查询返回该类
 */
public class Pagination {

	/**
	 * 默认列表展示数量
	 */
	public final static Integer DEFAULT_PAGE_SIZE = 50;

	/**
	 * 最大列表展示数量 500 ,防止他人模拟浏览器请求查询过大数量导致数据库出现问题
	 */
	public final static Integer MAX_PAGE_SIZE = 500;

	/**
	 * 默认展示第一页数据
	 */
	public final static Integer DEFAULT_PAGE = 1;

	/**
	 * 从高到低
	 */
	public final static String SORT_ORDER_DESC = "desc";

	/**
	 * 从低到高
	 */
	public final static String SORT_ORDER_ASC = "asc";

	/**
	 * 列表数据
	 */
	public List rows;
	/**
	 * 总数
	 */
	public Long total;
	/**
	 * 当前页
	 */
	public int curPage;
	/**
	 * 每页数量
	 */
	public int pageSize;

	/**
	 * 排序 字段
	 */
	public String sortName;

	/**
	 * 排序命令 desc / asc
	 */
	public String sortOrder;

	/**
	 * 格式化 sortOrder curPage pageSize 不用每次都重复写 暂未做最大数量控制
	 */
	public void format() {
		if (!StringUtil.isEqual(Pagination.SORT_ORDER_ASC, this.sortOrder)) {
			this.sortOrder = Pagination.SORT_ORDER_DESC;
		}
		if (this.curPage < 1) {
			this.curPage = Pagination.DEFAULT_PAGE;
		}
		if (this.pageSize < 1) {
			this.pageSize = Pagination.DEFAULT_PAGE_SIZE;
		}
	}

	/**
	 * 格式化 sortName sortOrder curPage pageSize 不用每次都重复写 暂未做最大数量控制
	 */
	public void format(String sortName) {
		if (StringUtil.isNull(this.sortName)) {
			this.sortName = sortName;
		}
		if (!StringUtil.isEqual(Pagination.SORT_ORDER_ASC, this.sortOrder)) {
			this.sortOrder = Pagination.SORT_ORDER_DESC;
		}
		if (this.curPage < 1) {
			this.curPage = Pagination.DEFAULT_PAGE;
		}
		if (this.pageSize < 1) {
			this.pageSize = Pagination.DEFAULT_PAGE_SIZE;
		}
	}
}
