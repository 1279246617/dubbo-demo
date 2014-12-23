package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.PackageRecord;
import com.coe.wms.util.Pagination;

public interface IPackageRecordDao {

	public long savePackageRecord(PackageRecord outWarehouseRecord);

	public PackageRecord getPackageRecordById(Long outWarehouseRecordId);

	public List<PackageRecord> findPackageRecord(PackageRecord outWarehouseRecord, Map<String, String> moreParam, Pagination page);

	public Long countPackageRecord(PackageRecord outWarehouseRecord, Map<String, String> moreParam);

	public int updatePackageRecordRemark(Long outWarehouseRecordId, String remark);

	public int updatePackageRecordIsShiped(Long outWarehouseRecordId, String isShiped, Long shippedTime);
}
