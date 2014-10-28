package com.coe.wms.model.warehouse.storage.record;

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
	private Long warehouseId;

	/**
	 * 操作员Id
	 */
	private Long userIdOfOperator;

	/**
	 * 操作员Id
	 */
	private Long userIdOfCustomer;

	/**
	 * 
	 */
	private Long inWarehouseRecordId;

	/**
	 * 批次号
	 * 
	 * 可空
	 */
	private String batchNo;

	private String trackingNo;

	/**
	 * 货位
	 */
	private String seatCode;

	/**
	 * sku下产品数量
	 * 
	 * 必填
	 */
	private Integer quantity;

	/**
	 * sku下产品数量 下架产品数量
	 */
	private Integer outQuantity;

	/**
	 * 预下架数量(打印捡货单,预下架的产品数量,用于连续打印捡货单,时不会使用同样的货位的产品)
	 * 
	 *使用定时任务,清空此字段
	 */
	private Integer preOutQuantity;

	private String status;

	/**
	 * 
	 * sku
	 * 
	 * 必填
	 */
	private String sku;

	/**
	 * 创建时间 (收货时间)
	 */
	private Long createdTime;

	public Long getId() {
		return id;
	}

	public Integer getPreOutQuantity() {
		return preOutQuantity;
	}

	public void setPreOutQuantity(Integer preOutQuantity) {
		this.preOutQuantity = preOutQuantity;
	}

	public Integer getOutQuantity() {
		return outQuantity;
	}

	public void setOutQuantity(Integer outQuantity) {
		this.outQuantity = outQuantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Long getUserIdOfOperator() {
		return userIdOfOperator;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public void setUserIdOfOperator(Long userIdOfOperator) {
		this.userIdOfOperator = userIdOfOperator;
	}

	public Long getUserIdOfCustomer() {
		return userIdOfCustomer;
	}

	public void setUserIdOfCustomer(Long userIdOfCustomer) {
		this.userIdOfCustomer = userIdOfCustomer;
	}

	public Long getInWarehouseRecordId() {
		return inWarehouseRecordId;
	}

	public void setInWarehouseRecordId(Long inWarehouseRecordId) {
		this.inWarehouseRecordId = inWarehouseRecordId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSeatCode() {
		return seatCode;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

}
