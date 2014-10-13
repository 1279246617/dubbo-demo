package com.coe.wms.dao.warehouse;

import java.util.List;

import com.coe.wms.model.warehouse.Warehouse;

public interface IWarehouseDao {

	public Warehouse getWarehouseById(Long warehouseId);

	public Warehouse getWarehouseByNo(String warehouseNo);
	
	public List<Warehouse> findAllWarehouse();
}
