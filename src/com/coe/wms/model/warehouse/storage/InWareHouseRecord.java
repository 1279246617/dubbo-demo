package com.coe.wms.model.warehouse.storage;

import java.io.Serializable;

/**
 * 入库单 (主单)
 * 
 * 
 * 
 * 如果入库单是来自于 仓配则 一个入库单中有且只有同一个大包号的sku
 * 
 * 如果入库单是来自于 转运则一个入库单商品 等于一个转运订单
 * 
 * @author Administrator
 * 
 */
public class InWareHouseRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1475638002960975692L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 仓库id
	 */
	private Long wareHouseId;

	/**
	 * 操作员Id
	 */
	private Long userId;

	/**
	 * 批次号
	 */
	private String batchNo;

	/**
	 * 客户下的大包号
	 * 
	 * 要求一个入库单中只能包含同一个大包号的sku
	 * 
	 * 如果无大包号, 表示是无主件,即无预报Package
	 */
	private String packageNo;

	/**
	 * 创建时间 (收货时间)
	 */
	private Long createdTime;

	/**
	 * 入库摘要
	 */
	private String remark;

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

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
}
