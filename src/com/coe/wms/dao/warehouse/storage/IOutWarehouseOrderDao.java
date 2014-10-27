package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.util.Pagination;

public interface IOutWarehouseOrderDao {

	public long saveOutWarehouseOrder(OutWarehouseOrder outWarehouseOrder);

	public OutWarehouseOrder getOutWarehouseOrderById(Long outWarehouseOrderId);

	public List<OutWarehouseOrder> findOutWarehouseOrder(OutWarehouseOrder outWarehouseOrder, Map<String, String> moreParam, Pagination page);

	public Long countOutWarehouseOrder(OutWarehouseOrder outWarehouseOrder, Map<String, String> moreParam);

	public int updateOutWarehouseOrderStatus(Long orderId, String newStatus);

	public String getOutWarehouseOrderStatus(Long orderId);

	public List<Long> findCallbackSendWeightUnSuccessOrderId();

	public List<Long> findCallbackSendStatusUnSuccessOrderId();

	public int updateOutWarehouseOrderCallbackSendWeight(OutWarehouseOrder outWarehouseOrder);

	public int updateOutWarehouseOrderIsPrinted(Long id, String isPrinted);

	public int updateOutWarehouseOrderCallbackSendStatus(OutWarehouseOrder outWarehouseOrder);

	public int updateOutWarehouseOrderWeight(OutWarehouseOrder outWarehouseOrder);
}
