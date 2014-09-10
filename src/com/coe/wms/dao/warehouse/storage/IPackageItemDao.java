package com.coe.wms.dao.warehouse.storage;

import java.util.List;

import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;

public interface IPackageItemDao {

	public long savePackageItem(InWarehouseOrderItem item);

	public int saveBatchPackageItem(List<InWarehouseOrderItem> itemList);
}
