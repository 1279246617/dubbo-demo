package com.coe.wms.pojo.api2.warehouse;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5627722174834814449L;

	private String customerReferenceNo;

	private String shipwayCode;

	private String trackingNo;

	private String warehouseNo;

	private String remark;

	private Sender sender;

	private Receiver receiver;

	private List<FirstWaybill> firstWaybills;

	public String getCustomerReferenceNo() {
		return customerReferenceNo;
	}

	public void setCustomerReferenceNo(String customerReferenceNo) {
		this.customerReferenceNo = customerReferenceNo;
	}

	public String getShipwayCode() {
		return shipwayCode;
	}

	public void setShipwayCode(String shipwayCode) {
		this.shipwayCode = shipwayCode;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getWarehouseNo() {
		return warehouseNo;
	}

	public void setWarehouseNo(String warehouseNo) {
		this.warehouseNo = warehouseNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	@XmlElementWrapper(name = "firstWaybills")
	@XmlElement(name = "firstWaybill")
	public List<FirstWaybill> getFirstWaybills() {
		return firstWaybills;
	}

	public void setFirstWaybills(List<FirstWaybill> firstWaybills) {
		this.firstWaybills = firstWaybills;
	}
}
