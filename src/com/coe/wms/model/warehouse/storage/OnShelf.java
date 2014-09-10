package com.coe.wms.model.warehouse.storage;

import java.io.Serializable;

/**
 * 上架表
 * 
 * @author Administrator
 * 
 */
public class OnShelf implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7518195625677660678L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 仓库id
	 */
	private Long wareHouseId;

	/**
	 * 货架id
	 */
	private Long shelfId;

	/**
	 * 操作员Id
	 */
	private Long userId;

	/**
	 * 批次号
	 * 
	 * 可空
	 */
	private String batchNo;

	/**
	 * 客户下的大包号
	 * 
	 * 要求一个上架记录单中只能包含同一个大包号的sku
	 * 
	 * 可空
	 */
	private String packageNo;

	/**
	 * 创建时间 (收货时间)
	 */
	private Long createdTime;

	/**
	 * 上架摘要
	 */
	private String remark;

	/**
	 * sku下产品数量
	 * 
	 * 必填
	 */
	private int quantity;

	/**
	 * 
	 * sku
	 * 
	 * 必填
	 */
	private String sku;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(Long wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	public Long getShelfId() {
		return shelfId;
	}

	public void setShelfId(Long shelfId) {
		this.shelfId = shelfId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
}
