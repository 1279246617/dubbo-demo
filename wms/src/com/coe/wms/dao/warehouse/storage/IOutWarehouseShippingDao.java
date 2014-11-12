package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.OutWarehouseShipping;
import com.coe.wms.util.Pagination;

public interface IOutWarehouseShippingDao {

	public long saveOutWarehouseShipping(OutWarehouseShipping outWarehouseShipping);

	public OutWarehouseShipping getOutWarehouseShippingById(Long outWarehouseShippingId);

	public List<OutWarehouseShipping> findOutWarehouseShipping(OutWarehouseShipping outWarehouseShipping, Map<String, String> moreParam, Pagination page);

	public Long countOutWarehouseShipping(OutWarehouseShipping outWarehouseShipping, Map<String, String> moreParam);

	public int deleteOutWarehouseShippingById(Long id);
}
