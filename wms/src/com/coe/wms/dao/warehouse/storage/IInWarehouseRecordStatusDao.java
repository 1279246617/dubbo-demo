package com.coe.wms.dao.warehouse.storage;

import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordStatus;

public interface IInWarehouseRecordStatusDao {

	public InWarehouseRecordStatus findInWarehouseRecordStatusByCode(String code);
}
