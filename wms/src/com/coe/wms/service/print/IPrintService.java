package com.coe.wms.service.print;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.OutWarehousePackageItem;

public interface IPrintService {

	/**
	 * 获取打印捡货单数据
	 * 
	 * @param outWarehouseOrderId
	 * @return
	 */
	public Map<String, Object> getPrintPackageListData(Long outWarehouseOrderId);

	public Map<String, Object> getPrintShipLabelData(Long outWarehouseOrderId, Double height, Double barcodeScale);

	public Map<String, Object> getPrintCoeLabelData(Long coeTrackingNoId);

	public Map<String, Object> getPrintTransportCoeLabelData(Long coeTrackingNoId);

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
	 * 获取出库记录
	 * 
	 * @return
	 */
	public List<Map<String, String>> getPrintTransportEIRData(Long coeTrackingNoId);

	/**
	 * 获取打印捡货单数据
	 * 
	 * @param outWarehouseOrderId
	 * @return
	 */
	public Map<String, Object> getPrintTransportPackageListData(Long orderId);

	/**
	 * 转运订单出货sf标签
	 * 
	 * @param outWarehouseOrderId
	 * @return
	 */
	public Map<String, Object> getPrintTransportShipLabedData(Long orderId);
}
