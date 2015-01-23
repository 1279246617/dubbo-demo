package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.OrderCirculations;
import com.coe.wms.util.Pagination;

public interface IOrderCirculationsDao {

	public long saveOrderCirculations(OrderCirculations orderCirculations);

	public OrderCirculations getOrderCirculationsById(Long orderId);

	public List<OrderCirculations> findOrderCirculations(OrderCirculations order, Map<String, String> moreParam, Pagination page);

	public Long countOrderCirculations(OrderCirculations order, Map<String, String> moreParam);
}
