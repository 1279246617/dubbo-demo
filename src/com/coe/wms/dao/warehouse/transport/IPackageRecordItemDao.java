package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.PackageRecordItem;
import com.coe.wms.util.Pagination;

public interface IPackageRecordItemDao {

	public long savePackageRecordItem(PackageRecordItem outWarehouseShipping);

	public PackageRecordItem getPackageRecordItemById(Long outWarehouseShippingId);

	public List<PackageRecordItem> findPackageRecordItem(PackageRecordItem outWarehouseShipping, Map<String, String> moreParam, Pagination page);

	public Long countPackageRecordItem(PackageRecordItem outWarehouseShipping, Map<String, String> moreParam);

	public int deletePackageRecordItemById(Long id);

	public List<Long> getBigPackageIdsByRecordTime(String startTime, String endTime, Long userIdOfCustomer, Long warehouseId);
}
