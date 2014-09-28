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
	private int quantity;

	private String sku;

	/**
	 * 商品名称
	 */
	private String skuName;

	/**
	 * 备注
	 */
	private String skuRemark;

	/**
	 * 该SKU已经入库的数量
	 * 
	 * 在添加入库单时,更新此字段.也显示此字段
	 */
	private int receivedQuantity;

	public int getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(int receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
}
