package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.OutWarehousePackage;
import com.coe.wms.util.Pagination;

public interface IOutWarehousePackageDao {

	public long saveOutWarehousePackage(OutWarehousePackage outWarehousePackage);

	public OutWarehousePackage getOutWarehousePackageById(Long outWarehousePackageId);

	public OutWarehousePackage getOutWarehousePackageByCoeTrackingNoId(Long coeTrackingNoId);

	public List<OutWarehousePackage> findOutWarehousePackage(OutWarehousePackage outWarehousePackage, Map<String, String> moreParam, Pagination page);

	public Long countOutWarehousePackage(OutWarehousePackage outWarehousePackage, Map<String, String> moreParam);

	public int updateOutWarehousePackageRemark(Long outWarehousePackageId, String remark);

	public int updateOutWarehousePackageIsShiped(Long outWarehouseRecordId, String isShiped, Long shippedTime);
}
