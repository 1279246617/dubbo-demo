package com.coe.wms.service.print;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.OutWarehouseRecordItem;

public interface IPrintService {

	/**
	 * 获取打印捡货单数据
	 * 
	 * @param outWarehouseOrderId
	 * @return
	 */
	public Map<String, Object> getPrintPackageListData(Long outWarehouseOrderId);

	public Map<String, Object> getPrintShipLabelData(Long outWarehouseOrderId);

	public Map<String, Object> getPrintCoeLabelData(Long coeTrackingNoId);

	/**
	 * 获取打印货位号数据
	 * 
	 * @param seatId
	 * @return
	 */
	public Map<String, Object> getPrintSeatCodeLabelData(Long seatId);

	/**
	 * 打印sku
	 * 
	 * @param sku
	 * @return
	 */
	public Map<String, Object> getPrintSkuBarcodeData(String sku);

	/**
	 * 获取出库记录
	 * 
	 * @return
	 */
	public List<Map<String, String>> getOutWarehouseShippings(Long coeTrackingNoId);

	/**
	 * 获取打印捡货单数据
	 * 
	 * @param outWarehouseOrderId
	 * @return
	 */
	public Map<String, Object> getPrintTransportPackageListData(Long bigPackageId);

	/**
	 * 转运订单出货sf标签
	 * 
	 * @param outWarehouseOrderId
	 * @return
	 */
	public Map<String, Object> printTransportShipLabel(Long bigPackageId);
}
