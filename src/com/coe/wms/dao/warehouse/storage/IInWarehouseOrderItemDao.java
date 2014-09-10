package com.coe.wms.dao.warehouse.storage;

import java.util.List;

import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;

public interface IInWarehouseOrderItemDao {

	public long saveInWarehouseOrderItem(InWarehouseOrderItem item);

	public int saveBatchInWarehouseOrderItem(List<InWarehouseOrderItem> itemList);
	
//	public List<InWarehouseOrderItem>
}
