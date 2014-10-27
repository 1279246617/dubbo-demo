package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.OnShelf;
import com.coe.wms.util.Pagination;

public interface IOnShelfDao {

	public long saveOnShelf(OnShelf oShelf);

	public List<OnShelf> findOnShelf(OnShelf onShelf, Map<String, String> moreParam, Pagination page);

	public List<OnShelf> findOnShelfForOutShelf(String sku, Long warehouseId, Long useIdOfCustomer);

	public Long countOnShelf(OnShelf onShelf, Map<String, String> moreParam);

	public Integer countOnShelfSkuQuantity(Long inWarehouseRecordId, String sku);
	
	public Integer updateOnShelfOutQuantityAndStatus(Long id, int outQuantity, String status);
	
	public Integer updateOnShelfPreOutQuantity(Long id, int preOutQuantity);
}
