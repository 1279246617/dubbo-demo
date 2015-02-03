package com.coe.wms.util;

import java.util.List;

/**
 * 分页类，所有需要分页的查询返回该类
 */
public class Pagination {
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
	 * 生成 分页sql
	 * 
	 * @return
	 */
	public String generatePageSqlOnTable(String table) {
		String sql = "";
		if (StringUtil.isNotNull(this.sortName)) {
			sql += " order by ";
			sql += table + "." + this.sortName + " ";
			if (StringUtil.isNotNull(this.sortOrder)) {
				sql += this.sortOrder + " ";
			}
		}
		if (curPage < 1) {
			return sql;
		}
		// 起始数字
		int start = (curPage - 1) * pageSize;
		sql += " limit " + start + "," + pageSize;
		return sql;
	}

	/**
	 * 生成 分页sql
	 * 
	 * @return
	 */
	public String generatePageSql() {
		String sql = "";
		if (StringUtil.isNotNull(this.sortName)) {
			sql += " order by ";
			sql += this.sortName + " ";
			if (StringUtil.isNotNull(this.sortOrder)) {
				sql += this.sortOrder + " ";
			}
		}
		if (curPage < 1) {
			return sql;
		}
		// 起始数字
		int start = (curPage - 1) * pageSize;
		sql += " limit " + start + "," + pageSize;
		return sql;
	}

	/**
	 * 排序字段格式化
	 * 
	 * @return
	 */
	public String sortNameFormat() {
		if (StringUtil.isNotNull(this.sortName)) {
			this.sortName = StringUtil.toInRowName(this.sortName);
			return this.sortName;
		}
		return null;
	}
}
