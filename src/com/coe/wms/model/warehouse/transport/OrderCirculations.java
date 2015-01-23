package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

/**
 * 转运订单流转
 * 
 * 只记录流转站点
 * 
 * @author Administrator
 * 
 */
public class OrderCirculations implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6280322649689879791L;
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 转运订单id
	 */
	private Long orderId;

	/**
	 * 当前仓库id
	 */
	private Long currentWarehouseId;

	/**
	 * 上一个仓库id
	 */
	private Long fromWarehouseId;

	/**
	 * 将会发往的仓库id
	 */
	private Long toWarehouseId;

	/**
	 * 订单所属客户id
	 */
	private Long userIdOfCustomer;

	/**
	 * 创建时间
	 */
	private Long createdTime;

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

	public Long getCurrentWarehouseId() {
		return currentWarehouseId;
	}

	public void setCurrentWarehouseId(Long currentWarehouseId) {
		this.currentWarehouseId = currentWarehouseId;
	}

	public Long getFromWarehouseId() {
		return fromWarehouseId;
	}

	public void setFromWarehouseId(Long fromWarehouseId) {
		this.fromWarehouseId = fromWarehouseId;
	}

	public Long getToWarehouseId() {
		return toWarehouseId;
	}

	public void setToWarehouseId(Long toWarehouseId) {
		this.toWarehouseId = toWarehouseId;
	}

	public Long getUserIdOfCustomer() {
		return userIdOfCustomer;
	}

	public void setUserIdOfCustomer(Long userIdOfCustomer) {
		this.userIdOfCustomer = userIdOfCustomer;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}
}
