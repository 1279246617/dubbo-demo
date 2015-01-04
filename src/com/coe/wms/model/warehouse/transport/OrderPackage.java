package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

/**
 * 转运订单大包
 * 
 * @author Administrator
 * 
 */
public class OrderPackage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4114773934773316263L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 仓库id
	 */
	private Long warehouseId;

	/**
	 * 大包所属客户id
	 */
	private Long userIdOfCustomer;

	/**
	 * 收货操作员id
	 */
	private Long userIdOfOperator;
	/**
	 * 创建时间
	 */
	private Long createdTime;
	/**
	 * 客户订单号, 用于客户对该出库指令进行修改,确认等
	 * 
	 * 对应 顺丰 tradeOrderId
	 */
	private String customerReferenceNo;
	/**
	 * 备注
	 */
	private String remark;
	
	private String trackingNo;
	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Long getUserIdOfCustomer() {
		return userIdOfCustomer;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public void setUserIdOfCustomer(Long userIdOfCustomer) {
		this.userIdOfCustomer = userIdOfCustomer;
	}

	public Long getUserIdOfOperator() {
		return userIdOfOperator;
	}

	public void setUserIdOfOperator(Long userIdOfOperator) {
		this.userIdOfOperator = userIdOfOperator;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getCustomerReferenceNo() {
		return customerReferenceNo;
	}

	public void setCustomerReferenceNo(String customerReferenceNo) {
		this.customerReferenceNo = customerReferenceNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
