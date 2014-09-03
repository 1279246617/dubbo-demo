package com.coe.wms.model.order;

import java.io.Serializable;

/**
 * 订单数量统计表
 * 
 * @author yechao
 * @date 2013年12月15日
 */
public class OrderStatusCount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5266566336485619961L;
	/**
	 * 客户Id customer表的id
	 */
	private Long userId;
	/**
	 * 订单状态
	 */
	private Integer status;

	/**
	 * 该状态下的订单数量
	 */
	private Long quantity;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
