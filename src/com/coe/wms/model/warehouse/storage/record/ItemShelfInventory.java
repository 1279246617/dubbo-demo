package com.coe.wms.model.warehouse.storage.record;

import java.io.Serializable;

/**
 * 产品货位库存
 * 
 * 
 * 
 * 在入库 出库时产品批次库存
 * 
 * 在上架和下架时修改产品货位库存
 * 
 * @author Administrator
 * 
 */
public class ItemShelfInventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7668477948339022691L;
	private Long id;

	private Long userIdOfCustomer;

	private Long warehouseId;

	private String sku;

	/**
	 * sku所属的批次号
	 */
	private String batchNo;
	/**
	 * 货位号,可以查到货位上的SKU数量
	 */
	private String seatCode;

	
	//
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

	private Long createdTime;

	public Long getUserIdOfCustomer() {
		return userIdOfCustomer;
	}

	public void setUserIdOfCustomer(Long userIdOfCustomer) {
		this.userIdOfCustomer = userIdOfCustomer;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
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

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
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

	public String getSeatCode() {
		return seatCode;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
}
