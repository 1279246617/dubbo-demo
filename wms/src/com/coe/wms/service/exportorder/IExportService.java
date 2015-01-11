package com.coe.wms.service.exportorder;

import com.coe.wms.exception.ServiceException;

/**
 * 
 * @author Administrator
 * 
 */
public interface IExportService {

	public String executeExportOutWarehouseOrder(Long warehouseId, String status, String userLoginName, String createdTimeStart, String createdTimeEnd, String isQuantityOnly1) throws ServiceException;

}
