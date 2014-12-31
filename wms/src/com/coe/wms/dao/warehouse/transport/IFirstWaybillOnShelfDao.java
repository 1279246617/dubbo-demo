package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.FirstWaybillOnShelf;
import com.coe.wms.util.Pagination;

public interface IFirstWaybillOnShelfDao {

	public long saveFirstWaybillOnShelf(FirstWaybillOnShelf FirstWaybillOnShelf);

	public List<FirstWaybillOnShelf> findFirstWaybillOnShelf(FirstWaybillOnShelf FirstWaybillOnShelf, Map<String, String> moreParam, Pagination page);

	public Long countFirstWaybillOnShelf(FirstWaybillOnShelf FirstWaybillOnShelf, Map<String, String> moreParam);

	public int updateFirstWaybillOnShelf(FirstWaybillOnShelf FirstWaybillOnShelf);

	public int updateFirstWaybillOnShelf(Long OrderId, String newStatus);

	public String findSeatCodeForOnShelf(String businessType);

	public String findStatusByFirstWaybillId(Long FirstWaybillId);

	public FirstWaybillOnShelf findFirstWaybillOnShelfByFirstWaybillId(Long FirstWaybillId);
}
