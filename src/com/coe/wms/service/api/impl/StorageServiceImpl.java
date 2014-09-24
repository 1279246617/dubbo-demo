package com.coe.wms.service.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordDao;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.pojo.api.warehouse.Response;
import com.coe.wms.pojo.api.warehouse.ResponseItems;
import com.coe.wms.pojo.api.warehouse.Responses;
import com.coe.wms.service.api.IStorageService;
import com.coe.wms.util.CommonUtil;
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

	@Resource(name = "inWarehouseRecordDao")
	private IInWarehouseRecordDao inWarehouseRecordDao;

	@Resource(name = "userDao")
	private IUserDao userDao;

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

	@Override
	public String warehouseInterface(String logisticsInterface, String key, String dataDigest, String msgType,
			String msgId, String version) {
		Responses responses = new Responses();
		// 验证内容和签名字符串
		String md5dataDigest = StringUtil.md5_32(logisticsInterface + key);
		if (!StringUtil.isEqual(md5dataDigest, dataDigest)) {
			ResponseItems responseItems = new ResponseItems();
			Response response = new Response();

			responseItems.setResponse(response);
			responses.setResponseItems(responseItems);
		}
		return XmlUtil.toXml(Responses.class, responses);
	}

	@Override
	public Map<String, String> saveInWarehouseRecord(String trackingNo, String userLoginName, String isUnKnowCustomer,
			String remark, Long userIdOfOperator) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(trackingNo)) {
			map.put(Constant.MESSAGE, "请输入跟踪单号.");
			return map;
		}
		InWarehouseRecord inWarehouseRecord = new InWarehouseRecord();
		inWarehouseRecord.setCreatedTime(System.currentTimeMillis());
		inWarehouseRecord.setPackageTrackingNo(trackingNo);
		inWarehouseRecord.setRemark(remark);
		// 可不输入登录名, 若输入则验证用户名必须存在
		if (StringUtil.isNotNull(userLoginName)) {
			Long userId = userDao.findUserIdByLoginName(userLoginName);
			if (userId == null || userId == 0) {
				map.put(Constant.MESSAGE, "请输入正确的客户帐号.");
				return map;
			}
			inWarehouseRecord.setUserIdOfCustomer(userId);
		}
		inWarehouseRecord.setUserIdOfOperator(userIdOfOperator);
		// 创建批次号
		String batchNo = InWarehouseRecord.generateBatchNo(null, null, Constant.SYMBOL_UNDERLINE, trackingNo, null,
				null, isUnKnowCustomer);
		inWarehouseRecord.setBatchNo(batchNo);

		// 返回id
		long id = inWarehouseRecordDao.saveInWarehouseRecord(inWarehouseRecord);
		map.put("id", "" + id);
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}
}
