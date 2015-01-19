package com.coe.wms.service.transport;

import java.util.Map;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.pojo.api.warehouse.EventBody;

/**
 * 
 * @author Administrator
 * 
 */
public interface ITransportInterfaceService2 {
	/**
	 * 校验
	 * 
	 * @param content
	 * @param token
	 * @param sign
	 * @return
	 */
	public Map<String, String> warehouseInterfaceValidate(String content, String token, String sign);

	/**
	 * 创建转运订单
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public String warehouseInterfaceSaveTransportOrder(EventBody eventBody, Long userIdOfCustomer) throws ServiceException;

	/**
	 * 创建转运订单大包
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public String warehouseInterfaceSaveTransportOrderPackage(EventBody eventBody, Long userIdOfCustomer) throws ServiceException;

	/**
	 * 获取事件类型
	 * 
	 * @return
	 */
	public Map<String, Object> warehouseInterfaceEventType(String content);
}
