package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.util.Pagination;

public interface IInWarehouseOrderDao {

	public long saveInWarehouseOrder(InWarehouseOrder inWarehouseOrder);

	public InWarehouseOrder getInWarehouseOrderById(Long inWarehouseOrderId);

	public List<InWarehouseOrder> findInWarehouseOrder(InWarehouseOrder inWarehouseOrder, Map<String, String> moreParam, Pagination page);

	public Long getUserIdByInWarehouseOrderId(Long inWarehouseOrderId);

	public Long countInWarehouseOrder(InWarehouseOrder inWarehouseOrder, Map<String, String> moreParam);

	public Long countInWarehouseOrderItemByTrackingNo(String trackingNo);

	public Long countInWarehouseOrderItemByInWarehouseOrderId(Long inWarehouseOrderId);

	public List<Long> findUnCompleteInWarehouseOrderId();

	public int updateInWarehouseOrderStatus(Long inWarehouseOrderId, String status);

	public int updateInWarehouseOrderRemark(Long inWarehouseOrderId, String remark);
}
