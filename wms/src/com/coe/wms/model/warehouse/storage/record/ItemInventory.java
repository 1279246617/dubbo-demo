package com.coe.wms.model.warehouse.storage.record;

import java.io.Serializable;

/**
 * 在入库 出库时产品批次库存
 * 
 * 在上架和下架时修改产品货位库存
 * 
 * @author Administrator
 * 
 */
public class ItemInventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7668477948339022691L;
	private Long id;

	private Long userIdOfCustomer;

	private Long warehouseId;

	private String sku;

	/**
	 * 实际库存
	 */
	private Integer quantity;

	/**
	 * 次字段暂时无用, 可用库存记录已经从 货位库存记录获取
	 */
	private Integer availableQuantity;

	private Long lastUpdateTime;

	private Long createdTime;
	/**
	 * 入库批次号 可以查到某批次还剩余多少货
	 */
	private String batchNo;

	public Long getUserIdOfCustomer() {
		return userIdOfCustomer;
	}

	public void setUserIdOfCustomer(Long userIdOfCustomer) {
		this.userIdOfCustomer = userIdOfCustomer;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAvailableQuantity() {
		return availableQuantity;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
}
