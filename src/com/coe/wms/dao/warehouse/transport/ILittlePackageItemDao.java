package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.FirstWaybillItem;
import com.coe.wms.util.Pagination;

public interface ILittlePackageItemDao {

	public long saveLittlePackageItem(FirstWaybillItem item);

	public int saveBatchLittlePackageItem(List<FirstWaybillItem> itemList);

	public int saveBatchLittlePackageItemWithLittlePackageId(List<FirstWaybillItem> itemList, Long packageId);

	public List<FirstWaybillItem> findLittlePackageItem(FirstWaybillItem orderItem, Map<String, String> moreParam, Pagination page);
}
