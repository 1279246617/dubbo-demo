package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.OutWarehousePackageItem;
import com.coe.wms.util.Pagination;

public interface IOutWarehousePackageItemDao {

	public long saveOutWarehousePackageItem(OutWarehousePackageItem outWarehouseShipping);

	public OutWarehousePackageItem getOutWarehousePackageItemById(Long outWarehouseShippingId);

	public List<OutWarehousePackageItem> findOutWarehousePackageItem(OutWarehousePackageItem outWarehouseShipping, Map<String, String> moreParam, Pagination page);

	public Long countOutWarehousePackageItem(OutWarehousePackageItem outWarehouseShipping, Map<String, String> moreParam);

	public int deleteOutWarehousePackageItemById(Long id);

	public List<Long> getOutWarehouseOrderIdsByRecordTime(String startTime, String endTime, Long userIdOfCustomer, Long warehouseId);
}
