package com.coe.wms.dao.warehouse.storage;

import java.util.List;

import com.coe.wms.model.warehouse.storage.record.ItemInventory;
import com.coe.wms.util.Pagination;

public interface IItemInventoryDao {

	public long saveItemInventory(ItemInventory itemInventory);

	public int saveBatchItemInventory(List<ItemInventory> itemInventoryList);

	/**
	 * 适合于 按批次,SKU,用户,仓库等条件查询可用库存和实际库存
	 * 
	 * @param itemInventory
	 * @param page
	 * @return
	 */
	public List<ItemInventory> findItemInventory(ItemInventory itemInventory, Pagination page);

	public int updateItemInventory(ItemInventory itemInventory);
}
