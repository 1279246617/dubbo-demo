package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItemShelf;
import com.coe.wms.util.Pagination;

public interface IOutWarehouseOrderItemShelfDao {

	public long saveOutWarehouseOrderItemShelf(OutWarehouseOrderItemShelf item);

	public int saveBatchOutWarehouseOrderItemShelf(List<OutWarehouseOrderItemShelf> itemList);

	public int saveBatchOutWarehouseOrderItemShelfWithOrderId(List<OutWarehouseOrderItemShelf> itemList, Long orderId);

	public List<OutWarehouseOrderItemShelf> findOutWarehouseOrderItemShelf(OutWarehouseOrderItemShelf outWarehouseOrderItem,
			Map<String, String> moreParam, Pagination page);
}
