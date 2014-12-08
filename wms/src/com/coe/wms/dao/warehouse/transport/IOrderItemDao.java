package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.OrderItem;
import com.coe.wms.util.Pagination;

public interface IOrderItemDao {

	public long saveOrderItem(OrderItem item);

	public int saveBatchOrderItem(List<OrderItem> itemList);

	public int saveBatchOrderItemWithOrderId(List<OrderItem> itemList, Long orderId);

	public List<OrderItem> findOrderItem(OrderItem orderItem, Map<String, String> moreParam, Pagination page);

	public String getSkuNameByCustomerIdAndSku(String sku, Long userIdOfCustomer);

	public Long sumSkuQuantityByOrderIdAndSku(List<Long> orderIds,String sku);
}
