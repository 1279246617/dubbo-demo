package com.coe.wms.service.api;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.pojo.api.response.Response;
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
	 * 解析顺丰仓配入库订单请求xml内容 返回 xml内容对应pojo
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 */
	public Response inWarehouse(String xml);

	/**
	 * 解析顺丰仓配出库订单请求xml内容 返回 xml内容对应pojo
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 */
	public Response outWarehouse(String xml);

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
}
