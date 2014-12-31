package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.transport.Order;
import com.coe.wms.util.Pagination;

public interface IBigPackageDao {

	public long saveBigPackage(Order bigPackage);

	public Order getBigPackageById(Long bigPackageId);

	public List<Order> findBigPackage(Order bigPackage, Map<String, String> moreParam, Pagination page);

	public Long countBigPackage(Order bigPackage, Map<String, String> moreParam);

	public String getBigPackageStatus(Long bigPackageId);

	public String getCustomerReferenceNoById(Long bigPackageId);

	public int updateBigPackageStatus(Long bigPackageId, String newStatus);

	public int updateBigPackageCheckResult(Long bigPackageId, String checkResult);

	public int updateBigPackageWeight(Order bigPackage);

	public List<Long> findCallbackSendCheckUnSuccessBigPackageId();

	public List<Long> findUnCheckAndTackingNoIsNullBigPackageId();

	public List<Long> findCallbackSendWeightUnSuccessBigPackageId();

	public List<Long> findCallbackSendStatusUnSuccessBigPackageId();

	public int updateBigPackageCallbackSendCheck(Order bigPackage);

	public int updateBigPackageCallbackSendWeight(Order bigPackage);

	public int updateBigPackageCallbackSendStatus(Order bigPackage);

	public int updateBigPackageTrackingNo(Order bigPackage);
}
