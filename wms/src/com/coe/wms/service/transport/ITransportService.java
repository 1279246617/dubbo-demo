package com.coe.wms.service.transport;

import org.apache.log4j.Logger;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.pojo.api.warehouse.EventBody;

/**
 * 转运 api service层
 * 
 * @author Administrator
 * 
 */
public interface ITransportService {

	static final Logger logger = Logger.getLogger(ITransportService.class);

	/**
	 * 创建转运订单
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public String warehouseInterfaceSaveTransportOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException;
	
	
	
}
