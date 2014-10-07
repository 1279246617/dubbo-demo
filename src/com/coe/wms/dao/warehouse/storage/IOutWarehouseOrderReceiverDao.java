package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.util.Pagination;

public interface IOutWarehouseOrderReceiverDao {

	public long saveOutWarehouseOrderReceiver(OutWarehouseOrderReceiver receiver);

	public int saveBatchOutWarehouseOrderReceiver(List<OutWarehouseOrderReceiver> receiverList);

	public int saveBatchOutWarehouseOrderReceiverWithOrderId(List<OutWarehouseOrderReceiver> receiverList, Long orderId);

	public List<OutWarehouseOrderReceiver> findOutWarehouseOrderReceiver(OutWarehouseOrderReceiver outWarehouseOrder,
			Map<String, String> moreParam, Pagination page);
	
	public OutWarehouseOrderReceiver getOutWarehouseOrderReceiverByOrderId(Long outWarehouseOrderId);
}
