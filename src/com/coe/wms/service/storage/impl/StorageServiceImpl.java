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
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderReceiverDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderSenderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderStatusDao;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.unit.Weight;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderStatus;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderStatus.InWarehouseOrderStatusCode;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderSender;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus.OurWareHouseStatusCode;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.pojo.api.warehouse.ErrorCode;
import com.coe.wms.pojo.api.warehouse.EventBody;
import com.coe.wms.pojo.api.warehouse.EventHeader;
import com.coe.wms.pojo.api.warehouse.LogisticsDetail;
import com.coe.wms.pojo.api.warehouse.LogisticsEvent;
import com.coe.wms.pojo.api.warehouse.LogisticsEventsRequest;
import com.coe.wms.pojo.api.warehouse.LogisticsOrder;
import com.coe.wms.pojo.api.warehouse.ReceiverDetail;
import com.coe.wms.pojo.api.warehouse.Response;
import com.coe.wms.pojo.api.warehouse.Responses;
import com.coe.wms.pojo.api.warehouse.SenderDetail;
import com.coe.wms.pojo.api.warehouse.Sku;
import com.coe.wms.pojo.api.warehouse.SkuDetail;
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

	@Resource(name = "warehouseDao")
	private IWarehouseDao warehouseDao;

	@Resource(name = "inWarehouseOrderDao")
	private IInWarehouseOrderDao inWarehouseOrderDao;

	@Resource(name = "inWarehouseOrderStatusDao")
	private IInWarehouseOrderStatusDao inWarehouseOrderStatusDao;

	@Resource(name = "inWarehouseOrderItemDao")
	private IInWarehouseOrderItemDao inWarehouseOrderItemDao;

	@Resource(name = "inWarehouseRecordDao")
	private IInWarehouseRecordDao inWarehouseRecordDao;

	@Resource(name = "outWarehouseOrderDao")
	private IOutWarehouseOrderDao outWarehouseOrderDao;

	@Resource(name = "outWarehouseOrderStatusDao")
	private IOutWarehouseOrderStatusDao outWarehouseOrderStatusDao;

	@Resource(name = "outWarehouseOrderItemDao")
	private IOutWarehouseOrderItemDao outWarehouseOrderItemDao;

	@Resource(name = "outWarehouseOrderSenderDao")
	private IOutWarehouseOrderSenderDao outWarehouseOrderSenderDao;

	@Resource(name = "outWarehouseOrderReceiverDao")
	private IOutWarehouseOrderReceiverDao outWarehouseOrderReceiverDao;

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
		List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(param, null, pagination);

		return pagination;
	}

	/**
	 * 查找入库订单
	 */
	@Override
	public List<InWarehouseOrder> findInWarehouseOrder(InWarehouseOrder inWarehouseOrder, Map<String, String> moreParam, Pagination page) {
		List<InWarehouseOrder> inWarehouseOrderList = inWarehouseOrderDao.findInWarehouseOrder(inWarehouseOrder, moreParam, page);
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
	public Map<String, Object> warehouseInterfaceEventType(String logisticsInterface, Long userIdOfCustomer, String dataDigest,
			String msgType, String msgId, String version) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.STATUS, Constant.FAIL);

		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);

		// logisticsInterface 转请求对象
		LogisticsEventsRequest logisticsEventsRequest = (LogisticsEventsRequest) XmlUtil.toObject(logisticsInterface,
				LogisticsEventsRequest.class);
		if (logisticsEventsRequest == null) {
			// xml 转对象得到空
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("logisticsInterface消息内容转LogisticsEventsRequest对象的得到Null");

			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}
		LogisticsEvent logisticsEvent = logisticsEventsRequest.getLogisticsEvent();
		if (logisticsEvent == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsEventsRequest对象获取LogisticsEvent对象的得到Null");

			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}
		// 事件头
		EventHeader eventHeader = logisticsEvent.getEventHeader();
		if (eventHeader == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsEvent对象获取EventHeader对象的得到Null");

			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}
		// 得到事件类型,根据事件类型,分发事件Body 到不同方法处理
		String eventType = eventHeader.getEventType();
		if (StringUtil.isNull(eventType)) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventHeader对象获取eventType的得到Null");
			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}
		// 事件Body
		EventBody eventBody = logisticsEvent.getEventBody();
		if (eventBody == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsEvent对象获取EventBody对象的得到Null");
			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}
		// 成功得到事件类型,返回body
		map.put(Constant.STATUS, Constant.SUCCESS);
		map.put("eventType", eventType);
		map.put("eventBody", eventBody);
		return map;
	}

	@Override
	public Map<String, String> warehouseInterfaceValidate(String logisticsInterface, String msgSource, String dataDigest, String msgType,
			String msgId, String version) {
		Map<String, String> map = new HashMap<String, String>();
		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);

		map.put(Constant.STATUS, Constant.FAIL);
		// 缺少关键字段
		// if (StringUtil.isNull(logisticsInterface) ||
		// StringUtil.isNull(msgSource) || StringUtil.isNull(dataDigest)
		// || StringUtil.isNull(msgType) || StringUtil.isNull(msgId)) {
		// response.setReason(ErrorCode.S12_CODE);
		// response.setReasonDesc("缺少关键字段,请检查以下字段:logistics_interface,data_digest,msg_type,msg_id");
		// map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
		// return map;
		// }

		// 根据msgSource 找到客户(token),找到密钥
		User user = userDao.findUserByMsgSource(msgSource);
		if (user == null) {
			response.setReason(ErrorCode.S03_CODE);
			response.setReasonDesc("根据msg_source 找不到客户");
			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}

		// 验证内容和签名字符串
		String md5dataDigest = StringUtil.encoderByMd5(logisticsInterface + user.getToken());
		// if (!StringUtil.isEqual(md5dataDigest, dataDigest)) {
		// // 签名错误
		// response.setReason(ErrorCode.S02_CODE);
		// response.setReasonDesc("收到消息签名:" + dataDigest + " 系统计算消息签名:" +
		// md5dataDigest);
		// map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
		// return map;
		// }
		map.put(Constant.STATUS, Constant.SUCCESS);
		map.put(Constant.USER_ID_OF_CUSTOMER, "" + user.getId());
		return map;
	}

	/**
	 * 保存入库记录主单
	 */
	@Override
	public Map<String, String> saveInWarehouseRecord(String trackingNo, String userLoginName, String isUnKnowCustomer, String remark,
			Long userIdOfOperator) {
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
		String batchNo = InWarehouseRecord.generateBatchNo(null, null, Constant.SYMBOL_UNDERLINE, trackingNo, null, null, isUnKnowCustomer);
		inWarehouseRecord.setBatchNo(batchNo);
		// 返回id
		long id = inWarehouseRecordDao.saveInWarehouseRecord(inWarehouseRecord);
		map.put("id", "" + id);
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	/**
	 * 获取出库记录明细数据
	 */
	@Override
	public Pagination getInWarehouseRecordItemData(Long inWarehouseRecordId, Pagination pagination) {
		InWarehouseRecordItem inWarehouseRecordItem = new InWarehouseRecordItem();
		inWarehouseRecordItem.setInWareHouseRecordId(inWarehouseRecordId);
		List<InWarehouseRecordItem> inWarehouseRecordItemList = inWarehouseRecordDao.findInWarehouseRecordItem(inWarehouseRecordItem, null,
				pagination);
		List<Object> list = new ArrayList<Object>();
		for (InWarehouseRecordItem item : inWarehouseRecordItemList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", item.getId());
			if (item.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(item.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
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

	/**
	 * 获取出库订单数据
	 */
	@Override
	public Pagination getInWarehouseOrderData(InWarehouseOrder inWarehouseOrder, Map<String, String> moreParam, Pagination pagination) {
		List<InWarehouseOrder> inWarehouseOrderList = inWarehouseOrderDao.findInWarehouseOrder(inWarehouseOrder, moreParam, pagination);
		List<Object> list = new ArrayList<Object>();
		for (InWarehouseOrder order : inWarehouseOrderList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", order.getId());
			if (order.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(order.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			// 查询用户名
			User user = userDao.getUserById(order.getUserIdOfCustomer());
			map.put("userNameOfCustomer", user.getLoginName());
			map.put("packageTrackingNo", order.getPackageTrackingNo());
			// 承运商
			map.put("carrierCode", order.getCarrierCode());
			if (order.getWeight() != null) {
				map.put("weight", Weight.gTurnToKg(order.getWeight()));
			}

			if (order.getWarehouseId() != null) {
				Warehouse warehouse = warehouseDao.getWarehouseById(order.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
				}
			}
			map.put("remark", order.getRemark());
			InWarehouseOrderStatus inWarehouseOrderStatus = inWarehouseOrderStatusDao.findInWarehouseOrderStatusByCode(order.getStatus());
			if (inWarehouseOrderStatus != null) {
				map.put("status", inWarehouseOrderStatus.getCn());
			}
			map.put("receivedQuantity", order.getReceivedQuantity());
			list.add(map);
		}
		pagination.total = inWarehouseOrderDao.countInWarehouseOrder(inWarehouseOrder, moreParam);
		pagination.rows = list;
		return pagination;
	}

	/**
	 * 获取出库订单列表数据
	 */
	@Override
	public Pagination getOutWarehouseOrderData(OutWarehouseOrder outWarehouseOrder, Map<String, String> moreParam, Pagination pagination) {
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao
				.findOutWarehouseOrder(outWarehouseOrder, moreParam, pagination);
		List<Object> list = new ArrayList<Object>();
		for (OutWarehouseOrder order : outWarehouseOrderList) {
			Map<String, Object> map = new HashMap<String, Object>();
			Long outWarehouseOrderId = order.getId();
			map.put("id", order.getId());
			if (order.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(order.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			// 查询用户名
			User user = userDao.getUserById(order.getUserIdOfCustomer());
			map.put("userNameOfCustomer", user.getLoginName());
			map.put("customerReferenceNo", order.getCustomerReferenceNo());
			if (order.getWarehouseId() != null && order.getWarehouseId()!=0) {
				Warehouse warehouse = warehouseDao.getWarehouseById(order.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
				}
			}
			map.put("remark", order.getRemark());
			OutWarehouseOrderStatus outWarehouseOrderStatus = outWarehouseOrderStatusDao.findOutWarehouseOrderStatusByCode(order
					.getStatus());
			if (outWarehouseOrderStatus != null) {
				map.put("status", outWarehouseOrderStatus.getCn());
			}
			// 收件人信息
			OutWarehouseOrderReceiver outWarehouseOrderReceiver = outWarehouseOrderReceiverDao.getOutWarehouseOrderReceiverByOrderId(outWarehouseOrderId);
			if (outWarehouseOrderReceiver != null) {
				map.put("receiverAddressLine1", outWarehouseOrderReceiver.getAddressLine1());
				map.put("receiverAddressLine2", outWarehouseOrderReceiver.getAddressLine2());
				map.put("receiverCity", outWarehouseOrderReceiver.getCity());
				map.put("receiverCompany", outWarehouseOrderReceiver.getCompany());
				map.put("receiverCountryCode", outWarehouseOrderReceiver.getCountryCode());
				map.put("receiverCountryName", outWarehouseOrderReceiver.getCountryName());
				map.put("receiverCounty", outWarehouseOrderReceiver.getCounty());
				map.put("receiverEmail", outWarehouseOrderReceiver.getEmail());
				map.put("receiverFirstName", outWarehouseOrderReceiver.getFirstName());
				map.put("receiverLastName", outWarehouseOrderReceiver.getLastName());
				map.put("receiverMobileNumber", outWarehouseOrderReceiver.getMobileNumber());
				map.put("receiverName", outWarehouseOrderReceiver.getName());
				map.put("receiverPhoneNumber", outWarehouseOrderReceiver.getPhoneNumber());
				map.put("receiverPostalCode", outWarehouseOrderReceiver.getPostalCode());
				map.put("receiverStateOrProvince", outWarehouseOrderReceiver.getStateOrProvince());
			}
			// 发件人
			OutWarehouseOrderSender outWarehouseOrderSender = outWarehouseOrderSenderDao.getOutWarehouseOrderSenderByOrderId(outWarehouseOrderId);
			if(outWarehouseOrderSender!=null){
				map.put("senderName", outWarehouseOrderSender.getName());
			}
			//物品明细(目前仅展示SKU*数量)
			String itemStr = "";
			OutWarehouseOrderItem outWarehouseOrderItemParam = new OutWarehouseOrderItem();
			outWarehouseOrderItemParam.setOutWarehouseOrderId(outWarehouseOrderId);
			List<OutWarehouseOrderItem> outWarehouseOrderItemList = outWarehouseOrderItemDao.findOutWarehouseOrderItem(outWarehouseOrderItemParam, null, null);
			for (OutWarehouseOrderItem outWarehouseOrderItem : outWarehouseOrderItemList) {
				itemStr += outWarehouseOrderItem.getSku() + "*" + outWarehouseOrderItem.getQuantity() + " ";
			}
			map.put("items", itemStr);
			list.add(map);
		}
		pagination.total = outWarehouseOrderDao.countOutWarehouseOrder(outWarehouseOrder, moreParam);
		pagination.rows = list;
		return pagination;
	}

	/**
	 * api 创建入库订单
	 */
	@Override
	public String warehouseInterfaceSaveInWarehouseOrder(EventBody eventBody, Long userIdOfCustomer) throws ServiceException {
		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);
		LogisticsDetail logisticsDetail = eventBody.getLogisticsDetail();

		if (logisticsDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventBody对象获取LogisticsDetail对象的得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		List<LogisticsOrder> logisticsOrders = logisticsDetail.getLogisticsOrders();
		if (logisticsOrders == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsDetail对象获取logisticsOrders对象的得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}

		// 开始入库
		for (int i = 0; i < logisticsOrders.size(); i++) {
			// 待添加事务关闭,开启
			LogisticsOrder logisticsOrder = logisticsOrders.get(i);
			logger.info("正在入库:第" + (i + 1) + " 跟踪单号(mailNo):" + logisticsOrder.getMailNo());
			if (StringUtil.isNull(logisticsOrder.getMailNo())) {
				throw new ServiceException("跟踪单号为空,订单入库失败");
			}
			// 判断是否已经存在相同的跟踪单号和承运商(目前仅判断相同的跟踪单号就不可以入库)
			InWarehouseOrder param = new InWarehouseOrder();
			param.setPackageTrackingNo(logisticsOrder.getMailNo());
			Long validate = inWarehouseOrderDao.countInWarehouseOrder(param, null);
			if (validate >= 1) {
				throw new ServiceException("跟踪单号:" + logisticsOrder.getMailNo() + " 重复,订单入库失败");
			}
			// pojo 转 model
			InWarehouseOrder inWarehouseOrder = new InWarehouseOrder();
			inWarehouseOrder.setCreatedTime(System.currentTimeMillis());
			inWarehouseOrder.setPackageTrackingNo(logisticsOrder.getMailNo());
			inWarehouseOrder.setUserIdOfCustomer(userIdOfCustomer);
			// 已预报,未入库
			inWarehouseOrder.setStatus(InWarehouseOrderStatusCode.NONE);
			// 大包号,目前等于跟踪单号
			inWarehouseOrder.setPackageNo(logisticsOrder.getMailNo());
			inWarehouseOrder.setCarrierCode(logisticsOrder.getCarrierCode());
			inWarehouseOrder.setLogisticsType(logisticsOrder.getLogisticsType());

			// sku明细
			SkuDetail skuDetail = logisticsOrder.getSkuDetail();
			if (skuDetail == null) {
				logger.info("正在入库:第" + (i + 1) + " 物品明细(SkuDetail) 得到Null");
				continue;
			}
			List<Sku> skuList = skuDetail.getSkus();
			if (skuList == null) {
				logger.info("正在入库:第" + (i + 1) + "物品明细SkuDetail - >(skuList) 得到Null");
				continue;
			}
			int smallPackageQuantity = 0;
			List<InWarehouseOrderItem> inwarehouseOrderItemList = new ArrayList<InWarehouseOrderItem>();
			for (int j = 0; j < skuList.size(); j++) {
				Sku sku = skuList.get(j);
				if (sku.getSkuQty() == null || StringUtil.isNull(sku.getSkuCode())) {
					continue;
				}
				InWarehouseOrderItem inwarehouseOrderItem = new InWarehouseOrderItem();
				inwarehouseOrderItem.setSku(sku.getSkuCode());
				inwarehouseOrderItem.setQuantity(sku.getSkuQty());
				inwarehouseOrderItem.setSkuName(sku.getSkuName());
				inwarehouseOrderItem.setSkuRemark(sku.getSkuRemark());
				smallPackageQuantity += sku.getSkuQty();
				// 入库主单的id
				inwarehouseOrderItemList.add(inwarehouseOrderItem);
			}
			// 物品总数量
			inWarehouseOrder.setSmallPackageQuantity(smallPackageQuantity);
			// 保存入库订单得到入库订单id
			Long orderId = inWarehouseOrderDao.saveInWarehouseOrder(inWarehouseOrder);

			int count = inWarehouseOrderItemDao.saveBatchInWarehouseOrderItemWithOrderId(inwarehouseOrderItemList, orderId);

			logger.info("入库主单id:" + orderId + " 入库明细数量:" + count);
		}
		response.setSuccess(Constant.TRUE);
		return XmlUtil.toXml(Responses.class, responses);
	}

	/**
	 * API创建出库订单
	 */
	@Override
	public String warehouseInterfaceSaveOutWarehouseOrder(EventBody eventBody, Long userIdOfCustomer) throws ServiceException {
		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);
		// 出库订单发件人信息
		LogisticsDetail logisticsDetail = eventBody.getLogisticsDetail();
		if (logisticsDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventBody对象获取LogisticsDetail对象的得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		List<LogisticsOrder> logisticsOrders = logisticsDetail.getLogisticsOrders();
		if (logisticsOrders == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsDetail对象获取LogisticsOrders对象的得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		// 保存
		for (int i = 0; i < logisticsOrders.size(); i++) {
			LogisticsOrder logisticsOrder = logisticsOrders.get(i);
			if (logisticsOrder == null) {
				throw new ServiceException("LogisticsOrders对象获取LogisticsOrder对象的得到Null");
			}
			ReceiverDetail receiverDetail = logisticsOrder.getReceiverDetail();
			if (receiverDetail == null) {
				throw new ServiceException("LogisticsOrder对象获取ReceiverDetail对象的得到Null");
			}
			SenderDetail senderDetail = logisticsOrder.getSenderDetail();
			if (senderDetail == null) {
				throw new ServiceException("LogisticsOrder对象获取SenderDetail对象的得到Null");
			}
			SkuDetail skuDetail = logisticsOrder.getSkuDetail();
			if (skuDetail == null) {
				throw new ServiceException("LogisticsOrder对象获取SkuDetail对象的得到Null");
			}
			List<Sku> skus = skuDetail.getSkus();
			if (skus == null) {
				throw new ServiceException("SkuDetail对象获取List<Sku>对象的得到Null");
			}
			logger.info("出库订单:第" + (i + 1) + "(poNo):" + logisticsOrder.getPoNo());

			// 主单
			OutWarehouseOrder outWarehouseOrder = new OutWarehouseOrder();
			outWarehouseOrder.setCreatedTime(System.currentTimeMillis());
			outWarehouseOrder.setRemark(logisticsOrder.getLogisticsRemark());
			outWarehouseOrder.setStatus(OurWareHouseStatusCode.DRAFT);
			outWarehouseOrder.setUserIdOfCustomer(userIdOfCustomer);
			// 客户参考号,用于后面客户对该出库订单进行修改,确认等,以及回传出库状态
			outWarehouseOrder.setCustomerReferenceNo(logisticsOrder.getPoNo());
			// 保存主单 得到主单Id
			Long outWarehouseOrderId = outWarehouseOrderDao.saveOutWarehouseOrder(outWarehouseOrder);

			logger.info("出库订单:第" + (i + 1) + "客户参考号customerReferenceNo(poNo):" + logisticsOrder.getPoNo() + " 保存出库订单,得到Id:"
					+ outWarehouseOrderId);

			// 出库订单明细信息
			List<OutWarehouseOrderItem> itemList = new ArrayList<OutWarehouseOrderItem>();
			for (int j = 0; j < skus.size(); j++) {
				Sku sku = skus.get(j);
				if (sku == null) {
					throw new ServiceException("SkuDetail对象获取Sku对象的得到Null");
				}
				OutWarehouseOrderItem outWarehouseOrderItem = new OutWarehouseOrderItem();
				outWarehouseOrderItem.setQuantity(sku.getSkuQty());
				outWarehouseOrderItem.setRemark(sku.getSkuRemark());
				outWarehouseOrderItem.setSku(sku.getSkuCode());
				outWarehouseOrderItem.setSkuName(sku.getSkuName());
				outWarehouseOrderItem.setSkuUnitPrice(sku.getSkuUnitPrice());
				outWarehouseOrderItem.setSkuPriceCurrency(sku.getSkuPriceCurrency());
				outWarehouseOrderItem.setOutWarehouseOrderId(outWarehouseOrderId);
				itemList.add(outWarehouseOrderItem);
			}
			// 保存出库订单明细
			long itemCount = outWarehouseOrderItemDao.saveBatchOutWarehouseOrderItemWithOrderId(itemList, outWarehouseOrderId);
			logger.info("出库订单:第" + (i + 1) + "客户参考号customerReferenceNo(poNo):" + logisticsOrder.getPoNo() + " 保存出库订单明细条数:" + itemCount);
			// 收件人
			OutWarehouseOrderReceiver outWarehouseOrderReceiver = new OutWarehouseOrderReceiver();
			outWarehouseOrderReceiver.setAddressLine1(receiverDetail.getStreetAddress());
			outWarehouseOrderReceiver.setCity(receiverDetail.getCity());
			outWarehouseOrderReceiver.setCountryCode(OutWarehouseOrderReceiver.CN);
			outWarehouseOrderReceiver.setCountryName(OutWarehouseOrderReceiver.CN_VALUE);
			outWarehouseOrderReceiver.setCounty(receiverDetail.getDistrict());
			outWarehouseOrderReceiver.setEmail(receiverDetail.getEmail());
			outWarehouseOrderReceiver.setName(receiverDetail.getName());
			outWarehouseOrderReceiver.setPhoneNumber(receiverDetail.getPhone());
			outWarehouseOrderReceiver.setPostalCode(receiverDetail.getZipCode());
			outWarehouseOrderReceiver.setStateOrProvince(receiverDetail.getProvince());
			outWarehouseOrderReceiver.setMobileNumber(receiverDetail.getMobile());
			outWarehouseOrderReceiver.setOutWarehouseOrderId(outWarehouseOrderId);
			// 保存收件人
			Long outWarehouseOrderReceiverId = outWarehouseOrderReceiverDao.saveOutWarehouseOrderReceiver(outWarehouseOrderReceiver);
			logger.info("出库订单:第" + (i + 1) + "客户参考号customerReferenceNo(poNo):" + logisticsOrder.getPoNo()
					+ " 保存收件人,outWarehouseOrderReceiverId:" + outWarehouseOrderReceiverId);
			// 发件人信息
			OutWarehouseOrderSender outWarehouseOrderSender = new OutWarehouseOrderSender();
			outWarehouseOrderSender.setAddressLine1(senderDetail.getStreetAddress());
			outWarehouseOrderSender.setCity(senderDetail.getCity());
			outWarehouseOrderSender.setCountryCode(senderDetail.getCountry());
			outWarehouseOrderSender.setCountryName(senderDetail.getCountry());
			outWarehouseOrderSender.setCounty(senderDetail.getDistrict());
			outWarehouseOrderSender.setEmail(senderDetail.getEmail());
			outWarehouseOrderSender.setName(senderDetail.getName());
			outWarehouseOrderSender.setPhoneNumber(senderDetail.getPhone());
			outWarehouseOrderSender.setPostalCode(senderDetail.getZipCode());
			outWarehouseOrderSender.setStateOrProvince(senderDetail.getProvince());
			outWarehouseOrderSender.setMobileNumber(senderDetail.getMobile());
			outWarehouseOrderSender.setOutWarehouseOrderId(outWarehouseOrderId);
			// 保存发件人
			Long outWarehouseOrderSenderId = outWarehouseOrderSenderDao.saveOutWarehouseOrderSender(outWarehouseOrderSender);
			logger.info("出库订单:第" + (i + 1) + "客户参考号customerReferenceNo(poNo):" + logisticsOrder.getPoNo()
					+ " 保存发件人,outWarehouseOrderSenderId:" + outWarehouseOrderSenderId);
		}
		response.setSuccess(Constant.TRUE);
		return XmlUtil.toXml(Responses.class, responses);
	}
}
