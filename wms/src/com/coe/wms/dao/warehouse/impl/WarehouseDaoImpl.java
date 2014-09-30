package com.coe.wms.dao.warehouse.impl;

import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.model.warehouse.Warehouse;

public class WarehouseDaoImpl implements IWarehouseDao {

	@Override
	public Warehouse getWarehouseById(Long warehouseId) {
		String sql = "select  id,length_unit_code,meters,width,height,length,address_line2,address_line1,postal_code,city,state_or_province,country_name,country_code,warehouse_name,remark,created_time,created_by_user_id,last_modifie_by_user_id,last_modifie_time where id = ?";
		
		
		return null;
	}
}
