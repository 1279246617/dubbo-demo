package com.coe.wms.model.order;

import java.io.Serializable;

/**
 * 订单物品|报关信息
 * 
 * @author yechao
 * @date 2013年11月3日
 */
public class OrderItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6403535606524196497L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long id;
	
	/**
	 * 订单ID, 订单表的ID
	 */
	private Long orderId;

	/**
	 * SKU名称 不可空
	 */
	private String sku;

	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 报关数量
	 */
	private Integer quantity;

	/**
	 * 报关重量 必选. 默认 500克 重量单位 克
	 */
	private Double weight;

	/**
	 * 报关价值(单价) 必选. 默认 0.5USD
	 */
	private Double value;

	/**
	 * 报关描述(申报品名),
	 */
	private String customesDescription;

	/**
	 * 海关编号
	 */
	private String hsCode;

	/**
	 * 行邮税号
	 */
	private String taxCode;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getCustomesDescription() {
		return customesDescription;
	}

	public void setCustomesDescription(String customesDescription) {
		this.customesDescription = customesDescription;
	}

	public String getHsCode() {
		return hsCode;
	}

	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

}
