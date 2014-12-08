package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.Order;
import com.coe.wms.util.Pagination;

public interface IOrderDao {

	public long saveOrder(Order order);

	public Order getOrderById(Long orderId);

	public List<Order> findOrder(Order order, Map<String, String> moreParam, Pagination page);

	public Long countOrder(Order order, Map<String, String> moreParam);

	public int updateOrderStatus(Long orderId, String newStatus);

	public String getOrderStatus(Long orderId);

	public List<Long> findCallbackSendWeightUnSuccessOrderId();

	public List<Long> findCallbackSendStatusUnSuccessOrderId();

	public int updateOrderCallbackSendWeight(Order order);

	public int updateOrderCallbackSendStatus(Order order);

	public int updateOrderWeight(Order order);
}
