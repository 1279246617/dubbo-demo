package com.coe.wms.dao.warehouse.storage;

import java.util.List;

import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus;

public interface IOutWarehouseOrderStatusDao {

	public OutWarehouseOrderStatus findOutWarehouseOrderStatusByCode(String code);

	public List<OutWarehouseOrderStatus> findAllOutWarehouseOrderStatus();
}
