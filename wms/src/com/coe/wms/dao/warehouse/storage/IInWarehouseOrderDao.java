package com.coe.wms.dao.warehouse.storage;

import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;

public interface IInWarehouseOrderDao {

	public long saveInWarehouseOrder(InWarehouseOrder inWarehouseOrder);
}
