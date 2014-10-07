package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderSender;
import com.coe.wms.util.Pagination;

public interface IOutWarehouseOrderSenderDao {

	public long saveOutWarehouseOrderSender(OutWarehouseOrderSender sender);

	public int saveBatchOutWarehouseOrderSender(List<OutWarehouseOrderSender> senderList);

	public int saveBatchOutWarehouseOrderSenderWithOrderId(List<OutWarehouseOrderSender> senderList, Long orderId);

	public List<OutWarehouseOrderSender> findOutWarehouseOrderSender(OutWarehouseOrderSender outWarehouseOrderSender,
			Map<String, String> moreParam, Pagination page);
}
