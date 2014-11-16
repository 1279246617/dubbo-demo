package com.coe.wms.service.print;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.storage.record.OutWarehouseShipping;

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
	 * 获取出库记录
	 * 
	 * @return
	 */
	public List<Map<String, String>> getOutWarehouseShippings(Long coeTrackingNoId);
	
}
