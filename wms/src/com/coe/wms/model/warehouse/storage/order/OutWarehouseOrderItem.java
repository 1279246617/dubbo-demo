package com.coe.wms.model.warehouse.storage.order;

import java.io.Serializable;

/**
 * 出库详情单
 * 
 * @author Administrator
 * 
 */
public class OutWarehouseOrderItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2079149253927077125L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 入库主单id
	 */
	private Long inWareHouseRecordId;

	/**
	 * 数量
	 */
	private Integer quantity;

	/**
	 * sku
	 */
	private String sku;

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

	public Long getInWareHouseRecordId() {
		return inWareHouseRecordId;
	}

	public void setInWareHouseRecordId(Long inWareHouseRecordId) {
		this.inWareHouseRecordId = inWareHouseRecordId;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
