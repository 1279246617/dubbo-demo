package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.util.Pagination;

public interface IInWarehouseRecordDao {

	public long saveInWarehouseRecord(InWarehouseRecord inWarehouseRecord);

	public InWarehouseRecord getInWarehouseRecordById(Long InWarehouseRecordId);

	public List<InWarehouseRecord> findInWarehouseRecord(InWarehouseRecord InWarehouseRecord, Map<String, String> moreParam, Pagination page);

	public Long countInWarehouseRecord(InWarehouseRecord InWarehouseRecord, Map<String, String> moreParam);

	public int updateInWarehouseRecordCallback(InWarehouseRecord InWarehouseRecord);

	public int updateInWarehouseRecordStatus(InWarehouseRecord InWarehouseRecord);

	public List<Long> findCallbackUnSuccessRecordId();

	public Long getInWarehouseOrderIdByRecordId(Long InWarehouseRecordId);
}
