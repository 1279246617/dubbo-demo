package com.coe.wms.service.storage;

import java.util.Map;

import org.apache.log4j.Logger;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.pojo.api.warehouse.EventBody;

/**
 * 
 * @author Administrator
 * 
 */
public interface IWarehouseInterfaceService {

	/**
	 * 
	 * 
	 * @param logisticsInterface
	 *            消息内容
	 * @param token
	 *            签名(签名参数名 顺丰未指定)
	 * @param dataDigest
	 *            消息签名
	 * @param msgType
	 *            消息类型,根据消息类型判断业务类型
	 * @param msgId
	 *            消息ID
	 * @param version
	 * @return
	 */
	public Map<String, Object> warehouseInterfaceEventType(String logisticsInterface, Long userIdOfCustomer, String dataDigest, String msgType, String msgId, String version);

	/**
	 * 接口验证
	 * 
	 * @param logisticsInterface
	 * @param token
	 * @param dataDigest
	 * @param msgType
	 * @param msgId
	 * @param version
	 * @return
	 */
	public Map<String, String> warehouseInterfaceValidate(String logisticsInterface, String token, String dataDigest, String msgType, String msgId, String version);

	/**
	 * 创建入库订单
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public String warehouseInterfaceSaveInWarehouseOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException;

	/**
	 * 创建出库订单
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public String warehouseInterfaceSaveOutWarehouseOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException;

	/**
	 * 顺丰确认出库订单
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public String warehouseInterfaceConfirmOutWarehouseOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException;

	/**
	 * 顺丰取消出库订单
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public String warehouseInterfaceCancelOutWarehouseOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException;

}
