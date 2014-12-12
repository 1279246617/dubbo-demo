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
	 * sku下商品数量
	 */
	private Integer quantity;

	/**
	 * sku 等于顺丰的商品条码
	 * 
	 * 2014-12-12
	 * 确认顺丰文档中的sku字段是商品条码(应当叫barcode字段)
	 * 对应系统商品库(product)的barcode字段
	 */
	private String sku;
	
	/**
	 * sku编号 等于顺丰的商品条码(skuId)
	 * 实际顺丰是以skuId作为sku
	 * 
	 * 2014-12-12对应系统商品库(product的sku字段)
	 */
	private String skuNo;

	/**
	 * 商品生产批次(也称客户批次)
	 */
	private String productionBatchNo;

	/**
	 * 商品有效期至
	 */
	private Long validityTime;

	/**
	 * 商品名称
	 */
	private String skuName;

	/**
	 * 商品规格
	 */
	private String specification;

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

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
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

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
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
