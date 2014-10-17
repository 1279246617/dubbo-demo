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
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordItemDao;
import com.coe.wms.dao.warehouse.storage.IItemInventoryDao;
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
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus.OutWarehouseOrderStatusCode;
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
import com.coe.wms.pojo.api.warehouse.TradeDetail;
import com.coe.wms.pojo.api.warehouse.TradeOrder;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.NumberUtil;
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

	@Resource(name = "inWarehouseRecordDao")
	private IInWarehouseRecordDao inWarehouseRecordDao;

	@Resource(name = "inWarehouseRecordItemDao")
	private IInWarehouseRecordItemDao inWarehouseRecordItemDao;

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Resource(name = "itemInventoryDao")
	private IItemInventoryDao itemInventoryDao;

	/**
	 * 根据入库订单id, 查找入库物品明细
	 * 
	 * @param orderId
	 * @param pagination
	 * @return
	 */
	@Override
	public Pagination getInWarehouseOrderItemData(Long orderId, Pagination pagination) {
		InWarehouseOrderItem param = new InWarehouseOrderItem();
		param.setOrderId(orderId);
		List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(param, null, pagination);

		return pagination;
	}

	/**
	 * 根据入库订单id, 查找入库物品明细
	 * 
	 * @param orderId
	 * @param pagination
	 * @return
	 */
	@Override
	public List<InWarehouseOrderItem> getInWarehouseOrderItem(Long orderId) {
		InWarehouseOrderItem param = new InWarehouseOrderItem();
		param.setOrderId(orderId);
		List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(param, null, null);
		return inWarehouseOrderItemList;
	}

	public List<Map<String, String>> getInWarehouseOrderItemMap(Long orderId) {
		InWarehouseOrderItem param = new InWarehouseOrderItem();
		param.setOrderId(orderId);
		List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(param, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (InWarehouseOrderItem item : inWarehouseOrderItemList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("sku", item.getSku());
			map.put("skuName", item.getSkuName());
			map.put("quantity", NumberUtil.intToString(item.getQuantity()));
			int receivedQuantity = inWarehouseRecordItemDao.countInWarehouseSkuQuantity(orderId, item.getSku());
			map.put("receivedQuantity", receivedQuantity + "");
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 保存入库明细
	 */
	@Override
	public Map<String, String> saveInWarehouseRecordItem(String itemSku, Integer itemQuantity, String itemRemark, Long warehouseId,
			String shelvesNo, String seatNo, Long inWarehouseRecordId, Long userIdOfOperator) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(itemSku)) {
			map.put(Constant.MESSAGE, "请输入产品SKU.");
			return map;
		}
		if (itemQuantity == null) {
			map.put(Constant.MESSAGE, "请输入产品数量.");
			return map;
		}
		Long orderId = inWarehouseRecordDao.getInWarehouseOrderIdByRecordId(inWarehouseRecordId);
		// 检查该SKU是否存在入库订单中
		InWarehouseOrderItem inWarehouseOrderItemParam = new InWarehouseOrderItem();
		inWarehouseOrderItemParam.setSku(itemSku);
		inWarehouseOrderItemParam.setOrderId(orderId);
		List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(inWarehouseOrderItemParam,
				null, null);
		if (inWarehouseOrderItemList.size() <= 0) {
			map.put(Constant.MESSAGE, "该产品SKU在此订单中无预报.");
			return map;
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		//查询入库主单信息,用于更新库存
//		inWarehouseRecordId
		inWarehouseRecordDao.getInWarehouseRecordById(InWarehouseRecordId);
		
		// 检查该SKU是否已经存在,已经存在则直接改变数量(同一个入库主单,同一个SKU只允许一个收货明细)
		InWarehouseRecordItem param = new InWarehouseRecordItem();
		param.setInWarehouseRecordId(inWarehouseRecordId);
		param.setSku(itemSku);
		List<InWarehouseRecordItem> inWarehouseRecordItemList = inWarehouseRecordItemDao.findInWarehouseRecordItem(param, null, null);
		if (inWarehouseRecordItemList.size() > 0) {
			// 返回入库主单的id
			Long recordItemId = inWarehouseRecordItemList.get(0).getId();
			map.put("id", "" + recordItemId);
			int newQuantity = inWarehouseRecordItemList.get(0).getQuantity() + itemQuantity;
			int updateCount = inWarehouseRecordItemDao.updateInWarehouseRecordItemReceivedQuantity(recordItemId, newQuantity);
			
			// 更新入库明细成功,则添加库存
			if (updateCount > 0) {
				itemInventoryDao.addItemInventory(warehouseId, userIdOfCustomer, batchNo, itemSku, itemQuantity);
			}
			return map;
		}

		InWarehouseRecordItem inWarehouseRecordItem = new InWarehouseRecordItem();
		inWarehouseRecordItem.setCreatedTime(System.currentTimeMillis());
		inWarehouseRecordItem.setInWarehouseRecordId(inWarehouseRecordId);
		inWarehouseRecordItem.setQuantity(itemQuantity);
		inWarehouseRecordItem.setRemark(itemRemark);
		// 分配货架货位的逻辑
		if (StringUtil.isNull(seatNo)) {
			seatNo = "G1";
		}
		if (StringUtil.isNull(shelvesNo)) {
			shelvesNo = "G";
		}
		inWarehouseRecordItem.setSeatNo(seatNo);
		inWarehouseRecordItem.setShelvesNo(shelvesNo);
		inWarehouseRecordItem.setSku(itemSku);
		inWarehouseRecordItem.setUserIdOfOperator(userIdOfOperator);
		inWarehouseRecordItem.setWarehouseId(warehouseId);
		// 返回id
		long id = inWarehouseRecordItemDao.saveInWarehouseRecordItem(inWarehouseRecordItem);
		map.put("id", "" + id);
		return map;
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
			response.setReasonDesc("logisticsInterface消息内容转LogisticsEventsRequest对象得到Null");
			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}
		LogisticsEvent logisticsEvent = logisticsEventsRequest.getLogisticsEvent();
		if (logisticsEvent == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsEventsRequest对象获取LogisticsEvent对象得到Null");

			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}
		// 事件头
		EventHeader eventHeader = logisticsEvent.getEventHeader();
		if (eventHeader == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsEvent对象获取EventHeader对象得到Null");

			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}
		// 得到事件类型,根据事件类型,分发事件Body 到不同方法处理
		String eventType = eventHeader.getEventType();
		// 事件目标,仓库编码
		String eventTarget = eventHeader.getEventTarget();
		if (StringUtil.isNull(eventType)) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventHeader对象获取eventType得到Null");
			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}

		// 事件Body
		EventBody eventBody = logisticsEvent.getEventBody();
		if (eventBody == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsEvent对象获取EventBody对象得到Null");
			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}
		// 成功得到事件类型,返回body
		map.put(Constant.STATUS, Constant.SUCCESS);
		map.put("eventType", eventType);
		map.put("eventTarget", eventTarget);
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
		if (StringUtil.isNull(logisticsInterface) || StringUtil.isNull(msgSource) || StringUtil.isNull(dataDigest)
				|| StringUtil.isNull(msgType) || StringUtil.isNull(msgId)) {
			response.setReason(ErrorCode.S12_CODE);
			response.setReasonDesc("缺少关键字段,请检查以下字段:logistics_interface,data_digest,msg_type,msg_id");
			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}

		// 根据msgSource 找到客户(token),找到密钥
		User user = userDao.findUserByMsgSource(msgSource);
		if (user == null) {
			response.setReason(ErrorCode.B0008_CODE);
			response.setReasonDesc("根据msg_source 找不到客户");
			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}

		// 验证内容和签名字符串
		String md5dataDigest = StringUtil.encoderByMd5(logisticsInterface + user.getToken());
		if (!StringUtil.isEqual(md5dataDigest, dataDigest)) {
			// 签名错误
			response.setReason(ErrorCode.S02_CODE);
			response.setReasonDesc("收到消息签名:" + dataDigest + " 系统计算消息签名:" + md5dataDigest);
			map.put(Constant.MESSAGE, XmlUtil.toXml(Responses.class, responses));
			return map;
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		map.put(Constant.USER_ID_OF_CUSTOMER, "" + user.getId());
		return map;
	}

	/**
	 * 保存入库记录主单
	 */
	@Override
	public Map<String, String> saveInWarehouseRecord(String trackingNo, String remark, Long userIdOfOperator, Long warehouseId,
			Long inWarehouseOrderId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(trackingNo)) {
			map.put(Constant.MESSAGE, "请输入跟踪单号.");
			return map;
		}

		Long userIdOfCustomer = inWarehouseOrderDao.getUserIdByInWarehouseOrderId(inWarehouseOrderId);
		InWarehouseRecord inWarehouseRecord = new InWarehouseRecord();
		inWarehouseRecord.setCreatedTime(System.currentTimeMillis());
		inWarehouseRecord.setTrackingNo(trackingNo);
		inWarehouseRecord.setUserIdOfCustomer(userIdOfCustomer);
		inWarehouseRecord.setInWarehouseOrderId(inWarehouseOrderId);
		inWarehouseRecord.setRemark(remark);
		inWarehouseRecord.setUserIdOfOperator(userIdOfOperator);
		// 创建批次号
		String batchNo = InWarehouseRecord.generateBatchNo(null, null, Constant.SYMBOL_UNDERLINE, trackingNo, null, null);
		inWarehouseRecord.setBatchNo(batchNo);
		inWarehouseRecord.setWarehouseId(warehouseId);
		// 返回id
		long id = inWarehouseRecordDao.saveInWarehouseRecord(inWarehouseRecord);
		map.put("id", "" + id);
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	/**
	 * 获取入库记录明细数据
	 */
	@Override
	public Pagination getInWarehouseRecordItemData(Long inWarehouseRecordId, Pagination pagination) {
		// 查询入库订单
		Long inWarehouseOrderId = inWarehouseRecordDao.getInWarehouseOrderIdByRecordId(inWarehouseRecordId);
		InWarehouseOrderItem param = new InWarehouseOrderItem();
		param.setOrderId(inWarehouseOrderId);
		List<InWarehouseOrderItem> orderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(param, null, null);
		List<Object> list = new ArrayList<Object>();
		for (InWarehouseOrderItem orderItem : orderItemList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderItem.getId());
			map.put("sku", orderItem.getSku());
			map.put("totalQuantity", orderItem.getQuantity());
			int totalReceivedQuantity = inWarehouseRecordItemDao.countInWarehouseSkuQuantity(inWarehouseOrderId, orderItem.getSku());
			map.put("totalReceivedQuantity", totalReceivedQuantity);
			int unReceivedquantity = orderItem.getQuantity() - totalReceivedQuantity;
			map.put("unReceivedquantity", unReceivedquantity);
			// 根据SKU查询收货记录的物品明细
			InWarehouseRecordItem inWarehouseRecordItemParam = new InWarehouseRecordItem();
			inWarehouseRecordItemParam.setInWarehouseRecordId(inWarehouseRecordId);
			inWarehouseRecordItemParam.setSku(orderItem.getSku());
			List<InWarehouseRecordItem> recordItemList = inWarehouseRecordItemDao.findInWarehouseRecordItem(inWarehouseRecordItemParam,
					null, null);
			if (recordItemList != null && recordItemList.size() > 0) {
				InWarehouseRecordItem recordItem = recordItemList.get(0);
				if (recordItem.getCreatedTime() != null) {
					map.put("createdTime", DateUtil.dateConvertString(new Date(recordItem.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
				}
				map.put("receivedQuantity", recordItem.getQuantity());
				map.put("remark", recordItem.getRemark());
				map.put("seatNo", recordItem.getSeatNo());
				map.put("shelvesNo", recordItem.getShelvesNo());
				if (NumberUtil.greaterThanZero(recordItem.getUserIdOfOperator())) {
					User user = userDao.getUserById(recordItem.getUserIdOfOperator());
					map.put("userLoginNameOfOperator", user.getLoginName());
				}
				if (NumberUtil.greaterThanZero(recordItem.getWarehouseId())) {
					Warehouse warehouse = warehouseDao.getWarehouseById(recordItem.getWarehouseId());
					if (warehouse != null) {
						map.put("warehouse", warehouse.getWarehouseName());
					}
				}
			}
			list.add(map);
		}
		pagination.total = inWarehouseOrderItemDao.countInWarehouseOrderItem(param, null);
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
			map.put("trackingNo", order.getTrackingNo());
			map.put("customerReferenceNo", order.getCustomerReferenceNo());
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
			InWarehouseOrderItem param = new InWarehouseOrderItem();
			param.setOrderId(order.getId());
			List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(param, null, null);
			String skus = "";
			for (InWarehouseOrderItem item : inWarehouseOrderItemList) {
				skus += item.getSku() + "*" + item.getQuantity() + " ";
			}
			map.put("skus", skus);
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
			if (NumberUtil.greaterThanZero(order.getWarehouseId())) {
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
			OutWarehouseOrderReceiver outWarehouseOrderReceiver = outWarehouseOrderReceiverDao
					.getOutWarehouseOrderReceiverByOrderId(outWarehouseOrderId);
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
			OutWarehouseOrderSender outWarehouseOrderSender = outWarehouseOrderSenderDao
					.getOutWarehouseOrderSenderByOrderId(outWarehouseOrderId);
			if (outWarehouseOrderSender != null) {
				map.put("senderName", outWarehouseOrderSender.getName());
			}
			// 物品明细(目前仅展示SKU*数量)
			String itemStr = "";
			OutWarehouseOrderItem outWarehouseOrderItemParam = new OutWarehouseOrderItem();
			outWarehouseOrderItemParam.setOutWarehouseOrderId(outWarehouseOrderId);
			List<OutWarehouseOrderItem> outWarehouseOrderItemList = outWarehouseOrderItemDao.findOutWarehouseOrderItem(
					outWarehouseOrderItemParam, null, null);
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
	public String warehouseInterfaceSaveInWarehouseOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo)
			throws ServiceException {
		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);
		LogisticsDetail logisticsDetail = eventBody.getLogisticsDetail();

		if (logisticsDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventBody对象获取LogisticsDetail对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		List<LogisticsOrder> logisticsOrders = logisticsDetail.getLogisticsOrders();
		if (logisticsOrders == null || logisticsOrders.size() == 0) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsDetail对象获取logisticsOrders对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}

		Warehouse warehouse = warehouseDao.getWarehouseByNo(warehouseNo);
		if (warehouse == null) {
			response.setReason(ErrorCode.B0003_CODE);
			response.setReasonDesc("根据仓库编号(eventTarget)获取仓库得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		if (logisticsOrders.size() > 1) {
			throw new ServiceException("每次仅能处理一条订单入库,此次请求处理失败");
		}
		// 开始入库
		LogisticsOrder logisticsOrder = logisticsOrders.get(0);
		logger.info("正在入库:跟踪单号(mailNo):" + logisticsOrder.getMailNo());
		if (StringUtil.isNull(logisticsOrder.getMailNo())) {
			response.setReason(ErrorCode.S13_CODE);
			response.setReasonDesc("跟踪单号(mailNo)为空,订单入库失败");
			return XmlUtil.toXml(Responses.class, responses);
		}
		if (StringUtil.isNull(logisticsOrder.getSkuStockInId())) {
			response.setReason(ErrorCode.S13_CODE);
			response.setReasonDesc("客户参考号(skuStockInId)为空,订单入库失败");
			return XmlUtil.toXml(Responses.class, responses);
		}
		// 判断是否已经存在相同的跟踪单号和承运商(目前仅判断相同的跟踪单号就不可以入库)
		InWarehouseOrder param = new InWarehouseOrder();
		param.setTrackingNo(logisticsOrder.getMailNo());
		param.setCustomerReferenceNo(logisticsOrder.getSkuStockInId());
		Long validate = inWarehouseOrderDao.countInWarehouseOrder(param, null);
		if (validate >= 1) {
			response.setReason(ErrorCode.B0200_CODE);
			response.setReasonDesc("跟踪单号:" + logisticsOrder.getMailNo() + "和客户参考号(skuStockInId):" + logisticsOrder.getSkuStockInId()
					+ "重复,订单入库失败");
			return XmlUtil.toXml(Responses.class, responses);
		}
		// pojo 转 model
		InWarehouseOrder inWarehouseOrder = new InWarehouseOrder();
		inWarehouseOrder.setCreatedTime(System.currentTimeMillis());
		inWarehouseOrder.setTrackingNo(logisticsOrder.getMailNo());
		inWarehouseOrder.setCustomerReferenceNo(logisticsOrder.getSkuStockInId());
		inWarehouseOrder.setUserIdOfCustomer(userIdOfCustomer);
		// 已预报,未入库
		inWarehouseOrder.setStatus(InWarehouseOrderStatusCode.NONE);
		inWarehouseOrder.setCarrierCode(logisticsOrder.getCarrierCode());
		inWarehouseOrder.setLogisticsType(logisticsOrder.getLogisticsType());
		inWarehouseOrder.setWarehouseId(warehouse.getId());
		// sku明细
		SkuDetail skuDetail = logisticsOrder.getSkuDetail();
		if (skuDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsOrder对象获取SkuDetail对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		List<Sku> skuList = skuDetail.getSkus();
		if (skuList == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("SkuDetail对象获取Skus对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
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
			// 入库主单的id
			inwarehouseOrderItemList.add(inwarehouseOrderItem);
		}
		// 保存入库订单得到入库订单id
		Long orderId = inWarehouseOrderDao.saveInWarehouseOrder(inWarehouseOrder);
		int count = inWarehouseOrderItemDao.saveBatchInWarehouseOrderItemWithOrderId(inwarehouseOrderItemList, orderId);
		logger.info("入库主单id:" + orderId + " 入库明细数量:" + count);
		response.setSuccess(Constant.TRUE);
		return XmlUtil.toXml(Responses.class, responses);
	}

	/**
	 * API创建出库订单
	 */
	@Override
	public String warehouseInterfaceSaveOutWarehouseOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo)
			throws ServiceException {
		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);
		// 取 tradeDetail 中tradeOrderId 作为客户参考号
		TradeDetail tradeDetail = eventBody.getTradeDetail();
		if (tradeDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventBody对象获取TradeDetail对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		List<TradeOrder> tradeOrderList = tradeDetail.getTradeOrders();
		if (tradeOrderList == null || tradeOrderList.size() == 0) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeDetail对象获取TradeOrders对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		// 客户参考号
		String customerReferenceNo = tradeOrderList.get(0).getTradeOrderId();
		if (StringUtil.isNull(customerReferenceNo)) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeOrder对象获取tradeOrderId得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		// 出库订单发件人信息
		LogisticsDetail logisticsDetail = eventBody.getLogisticsDetail();
		if (logisticsDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventBody对象获取LogisticsDetail对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		List<LogisticsOrder> logisticsOrders = logisticsDetail.getLogisticsOrders();
		if (logisticsOrders == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsDetail对象获取LogisticsOrders对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		Warehouse warehouse = warehouseDao.getWarehouseByNo(warehouseNo);
		if (warehouse == null) {
			response.setReason(ErrorCode.B0003_CODE);
			response.setReasonDesc("根据仓库编号(eventTarget)获取仓库得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}

		// 保存
		for (int i = 0; i < logisticsOrders.size(); i++) {
			LogisticsOrder logisticsOrder = logisticsOrders.get(i);
			if (logisticsOrder == null) {
				throw new ServiceException("LogisticsOrders对象获取LogisticsOrder对象得到Null");
			}
			ReceiverDetail receiverDetail = logisticsOrder.getReceiverDetail();
			if (receiverDetail == null) {
				throw new ServiceException("LogisticsOrder对象获取ReceiverDetail对象得到Null");
			}
			SenderDetail senderDetail = logisticsOrder.getSenderDetail();
			if (senderDetail == null) {
				throw new ServiceException("LogisticsOrder对象获取SenderDetail对象得到Null");
			}
			SkuDetail skuDetail = logisticsOrder.getSkuDetail();
			if (skuDetail == null) {
				throw new ServiceException("LogisticsOrder对象获取SkuDetail对象得到Null");
			}
			List<Sku> skus = skuDetail.getSkus();
			if (skus == null) {
				throw new ServiceException("SkuDetail对象获取List<Sku>对象得到Null");
			}
			logger.info("出库订单:第" + (i + 1) + "(customerReferenceNo):" + customerReferenceNo);
			// 检查客户参考号是否重复
			OutWarehouseOrder outWarehouseOrderParam = new OutWarehouseOrder();
			outWarehouseOrderParam.setCustomerReferenceNo(customerReferenceNo);
			Long count = outWarehouseOrderDao.countOutWarehouseOrder(outWarehouseOrderParam, null);
			if (count > 0) {
				response.setReason(ErrorCode.B0200_CODE);
				response.setReasonDesc("客户参考号(tradeOrderId)重复,保存失败");
				return XmlUtil.toXml(Responses.class, responses);
			}

			// 主单
			OutWarehouseOrder outWarehouseOrder = new OutWarehouseOrder();
			outWarehouseOrder.setCreatedTime(System.currentTimeMillis());
			outWarehouseOrder.setRemark(logisticsOrder.getLogisticsRemark());
			outWarehouseOrder.setStatus(OutWarehouseOrderStatusCode.WWC);
			outWarehouseOrder.setUserIdOfCustomer(userIdOfCustomer);
			outWarehouseOrder.setWarehouseId(warehouse.getId());
			// 客户参考号,用于后面客户对该出库订单进行修改,确认等,以及回传出库状态
			outWarehouseOrder.setCustomerReferenceNo(customerReferenceNo);
			// 保存主单 得到主单Id
			Long outWarehouseOrderId = outWarehouseOrderDao.saveOutWarehouseOrder(outWarehouseOrder);

			logger.info("出库订单:第" + (i + 1) + "客户参考号customerReferenceNo(tradeOrderId):" + customerReferenceNo + " 保存出库订单,得到Id:"
					+ outWarehouseOrderId);

			// 出库订单明细信息
			List<OutWarehouseOrderItem> itemList = new ArrayList<OutWarehouseOrderItem>();
			for (int j = 0; j < skus.size(); j++) {
				Sku sku = skus.get(j);
				if (sku == null) {
					throw new ServiceException("SkuDetail对象获取Sku对象得到Null");
				}
				OutWarehouseOrderItem outWarehouseOrderItem = new OutWarehouseOrderItem();
				outWarehouseOrderItem.setQuantity(sku.getSkuQty());
				outWarehouseOrderItem.setRemark(sku.getSkuRemark());
				outWarehouseOrderItem.setSku(sku.getSkuCode());
				outWarehouseOrderItem.setSkuName(sku.getSkuName());
				outWarehouseOrderItem.setSkuUnitPrice(sku.getSkuUnitPrice());
				outWarehouseOrderItem.setSkuPriceCurrency(sku.getSkuPriceCurrency());
				outWarehouseOrderItem.setSkuNetWeight(sku.getSkuNetWeight());
				outWarehouseOrderItem.setOutWarehouseOrderId(outWarehouseOrderId);
				itemList.add(outWarehouseOrderItem);
			}
			// 保存出库订单明细
			long itemCount = outWarehouseOrderItemDao.saveBatchOutWarehouseOrderItemWithOrderId(itemList, outWarehouseOrderId);
			logger.info("出库订单:第" + (i + 1) + "客户参考号customerReferenceNo(tradeOrderId):" + customerReferenceNo + " 保存出库订单明细条数:" + itemCount);
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
			logger.info("出库订单:第" + (i + 1) + "客户参考号customerReferenceNo(tradeOrderId):" + customerReferenceNo
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
			logger.info("出库订单:第" + (i + 1) + "客户参考号customerReferenceNo(tradeOrderId):" + customerReferenceNo
					+ " 保存发件人,outWarehouseOrderSenderId:" + outWarehouseOrderSenderId);
		}
		response.setSuccess(Constant.TRUE);
		return XmlUtil.toXml(Responses.class, responses);
	}

	/**
	 * 获取入库记录
	 */
	@Override
	public Pagination getInWarehouseRecordData(InWarehouseRecord inWarehouseRecord, Map<String, String> moreParam, Pagination pagination) {
		List<InWarehouseRecord> inWarehouseRecordList = inWarehouseRecordDao
				.findInWarehouseRecord(inWarehouseRecord, moreParam, pagination);
		List<Object> list = new ArrayList<Object>();
		for (InWarehouseRecord record : inWarehouseRecordList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", record.getId());
			if (record.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(record.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			// 查询用户名
			User user = userDao.getUserById(record.getUserIdOfCustomer());
			map.put("userLoginNameOfCustomer", user.getLoginName());
			// 查询操作员
			if (NumberUtil.greaterThanZero(record.getUserIdOfOperator())) {
				User userOfOperator = userDao.getUserById(record.getUserIdOfOperator());
				map.put("userLoginNameOfOperator", userOfOperator.getLoginName());
			}
			map.put("trackingNo", record.getTrackingNo());
			map.put("inWarehouseOrderId", record.getInWarehouseOrderId());
			map.put("batchNo", record.getBatchNo());
			if (NumberUtil.greaterThanZero(record.getWarehouseId())) {
				Warehouse warehouse = warehouseDao.getWarehouseById(record.getWarehouseId());
				map.put("warehouse", warehouse.getWarehouseName());
			}
			map.put("remark", record.getRemark());
			InWarehouseRecordItem param = new InWarehouseRecordItem();
			param.setInWarehouseRecordId(record.getId());
			List<InWarehouseRecordItem> inWarehouseRecordItemList = inWarehouseRecordItemDao.findInWarehouseRecordItem(param, null, null);
			Integer receivedQuantity = 0;
			String skus = "";
			for (InWarehouseRecordItem item : inWarehouseRecordItemList) {
				skus += item.getSku() + "*" + item.getQuantity() + " ";
				receivedQuantity += item.getQuantity();
			}
			map.put("skus", skus);
			map.put("receivedQuantity", receivedQuantity);
			// 预报物品数量,根据跟踪单号找入库订单
			Long orderItemsize = inWarehouseOrderDao.countInWarehouseOrderItemByTrackingNo(record.getTrackingNo());
			map.put("quantity", orderItemsize);
			list.add(map);
		}
		pagination.total = inWarehouseRecordDao.countInWarehouseRecord(inWarehouseRecord, moreParam);
		pagination.rows = list;
		return pagination;
	}

	@Override
	public Map<String, String> checkOutWarehouseOrder(String orderIds, Integer checkResult, Long userIdOfOperator) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(orderIds)) {
			map.put(Constant.MESSAGE, "出库订单id(orderIds)为空,无法处理");
			return map;
		}
		if (checkResult == null) {
			map.put(Constant.MESSAGE, "审核结果(checkResult)为空,无法处理");
			return map;
		}
		String orderIdArr[] = orderIds.split(",");
		for (String orderId : orderIdArr) {
			if (StringUtil.isNull(orderId)) {
				continue;
			}
			// 查询订单的当前状态
			String oldStatus = outWarehouseOrderDao.getOutWarehouseOrderStatus(Long.valueOf(orderId));
			// 如果不是等待审核状态的订单,直接跳过
			if (!StringUtil.isEqual(oldStatus, OutWarehouseOrderStatusCode.WWC)) {
				continue;
			}
			// COE审核通过,等待称重 Wait Warehouse Weighing
			int updateResult = outWarehouseOrderDao.updateOutWarehouseOrderStatus(Long.valueOf(orderId), OutWarehouseOrderStatusCode.WWW);
			if (updateResult < 1) {
				logger.warn("更新出库订单为审核通过时,得到结果0, orderId:" + orderId);
			}
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public List<OutWarehouseOrderStatus> findAllOutWarehouseOrderStatus() throws ServiceException {
		return outWarehouseOrderStatusDao.findAllOutWarehouseOrderStatus();
	}

	/**
	 * API确认出库订单
	 */
	@Override
	public String warehouseInterfaceConfirmOutWarehouseOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo)
			throws ServiceException {
		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);
		// 取 tradeDetail 中tradeOrderId 作为客户参考号
		TradeDetail tradeDetail = eventBody.getTradeDetail();
		if (tradeDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventBody对象获取TradeDetail对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		List<TradeOrder> tradeOrderList = tradeDetail.getTradeOrders();
		if (tradeOrderList == null || tradeOrderList.size() == 0) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeDetail对象获取TradeOrders对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		// 客户参考号
		String customerReferenceNo = tradeOrderList.get(0).getTradeOrderId();
		if (StringUtil.isNull(customerReferenceNo)) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeOrder对象获取tradeOrderId得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		// 根据客户参考号和客户帐号查找出库订单
		OutWarehouseOrder outWarehouseOrderParam = new OutWarehouseOrder();
		outWarehouseOrderParam.setUserIdOfCustomer(userIdOfCustomer);
		outWarehouseOrderParam.setCustomerReferenceNo(customerReferenceNo);
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(outWarehouseOrderParam, null, null);
		if (outWarehouseOrderList.size() <= 0) {
			response.setReason(ErrorCode.B0005_CODE);
			response.setReasonDesc("根据客户参考号(tradeOrderId)和客户帐号(msgSource)查找订单得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderList.get(0);
		// 只有当前状态 是等待顺丰确认的订单 才允许处理
		if (!StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WCC)) {
			response.setReason(ErrorCode.B0100_CODE);
			response.setReasonDesc("出库订单当前状态非等待客户确认状态,不能修改");
			return XmlUtil.toXml(Responses.class, responses);
		}
		int count = outWarehouseOrderDao.updateOutWarehouseOrderStatus(outWarehouseOrder.getId(), OutWarehouseOrderStatusCode.WWO);
		logger.info("确认出库成功: 更新状态影响行数=" + count);

		response.setSuccess(Constant.TRUE);
		return XmlUtil.toXml(Responses.class, responses);
	}

	/**
	 * 获取所有仓库
	 */
	@Override
	public List<Warehouse> findAllWarehouse() throws ServiceException {
		return warehouseDao.findAllWarehouse();
	}

	@Override
	public List<Warehouse> findAllWarehouse(Long firstWarehouseId) throws ServiceException {
		if (firstWarehouseId == null) {
			return warehouseDao.findAllWarehouse();
		}
		List<Warehouse> newWarehouseList = new ArrayList<Warehouse>();
		newWarehouseList.add(warehouseDao.getWarehouseById(firstWarehouseId));

		List<Warehouse> warehouseList = warehouseDao.findAllWarehouse();
		for (int i = 0; i < warehouseList.size(); i++) {
			Warehouse warehouse = warehouseList.get(i);
			if (warehouse.getId() - firstWarehouseId == 0) {
				warehouseList.remove(i);
				break;
			}
		}
		newWarehouseList.addAll(warehouseList);
		return newWarehouseList;
	}

	/**
	 * 根据入库订单id, 查找入库物品明细
	 * 
	 * @param orderId
	 * @param pagination
	 * @return
	 */
	@Override
	public List<OutWarehouseOrderItem> getOutWarehouseOrderItem(Long orderId) {
		OutWarehouseOrderItem param = new OutWarehouseOrderItem();
		param.setOutWarehouseOrderId(orderId);
		List<OutWarehouseOrderItem> outWarehouseOrderItemList = outWarehouseOrderItemDao.findOutWarehouseOrderItem(param, null, null);
		return outWarehouseOrderItemList;
	}

	@Override
	public List<Map<String, String>> checkInWarehouseOrder(InWarehouseOrder inWarehouseOrder) {
		List<InWarehouseOrder> inWarehouseOrderList = inWarehouseOrderDao.findInWarehouseOrder(inWarehouseOrder, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (InWarehouseOrder order : inWarehouseOrderList) {
			Map<String, String> map = new HashMap<String, String>();
			Long userId = order.getUserIdOfCustomer();
			User user = userDao.getUserById(userId);
			map.put("inWarehouseOrderId", String.valueOf(order.getId()));
			map.put("userLoginName", user.getLoginName());
			map.put("trackingNo", order.getTrackingNo());
			map.put("customerReferenceNo", order.getCustomerReferenceNo());
			map.put("carrierCode", order.getCarrierCode());
			String time = DateUtil.dateConvertString(new Date(order.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss);
			map.put("createdTime", time);
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public List<Map<String, String>> getInWarehouseRecordItemMapByRecordId(Long recordId) {
		InWarehouseRecordItem param = new InWarehouseRecordItem();
		param.setInWarehouseRecordId(recordId);
		List<InWarehouseRecordItem> inWarehouseRecordItemList = inWarehouseRecordItemDao.findInWarehouseRecordItem(param, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (InWarehouseRecordItem item : inWarehouseRecordItemList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("sku", item.getSku());
			// 待建立SKU库后,从SKU库取产品名称
			map.put("skuName", "");
			map.put("quantity", NumberUtil.intToString(item.getQuantity()));
			map.put("seatNo", item.getSeatNo());
			mapList.add(map);
		}
		return mapList;
	}
}
