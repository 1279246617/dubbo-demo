package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.BigPackageAdditionalSf;
import com.coe.wms.util.Pagination;

public interface IBigPackageAdditionalSfDao {

	public long saveBigPackageAdditionalSf(BigPackageAdditionalSf additionalSf);

	public int saveBatchBigPackageAdditionalSf(List<BigPackageAdditionalSf> additionalSfList);

	public int saveBatchBigPackageAdditionalSfWithPackageId(List<BigPackageAdditionalSf> additionalSfList, Long packageId);

	public List<BigPackageAdditionalSf> findBigPackageAdditionalSf(BigPackageAdditionalSf additionalSfList, Map<String, String> moreParam, Pagination page);

	public BigPackageAdditionalSf getBigPackageAdditionalSfByPackageId(Long packageId);
}
