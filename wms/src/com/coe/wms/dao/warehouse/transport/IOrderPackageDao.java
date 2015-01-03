package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.OrderPackage;
import com.coe.wms.util.Pagination;

public interface IOrderPackageDao {

	public long saveOrderPackage(OrderPackage orderPackage);

	public OrderPackage getOrderPackageById(Long orderPackageId);

	public List<OrderPackage> findOrderPackage(OrderPackage orderPackage, Map<String, String> moreParam, Pagination page);

	public Long countOrderPackage(OrderPackage orderPackage, Map<String, String> moreParam);

	public String getOrderPackageStatus(Long orderPackageId);

	public String getCustomerReferenceNoById(Long orderPackageId);

	public int updateOrderPackageStatus(Long orderPackageId, String newStatus);

	public List<Long> findCallbackSendStatusUnSuccessOrderPackageId();

	public int updateOrderPackageCallbackSendStatus(OrderPackage orderPackage);
}
