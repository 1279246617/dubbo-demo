package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.util.Pagination;

public interface IInWarehouseRecordDao {

	public long saveInWarehouseRecord(InWarehouseRecord inWarehouseRecord);

	public InWarehouseRecord getInWarehouseRecordById(Long InWarehouseRecordId);

	public List<InWarehouseRecord> findInWarehouseRecord(InWarehouseRecord InWarehouseRecord,
			Map<String, String> moreParam, Pagination page);

}
