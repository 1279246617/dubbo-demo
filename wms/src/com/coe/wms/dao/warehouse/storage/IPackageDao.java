package com.coe.wms.dao.warehouse.storage;

import com.coe.wms.model.warehouse.storage.order.InWareHouseOrderStatus;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;

public interface IPackageDao {

	public long savePackage(InWarehouseOrder pag);

	public InWareHouseOrderStatus findPackageStatusByCode(String code);
}
