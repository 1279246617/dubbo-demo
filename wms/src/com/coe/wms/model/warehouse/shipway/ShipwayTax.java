package com.coe.wms.model.warehouse.shipway;

import java.io.Serializable;

public class ShipwayTax implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 299542286334796384L;
	private Long id;
	/**
	 * 客户订单号
	 */
	private String customerReferenceNo;
	/**
	 * 订单id
	 */
	private String orderId;
	/**
	 * 订单类型
	 */
	private String orderType;
	/**
	 * 发货渠道
	 */
	private String shipwayCode;
	/**
	 * 税费 默认单位:人民币元
	 */
	private Double taxValue;
	/**
	 * 币种
	 */
	private Double taxCurrency;
	/**
	 * 收税时间
	 */
	private Long taxTime;
	/**
	 * 订单商品价格
	 */
	private Double orderValue;
	/**
	 * 订单商品价格单位
	 */
	private String orderValueCurrency;
	/**
	 * 此记录创建时间
	 */
	private Long createdTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerReferenceNo() {
		return customerReferenceNo;
	}

	public void setCustomerReferenceNo(String customerReferenceNo) {
		this.customerReferenceNo = customerReferenceNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getShipwayCode() {
		return shipwayCode;
	}

	public void setShipwayCode(String shipwayCode) {
		this.shipwayCode = shipwayCode;
	}

	public Double getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(Double taxValue) {
		this.taxValue = taxValue;
	}

	public Double getTaxCurrency() {
		return taxCurrency;
	}

	public void setTaxCurrency(Double taxCurrency) {
		this.taxCurrency = taxCurrency;
	}

	public Long getTaxTime() {
		return taxTime;
	}

	public void setTaxTime(Long taxTime) {
		this.taxTime = taxTime;
	}

	public Double getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Double orderValue) {
		this.orderValue = orderValue;
	}

	public String getOrderValueCurrency() {
		return orderValueCurrency;
	}

	public void setOrderValueCurrency(String orderValueCurrency) {
		this.orderValueCurrency = orderValueCurrency;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}
}
