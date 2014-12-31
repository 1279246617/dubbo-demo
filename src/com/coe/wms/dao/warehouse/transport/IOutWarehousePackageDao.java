package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.OutWarehousePackage;
import com.coe.wms.util.Pagination;

public interface IOutWarehousePackageDao {

	public long saveOutWarehousePackage(OutWarehousePackage outWarehouseRecord);

	public OutWarehousePackage getOutWarehousePackageById(Long outWarehouseRecordId);

	public List<OutWarehousePackage> findOutWarehousePackage(OutWarehousePackage outWarehouseRecord, Map<String, String> moreParam, Pagination page);

	public Long countOutWarehousePackage(OutWarehousePackage outWarehouseRecord, Map<String, String> moreParam);

	public int updateOutWarehousePackageRemark(Long outWarehouseRecordId, String remark);

	public int updateOutWarehousePackageIsShiped(Long outWarehouseRecordId, String isShiped, Long shippedTime);
}
