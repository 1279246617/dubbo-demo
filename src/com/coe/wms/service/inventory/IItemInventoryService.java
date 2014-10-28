package com.coe.wms.service.inventory;

import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.ItemInventory;
import com.coe.wms.model.warehouse.storage.record.ItemShelfInventory;
import com.coe.wms.util.Pagination;

/**
 * 
 * @author Administrator
 * 
 */
public interface IItemInventoryService {

	/**
	 * 
	 * @param itemInventory
	 * @param moreParam
	 * @param page
	 * @return
	 */
	public Pagination getListInventoryData(ItemInventory itemInventory, Map<String, String> moreParam, Pagination page);

	public Pagination getListItemShelfInventoryData(ItemShelfInventory itemShelfInventory, Map<String, String> moreParam, Pagination page);
}
