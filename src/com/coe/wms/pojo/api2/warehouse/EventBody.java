package com.coe.wms.pojo.api2.warehouse;

import java.io.Serializable;

/**
 * 仓配入库 eventBody
 * 
 * @author Administrator
 * 
 */
public class EventBody implements Serializable {

	private static final long serialVersionUID = 5322710746438843491L;

	private Order order;

	private OrderPackage orderPackage;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public OrderPackage getOrderPackage() {
		return orderPackage;
	}

	public void setOrderPackage(OrderPackage orderPackage) {
		this.orderPackage = orderPackage;
	}
}
