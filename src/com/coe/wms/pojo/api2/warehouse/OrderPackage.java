package com.coe.wms.pojo.api2.warehouse;

import java.io.Serializable;

public class OrderPackage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2910392177572717880L;

	/**
	 * 客户订单号
	 */
	private String customerReferenceNo;

	private String warehouseNo;
	/**
	 * 大包承运商
	 */
	private String carrierCode;
	/**
	 * 头程跟踪单号
	 */
	private String trackingNo;

	public String getCustomerReferenceNo() {
		return customerReferenceNo;
	}

	public void setCustomerReferenceNo(String customerReferenceNo) {
		this.customerReferenceNo = customerReferenceNo;
	}

	public String getWarehouseNo() {
		return warehouseNo;
	}

	public void setWarehouseNo(String warehouseNo) {
		this.warehouseNo = warehouseNo;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
}
