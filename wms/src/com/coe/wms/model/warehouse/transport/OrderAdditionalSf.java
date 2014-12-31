package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

/**
 * 
 * 
 * 顺丰附加
 * 
 * 
 * 对应文档中:clearanceDetail内容
 * 
 * @author Administrator
 * 
 */
public class OrderAdditionalSf implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8950306490791178866L;
	/**
	 * 主键
	 */
	private Long id;

	private Long bigPackageId;

	/**
	 * 顺丰指定运输方式 , 在出库订单主表也存在此字段,名称 shipwayCode
	 */
	private String carrierCode;

	private String mailNo;

	/**
	 * 寄方地址，需要打印在顺丰运单上
	 */
	private String senderAddress;

	/**
	 * 月结卡号，需要打印在顺丰运单上
	 */
	private String custId;
	/**
	 * 付款方式，需要打印在顺丰运单上，默认寄付月结
	 */
	private String payMethod;

	/**
	 * 寄件方代码，系统定义，需要打印在顺丰运单上
	 */
	private String shipperCode;
	/**
	 * 到件方代码，目的地代码BSP返回，需打印到顺丰运单上
	 */
	private String deliveryCode;

	/**
	 * 用户订单号，需要打印到顺丰运单上
	 * 
	 * 用户订单号 非本系统出库订单的id
	 */
	private String customerOrderId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getShipperCode() {
		return shipperCode;
	}

	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public Long getBigPackageId() {
		return bigPackageId;
	}

	public void setBigPackageId(Long bigPackageId) {
		this.bigPackageId = bigPackageId;
	}

	public String getCustomerOrderId() {
		return customerOrderId;
	}

	public void setCustomerOrderId(String customerOrderId) {
		this.customerOrderId = customerOrderId;
	}
}
