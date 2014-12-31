package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.OrderReceiver;
import com.coe.wms.util.Pagination;

public interface IOrderReceiverDao {

	public long saveOrderReceiver(OrderReceiver receiver);

	public int saveBatchOrderReceiver(List<OrderReceiver> receiverList);

	public int saveBatchOrderReceiverWithPackageId(List<OrderReceiver> receiverList, Long orderId);

	public List<OrderReceiver> findOrderReceiver(OrderReceiver order, Map<String, String> moreParam, Pagination page);

	public OrderReceiver getOrderReceiverByPackageId(Long orderId);
}
