package com.coe.wms.service.print;

import java.util.Map;

public interface PrintService {

	/**
	 * 获取打印捡货单数据
	 * 
	 * @param outWarehouseOrderId
	 * @return
	 */
	public Map<String, Object> getPrintPackageListData(Long outWarehouseOrderId);
}
