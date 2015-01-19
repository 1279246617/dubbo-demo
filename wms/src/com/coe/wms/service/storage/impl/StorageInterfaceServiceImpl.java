package com.coe.wms.service.storage.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.product.IProductDao;
import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.ISeatDao;
import com.coe.wms.dao.warehouse.IShelfDao;
import com.coe.wms.dao.warehouse.ITrackingNoDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.dao.warehouse.shipway.IShipwayDao;
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
import com.coe.wms.dao.warehouse.storage.IOutWarehousePackageDao;
import com.coe.wms.dao.warehouse.storage.IReportDao;
import com.coe.wms.dao.warehouse.storage.IReportTypeDao;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderStatus.InWarehouseOrderStatusCode;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderAdditionalSf;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderSender;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus.OutWarehouseOrderStatusCode;
import com.coe.wms.pojo.api.warehouse.Buyer;
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
import com.coe.wms.service.storage.IStorageInterfaceService;
import com.coe.wms.util.Config;
import com.coe.wms.util.Constant;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.StringUtil;
import com.coe.wms.util.XmlUtil;

/**
 * 仓配服务
 * 
 * @author Administrator
 * 
 */
@Service("storageInterfaceService")
public class StorageInterfaceServiceImpl implements IStorageInterfaceService {

	private static final Logger logger = Logger.getLogger(StorageInterfaceServiceImpl.class);

	@Resource(name = "warehouseDao")
	private IWarehouseDao warehouseDao;

	@Resource(name = "reportTypeDao")
	private IReportTypeDao reportTypeDao;

	@Resource(name = "reportDao")
	private IReportDao reportDao;

	@Resource(name = "trackingNoDao")
	private ITrackingNoDao trackingNoDao;

	@Resource(name = "seatDao")
	private ISeatDao seatDao;

	@Resource(name = "shelfDao")
	private IShelfDao shelfDao;

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

	@Resource(name = "outWarehousePackageDao")
	private IOutWarehousePackageDao outWarehousePackageDao;

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

	@Resource(name = "productDao")
	private IProductDao productDao;

	@Resource(name = "config")
	private Config config;

	@Resource(name = "shipwayDao")
	private IShipwayDao shipwayDao;

	public List<Map<String, String>> getInWarehouseOrderItemMap(Long orderId) {
		InWarehouseOrderItem param = new InWarehouseOrderItem();
		param.setOrderId(orderId);
		List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(param, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (InWarehouseOrderItem item : inWarehouseOrderItemList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("sku", item.getSku());
			map.put("skuName", item.getSkuName());
			map.put("skuNo", item.getSkuNo() == null ? "" : item.getSkuNo());
			map.put("quantity", NumberUtil.intToString(item.getQuantity()));
			int receivedQuantity = inWarehouseRecordItemDao.countInWarehouseItemSkuQuantityByOrderId(orderId, item.getSku());
			map.put("receivedQuantity", receivedQuantity + "");
			mapList.add(map);
		}
		return mapList;
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
			map.put(Constant.MESSAGE, XmlUtil.toXml(responses));
			return map;
		}
		LogisticsEvent logisticsEvent = logisticsEventsRequest.getLogisticsEvent();
		if (logisticsEvent == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsEventsRequest对象获取LogisticsEvent对象得到Null");

			map.put(Constant.MESSAGE, XmlUtil.toXml(responses));
			return map;
		}
		// 事件头
		EventHeader eventHeader = logisticsEvent.getEventHeader();
		if (eventHeader == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsEvent对象获取EventHeader对象得到Null");

			map.put(Constant.MESSAGE, XmlUtil.toXml(responses));
			return map;
		}
		// 得到事件类型,根据事件类型,分发事件Body 到不同方法处理
		String eventType = eventHeader.getEventType();
		// 事件目标,仓库编码
		String eventTarget = eventHeader.getEventTarget();
		if (StringUtil.isNull(eventType)) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventHeader对象获取eventType得到Null");
			map.put(Constant.MESSAGE, XmlUtil.toXml(responses));
			return map;
		}

		// 事件Body
		EventBody eventBody = logisticsEvent.getEventBody();
		if (eventBody == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsEvent对象获取EventBody对象得到Null");
			map.put(Constant.MESSAGE, XmlUtil.toXml(responses));
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
			map.put(Constant.MESSAGE, XmlUtil.toXml(responses));
			return map;
		}

