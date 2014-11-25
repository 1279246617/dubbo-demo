package com.coe.wms.model.warehouse.storage.record;

import java.io.Serializable;

/**
 * 
 * 每天库存
 * 
 * @author Administrator
 * 
 */
public class ItemDailyInventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6253560364864229579L;

	private Long id;

	private Long userIdOfCustomer;

	private Long warehouseId;

	private String sku;

	/**
	 * 实际库存
	 */
	private Integer quantity;

	/**
	 * 可用库存
	 */
	private Integer availableQuantity;

	/**
	 * 结算日期 YYYY-MM-DD
	 */
	private String inventoryDate;

	/**
	 * 结算的时间
	 */
	private Long createdTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserIdOfCustomer() {
		return userIdOfCustomer;
	}

	public void setUserIdOfCustomer(Long userIdOfCustomer) {
		this.userIdOfCustomer = userIdOfCustomer;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public String getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(String inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}
}
