package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

/**
 * 转运订单轨迹表
 * 
 * @author Administrator
 * 
 */
public class OrderTraces implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1196386381305991670L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 转运订单id
	 */
	private Long orderId;
	/**
	 * 订单所属客户id
	 */
	private Long userIdOfCustomer;
	/**
	 * 操作人
	 */
	private Long userIdOfOperator;

	/**
	 * 轨迹发生的仓库id
	 */
	private Long warehouseId;
	/**
	 * 详细地址(和仓库id 最少有一个不为空)
	 */
	private String location;
	/**
	 * 事件(收货,上架,出货)
	 */
	private String event;
	/**
	 * 事件类型
	 */
	private String eventType;
	/**
	 * 创建时间
	 */
	private Long createdTime;
	/**
	 * 备注
	 */
	private String remark;

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

	public Long getUserIdOfCustomer() {
		return userIdOfCustomer;
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

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
