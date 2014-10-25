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
	 * 货位编号
	 */
	private String seatCode;

	/**
	 * 货架编号
	 */
	private String shelvesCode;
	
	
	

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

	public String getSeatCode() {
		return seatCode;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
