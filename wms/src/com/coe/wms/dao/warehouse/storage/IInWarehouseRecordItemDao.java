package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.util.Pagination;

public interface IInWarehouseRecordItemDao {

	public long saveInWarehouseRecordItem(InWarehouseRecordItem item);

	public int saveBatchInWarehouseRecordItem(List<InWarehouseRecordItem> itemList);

	public int saveBatchInWarehouseRecordItemWithRecordId(List<InWarehouseRecordItem> itemList, Long orderId);

	public List<InWarehouseRecordItem> findInWarehouseRecordItem(InWarehouseRecordItem inWarehouseRecord, Map<String, String> moreParam, Pagination page);

	public Long countInWarehouseRecordItem(InWarehouseRecordItem inWarehouseRecordItem, Map<String, String> moreParam);

	public int countInWarehouseItemSkuQuantityByRecordId(Long inWarehouseRecordId, String sku);

	public Long countItemSkuQuantity(String createdTimeStart, String createdTimeEnd, String sku, Long userIdOfCustomer, Long warehouseId);

	public int countInWarehouseItemSkuQuantityByOrderId(Long inWarehouseOrderId, String sku);

	public int updateInWarehouseRecordItemReceivedQuantity(Long recordItemId, int newQuantity);

	public Long countInWarehouseRecordItemList(Map<String, String> moreParam);

	public List<Map<String, Object>> getInWarehouseRecordItemListData(Map<String, String> moreParam, Pagination pagination);
}
