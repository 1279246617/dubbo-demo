package com.coe.wms.model.warehouse;

import java.io.Serializable;

/**
 * 货位
 * 
 * @author Administrator
 * 
 */
public class Seat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2216058659884286333L;
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 序号
	 */
	private String sort;

	/**
	 * 货位编号
	 */
	private String number;

	/**
	 * 备注
	 */
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
