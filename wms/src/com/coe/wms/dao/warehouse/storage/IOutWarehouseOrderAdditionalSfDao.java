package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderAdditionalSf;
import com.coe.wms.util.Pagination;

public interface IOutWarehouseOrderAdditionalSfDao {

	public long saveOutWarehouseOrderAdditionalSf(OutWarehouseOrderAdditionalSf additionalSf);

	public int saveBatchOutWarehouseOrderAdditionalSf(List<OutWarehouseOrderAdditionalSf> additionalSfList);

	public int saveBatchOutWarehouseOrderAdditionalSfWithOrderId(List<OutWarehouseOrderAdditionalSf> additionalSfList, Long orderId);

	public List<OutWarehouseOrderAdditionalSf> findOutWarehouseOrderAdditionalSf(OutWarehouseOrderAdditionalSf outWarehouseOrder,
			Map<String, String> moreParam, Pagination page);

	public OutWarehouseOrderAdditionalSf getOutWarehouseOrderAdditionalSfByOrderId(Long outWarehouseOrderId);
}
