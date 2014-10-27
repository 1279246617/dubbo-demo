package com.coe.wms.model.warehouse.storage.record;

import java.io.Serializable;

/**
 * 产品库存
 * 
 * 
 * 通过触发器 在入库 出库时 修改此表记录. 定时和流水记录统计对比
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
	 * 可用库存 客户创建了出库订单,但是出库订单并没有完成, 这时可用库存等于 实际库存 - 出库订单sku数量
	 * 
	 * 预扣库存
	 */
	private Integer availableQuantity;

	private Long lastUpdateTime;
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
