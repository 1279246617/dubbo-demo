package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.OutWarehouseRecord;
import com.coe.wms.util.Pagination;

public interface IOutWarehouseRecordDao {

	public long saveOutWarehouseRecord(OutWarehouseRecord outWarehouseRecord);

	public OutWarehouseRecord getOutWarehouseRecordById(Long outWarehouseRecordId);

	public List<OutWarehouseRecord> findOutWarehouseRecord(OutWarehouseRecord outWarehouseRecord, Map<String, String> moreParam, Pagination page);

	public Long countOutWarehouseRecord(OutWarehouseRecord outWarehouseRecord, Map<String, String> moreParam);
	
	public int updateOutWarehouseRecordRemark(Long outWarehouseRecordId, String remark);
}
