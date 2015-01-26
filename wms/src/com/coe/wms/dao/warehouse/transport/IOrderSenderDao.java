package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.OrderSender;
import com.coe.wms.util.Pagination;

public interface IOrderSenderDao {

	public long saveOrderSender(OrderSender sender);

	public int saveBatchOrderSender(List<OrderSender> senderList);

	public int saveBatchOrderSenderWithPackageId(List<OrderSender> senderList, Long orderId);

	public List<OrderSender> findOrderSender(OrderSender orderSender, Map<String, String> moreParam, Pagination page);

	public OrderSender getOrderSenderByOrderId(Long orderId);
}
