package com.coe.wms.dao.warehouse.transport;

import java.util.List;

import com.coe.wms.model.warehouse.transport.OrderStatus;

public interface IOrderStatusDao {

	public OrderStatus findOrderStatusByCode(String code);

	public List<OrderStatus> findAllOrderStatus();
}
