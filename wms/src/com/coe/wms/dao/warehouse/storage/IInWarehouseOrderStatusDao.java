package com.coe.wms.dao.warehouse.storage;

import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderStatus;

public interface IInWarehouseOrderStatusDao {

	public InWarehouseOrderStatus findInWarehouseOrderStatusByCode(String code);
}
