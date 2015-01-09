package com.coe.wms.service.transport;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.pojo.api.warehouse.EventBody;

/**
 * 
 * @author Administrator
 * 
 */
public interface ITransportInterfaceService {

	/**
	 * 创建转运订单
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public String warehouseInterfaceSaveTransportOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException;

	/**
	 * 创建转运订单大包
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public String warehouseInterfaceSaveTransportOrderPackage(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException;

	/**
	 * 区分顺丰下单是 下大包还是小包(转运订单)
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public Integer warehouseInterfaceDistinguishOrderOrPackage(EventBody eventBody) throws ServiceException;

	/**
	 * 转运订单确认出库
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public String warehouseInterfaceConfirmTransportOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException;

}
