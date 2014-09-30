package com.coe.wms.dao.warehouse;

import com.coe.wms.model.warehouse.Warehouse;

public interface IWarehouseDao {
	
	public Warehouse getWarehouseById(Long warehouseId);
}
