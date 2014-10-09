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

	private Long userId;

	private Long wareHouseId;

	private String sku;
	
	private Integer quantity;

	/**
	 * 入库批次号 可以查到某批次还剩余多少货
	 */
	private String batchNo;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(Long wareHouseId) {
		this.wareHouseId = wareHouseId;
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

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
}
