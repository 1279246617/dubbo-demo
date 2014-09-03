package com.coe.wms.model.warehouse;

import java.io.Serializable;

public class Shelves implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1289101354442689843L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 所属仓库
	 */
	private Long wareHoseId;

	/**
	 * 货架类型(大小,货位数)
	 */
	private Long shelvesTypeId;

	/**
	 * 货位数
	 * 
	 * 比货架类型货位数优先级高
	 */
	private int seats;

	/**
	 * 货架类型备注
	 */
	private String remark;

	/**
	 * 货架序号 A BC 123...
	 */
	private String sort;

	private Long createdTime;

	private Long createdByUserId;

	private Long lastModifieTime;

	private Long lastModifieByUserId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWareHoseId() {
		return wareHoseId;
	}

	public void setWareHoseId(Long wareHoseId) {
		this.wareHoseId = wareHoseId;
	}

	public Long getShelvesTypeId() {
		return shelvesTypeId;
	}

	public void setShelvesTypeId(Long shelvesTypeId) {
		this.shelvesTypeId = shelvesTypeId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Long getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(Long createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public Long getLastModifieTime() {
		return lastModifieTime;
	}

	public void setLastModifieTime(Long lastModifieTime) {
		this.lastModifieTime = lastModifieTime;
	}

	public Long getLastModifieByUserId() {
		return lastModifieByUserId;
	}

	public void setLastModifieByUserId(Long lastModifieByUserId) {
		this.lastModifieByUserId = lastModifieByUserId;
	}
}
