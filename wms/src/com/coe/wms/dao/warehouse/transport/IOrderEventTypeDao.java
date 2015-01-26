package com.coe.wms.dao.warehouse.transport;

import java.util.List;

import com.coe.wms.model.warehouse.transport.OrderEventType;

public interface IOrderEventTypeDao {

	public OrderEventType findOrderEventTypeByCode(String code);

	public List<OrderEventType> findAllOrderEventType();
}
