package com.coe.wms.service.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderItemDao;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.pojo.api.response.Response;
import com.coe.wms.pojo.api.warehouse.InOrder;
import com.coe.wms.pojo.api.warehouse.InOrderItem;
import com.coe.wms.pojo.api.warehouse.OutOrder;
import com.coe.wms.pojo.api.warehouse.OutOrderItem;
import com.coe.wms.service.api.IStorageService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.coe.wms.util.XmlUtil;

/**
 * 仓配服务
 * 
 * @author Administrator
 * 
 */
@Service("storageService")
public class StorageServiceImpl implements IStorageService {

	private static final Logger logger = Logger.getLogger(StorageServiceImpl.class);

	@Resource(name = "inWarehouseOrderDao")
	private IInWarehouseOrderDao inWarehouseOrderDao;

	@Resource(name = "inWarehouseOrderItemDao")
	private IInWarehouseOrderItemDao inWarehouseOrderItemDao;

	@Resource(name = "userDao")
	private IUserDao userDao;

	/**
	 * 入库预报
	 */
	@Override
	public Response inWarehouse(String xml) {
		Response response = new Response();
		response.setSucceeded(Constant.FALSE);
		if (StringUtil.isNull(xml)) {
			response.setDescription(Response.DESCRIPTION_EMPTY);
			return response;
		}
		Long userId = 1l;
		InOrder order = (InOrder) XmlUtil.toObject(xml, OutOrder.class);
		List<InOrderItem> itemList = order.getItemList();
		// pojo 转换 model 保存入数据库
		InWarehouseOrder pag = order.changeToInWarehouseOrder();
		// 大包id
		long packageId = inWarehouseOrderDao.saveInWarehouseOrder(pag);
		// 商品明细
		List<InWarehouseOrderItem> packageItemList = new ArrayList<InWarehouseOrderItem>();
		for (InOrderItem inOrderItem : itemList) {
			InWarehouseOrderItem packageItem = inOrderItem.changeToInWarehouseOrderItem(packageId);
			packageItemList.add(packageItem);
		}
		int changeSize = inWarehouseOrderItemDao.saveBatchInWarehouseOrderItem(packageItemList);

		logger.info("入库大包号:" + pag.getPackageNo() + " 入库SKU数量:" + changeSize);
		response.setSucceeded(Constant.SUCCESS);
		return response;
	}

	/**
	 * 出库
	 */
	@Override
	public Response outWarehouse(String xml) {
		Response response = new Response();
		response.setSucceeded(Constant.FALSE);
		if (StringUtil.isNull(xml)) {
			response.setDescription(Response.DESCRIPTION_EMPTY);
			return response;
		}
		OutOrder order = (OutOrder) XmlUtil.toObject(xml, OutOrder.class);
		List<OutOrderItem> itemList = order.getItemList();

		return response;
	}

	/**
	 * 根据入库订单id, 查找入库物品明细
	 * 
	 * @param orderId
	 * @param page
	 * @return
	 */
	@Override
	public Pagination getInWarehouseItemData(Long orderId, Pagination page) {
		InWarehouseOrder inWarehouseOrder = inWarehouseOrderDao.getInWarehouseOrderById(orderId);
		InWarehouseOrderItem param = new InWarehouseOrderItem();
		List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(param,
				null, page);
		return page;
	}

	/**
	 * 查找入库订单
	 */
	@Override
	public List<InWarehouseOrder> findInWarehouseOrder(InWarehouseOrder inWarehouseOrder,
			Map<String, String> moreParam, Pagination page) {
		List<InWarehouseOrder> inWarehouseOrderList = inWarehouseOrderDao.findInWarehouseOrder(inWarehouseOrder,
				moreParam, page);
		return inWarehouseOrderList;
	}

	/***
	 * 根据入库订单 找用户id
	 */
	@Override
	public List<User> findUserByInWarehouseOrder(List<InWarehouseOrder> inWarehouseOrderList) {
		List<User> userList = new ArrayList<User>();
		for (InWarehouseOrder inWarehouseOrder : inWarehouseOrderList) {
			if (inWarehouseOrder.getUserId() == null) {
				continue;
			}
			boolean bool = true;
			for (User user : userList) {
				if (user.getId() == inWarehouseOrder.getUserId()) {
					bool = false;
					break;
				}
			}
			if (bool) {
				User user = userDao.getUserById(inWarehouseOrder.getUserId());
				userList.add(user);
			}
		}
		return userList;
	}
}
