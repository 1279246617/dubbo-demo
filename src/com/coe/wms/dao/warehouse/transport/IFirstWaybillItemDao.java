package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.FirstWaybillItem;
import com.coe.wms.util.Pagination;

public interface IFirstWaybillItemDao {

	public long saveFirstWaybillItem(FirstWaybillItem item);

	public int saveBatchFirstWaybillItem(List<FirstWaybillItem> itemList);

	public int saveBatchFirstWaybillItemWithFirstWaybillId(List<FirstWaybillItem> itemList, Long packageId);

	public List<FirstWaybillItem> findFirstWaybillItem(FirstWaybillItem orderItem, Map<String, String> moreParam, Pagination page);
}
