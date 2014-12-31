package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.OutWarehousePackageItem;
import com.coe.wms.util.Pagination;

public interface IPackageRecordItemDao {

	public long savePackageRecordItem(OutWarehousePackageItem outWarehouseShipping);

	public OutWarehousePackageItem getPackageRecordItemById(Long outWarehouseShippingId);

	public List<OutWarehousePackageItem> findPackageRecordItem(OutWarehousePackageItem outWarehouseShipping, Map<String, String> moreParam, Pagination page);

	public Long countPackageRecordItem(OutWarehousePackageItem outWarehouseShipping, Map<String, String> moreParam);

	public int deletePackageRecordItemById(Long id);

	public List<Long> getOrderIdsByRecordTime(String startTime, String endTime, Long userIdOfCustomer, Long warehouseId);
}
