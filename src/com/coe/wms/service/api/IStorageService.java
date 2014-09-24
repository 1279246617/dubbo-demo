package com.coe.wms.service.api;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.pojo.api.warehouse.LogisticsEventsRequest;
import com.coe.wms.util.Pagination;

/**
 * 仓配 api service层
 * 
 * @author Administrator
 * 
 */
public interface IStorageService {

	static final Logger logger = Logger.getLogger(IStorageService.class);

	/**
	 * 保存入库订单 返回成功,失败,错误信息
	 * 
	 * @param inWarehouseOrder
	 * @param moreParam
	 * @param page
	 * @return
	 */
	public Map<String, String> saveInWarehouseRecord(String trackingNo, String userLoginName, String isUnKnowCustomer,
			String remark);

	/**
	 * 找入库订单
	 * 
	 * @param inWarehouseOrder
	 * @param moreParam
	 * @param page
	 * @return
	 */
	public List<InWarehouseOrder> findInWarehouseOrder(InWarehouseOrder inWarehouseOrder,
			Map<String, String> moreParam, Pagination page);

	/**
	 * 找入库订单中包含的用户信息
	 * 
	 * @param inWarehouseOrder
	 * @param moreParam
	 * @param page
	 * @return
	 */
	public List<User> findUserByInWarehouseOrder(List<InWarehouseOrder> inWarehouseOrderList);

	/**
	 * 获取入库订单物品
	 * 
	 * @param orderId
	 * @param page
	 * @return
	 */
	public Pagination getInWarehouseItemData(Long orderId, Pagination page);

	/**
	 * 
	 * @param logisticsInterface
	 *            消息内容
	 * @param key
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
	public String warehouseInterface(String logisticsInterface, String key, String dataDigest, String msgType,
			String msgId, String version);
}
