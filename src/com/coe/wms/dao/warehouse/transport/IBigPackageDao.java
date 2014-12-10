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

	public String getBigPackageStatus(Long bigPackageId);

	public int updateBigPackageStatus(Long bigPackageId, String newStatus);

	public int updateBigPackageCheckResult(Long bigPackageId, String checkResult);

	public int updateBigPackageWeight(BigPackage bigPackage);

	public List<Long> findCallbackSendCheckUnSuccessBigPackageId();

	public List<Long> findCallbackSendWeightUnSuccessBigPackageId();

	public List<Long> findCallbackSendStatusUnSuccessBigPackageId();

	public int updateBigPackageCallbackSendCheck(BigPackage bigPackage);

	public int updateBigPackageCallbackSendWeight(BigPackage bigPackage);

	public int updateBigPackageCallbackSendStatus(BigPackage bigPackage);

}