		// 根据msgSource 找到客户(token),找到密钥
		User user = userDao.findUserByToken(msgSource);
		if (user == null) {
			response.setReason(ErrorCode.B0008_CODE);
			response.setReasonDesc("根据msg_source 找不到客户");
			map.put(Constant.MESSAGE, XmlUtil.toXml(responses));
			return map;
		}

		// 验证内容和签名字符串
		String md5dataDigest = StringUtil.encoderByMd5(logisticsInterface + user.getSecretKey());
		if (!StringUtil.isEqual(md5dataDigest, dataDigest)) {
			// 签名错误
			response.setReason(ErrorCode.S02_CODE);
			response.setReasonDesc("收到消息签名:" + dataDigest + " 系统计算消息签名:" + md5dataDigest);
			map.put(Constant.MESSAGE, XmlUtil.toXml(responses));
			return map;
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		map.put(Constant.USER_ID_OF_CUSTOMER, "" + user.getId());
		return map;
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
			return XmlUtil.toXml(responses);
		}
		List<LogisticsOrder> logisticsOrders = logisticsDetail.getLogisticsOrders();
		if (logisticsOrders == null || logisticsOrders.size() == 0) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsDetail对象获取logisticsOrders对象得到Null");
			return XmlUtil.toXml(responses);
		}

		Warehouse warehouse = warehouseDao.getWarehouseByNo(warehouseNo);
		if (warehouse == null) {
			response.setReason(ErrorCode.B0003_CODE);
			response.setReasonDesc("根据仓库编号(eventTarget)获取仓库得到Null");
			return XmlUtil.toXml(responses);
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
			return XmlUtil.toXml(responses);
		}
		if (StringUtil.isNull(logisticsOrder.getSkuStockInId())) {
			response.setReason(ErrorCode.S13_CODE);
			response.setReasonDesc("客户订单号(skuStockInId)为空,订单入库失败");
			return XmlUtil.toXml(responses);
		}
		// 判断是否已经存在相同的跟踪单号和承运商(目前仅判断相同的跟踪单号就不可以入库)
		InWarehouseOrder param = new InWarehouseOrder();
		param.setTrackingNo(logisticsOrder.getMailNo());
		param.setCustomerReferenceNo(logisticsOrder.getSkuStockInId());
		Long validate = inWarehouseOrderDao.countInWarehouseOrder(param, null);
		if (validate >= 1) {
			response.setReason(ErrorCode.B0200_CODE);
			response.setReasonDesc("跟踪单号:" + logisticsOrder.getMailNo() + "和客户订单号(skuStockInId):" + logisticsOrder.getSkuStockInId() + "重复,订单入库失败");
			return XmlUtil.toXml(responses);
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
			return XmlUtil.toXml(responses);
		}
		List<Sku> skuList = skuDetail.getSkus();
		if (skuList == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("SkuDetail对象获取Skus对象得到Null");
			return XmlUtil.toXml(responses);
		}
		List<InWarehouseOrderItem> inwarehouseOrderItemList = new ArrayList<InWarehouseOrderItem>();
		for (int j = 0; j < skuList.size(); j++) {
			Sku sku = skuList.get(j);
			if (sku.getSkuQty() == null || (StringUtil.isNull(sku.getSkuCode()) && StringUtil.isNull(sku.getSkuId()))) {
				continue;
			}
			String skuCode = sku.getSkuCode();
			String skuId = sku.getSkuId();
			if (StringUtil.isNotNull(skuCode)) {
				skuCode = skuCode.trim();
			}
			if (StringUtil.isNotNull(skuId)) {
				skuId = skuId.trim();
			}
			InWarehouseOrderItem inwarehouseOrderItem = new InWarehouseOrderItem();
			inwarehouseOrderItem.setSku(skuCode);// 商品条码
			inwarehouseOrderItem.setSkuNo(skuId);// 商品SKU
			inwarehouseOrderItem.setQuantity(sku.getSkuQty());
			inwarehouseOrderItem.setSkuName(sku.getSkuName());
			inwarehouseOrderItem.setSkuRemark(sku.getSkuRemark());
			inwarehouseOrderItem.setSpecification(sku.getSpecification());
			// 入库主单的id
			inwarehouseOrderItemList.add(inwarehouseOrderItem);
		}
		// 保存入库订单得到入库订单id
		Long orderId = inWarehouseOrderDao.saveInWarehouseOrder(inWarehouseOrder);
		int count = inWarehouseOrderItemDao.saveBatchInWarehouseOrderItemWithOrderId(inwarehouseOrderItemList, orderId);
		logger.info("入库主单id:" + orderId + " 入库明细数量:" + count);
		response.setSuccess(Constant.TRUE);
		return XmlUtil.toXml(responses);
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
			return XmlUtil.toXml(responses);
		}
		List<TradeOrder> tradeOrderList = tradeDetail.getTradeOrders();
		if (tradeOrderList == null || tradeOrderList.size() == 0) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeDetail对象获取TradeOrders对象得到Null");
			return XmlUtil.toXml(responses);
		}
		TradeOrder tradeOrder = tradeOrderList.get(0);
		// 客户订单号
		String customerReferenceNo = tradeOrder.getTradeOrderId();
		if (StringUtil.isNull(customerReferenceNo)) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeOrder对象获取tradeOrderId得到Null");
			return XmlUtil.toXml(responses);
		}
		// 交易备注,等于打印捡货单上的买家备注
		String tradeRemark = tradeOrder.getTradeRemark();
		Buyer buyer = tradeOrder.getBuyer();
		if (buyer == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeOrder对象获取Buyer对象得到Null");
			return XmlUtil.toXml(responses);
		}

		// 出库订单发件人信息
		LogisticsDetail logisticsDetail = eventBody.getLogisticsDetail();
		if (logisticsDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventBody对象获取LogisticsDetail对象得到Null");
			return XmlUtil.toXml(responses);
		}
		List<LogisticsOrder> logisticsOrders = logisticsDetail.getLogisticsOrders();
		if (logisticsOrders == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsDetail对象获取LogisticsOrders对象得到Null");
			return XmlUtil.toXml(responses);
		}
		Warehouse warehouse = warehouseDao.getWarehouseByNo(warehouseNo);
		if (warehouse == null) {
			response.setReason(ErrorCode.B0003_CODE);
			response.setReasonDesc("根据仓库编号(eventTarget)获取仓库得到Null");
			return XmlUtil.toXml(responses);
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
				return XmlUtil.toXml(responses);
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
				String skuCode = sku.getSkuCode();
				String skuId = sku.getSkuId();
				if (StringUtil.isNotNull(skuCode)) {
					skuCode = skuCode.trim();
				}
				if (StringUtil.isNotNull(skuId)) {
					skuId = skuId.trim();
				}
				OutWarehouseOrderItem outWarehouseOrderItem = new OutWarehouseOrderItem();
				outWarehouseOrderItem.setQuantity(sku.getSkuQty());
				outWarehouseOrderItem.setRemark(sku.getSkuRemark());
				outWarehouseOrderItem.setSku(skuCode);
				outWarehouseOrderItem.setSkuNo(skuId);
				outWarehouseOrderItem.setSpecification(sku.getSpecification());
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
			outWarehouseOrderReceiver.setAddressLine1(buyer.getStreetAddress());
			outWarehouseOrderReceiver.setCity(buyer.getCity());
			outWarehouseOrderReceiver.setCountryCode(OutWarehouseOrderReceiver.CN);
			outWarehouseOrderReceiver.setCountryName(OutWarehouseOrderReceiver.CN_VALUE);
			outWarehouseOrderReceiver.setCounty(buyer.getDistrict());
			outWarehouseOrderReceiver.setEmail(buyer.getEmail());
			outWarehouseOrderReceiver.setName(buyer.getName());
			outWarehouseOrderReceiver.setPhoneNumber(buyer.getPhone());
			outWarehouseOrderReceiver.setPostalCode(buyer.getZipCode());
			outWarehouseOrderReceiver.setStateOrProvince(buyer.getProvince());
			outWarehouseOrderReceiver.setMobileNumber(buyer.getMobile());
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
				logger.info("出库订单:第" + (i + 1) + "客户订单号customerReferenceNo(tradeOrderId):" + customerReferenceNo + " 保存顺丰标签内容,outWarehouseOrderAdditionalSfId:" + outWarehouseOrderAdditionalSfId);
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
		return XmlUtil.toXml(responses);
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
			return XmlUtil.toXml(responses);
		}
		List<TradeOrder> tradeOrderList = tradeDetail.getTradeOrders();
		if (tradeOrderList == null || tradeOrderList.size() == 0) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeDetail对象获取TradeOrders对象得到Null");
			return XmlUtil.toXml(responses);
		}
		// 客户订单号
		String customerReferenceNo = tradeOrderList.get(0).getTradeOrderId();
		if (StringUtil.isNull(customerReferenceNo)) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeOrder对象获取tradeOrderId得到Null");
			return XmlUtil.toXml(responses);
		}
		// 根据客户订单号和客户帐号查找出库订单
		OutWarehouseOrder outWarehouseOrderParam = new OutWarehouseOrder();
		outWarehouseOrderParam.setUserIdOfCustomer(userIdOfCustomer);
		outWarehouseOrderParam.setCustomerReferenceNo(customerReferenceNo);
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(outWarehouseOrderParam, null, null);
		if (outWarehouseOrderList.size() <= 0) {
			response.setReason(ErrorCode.B0005_CODE);
			response.setReasonDesc("根据客户订单号(tradeOrderId)和客户帐号(msgSource)查找订单得到Null");
			return XmlUtil.toXml(responses);
		}
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderList.get(0);
		// 只有当前状态 是等待顺丰确认的订单 才允许处理
		if (!StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WCC)) {
			response.setReason(ErrorCode.B0100_CODE);
			response.setReasonDesc("出库订单当前状态非等待客户确认状态,不能修改");
			return XmlUtil.toXml(responses);
		}
		int count = outWarehouseOrderDao.updateOutWarehouseOrderStatus(outWarehouseOrder.getId(), OutWarehouseOrderStatusCode.WWO);
		logger.info("确认出库成功: 更新状态影响行数=" + count);

		response.setSuccess(Constant.TRUE);
		return XmlUtil.toXml(responses);
	}

	@Override
	public String warehouseInterfaceCancelOutWarehouseOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException {
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
			return XmlUtil.toXml(responses);
		}
		List<TradeOrder> tradeOrderList = tradeDetail.getTradeOrders();
		if (tradeOrderList == null || tradeOrderList.size() == 0) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeDetail对象获取TradeOrders对象得到Null");
			return XmlUtil.toXml(responses);
		}
		// 客户订单号
		String customerReferenceNo = tradeOrderList.get(0).getTradeOrderId();
		if (StringUtil.isNull(customerReferenceNo)) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeOrder对象获取tradeOrderId得到Null");
			return XmlUtil.toXml(responses);
		}
		// 根据客户订单号和客户帐号查找出库订单
		OutWarehouseOrder outWarehouseOrderParam = new OutWarehouseOrder();
		outWarehouseOrderParam.setUserIdOfCustomer(userIdOfCustomer);
		outWarehouseOrderParam.setCustomerReferenceNo(customerReferenceNo);
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(outWarehouseOrderParam, null, null);
		if (outWarehouseOrderList.size() <= 0) {
			response.setReason(ErrorCode.B0005_CODE);
			response.setReasonDesc("根据客户订单号(tradeOrderId)和客户帐号(msgSource)查找订单得到Null");
			return XmlUtil.toXml(responses);
		}
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderList.get(0);
		// 只有当前状态 是等待顺丰确认的订单 才允许处理
		if (StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.SUCCESS)) {
			response.setReason(ErrorCode.B0100_CODE);
			response.setReasonDesc("出库订单已经完成出库,不能取消");
			return XmlUtil.toXml(responses);
		}
		if (!StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WWC)) {
			response.setReason(ErrorCode.B0100_CODE);
			response.setReasonDesc("出库订单已经开始出库,不能取消");
			return XmlUtil.toXml(responses);
		}
		int count = outWarehouseOrderDao.deleteOutWarehouseOrder(outWarehouseOrder.getId());
		logger.info("取消出库成功: 影响行数=" + count);
		response.setSuccess(Constant.TRUE);
		return XmlUtil.toXml(responses);
	}
}
