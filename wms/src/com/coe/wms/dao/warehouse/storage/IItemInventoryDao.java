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

	/**
	 * 添加库存 所有参数都是必须.  wareHouseId,userIdOfCustomer,batchNo,sku 4个参数作为查询条件
	 * 如果能查到库存ItemInventory,则 库存数量和可用库存 + addQuantity, 否则新建ItemInventory, 初始库存和可用库存为addQuantity
	 * @param wareHouseId
	 * @param userIdOfCustomer
	 * @param batchNo
	 * @param sku
	 * @param addQuantity
	 * @return
	 */
	public int addItemInventory(Long wareHouseId, Long userIdOfCustomer, String batchNo, String sku, Integer addQuantity);
}
