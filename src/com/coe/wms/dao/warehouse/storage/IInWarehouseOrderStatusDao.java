package com.coe.wms.dao.warehouse.storage;

import java.util.List;

import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderStatus;

public interface IInWarehouseOrderStatusDao {

	public InWarehouseOrderStatus findInWarehouseOrderStatusByCode(String code);

	public List<InWarehouseOrderStatus> findAllInWarehouseOrderStatus();
}
