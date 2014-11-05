package com.coe.wms.service.storage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.ITrackingNoDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordStatusDao;
import com.coe.wms.dao.warehouse.storage.IItemInventoryDao;
import com.coe.wms.dao.warehouse.storage.IItemShelfInventoryDao;
import com.coe.wms.dao.warehouse.storage.IOnShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderAdditionalSfDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderReceiverDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderSenderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderStatusDao;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.unit.Weight;
import com.coe.wms.model.unit.Weight.WeightCode;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.TrackingNo;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderStatus;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderStatus.InWarehouseOrderStatusCode;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderAdditionalSf;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItemShelf;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderSender;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus.OutWarehouseOrderStatusCode;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordStatus;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordStatus.InWarehouseRecordStatusCode;
import com.coe.wms.model.warehouse.storage.record.ItemShelfInventory;
import com.coe.wms.model.warehouse.storage.record.OnShelf;
import com.coe.wms.model.warehouse.storage.record.OutShelf;
import com.coe.wms.pojo.api.warehouse.ClearanceDetail;
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

	@Resource(name = "trackingNoDao")
	private ITrackingNoDao trackingNoDao;

	@Resource(name = "onShelfDao")
	private IOnShelfDao onShelfDao;

	@Resource(name = "outShelfDao")
	private IOutShelfDao outShelfDao;

	@Resource(name = "inWarehouseOrderDao")
	private IInWarehouseOrderDao inWarehouseOrderDao;

	@Resource(name = "inWarehouseOrderStatusDao")
	private IInWarehouseOrderStatusDao inWarehouseOrderStatusDao;

	@Resource(name = "inWarehouseRecordStatusDao")
	private IInWarehouseRecordStatusDao inWarehouseRecordStatusDao;

	@Resource(name = "outWarehouseOrderItemShelfDao")
	private IOutWarehouseOrderItemShelfDao outWarehouseOrderItemShelfDao;

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

	@Resource(name = "itemShelfInventoryDao")
	private IItemShelfInventoryDao itemShelfInventoryDao;

	@Resource(name = "outWarehouseOrderAdditionalSfDao")
	private IOutWarehouseOrderAdditionalSfDao outWarehouseOrderAdditionalSfDao;

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
			int receivedQuantity = inWarehouseRecordItemDao.countInWarehouseItemSkuQuantityByOrderId(orderId, item.getSku());
			map.put("receivedQuantity", receivedQuantity + "");
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 保存入库明细
	 */
	@Override
	public Map<String, String> saveInWarehouseRecordItem(String itemSku, Integer itemQuantity, String itemRemark, Long warehouseId, Long inWarehouseRecordId, Long userIdOfOperator) {
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
		List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(inWarehouseOrderItemParam, null, null);
		if (inWarehouseOrderItemList.size() <= 0) {
			map.put(Constant.MESSAGE, "该产品SKU在此订单中无预报.");
			return map;
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		// 查询入库主单信息,用于更新库存
		InWarehouseRecord inWarehouseRecord = inWarehouseRecordDao.getInWarehouseRecordById(inWarehouseRecordId);
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
				itemInventoryDao.addItemInventory(warehouseId, inWarehouseRecord.getUserIdOfCustomer(), inWarehouseRecord.getBatchNo(), itemSku, itemQuantity);
			}
			return map;
		}
		InWarehouseRecordItem inWarehouseRecordItem = new InWarehouseRecordItem();
		inWarehouseRecordItem.setCreatedTime(System.currentTimeMillis());
		inWarehouseRecordItem.setInWarehouseRecordId(inWarehouseRecordId);
		inWarehouseRecordItem.setQuantity(itemQuantity);
		inWarehouseRecordItem.setRemark(itemRemark);
		inWarehouseRecordItem.setSku(itemSku);
		inWarehouseRecordItem.setUserIdOfOperator(userIdOfOperator);
		// 返回id
		long id = inWarehouseRecordItemDao.saveInWarehouseRecordItem(inWarehouseRecordItem);
		// 保存成功,添加库存
		if (id > 0) {
			itemInventoryDao.addItemInventory(warehouseId, inWarehouseRecord.getUserIdOfCustomer(), inWarehouseRecord.getBatchNo(), itemSku, itemQuantity);
		}
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
	public Map<String, Object> warehouseInterfaceEventType(String logisticsInterface, Long userIdOfCustomer, String dataDigest, String msgType, String msgId, String version) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.STATUS, Constant.FAIL);

		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);

		// logisticsInterface 转请求对象
		LogisticsEventsRequest logisticsEventsRequest = (LogisticsEventsRequest) XmlUtil.toObject(logisticsInterface, LogisticsEventsRequest.class);
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
	public Map<String, String> warehouseInterfaceValidate(String logisticsInterface, String msgSource, String dataDigest, String msgType, String msgId, String version) {
		Map<String, String> map = new HashMap<String, String>();
		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);

		map.put(Constant.STATUS, Constant.FAIL);
		// 缺少关键字段
		if (StringUtil.isNull(logisticsInterface) || StringUtil.isNull(msgSource) || StringUtil.isNull(dataDigest) || StringUtil.isNull(msgType) || StringUtil.isNull(msgId)) {
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
	public Map<String, String> saveInWarehouseRecord(String trackingNo, String remark, Long userIdOfOperator, Long warehouseId, Long inWarehouseOrderId) {
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
		inWarehouseRecord.setStatus(InWarehouseRecordStatusCode.NONE);
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
		InWarehouseRecord inWarehouseRecord = inWarehouseRecordDao.getInWarehouseRecordById(inWarehouseRecordId);
		Long inWarehouseOrderId = inWarehouseRecord.getInWarehouseOrderId();
		Long warehouseId = inWarehouseRecord.getWarehouseId();

		InWarehouseOrderItem param = new InWarehouseOrderItem();
		param.setOrderId(inWarehouseOrderId);
		List<InWarehouseOrderItem> orderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(param, null, null);
		List<Object> list = new ArrayList<Object>();
		for (InWarehouseOrderItem orderItem : orderItemList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderItem.getId());
			map.put("sku", orderItem.getSku());
			map.put("totalQuantity", orderItem.getQuantity());
			int totalReceivedQuantity = inWarehouseRecordItemDao.countInWarehouseItemSkuQuantityByOrderId(inWarehouseOrderId, orderItem.getSku());
			map.put("totalReceivedQuantity", totalReceivedQuantity);
			int unReceivedquantity = orderItem.getQuantity() - totalReceivedQuantity;
			map.put("unReceivedquantity", unReceivedquantity);
			// 根据SKU查询收货记录的物品明细
			InWarehouseRecordItem inWarehouseRecordItemParam = new InWarehouseRecordItem();
			inWarehouseRecordItemParam.setInWarehouseRecordId(inWarehouseRecordId);
			inWarehouseRecordItemParam.setSku(orderItem.getSku());
			List<InWarehouseRecordItem> recordItemList = inWarehouseRecordItemDao.findInWarehouseRecordItem(inWarehouseRecordItemParam, null, null);
			if (recordItemList != null && recordItemList.size() > 0) {
				InWarehouseRecordItem recordItem = recordItemList.get(0);
				if (recordItem.getCreatedTime() != null) {
					map.put("createdTime", DateUtil.dateConvertString(new Date(recordItem.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
				}
				map.put("receivedQuantity", recordItem.getQuantity());
				map.put("remark", recordItem.getRemark());
				if (NumberUtil.greaterThanZero(recordItem.getUserIdOfOperator())) {
					User user = userDao.getUserById(recordItem.getUserIdOfOperator());
					map.put("userLoginNameOfOperator", user.getLoginName());
				}
				Warehouse warehouse = warehouseDao.getWarehouseById(warehouseId);
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
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
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(outWarehouseOrder, moreParam, pagination);
		List<Object> list = new ArrayList<Object>();
		for (OutWarehouseOrder order : outWarehouseOrderList) {
			Map<String, Object> map = new HashMap<String, Object>();
			Long outWarehouseOrderId = order.getId();
			map.put("id", order.getId());
			if (order.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(order.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			map.put("shipwayCode", order.getShipwayCode());
			map.put("trackingNo", order.getTrackingNo());
			// 回传称重
			if (StringUtil.isEqual(order.getCallbackSendWeightIsSuccess(), Constant.Y)) {
				map.put("callbackSendWeightIsSuccess", "成功");
			} else {
				if (order.getCallbackSendWeighCount() != null && order.getCallbackSendWeighCount() > 0) {
					map.put("callbackSendWeightIsSuccess", "失败次数:" + order.getCallbackSendWeighCount());
				} else {
					map.put("callbackSendWeightIsSuccess", "未回传");
				}
			}

			// 回传出库
			if (StringUtil.isEqual(order.getCallbackSendStatusIsSuccess(), Constant.Y)) {
				map.put("callbackSendStatusIsSuccess", "成功");
			} else {
				if (order.getCallbackSendStatusCount() != null && order.getCallbackSendStatusCount() > 0) {
					map.put("callbackSendStatusIsSuccess", "失败次数:" + order.getCallbackSendStatusCount());
				} else {
					map.put("callbackSendStatusIsSuccess", "未回传");
				}
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
			map.put("printedCount", order.getPrintedCount());
			OutWarehouseOrderStatus outWarehouseOrderStatus = outWarehouseOrderStatusDao.findOutWarehouseOrderStatusByCode(order.getStatus());
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
			if (outWarehouseOrderSender != null) {
				map.put("senderName", outWarehouseOrderSender.getName());
			}
			// 物品明细(目前仅展示SKU*数量)
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
	public String warehouseInterfaceSaveInWarehouseOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException {
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
			response.setReasonDesc("客户订单号(skuStockInId)为空,订单入库失败");
			return XmlUtil.toXml(Responses.class, responses);
		}
		// 判断是否已经存在相同的跟踪单号和承运商(目前仅判断相同的跟踪单号就不可以入库)
		InWarehouseOrder param = new InWarehouseOrder();
		param.setTrackingNo(logisticsOrder.getMailNo());
		param.setCustomerReferenceNo(logisticsOrder.getSkuStockInId());
		Long validate = inWarehouseOrderDao.countInWarehouseOrder(param, null);
		if (validate >= 1) {
			response.setReason(ErrorCode.B0200_CODE);
			response.setReasonDesc("跟踪单号:" + logisticsOrder.getMailNo() + "和客户订单号(skuStockInId):" + logisticsOrder.getSkuStockInId() + "重复,订单入库失败");
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
	public String warehouseInterfaceSaveOutWarehouseOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException {
		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);

		// 取 tradeDetail 中tradeOrderId 作为客户订单号
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
		TradeOrder tradeOrder = tradeOrderList.get(0);
		// 客户订单号
		String customerReferenceNo = tradeOrder.getTradeOrderId();
		if (StringUtil.isNull(customerReferenceNo)) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeOrder对象获取tradeOrderId得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		// 交易备注,等于打印捡货单上的买家备注
		String tradeRemark = tradeOrder.getTradeRemark();

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
			// 检查客户订单号是否重复
			OutWarehouseOrder outWarehouseOrderParam = new OutWarehouseOrder();
			outWarehouseOrderParam.setCustomerReferenceNo(customerReferenceNo);
			Long count = outWarehouseOrderDao.countOutWarehouseOrder(outWarehouseOrderParam, null);
			if (count > 0) {
				response.setReason(ErrorCode.B0200_CODE);
				response.setReasonDesc("客户订单号(tradeOrderId)重复,保存失败");
				return XmlUtil.toXml(Responses.class, responses);
			}
			// 主单
			OutWarehouseOrder outWarehouseOrder = new OutWarehouseOrder();
			outWarehouseOrder.setCreatedTime(System.currentTimeMillis());
			outWarehouseOrder.setLogisticsRemark(logisticsOrder.getLogisticsRemark());
			outWarehouseOrder.setStatus(OutWarehouseOrderStatusCode.WWC);
			outWarehouseOrder.setUserIdOfCustomer(userIdOfCustomer);
			outWarehouseOrder.setWarehouseId(warehouse.getId());
			outWarehouseOrder.setTradeRemark(tradeRemark);
			ClearanceDetail clearanceDetail = eventBody.getClearanceDetail();
			if (clearanceDetail != null) {
				// 顺丰指定,出货运单号和渠道
				outWarehouseOrder.setTrackingNo(clearanceDetail.getMailNo());
				outWarehouseOrder.setShipwayCode(clearanceDetail.getCarrierCode());
			}
			// 客户订单号,用于后面客户对该出库订单进行修改,确认等,以及回传出库状态
			outWarehouseOrder.setCustomerReferenceNo(customerReferenceNo);
			// 保存主单 得到主单Id
			Long outWarehouseOrderId = outWarehouseOrderDao.saveOutWarehouseOrder(outWarehouseOrder);

			logger.info("出库订单:第" + (i + 1) + "客户订单号customerReferenceNo(tradeOrderId):" + customerReferenceNo + " 保存出库订单,得到Id:" + outWarehouseOrderId);

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
			logger.info("出库订单:第" + (i + 1) + "客户订单号customerReferenceNo(tradeOrderId):" + customerReferenceNo + " 保存出库订单明细条数:" + itemCount);
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
			logger.info("出库订单:第" + (i + 1) + "客户订单号customerReferenceNo(tradeOrderId):" + customerReferenceNo + " 保存收件人,outWarehouseOrderReceiverId:" + outWarehouseOrderReceiverId);
			// 顺丰标签附加内容
			if (clearanceDetail != null) {
				OutWarehouseOrderAdditionalSf additionalSf = new OutWarehouseOrderAdditionalSf();
				additionalSf.setCarrierCode(clearanceDetail.getCarrierCode());
				additionalSf.setCustId(clearanceDetail.getCustId());
				additionalSf.setDeliveryCode(clearanceDetail.getDeliveryCode());
				additionalSf.setMailNo(clearanceDetail.getMailNo());
				additionalSf.setOrderId(clearanceDetail.getOrderId());
				additionalSf.setOutWarehouseOrderId(outWarehouseOrderId);
				additionalSf.setPayMethod(clearanceDetail.getPayMethod());
				additionalSf.setSenderAddress(clearanceDetail.getSenderAddress());
				additionalSf.setShipperCode(clearanceDetail.getShipperCode());
				Long outWarehouseOrderAdditionalSfId = outWarehouseOrderAdditionalSfDao.saveOutWarehouseOrderAdditionalSf(additionalSf);
				logger.info("出库订单:第" + (i + 1) + "客户订单号customerReferenceNo(tradeOrderId):" + customerReferenceNo + " 保存顺丰标签附近内容人,outWarehouseOrderAdditionalSfId:" + outWarehouseOrderAdditionalSfId);
			}
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
			logger.info("出库订单:第" + (i + 1) + "客户订单号customerReferenceNo(tradeOrderId):" + customerReferenceNo + " 保存发件人,outWarehouseOrderSenderId:" + outWarehouseOrderSenderId);
		}
		response.setSuccess(Constant.TRUE);
		return XmlUtil.toXml(Responses.class, responses);
	}

	/**
	 * 获取入库记录
	 */
	@Override
	public Pagination getInWarehouseRecordData(InWarehouseRecord inWarehouseRecord, Map<String, String> moreParam, Pagination pagination) {
		List<InWarehouseRecord> inWarehouseRecordList = inWarehouseRecordDao.findInWarehouseRecord(inWarehouseRecord, moreParam, pagination);
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
			Long orderItemsize = inWarehouseOrderDao.countInWarehouseOrderItemByInWarehouseOrderId(record.getInWarehouseOrderId());
			map.put("quantity", orderItemsize);

			String status = "";
			if (record.getStatus() != null) {
				InWarehouseRecordStatus inWarehouseRecordStatus = inWarehouseRecordStatusDao.findInWarehouseRecordStatusByCode(record.getStatus());
				if (inWarehouseRecordStatus != null) {
					status = inWarehouseRecordStatus.getCn();
				}
			}
			map.put("status", status);
			// 回传成功
			if (StringUtil.isEqual(record.getCallbackIsSuccess(), Constant.Y)) {
				map.put("callbackIsSuccess", "成功");
			} else {
				if (record.getCallbackCount() != null && record.getCallbackCount() > 0) {
					map.put("callbackIsSuccess", "失败次数:" + record.getCallbackCount());
				} else {
					map.put("callbackIsSuccess", "未回传");
				}
			}
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

		int updateQuantity = 0;
		int noUpdateQuantity = 0;
		int notEnougnQuantity = 0;
		String orderIdArr[] = orderIds.split(",");
		for (String orderId : orderIdArr) {
			if (StringUtil.isNull(orderId)) {
				continue;
			}
			Long orderIdLong = Long.valueOf(orderId);
			// 查询订单的当前状态
			String oldStatus = outWarehouseOrderDao.getOutWarehouseOrderStatus(orderIdLong);
			// 如果不是等待审核状态的订单,直接跳过
			if (!StringUtil.isEqual(oldStatus, OutWarehouseOrderStatusCode.WWC)) {
				noUpdateQuantity++;
				continue;
			}

			// ==========================================================================================================================================================================
			// 分配从库位库存找产品库位,预生成打印捡货单需要的信息
			List<OutWarehouseOrderItemShelf> outWarehouseOrderItemShelfList = new ArrayList<OutWarehouseOrderItemShelf>();
			List<ItemShelfInventory> waitUpdateavAilableQuantityList = new ArrayList<ItemShelfInventory>();
			OutWarehouseOrder outWarehouseOrder = outWarehouseOrderDao.getOutWarehouseOrderById(orderIdLong);
			OutWarehouseOrderItem itemParam = new OutWarehouseOrderItem();
			itemParam.setOutWarehouseOrderId(orderIdLong);
			List<OutWarehouseOrderItem> outWarehouseOrderItemList = outWarehouseOrderItemDao.findOutWarehouseOrderItem(itemParam, null, null);
			boolean isNotEnough = false;
			for (OutWarehouseOrderItem item : outWarehouseOrderItemList) {
				// 根据出库订单物品 SKU和数量,按批次,SKU查找上架表
				List<ItemShelfInventory> itemShelfInventoryList = itemShelfInventoryDao.findItemShelfInventoryForPreOutShelf(outWarehouseOrder.getUserIdOfCustomer(), outWarehouseOrder.getWarehouseId(), item.getSku());
				int needQuantity = item.getQuantity();// 需要预下架的产品数量
				int isEnoughQuantity = needQuantity;// 循环执行完后,isEnoughQuantity大于0,代表可用库存不足,审核失败
				for (ItemShelfInventory itemShelfInventory : itemShelfInventoryList) {
					int quantity = 0;
					// 该库位的可用库存数量
					int availableQuantity = itemShelfInventory.getAvailableQuantity();
					boolean isBreak = false;
					if (availableQuantity > needQuantity) {
						// 如果此库位的可用库存大于需要的数量
						quantity = needQuantity;
						// 无需再找下一个库位,更新可用库存
						availableQuantity = availableQuantity - needQuantity;
						isBreak = true;
					} else {
						// 否则此库位的可用库存全部使用,并继续找下一库位
						// 需要下架的产品减去此货架可用库存
						needQuantity = needQuantity - availableQuantity;
						quantity = availableQuantity;
						// 更新可用库存=0
						availableQuantity = 0;
						if (needQuantity <= 0) {
							isBreak = true;
						}
					}
					isEnoughQuantity = isEnoughQuantity - quantity;
					// 执行更新可用库存
					itemShelfInventory.setAvailableQuantity(availableQuantity);
					waitUpdateavAilableQuantityList.add(itemShelfInventory);
					// 打印捡货单,记录出库订单对应的货位和物品.下次打印时 使用已经保存的货位和物品信息
					OutWarehouseOrderItemShelf outWarehouseOrderItemShelf = OutWarehouseOrderItemShelf.createOutWarehouseOrderItemShelf(orderIdLong, quantity, itemShelfInventory.getSeatCode(), item.getSku(), item.getSkuName(),
							item.getSkuNetWeight(), item.getSkuPriceCurrency(), item.getSkuUnitPrice(), itemShelfInventory.getBatchNo());
					outWarehouseOrderItemShelfList.add(outWarehouseOrderItemShelf);
					if (isBreak) {
						break;
					}
				}
				// 如果所有库位的可用库存都不足,不允许出库,审核失败
				if (isEnoughQuantity > 0) {
					isNotEnough = true;
				}
			}

			if (!isNotEnough) {
				// 更新库位的可用库存
				itemShelfInventoryDao.updateBatchItemShelfInventoryAvailableQuantity(waitUpdateavAilableQuantityList);
				// 保存打印捡货单需要的库位和物品信息
				outWarehouseOrderItemShelfDao.saveBatchOutWarehouseOrderItemShelf(outWarehouseOrderItemShelfList);
				// COE审核通过,等待打印捡货单
				int updateResult = outWarehouseOrderDao.updateOutWarehouseOrderStatus(orderIdLong, OutWarehouseOrderStatusCode.WPP);
				updateQuantity++;
			} else {
				notEnougnQuantity++;
			}
			// ==========================================================================================================================================================================
		}
		map.put(Constant.MESSAGE, "审核通过:" + updateQuantity + "个订单,  审核不通过:" + noUpdateQuantity + "个非待审核状态订单," + notEnougnQuantity + "个库存不足订单");
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
	public String warehouseInterfaceConfirmOutWarehouseOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException {
		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);
		// 取 tradeDetail 中tradeOrderId 作为客户订单号
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
		// 客户订单号
		String customerReferenceNo = tradeOrderList.get(0).getTradeOrderId();
		if (StringUtil.isNull(customerReferenceNo)) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeOrder对象获取tradeOrderId得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		// 根据客户订单号和客户帐号查找出库订单
		OutWarehouseOrder outWarehouseOrderParam = new OutWarehouseOrder();
		outWarehouseOrderParam.setUserIdOfCustomer(userIdOfCustomer);
		outWarehouseOrderParam.setCustomerReferenceNo(customerReferenceNo);
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(outWarehouseOrderParam, null, null);
		if (outWarehouseOrderList.size() <= 0) {
			response.setReason(ErrorCode.B0005_CODE);
			response.setReasonDesc("根据客户订单号(tradeOrderId)和客户帐号(msgSource)查找订单得到Null");
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

		// 获取入库订单id
		Long inWarehouseOrderId = inWarehouseRecordDao.getInWarehouseOrderIdByRecordId(recordId);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (InWarehouseRecordItem item : inWarehouseRecordItemList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("sku", item.getSku());
			// 获取sku产品名
			InWarehouseOrderItem orderItemParam = new InWarehouseOrderItem();
			orderItemParam.setOrderId(inWarehouseOrderId);
			orderItemParam.setSku(item.getSku());
			List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(orderItemParam, null, null);
			if (inWarehouseOrderItemList != null && inWarehouseOrderItemList.size() == 1) {
				map.put("skuName", inWarehouseOrderItemList.get(0).getSkuName());
				// 预报数量
				map.put("orderQuantity", inWarehouseOrderItemList.get(0).getQuantity() + "");
			} else {
				map.put("skuName", "");
				map.put("orderQuantity", "");
			}
			map.put("quantity", NumberUtil.intToString(item.getQuantity()));
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public Map<String, String> outWarehouseShippingConfirm(String coeTrackingNo, Long coeTrackingNoId, String orderIds, Long userIdOfOperator) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(orderIds)) {
			map.put(Constant.MESSAGE, "请输入出货跟踪单号再按完成出货总单!");
			return map;
		}
		// 前端用||分割多个跟踪单号
		String orderIdsArray[] = orderIds.split("\\|\\|");
		if (orderIdsArray.length < 1) {
			map.put(Constant.MESSAGE, "请输入出货跟踪单号再按完成出货总单!");
			return map;
		}
		if (StringUtil.isNull(coeTrackingNo)) {
			map.put(Constant.MESSAGE, "COE交接单号不能为空,请刷新页面重试!");
			return map;
		}

		// 迭代,检查跟踪号
		for (String orderId : orderIdsArray) {
			// 改变状态 ,发送到哲盟
			System.out.println("orderId = " + orderId);
		}

		// 标记coe单号已经使用
		trackingNoDao.usedTrackingNo(coeTrackingNoId);

		// 返回新COE单号,供下一批出库
		TrackingNo nextTrackingNo = trackingNoDao.getAvailableTrackingNoByType(TrackingNo.TYPE_COE);
		if (nextTrackingNo == null) {
			map.put(Constant.MESSAGE, "本次出货总单已完成,但COE单号不足,不能继续操作出库!");
			map.put(Constant.STATUS, "2");
			map.put("coeTrackingNo", "");
			map.put("coeTrackingNoId", "");
			return map;
		}
		trackingNoDao.lockTrackingNo(nextTrackingNo.getId());
		map.put("coeTrackingNo", nextTrackingNo.getTrackingNo());
		map.put("coeTrackingNoId", nextTrackingNo.getId().toString());

		map.put(Constant.STATUS, Constant.SUCCESS);
		map.put(Constant.MESSAGE, "完成出货总单成功,请继续下一批!");
		return map;
	}

	@Override
	public Map<String, Object> outWarehouseSubmitCustomerReferenceNo(String customerReferenceNo, Long userIdOfOperator) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.STATUS, Constant.FAIL);

		// 根据客户参考好查询出库订单customerReferenceNo
		OutWarehouseOrder param = new OutWarehouseOrder();
		param.setCustomerReferenceNo(customerReferenceNo);
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(param, null, null);
		if (outWarehouseOrderList == null || outWarehouseOrderList.size() == 0) {
			map.put(Constant.MESSAGE, "根据客户订单号找不到出库订单,请重新输入");
			return map;
		}
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderList.get(0);
		map.put("shipwayCode", outWarehouseOrder.getShipwayCode());
		map.put("trackingNo", outWarehouseOrder.getTrackingNo());
		map.put("outWarehouseOrderId", outWarehouseOrder.getId());
		OutWarehouseOrderStatus outWarehouseOrderStatus = outWarehouseOrderStatusDao.findOutWarehouseOrderStatusByCode(outWarehouseOrder.getStatus());
		if (outWarehouseOrderStatus != null) {
			map.put("outWarehouseOrderStatus", outWarehouseOrderStatus.getCn());
		}
		// 查询出库订单SKU
		OutWarehouseOrderItem outWarehouseOrderItemParam = new OutWarehouseOrderItem();
		outWarehouseOrderItemParam.setOutWarehouseOrderId(outWarehouseOrder.getId());
		List<OutWarehouseOrderItem> outWarehouseOrderItemList = outWarehouseOrderItemDao.findOutWarehouseOrderItem(outWarehouseOrderItemParam, null, null);
		map.put("outWarehouseOrderItemList", outWarehouseOrderItemList);
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public Map<String, Object> outWarehouseSubmitWeight(String customerReferenceNo, Double weight, Long userIdOfOperator) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.STATUS, Constant.FAIL);
		// 根据客户参考好查询出库订单customerReferenceNo
		OutWarehouseOrder param = new OutWarehouseOrder();
		param.setCustomerReferenceNo(customerReferenceNo);
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(param, null, null);
		if (outWarehouseOrderList == null || outWarehouseOrderList.size() == 0) {
			map.put(Constant.MESSAGE, "根据客户订单号找不到出库订单,请重新输入");
			return map;
		}
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderList.get(0);
		if (!(StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WWW) || StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WSW))) {
			map.put(Constant.MESSAGE, "出库订单当前状态不允许改变出库总重量");
			return map;
		}
		// 称重,并改变订单状态为已经称重,等待回传给顺丰
		outWarehouseOrder.setStatus(OutWarehouseOrderStatusCode.WSW);

		outWarehouseOrder.setOutWarehouseWeight(weight);
		outWarehouseOrder.setWeightCode(WeightCode.KG);
		int updateCount = outWarehouseOrderDao.updateOutWarehouseOrderWeight(outWarehouseOrder);
		if (updateCount > 0) {
			map.put(Constant.STATUS, Constant.SUCCESS);
		} else {
			map.put(Constant.MESSAGE, "执行数据库更新时失败,数据库返回更新行数:" + updateCount);
		}
		return map;
	}

	public List<Map<String, String>> checkInWarehouseRecord(InWarehouseRecord inWarehouseRecord) {
		List<InWarehouseRecord> inWarehouseRecordList = inWarehouseRecordDao.findInWarehouseRecord(inWarehouseRecord, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (InWarehouseRecord record : inWarehouseRecordList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("recordId", record.getId() + "");
			// 客户
			Long userId = record.getUserIdOfCustomer();
			User user = userDao.getUserById(userId);
			map.put("userLoginName", user.getLoginName());
			// 操作员
			Long userIdOfOperator = record.getUserIdOfOperator();
			User userOfOperator = userDao.getUserById(userIdOfOperator);
			map.put("userLoginNameOfOperator", userOfOperator.getLoginName());

			map.put("trackingNo", record.getTrackingNo());
			map.put("batchNo", record.getBatchNo());
			String status = "";
			if (record.getStatus() != null) {
				InWarehouseRecordStatus inWarehouseRecordStatus = inWarehouseRecordStatusDao.findInWarehouseRecordStatusByCode(record.getStatus());
				if (inWarehouseRecordStatus != null) {
					status = inWarehouseRecordStatus.getCn();
				}
			}
			map.put("status", status);
			String time = DateUtil.dateConvertString(new Date(record.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss);
			map.put("createdTime", time);
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 保存上架
	 */
	@Override
	public Map<String, String> saveOnShelvesItem(String itemSku, Integer itemQuantity, String seatCode, Long inWarehouseRecordId, Long userIdOfOperator) {
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
		InWarehouseRecord inWarehouseRecord = inWarehouseRecordDao.getInWarehouseRecordById(inWarehouseRecordId);
		// 检查该SKU是否存在入库订单收货中
		int countInWarehouseItemSkuQuantityByRecordId = inWarehouseRecordItemDao.countInWarehouseItemSkuQuantityByRecordId(inWarehouseRecordId, itemSku);
		if (countInWarehouseItemSkuQuantityByRecordId <= 0) {
			map.put(Constant.MESSAGE, "该产品SKU在此收货记录未找到.");
			return map;
		}
		// 先统计该入库订单收货记录中,是否包含此,SKU,数量
		int countOnShelfSkuQuantity = onShelfDao.countOnShelfSkuQuantity(inWarehouseRecordId, itemSku);
		if (countOnShelfSkuQuantity >= countInWarehouseItemSkuQuantityByRecordId) {
			map.put(Constant.MESSAGE, "该产品SKU在此收货记录已经完全上架.");
			return map;
		}
		// 计算全部已上架数
		int allQuantity = onShelfDao.countOnShelfSkuQuantity(inWarehouseRecordId, itemSku) + itemQuantity;
		if (allQuantity > countInWarehouseItemSkuQuantityByRecordId) {
			map.put(Constant.MESSAGE, "上架数量不能大于收货数量.");
			return map;
		}
		// 保存新的上架记录
		OnShelf onShelf = new OnShelf();
		onShelf.setBatchNo(inWarehouseRecord.getBatchNo());
		onShelf.setCreatedTime(System.currentTimeMillis());
		onShelf.setInWarehouseRecordId(inWarehouseRecordId);
		onShelf.setQuantity(itemQuantity);
		onShelf.setSeatCode(seatCode);
		onShelf.setSku(itemSku);
		onShelf.setTrackingNo(inWarehouseRecord.getTrackingNo());
		onShelf.setUserIdOfCustomer(inWarehouseRecord.getUserIdOfCustomer());
		onShelf.setUserIdOfOperator(userIdOfOperator);
		onShelf.setWarehouseId(inWarehouseRecord.getWarehouseId());
		Long id = onShelfDao.saveOnShelf(onShelf);
		// 添加库位库存
		int upateCount = itemShelfInventoryDao.addItemShelfInventory(inWarehouseRecord.getWarehouseId(), inWarehouseRecord.getUserIdOfCustomer(), seatCode, itemSku, itemQuantity, inWarehouseRecord.getBatchNo());
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	/**
	 * 获取上架订单数据
	 */
	@Override
	public Pagination getOnShelvesData(OnShelf onShelf, Map<String, String> moreParam, Pagination page) {
		List<OnShelf> onShelfList = onShelfDao.findOnShelf(onShelf, moreParam, page);
		List<Object> list = new ArrayList<Object>();
		for (OnShelf onShelfTemp : onShelfList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", onShelfTemp.getId());
			if (onShelfTemp.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(onShelfTemp.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			// 查询用户名
			User user = userDao.getUserById(onShelfTemp.getUserIdOfCustomer());
			map.put("userLoginNameOfCustomer", user.getLoginName());

			if (onShelfTemp.getWarehouseId() != null) {
				Warehouse warehouse = warehouseDao.getWarehouseById(onShelfTemp.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
				}
			}
			map.put("trackingNo", onShelfTemp.getTrackingNo());
			map.put("batchNo", onShelfTemp.getBatchNo());
			map.put("seatCode", onShelfTemp.getSeatCode());
			map.put("sku", onShelfTemp.getSku());
			map.put("quantity", onShelfTemp.getQuantity());
			map.put("inWarehouseRecordId", onShelfTemp.getInWarehouseRecordId());
			int receivedQuantity = inWarehouseRecordItemDao.countInWarehouseItemSkuQuantityByRecordId(onShelfTemp.getInWarehouseRecordId(), onShelfTemp.getSku());
			map.put("receivedQuantity", receivedQuantity);
			// 查询用户名
			User userOfOperator = userDao.getUserById(onShelfTemp.getUserIdOfOperator());
			map.put("userLoginNameOfOperator", userOfOperator.getLoginName());
			list.add(map);
		}
		page.total = onShelfDao.countOnShelf(onShelf, moreParam);
		page.rows = list;
		return page;
	}

	@Override
	public Pagination getInWarehouseRecordItemListData(Map<String, String> moreParam, Pagination pagination) {
		List<Map<String, Object>> recordItemList = inWarehouseRecordItemDao.getInWarehouseRecordItemListData(moreParam, pagination);
		List<Object> list = new ArrayList<Object>();
		for (Map<String, Object> recordItem : recordItemList) {
			Long warehouseId = (Long) recordItem.get("warehouse_id");
			Long userIdOfOperator = (Long) recordItem.get("user_id_of_operator");
			Long userIdOfCustomer = (Long) recordItem.get("user_id_of_customer");
			// 查询用户名
			User userOfOperator = userDao.getUserById(Long.valueOf(userIdOfOperator));
			recordItem.put("userLoginNameOfOperator", userOfOperator.getLoginName());
			// 查询用户名
			User userOfCustomer = userDao.getUserById(Long.valueOf(userIdOfCustomer));
			recordItem.put("userLoginNameOfCustomer", userOfCustomer.getLoginName());
			Warehouse warehouse = warehouseDao.getWarehouseById(Long.valueOf(warehouseId));
			if (warehouse != null) {
				recordItem.put("warehouse", warehouse.getWarehouseName());
			}

			list.add(recordItem);
		}
		pagination.total = inWarehouseRecordItemDao.countInWarehouseRecordItemList(moreParam);
		pagination.rows = list;
		return pagination;
	}

	@Override
	public Map<String, String> checkOutWarehouseOrderByCustomerReferenceNo(String customerReferenceNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		OutWarehouseOrder outWarehouseOrder = new OutWarehouseOrder();
		outWarehouseOrder.setCustomerReferenceNo(customerReferenceNo);
		outWarehouseOrder.setStatus(OutWarehouseOrderStatusCode.WOS);
		long count = outWarehouseOrderDao.countOutWarehouseOrder(outWarehouseOrder, null);
		if (count <= 0) {
			outWarehouseOrder.setStatus(null);
			count = outWarehouseOrderDao.countOutWarehouseOrder(outWarehouseOrder, null);
			// 区分是找不到出库订单还是找不到等待下架状态的出库订单
			if (count > 0) {
				map.put(Constant.MESSAGE, "根据该客户订单号找不到待捡货下架的出库订单");
			} else {
				map.put(Constant.MESSAGE, "根据该客户订单号找不到出库订单");
			}
		} else {
			// 成功
			map.put(Constant.STATUS, Constant.SUCCESS);
		}
		return map;
	}

	@Override
	public Map<String, String> submitOutShelfItems(String customerReferenceNo, String outShelfItems, Long userIdOfOperator) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(customerReferenceNo)) {
			map.put(Constant.MESSAGE, "根据该客户订单号不能为空");
			return map;
		}
		if (StringUtil.isNull(outShelfItems) || StringUtil.isNull(customerReferenceNo)) {
			map.put(Constant.MESSAGE, "下架明细不能为空");
			return map;
		}
		OutWarehouseOrder param = new OutWarehouseOrder();
		param.setCustomerReferenceNo(customerReferenceNo);
		param.setStatus(OutWarehouseOrderStatusCode.WOS);
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(param, null, null);
		if (outWarehouseOrderList == null || outWarehouseOrderList.size() < 0) {
			map.put(Constant.MESSAGE, "根据该客户订单号找不到待捡货下架的出库订单");
			return map;
		}
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderList.get(0);
		// 查找预分配的货位,对比下架是否准确
		OutWarehouseOrderItemShelf outWarehouseOrderItemShelfParam = new OutWarehouseOrderItemShelf();
		outWarehouseOrderItemShelfParam.setOutWarehouseOrderId(outWarehouseOrder.getId());
		List<OutWarehouseOrderItemShelf> outWarehouseOrderItemShelfList = outWarehouseOrderItemShelfDao.findOutWarehouseOrderItemShelf(outWarehouseOrderItemShelfParam, null, null);
		String[] outShelfItemArry = outShelfItems.split("\\|\\|");
		Pattern p = Pattern.compile("seatCode:(\\w+),sku:(\\w+),quantity:(\\w+)");
		for (String outShelfIten : outShelfItemArry) {
			Matcher m = p.matcher(outShelfIten);
			if (!m.find()) {
				continue;
			}
			String seatCode = m.group(1);
			String sku = m.group(2);
			String quantity = m.group(3);
			// 循环outWarehouseOrderItemShelfList
			int subQuantity = -1;
			for (OutWarehouseOrderItemShelf oItemShelf : outWarehouseOrderItemShelfList) {
				if (StringUtil.isEqual(seatCode, oItemShelf.getSeatCode()) || StringUtil.isEqual(sku, oItemShelf.getSku())) {
					// 库位号和sku相同,数量相减,最后outWarehouseOrderItemShelfList的内容数量都等于0
					subQuantity = oItemShelf.getQuantity() - Integer.valueOf(quantity);
					break;
				}
			}
			if (subQuantity != 0) {
				// 下架数量不准确
				map.put(Constant.MESSAGE, "下架货位,SKU,数量和捡货单上的数量不对应,请重新下架");
				return map;
			}
		}
		// 下架准确,开始执行下架
		for (OutWarehouseOrderItemShelf oItemShelf : outWarehouseOrderItemShelfList) {
			OutShelf outShelf = new OutShelf();
			outShelf.setBatchNo(oItemShelf.getBatchNo());
			outShelf.setCreatedTime(System.currentTimeMillis());
			outShelf.setCustomerReferenceNo(customerReferenceNo);
			outShelf.setOutWarehouseOrderId(outWarehouseOrder.getId());
			outShelf.setQuantity(Integer.valueOf(oItemShelf.getQuantity()));
			outShelf.setSeatCode(oItemShelf.getSeatCode());
			outShelf.setSku(oItemShelf.getSku());
			outShelf.setUserIdOfCustomer(outWarehouseOrder.getUserIdOfCustomer());
			outShelf.setUserIdOfOperator(userIdOfOperator);
			outShelf.setWarehouseId(outWarehouseOrder.getWarehouseId());
			outShelfDao.saveOutShelf(outShelf);
			// 改变库位库存(ItemShelfInventory) ,不改变仓库sku库存(出货时改变ItemInventory)

			ItemShelfInventory itemShelfInventoryParam = new ItemShelfInventory();
			itemShelfInventoryParam.setBatchNo(oItemShelf.getBatchNo());
			itemShelfInventoryParam.setSeatCode(oItemShelf.getSeatCode());
			itemShelfInventoryParam.setSku(oItemShelf.getSku());
			itemShelfInventoryParam.setWarehouseId(outWarehouseOrder.getWarehouseId());
			itemShelfInventoryParam.setUserIdOfCustomer(outWarehouseOrder.getUserIdOfCustomer());
			List<ItemShelfInventory> itemShelfInventoryList = itemShelfInventoryDao.findItemShelfInventory(itemShelfInventoryParam, null, null);
			if (itemShelfInventoryList != null && itemShelfInventoryList.size() > 0) {
				ItemShelfInventory itemShelfInventory = itemShelfInventoryList.get(0);
				int outQuantity = outShelf.getQuantity();
				itemShelfInventoryDao.updateItemShelfInventoryQuantity(itemShelfInventory.getId(), itemShelfInventory.getQuantity() - outQuantity);
				int updateCount = outWarehouseOrderDao.updateOutWarehouseOrderStatus(outWarehouseOrder.getId(), OutWarehouseOrderStatusCode.WWW);
				if (updateCount > 0) {
					map.put(Constant.STATUS, Constant.SUCCESS);
				} else {
					map.put(Constant.MESSAGE, "执行数据库更新失败,请重试保存");
				}
			} else {
				map.put(Constant.MESSAGE, "找不到库位库存记录,出库订单Id:" + outWarehouseOrder.getId());
				// 待添加事务回滚
				// throw new ServiceException("找不到库位库存记录,出库订单Id:"
				// +outWarehouseOrder.getId());
			}
		}
		return map;
	}

	/**
	 * 获取下架订单数据
	 */
	@Override
	public Pagination getOutShelvesData(OutShelf outShelf, Map<String, String> moreParam, Pagination page) {
		List<OutShelf> outShelfList = outShelfDao.findOutShelf(outShelf, moreParam, page);
		List<Object> list = new ArrayList<Object>();
		for (OutShelf outShelfTemp : outShelfList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", outShelfTemp.getId());
			if (outShelfTemp.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(outShelfTemp.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			// 查询用户名
			User user = userDao.getUserById(outShelfTemp.getUserIdOfCustomer());
			map.put("userLoginNameOfCustomer", user.getLoginName());

			if (outShelfTemp.getWarehouseId() != null) {
				Warehouse warehouse = warehouseDao.getWarehouseById(outShelfTemp.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
				}
			}
			map.put("customerReferenceNo", outShelfTemp.getCustomerReferenceNo());
			map.put("batchNo", outShelfTemp.getBatchNo());
			map.put("seatCode", outShelfTemp.getSeatCode());
			map.put("sku", outShelfTemp.getSku());
			map.put("quantity", outShelfTemp.getQuantity());
			map.put("outWarehouseOrderId", outShelfTemp.getOutWarehouseOrderId());
			// 查询用户名
			User userOfOperator = userDao.getUserById(outShelfTemp.getUserIdOfOperator());
			map.put("userLoginNameOfOperator", userOfOperator.getLoginName());
			list.add(map);
		}
		page.total = outShelfDao.countOutShelf(outShelf, moreParam);
		page.rows = list;
		return page;
	}

	@Override
	public Map<String, String> checkOutWarehouseShipping(String trackingNo, Long userIdOfOperator) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		OutWarehouseOrder param = new OutWarehouseOrder();
		param.setTrackingNo(trackingNo);
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(param, null, null);
		if (outWarehouseOrderList == null || outWarehouseOrderList.size() == 0) {
			map.put(Constant.MESSAGE, "查询不到出库订单,请重新输入出货跟踪单号");
			return map;
		}
		if (outWarehouseOrderList.size() > 1) {
			// 找到多个出库订单的情况,待处理
			map.put(Constant.MESSAGE, "查询到多个出库订单,请输入客户订单号");
			return map;
		}
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderList.get(0);
		// 只有顺丰确认出库,顺丰已确认的订单 可以出库
		if (StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WWO)) {
			map.put(Constant.STATUS, Constant.SUCCESS);
		} else if (StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.SUCCESS)) {
			map.put(Constant.MESSAGE, "出库订单当前状态已经是出库成功");
		} else if (StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WCC)) {
			map.put(Constant.MESSAGE, "出库订单当前状态是等待客户确认出库,不能出库");
		} else if (StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WSW)) {
			map.put(Constant.MESSAGE, "出库订单当前状态是等待发送出库重量给客户,不能出库");
		} else {
			map.put(Constant.MESSAGE, "出库订单当前状态不能出库");
		}
		map.put("orderId", outWarehouseOrder.getId() + "");
		return map;
	}

	@Override
	public TrackingNo getCoeTrackingNoforOutWarehouseShipping() throws ServiceException {
		TrackingNo trackingNo = trackingNoDao.getAvailableTrackingNoByType(TrackingNo.TYPE_COE);
		if (trackingNo == null) {
			return null;
		}
		// lock
		trackingNoDao.lockTrackingNo(trackingNo.getId());
		return trackingNo;
	}

	@Override
	public Map<String, String> saveInWarehouseOrderRemark(String remark, Long id) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		if (inWarehouseOrderDao.updateInWarehouseOrderRemark(id, remark) > 0) {
			map.put(Constant.STATUS, Constant.SUCCESS);
		} else {
			map.put(Constant.STATUS, Constant.FAIL);
		}
		return map;
	}
}
