package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.FirstWaybill;
import com.coe.wms.util.Pagination;

public interface IFirstWaybillDao {

	public long saveFirstWaybill(FirstWaybill firstWaybill);

	public FirstWaybill getFirstWaybillById(Long firstWaybillId);

	public int deleteFirstWaybillById(Long firstWaybillId);

	public List<FirstWaybill> findFirstWaybill(FirstWaybill firstWaybill, Map<String, String> moreParam, Pagination page);

	public List<String> findFirstWaybillTrackingNos(Long OrderId);

	public Long countFirstWaybill(FirstWaybill firstWaybill, Map<String, String> moreParam);

	public int updateFirstWaybillSeatCode(FirstWaybill firstWaybill);

	public int updateFirstWaybillCallback(FirstWaybill firstWaybill);

	public int updateFirstWaybillStatus(FirstWaybill firstWaybill);

	public int updateFirstWaybillStatus(Long OrderId, String newStatus);

	public int receivedFirstWaybill(FirstWaybill firstWaybill);

	public List<Long> findCallbackUnSuccessFirstWaybillId();
}
