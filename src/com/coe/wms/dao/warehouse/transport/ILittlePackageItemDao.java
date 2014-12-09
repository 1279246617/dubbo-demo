package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.LittlePackageItem;
import com.coe.wms.util.Pagination;

public interface ILittlePackageItemDao {

	public long saveLittlePackageItem(LittlePackageItem item);

	public int saveBatchLittlePackageItem(List<LittlePackageItem> itemList);

	public int saveBatchLittlePackageItemWithLittlePackageId(List<LittlePackageItem> itemList, Long packageId);

	public List<LittlePackageItem> findLittlePackageItem(LittlePackageItem orderItem, Map<String, String> moreParam, Pagination page);
}
