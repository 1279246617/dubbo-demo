package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.OrderSender;
import com.coe.wms.util.Pagination;

public interface IBigPackageSenderDao {

	public long saveBigPackageSender(OrderSender sender);

	public int saveBatchBigPackageSender(List<OrderSender> senderList);

	public int saveBatchBigPackageSenderWithPackageId(List<OrderSender> senderList, Long orderId);

	public List<OrderSender> findBigPackageSender(OrderSender orderSender, Map<String, String> moreParam, Pagination page);

	public OrderSender getBigPackageSenderByPackageId(Long orderId);
}
