package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.OrderTraces;
import com.coe.wms.util.Pagination;

public interface IOrderTracesDao {

	public long saveOrderTraces(OrderTraces orderTraces);

	public OrderTraces getOrderTracesById(Long orderId);

	public List<OrderTraces> findOrderTraces(OrderTraces order, Map<String, String> moreParam, Pagination page);
	
	public Long countOrderTraces(OrderTraces order, Map<String, String> moreParam);
}
