package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.util.Pagination;

public interface IOutWarehouseOrderItemDao {

	public long saveOutWarehouseOrderItem(OutWarehouseOrderItem item);

	public int saveBatchOutWarehouseOrderItem(List<OutWarehouseOrderItem> itemList);
	
	public int saveBatchOutWarehouseOrderItemWithOrderId(List<OutWarehouseOrderItem> itemList,Long orderId);
	
	public List<OutWarehouseOrderItem> findOutWarehouseOrderItem(OutWarehouseOrderItem outWarehouseOrderItem,Map<String,String> moreParam, Pagination page);
}
