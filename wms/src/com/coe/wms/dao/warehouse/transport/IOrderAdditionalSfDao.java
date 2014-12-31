package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.OrderAdditionalSf;
import com.coe.wms.util.Pagination;

public interface IOrderAdditionalSfDao {

	public long saveOrderAdditionalSf(OrderAdditionalSf additionalSf);

	public int saveBatchOrderAdditionalSf(List<OrderAdditionalSf> additionalSfList);

	public int saveBatchOrderAdditionalSfWithPackageId(List<OrderAdditionalSf> additionalSfList, Long orderId);

	public List<OrderAdditionalSf> findOrderAdditionalSf(OrderAdditionalSf additionalSfList, Map<String, String> moreParam, Pagination page);

	public OrderAdditionalSf getOrderAdditionalSfByOrderId(Long orderId);
}
