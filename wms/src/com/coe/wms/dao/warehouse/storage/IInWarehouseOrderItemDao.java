package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.util.Pagination;

public interface IInWarehouseOrderItemDao {

	public long saveInWarehouseOrderItem(InWarehouseOrderItem item);

	public int saveBatchInWarehouseOrderItem(List<InWarehouseOrderItem> itemList);

	public long saveInWarehouseOrderItemSku(Long id, String sku);

	public int saveBatchInWarehouseOrderItemWithOrderId(List<InWarehouseOrderItem> itemList, Long orderId);

	public List<InWarehouseOrderItem> findInWarehouseOrderItem(InWarehouseOrderItem inWarehouseOrder, Map<String, String> moreParam, Pagination page);

	public Long countInWarehouseOrderItem(InWarehouseOrderItem inWarehouseOrderItem, Map<String, String> moreParam);

	public String getSkuNameByCustomerIdAndSku(String sku, Long userIdOfCustomer);

}
