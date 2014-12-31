package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.OrderReceiver;
import com.coe.wms.util.Pagination;

public interface IBigPackageReceiverDao {

	public long saveBigPackageReceiver(OrderReceiver receiver);

	public int saveBatchBigPackageReceiver(List<OrderReceiver> receiverList);

	public int saveBatchBigPackageReceiverWithPackageId(List<OrderReceiver> receiverList, Long orderId);

	public List<OrderReceiver> findBigPackageReceiver(OrderReceiver order, Map<String, String> moreParam, Pagination page);

	public OrderReceiver getBigPackageReceiverByPackageId(Long orderId);
}
