package com.coe.wms.model.warehouse.shipway;

import java.io.Serializable;

public class ShipwayTax implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 299542286334796384L;
	private Long id;

	private Long userIdOfCustomer;

	/**
	 * 客户订单号
	 */
	private String customerReferenceNo;
	/**
	 * 订单id
	 */
	private Long orderId;
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
	private String taxCurrency;
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
	/**
	 * 回调是否成功 Y 成功 N或者空失败
	 */
	private String sendIsSuccess;
	/**
	 * 回调次数
	 */
	private Integer sendCount;

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

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
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

	public Long getUserIdOfCustomer() {
		return userIdOfCustomer;
	}

	public void setUserIdOfCustomer(Long userIdOfCustomer) {
		this.userIdOfCustomer = userIdOfCustomer;
	}

	public String getSendIsSuccess() {
		return sendIsSuccess;
	}

	public void setSendIsSuccess(String sendIsSuccess) {
		this.sendIsSuccess = sendIsSuccess;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
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

	public String getTaxCurrency() {
		return taxCurrency;
	}

	public void setTaxCurrency(String taxCurrency) {
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
