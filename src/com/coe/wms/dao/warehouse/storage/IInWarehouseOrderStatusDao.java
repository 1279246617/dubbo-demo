package com.coe.wms.dao.warehouse.storage;

import com.coe.wms.model.warehouse.storage.order.InWareHouseOrderStatus;

public interface IInWarehouseOrderStatusDao {

	public InWareHouseOrderStatus findInWarehouseOrderStatusByCode(String code);
}
