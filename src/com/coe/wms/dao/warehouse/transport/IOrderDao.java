package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.Order;
import com.coe.wms.util.Pagination;

public interface IOrderDao {

	public long saveOrder(Order Order);

	public Order getOrderById(Long orderId);

	public List<Order> findOrder(Order order, Map<String, String> moreParam, Pagination page);

	public Long countOrder(Order order, Map<String, String> moreParam);

	public String getOrderStatus(Long orderId);

	public String getCustomerReferenceNoById(Long orderId);

	public int updateOrderStatus(Long orderId, String newStatus);

	public int updateOrderCheckResult(Long orderId, String checkResult);

	public int updateOrderWeight(Order Order);

	public List<Long> findCallbackSendCheckUnSuccessOrderId();

	public List<Long> findUnCheckAndTackingNoIsNullOrderId();

	public List<Long> findCallbackSendWeightUnSuccessOrderId();

	public List<Long> findCallbackSendStatusUnSuccessOrderId();

	public int updateOrderCallbackSendCheck(Order Order);

	public int updateOrderCallbackSendWeight(Order Order);

	public int updateOrderCallbackSendStatus(Order Order);

	public int updateOrderTrackingNo(Order Order);
}
