package com.coe.wms.model.warehouse.storage.order;

import java.io.Serializable;

public class InWarehouseOrderItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 61592453340395240L;

	private Long id;

	/**
	 * 入库订单id
	 */
	private Long orderId;

	/**
	 * sku下产品数量
	 */
	private Integer quantity;

	private String sku;

	/**
	 * 产品生产批次(也称客户批次)
	 */
	private String productionBatchNo;

	/**
	 * 产品有效期至
	 */
	private Long validityTime;

	/**
	 * 商品名称
	 */
	private String skuName;

	/**
	 * 备注
	 */
	private String skuRemark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuRemark() {
		return skuRemark;
	}

	public void setSkuRemark(String skuRemark) {
		this.skuRemark = skuRemark;
	}

	public String getProductionBatchNo() {
		return productionBatchNo;
	}

	public void setProductionBatchNo(String productionBatchNo) {
		this.productionBatchNo = productionBatchNo;
	}

	public Long getValidityTime() {
		return validityTime;
	}

	public void setValidityTime(Long validityTime) {
		this.validityTime = validityTime;
	}
}
