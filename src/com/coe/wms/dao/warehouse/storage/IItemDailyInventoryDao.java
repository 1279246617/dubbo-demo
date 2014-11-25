package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.ItemDailyInventory;
import com.coe.wms.util.Pagination;

public interface IItemDailyInventoryDao {

	public long saveItemDailyInventory(ItemDailyInventory ItemDailyInventory);

	public List<ItemDailyInventory> findItemDailyInventory(ItemDailyInventory ItemDailyInventory, Map<String, String> moreParam, Pagination page);

	public Long countItemDailyInventory(ItemDailyInventory ItemDailyInventory, Map<String, String> moreParam);

	public int addItemDailyInventory(Long wareHouseId, Long userIdOfCustomer, String sku, Integer addQuantity,Integer availableQuantity,String inventoryDate);
}
