package com.coe.wms.service.storage.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.pojo.api.warehouse.ErrorCode;
import com.coe.wms.pojo.api.warehouse.Response;
import com.coe.wms.pojo.api.warehouse.Responses;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
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
	 * @param pagination
	 * @return
	 */
	@Override
	public Pagination getInWarehouseItemData(Long orderId, Pagination pagination) {
		InWarehouseOrderItem param = new InWarehouseOrderItem();
		param.setOrderId(orderId);
		List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(param,
				null, pagination);

		return pagination;
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
			if (inWarehouseOrder.getUserIdOfCustomer() == null) {
				continue;
			}
			boolean bool = true;
			for (User user : userList) {
				if (user.getId() == inWarehouseOrder.getUserIdOfCustomer()) {
					bool = false;
					break;
				}
			}
			if (bool) {
				User user = userDao.getUserById(inWarehouseOrder.getUserIdOfCustomer());
				userList.add(user);
			}
		}
		return userList;
	}

	/**
	 * 
	 */
	@Override
	public String warehouseInterface(String logisticsInterface, Long userIdOfCustomer, String dataDigest,
			String msgType, String msgId, String version) {
		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);
		
		
		return XmlUtil.toXml(Responses.class, responses);
	}

	@Override
	public Map<String, String> warehouseInterfaceValidate(String logisticsInterface, String msgSource,
			String dataDigest, String msgType, String msgId, String version) {
		Map<String, String> map = new HashMap<String, String>();
		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);

		map.put("status", Constant.FAIL);
		// 缺少关键字段
		if (StringUtil.isNull(logisticsInterface) || StringUtil.isNull(msgSource) || StringUtil.isNull(dataDigest)
				|| StringUtil.isNull(msgType) || StringUtil.isNull(msgId)) {
			response.setReason(ErrorCode.S12);
			response.setReasonDesc("缺少关键字段,请检查以下字段:logistics_interface,data_digest,msg_type,msg_id");
			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}

		// 根据msgSource 找到客户(token),找到密钥
		User user = userDao.findUserByMsgSource(msgSource);
		if (user == null) {
			response.setReason(ErrorCode.S03);
			response.setReasonDesc("根据msg_source 找不到客户");
			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}

		// 验证内容和签名字符串
		String md5dataDigest = StringUtil.md5_32(logisticsInterface + user.getToken());
		if (!StringUtil.isEqual(md5dataDigest, dataDigest)) {
			// 签名错误
			response.setReason(ErrorCode.S02);
			response.setReasonDesc("收到消息签名:" + dataDigest + " 系统计算消息签名:" + md5dataDigest);
			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		map.put(Constant.USER_ID_OF_CUSTOMER, "" + user.getId());
		return map;
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
		// 必须输入登录名, 验证用户名必须存在
		if (StringUtil.isNotNull(userLoginName)) {
			Long userId = userDao.findUserIdByLoginName(userLoginName);
			if (userId == null || userId == 0) {
				map.put(Constant.MESSAGE, "请输入正确的客户帐号.");
				return map;
			}
			inWarehouseRecord.setUserIdOfCustomer(userId);
		} else {
			map.put(Constant.MESSAGE, "请输入客户帐号.");
			return map;
		}

		// 检查该跟踪单号,入库主单是否已经存在
		InWarehouseRecord param = new InWarehouseRecord();
		param.setPackageTrackingNo(trackingNo);
		List<InWarehouseRecord> inWarehouseRecordList = inWarehouseRecordDao.findInWarehouseRecord(param, null, null);
		if (inWarehouseRecordList.size() > 0) {
			// 返回入库主单的id
			map.put("id", "" + inWarehouseRecordList.get(0).getId());
			map.put(Constant.MESSAGE, "跟踪单号已存在入库主单.");
			return map;
		}

		if (StringUtil.isEqual(isUnKnowCustomer, Constant.TRUE)) {
			inWarehouseRecord.setIsUnKnowCustomer(Constant.Y);
		} else {
			inWarehouseRecord.setIsUnKnowCustomer(Constant.N);
		}
		inWarehouseRecord.setCreatedTime(System.currentTimeMillis());
		inWarehouseRecord.setPackageTrackingNo(trackingNo);
		inWarehouseRecord.setRemark(remark);
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

	@Override
	public Pagination getInWarehouseRecordItemData(Long inWarehouseRecordId, Pagination pagination) {
		InWarehouseRecordItem inWarehouseRecordItem = new InWarehouseRecordItem();
		inWarehouseRecordItem.setInWareHouseRecordId(inWarehouseRecordId);
		List<InWarehouseRecordItem> inWarehouseRecordItemList = inWarehouseRecordDao.findInWarehouseRecordItem(
				inWarehouseRecordItem, null, pagination);
		List list = new ArrayList();
		for (InWarehouseRecordItem item : inWarehouseRecordItemList) {
			Map map = new HashMap();
			map.put("id", item.getId());
			if (item.getCreatedTime() != null) {
				map.put("createdTime",
						DateUtil.dateConvertString(new Date(item.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			map.put("inWareHouseRecordId", item.getInWareHouseRecordId());
			map.put("quantity", item.getQuantity());
			map.put("remark", item.getRemark());
			map.put("seatId", item.getSeatId());
			map.put("shelvesId", item.getShelvesId());
			map.put("sku", item.getSku());
			map.put("userNameOfOperator", item.getUserIdOfOperator());
			map.put("warehouseId", item.getWarehouseId());
			list.add(map);
		}
		pagination.total = inWarehouseRecordDao.countInWarehouseRecordItem(inWarehouseRecordItem, null);
		pagination.rows = list;
		return pagination;
	}

	@Override
	public Pagination getInWarehouseOrderData(InWarehouseOrder inWarehouseOrder, Map<String, String> moreParam,
			Pagination pagination) {
		List<InWarehouseOrder> inWarehouseOrderList = inWarehouseOrderDao.findInWarehouseOrder(inWarehouseOrder,
				moreParam, pagination);
		List list = new ArrayList();
		for (InWarehouseOrder order : inWarehouseOrderList) {
			Map map = new HashMap();
			map.put("id", order.getId());
			if (order.getCreatedTime() != null) {
				map.put("createdTime",
						DateUtil.dateConvertString(new Date(order.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			map.put("userNameOfOperator", order.getUserIdOfOperator());
			map.put("warehouseId", order.getWarehouseId());
			list.add(map);
		}
		pagination.total = inWarehouseOrderDao.countInWarehouseOrder(inWarehouseOrder, moreParam);
		pagination.rows = list;
		return pagination;
	}
}
