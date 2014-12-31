package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.OrderAdditionalSf;
import com.coe.wms.util.Pagination;

public interface IBigPackageAdditionalSfDao {

	public long saveBigPackageAdditionalSf(OrderAdditionalSf additionalSf);

	public int saveBatchBigPackageAdditionalSf(List<OrderAdditionalSf> additionalSfList);

	public int saveBatchBigPackageAdditionalSfWithPackageId(List<OrderAdditionalSf> additionalSfList, Long packageId);

	public List<OrderAdditionalSf> findBigPackageAdditionalSf(OrderAdditionalSf additionalSfList, Map<String, String> moreParam, Pagination page);

	public OrderAdditionalSf getBigPackageAdditionalSfByPackageId(Long packageId);
}
