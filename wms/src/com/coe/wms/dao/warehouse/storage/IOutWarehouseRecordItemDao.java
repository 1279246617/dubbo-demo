package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.OutWarehouseRecordItem;
import com.coe.wms.util.Pagination;

public interface IOutWarehouseRecordItemDao {

	public long saveOutWarehouseRecordItem(OutWarehouseRecordItem outWarehouseShipping);

	public OutWarehouseRecordItem getOutWarehouseRecordItemById(Long outWarehouseShippingId);

	public List<OutWarehouseRecordItem> findOutWarehouseRecordItem(OutWarehouseRecordItem outWarehouseShipping, Map<String, String> moreParam, Pagination page);

	public Long countOutWarehouseRecordItem(OutWarehouseRecordItem outWarehouseShipping, Map<String, String> moreParam);

	public int deleteOutWarehouseRecordItemById(Long id);

	public List<Long> getOutWarehouseOrderIdsByRecordTime(String startTime, String endTime, Long userIdOfCustomer, Long warehouseId);
}
