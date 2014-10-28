package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.ItemShelfInventory;
import com.coe.wms.util.Pagination;

public interface IItemShelfInventoryDao {

	public long saveItemShelfInventory(ItemShelfInventory itemShelfInventory);

	public int saveBatchItemShelfInventory(List<ItemShelfInventory> itemShelfInventoryList);

	/**
	 * 适合于 按批次,SKU,用户,仓库等条件查询可用库存和实际库存
	 * 
	 * @param itemShelfInventory
	 * @param page
	 * @return
	 */
	public List<ItemShelfInventory> findItemShelfInventory(ItemShelfInventory itemShelfInventory, Map<String, String> moreParam,
			Pagination page);

	public Long countItemShelfInventory(ItemShelfInventory itemShelfInventory, Map<String, String> moreParam);

	/**
	 * 添加库存 所有参数都是必须. wareHouseId,userIdOfCustomer,batchNo,sku 4个参数作为查询条件
	 * 如果能查到库存ItemShelfInventory,则 库存数量和可用库存 + addQuantity,
	 * 否则新建ItemShelfInventory, 初始库存和可用库存为addQuantity
	 * 
	 * @param wareHouseId
	 * @param userIdOfCustomer
	 * @param batchNo
	 * @param sku
	 * @param addQuantity
	 * @return
	 */
	public int addItemShelfInventory(Long wareHouseId, Long userIdOfCustomer, String seatCode, String sku, Integer addQuantity);
}
