package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.BigPackage;
import com.coe.wms.util.Pagination;

public interface IBigPackageDao {

	public long saveBigPackage(BigPackage bigPackage);

	public BigPackage getBigPackageById(Long bigPackageId);

	public List<BigPackage> findBigPackage(BigPackage bigPackage, Map<String, String> moreParam, Pagination page);

	public Long countBigPackage(BigPackage bigPackage, Map<String, String> moreParam);

	public int updateBigPackageStatus(Long bigPackageId, String newStatus);

	public String getBigPackageStatus(Long bigPackageId);

	public List<Long> findCallbackSendWeightUnSuccessBigPackageId();

	public List<Long> findCallbackSendStatusUnSuccessBigPackageId();

	public int updateBigPackageCallbackSendWeight(BigPackage bigPackage);

	public int updateBigPackageCallbackSendStatus(BigPackage bigPackage);

	public int updateBigPackageWeight(BigPackage bigPackage);
}
