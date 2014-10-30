package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.OutShelf;
import com.coe.wms.util.Pagination;

public interface IOutShelfDao {

	public long saveOutShelf(OutShelf oShelf);

	public List<OutShelf> findOutShelf(OutShelf onShelf, Map<String, String> moreParam, Pagination page);

	public Long countOutShelf(OutShelf onShelf, Map<String, String> moreParam);

	public Integer countOutShelfSkuQuantity(Long inWarehouseRecordId, String sku);
}
