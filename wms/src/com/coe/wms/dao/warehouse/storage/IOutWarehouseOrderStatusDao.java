package com.coe.wms.dao.warehouse.storage;

import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus;

public interface IOutWarehouseOrderStatusDao {

	public OutWarehouseOrderStatus findOutWarehouseOrderStatusByCode(String code);
}
