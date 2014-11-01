package com.coe.wms.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordItemDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderReceiverDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderSenderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderStatusDao;
import com.coe.wms.model.unit.Weight;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus.OutWarehouseOrderStatusCode;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.pojo.api.warehouse.EventBody;
import com.coe.wms.pojo.api.warehouse.EventHeader;
import com.coe.wms.pojo.api.warehouse.EventType;
import com.coe.wms.pojo.api.warehouse.LogisticsDetail;
import com.coe.wms.pojo.api.warehouse.LogisticsEvent;
import com.coe.wms.pojo.api.warehouse.LogisticsEventsRequest;
import com.coe.wms.pojo.api.warehouse.LogisticsOrder;
import com.coe.wms.pojo.api.warehouse.Response;
import com.coe.wms.pojo.api.warehouse.Responses;
import com.coe.wms.pojo.api.warehouse.Sku;
import com.coe.wms.pojo.api.warehouse.SkuDetail;
import com.coe.wms.pojo.api.warehouse.TradeDetail;
import com.coe.wms.pojo.api.warehouse.TradeOrder;
import com.coe.wms.task.ICallCustomerTask;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.HttpUtil;
import com.coe.wms.util.StringUtil;
import com.coe.wms.util.XmlUtil;

@Component
public class CallCustomerTaskImpl implements ICallCustomerTask {

	private static final Logger logger = Logger.getLogger(CallCustomerTaskImpl.class);

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

	/**
	 * 回传入库SKU,顺丰服务名
	 */
	private final String serviceNameSkuStockin = "logistics.event.wms.skustockin.info";
	/**
	 * 回传SKU出库重量
	 */
	private final String serviceNameSendWeight = "logistics.event.wms.weight";

	/**
	 * 回传SKU出库状态
	 */
	private final String serviceNameSendStatus = "logistics.event.wms.stockout";

	/**
	 * 发送仓配入库订单信息给客户
	 */
	@Scheduled(cron = "0 0/10 8-23 * * ? ")
	@Override
	public void sendInWarehouseInfoToCustomer() {
		InWarehouseRecord param = new InWarehouseRecord();
		param.setCallbackIsSuccess(Constant.N);
		List<Long> recordIdList = inWarehouseRecordDao.findCallbackUnSuccessRecordId();
		logger.info("找到待回传SKU入库信息,总数:" + recordIdList.size());
		// 根据id 获取记录
		for (int i = 0; i < recordIdList.size(); i++) {
			Long recordId = recordIdList.get(i);
			InWarehouseRecord inWarehouseRecord = inWarehouseRecordDao.getInWarehouseRecordById(recordId);
			// 仓库
			Warehouse warehouse = warehouseDao.getWarehouseById(inWarehouseRecord.getWarehouseId());
			// 大包跟踪号
			String trackingNo = inWarehouseRecord.getTrackingNo();
			InWarehouseRecordItem recordItemParam = new InWarehouseRecordItem();
			recordItemParam.setInWarehouseRecordId(recordId);
			// 入库明细
			List<InWarehouseRecordItem> recordItemList = inWarehouseRecordItemDao.findInWarehouseRecordItem(recordItemParam, null, null);
			LogisticsEventsRequest logisticsEventsRequest = new LogisticsEventsRequest();
			LogisticsEvent logisticsEvent = new LogisticsEvent();
			// 事件头
			EventHeader eventHeader = new EventHeader();
			eventHeader.setEventType(EventType.WMS_SKU_STOCKIN_INFO);
			eventHeader.setEventTime(DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss));
			// 仓库编码
			eventHeader.setEventSource(warehouse.getWarehouseNo());
			// CP_PARTNERFLAT 为 顺丰文档指定
			eventHeader.setEventTarget("CP_PARTNERFLAT");
			logisticsEvent.setEventHeader(eventHeader);
			// 事件body
			EventBody eventBody = new EventBody();
			// 物流详情
			LogisticsDetail logisticsDetail = new LogisticsDetail();
			List<LogisticsOrder> logisticsOrders = new ArrayList<LogisticsOrder>();
			LogisticsOrder logisticsOrder = new LogisticsOrder();
			// 对应入库订单
			InWarehouseOrder inWarehouseOrderParam = new InWarehouseOrder();
			inWarehouseOrderParam.setTrackingNo(trackingNo);
			List<InWarehouseOrder> inWarehouseOrderList = inWarehouseOrderDao.findInWarehouseOrder(inWarehouseOrderParam, null, null);
			if (inWarehouseOrderList.size() > 0) {
				InWarehouseOrder inWarehouseOrder = inWarehouseOrderList.get(0);
				logisticsOrder.setLogisticsType(inWarehouseOrder.getLogisticsType());
				logisticsOrder.setCarrierCode(inWarehouseOrder.getCarrierCode());
				logisticsOrder.setSkuStockInId(inWarehouseOrder.getCustomerReferenceNo());
			}
			logisticsOrder.setMailNo(trackingNo);
			// sku 详情
			SkuDetail skuDetail = new SkuDetail();
			List<Sku> skuList = new ArrayList<Sku>();
			for (InWarehouseRecordItem recordItem : recordItemList) {
				Sku sku = new Sku();
				sku.setSkuCode(recordItem.getSku());
				sku.setSkuName(recordItem.getSku());
				// 2014-10-16 当一个入库订单多次收货,产生多个入库单时,每次回传SKU入库情况,回传所有已收货数量
				// 根据SKU和入库订单Id, 查询已经收货的SKU数量
				int count = inWarehouseRecordItemDao.countInWarehouseItemSkuQuantityByOrderId(inWarehouseRecord.getInWarehouseOrderId(),
						recordItem.getSku());
				sku.setSkuInBoundQty(count);
				sku.setSkuCheckQty(count);
				sku.setSkuBoundTime(DateUtil.dateConvertString(new Date(recordItem.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
				sku.setSkuCheckTime(DateUtil.dateConvertString(new Date(recordItem.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
				skuList.add(sku);
			}
			skuDetail.setSkus(skuList);
			logisticsOrder.setSkuDetail(skuDetail);

			logisticsOrders.add(logisticsOrder);
			logisticsDetail.setLogisticsOrders(logisticsOrders);
			eventBody.setLogisticsDetail(logisticsDetail);

			logisticsEvent.setEventBody(eventBody);
			logisticsEventsRequest.setLogisticsEvent(logisticsEvent);

			String xml = XmlUtil.toXml(LogisticsEventsRequest.class, logisticsEventsRequest);
			User user = userDao.getUserById(inWarehouseRecord.getUserIdOfCustomer());

			String msgSource = user.getOppositeMsgSource();
			List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
			basicNameValuePairs.add(new BasicNameValuePair("logistics_interface", xml));
			// 仓库编号
			basicNameValuePairs.add(new BasicNameValuePair("logistics_provider_id", warehouse.getWarehouseNo()));
			basicNameValuePairs.add(new BasicNameValuePair("msg_type", serviceNameSkuStockin));
			basicNameValuePairs.add(new BasicNameValuePair("msg_source", msgSource));
			String dataDigest = StringUtil.encoderByMd5(xml + user.getOppositeToken());
			basicNameValuePairs.add(new BasicNameValuePair("data_digest", dataDigest));
			basicNameValuePairs.add(new BasicNameValuePair("version", "1.0"));
			String url = user.getOppositeServiceUrl();
			logger.info("回传SKU入库信息: url=" + url);
			logger.info("回传SKU入库信息: logistics_interface=" + xml);
			logger.info("回传SKU入库信息: data_digest=" + dataDigest + " msg_source=" + msgSource + " msg_type=" + serviceNameSkuStockin
					+ " logistics_provider_id=" + warehouse.getWarehouseNo());
			try {
				String response = HttpUtil.postRequest(url, basicNameValuePairs);
				logger.info("顺丰返回:" + response);
				inWarehouseRecord.setCallbackCount(inWarehouseRecord.getCallbackCount() == null ? 0
						: inWarehouseRecord.getCallbackCount() + 1);
				Responses responses = (Responses) XmlUtil.toObject(response, Responses.class);
				if (responses == null) {
					logger.error("回传SKU入库信息 返回信息无法转换成Responses对象");
					continue;
				}
				List<Response> responseList = responses.getResponseItems();
				if (responseList != null && responseList.size() > 0) {
					if (StringUtil.isEqualIgnoreCase(responseList.get(0).getSuccess(), Constant.TRUE)) {
						inWarehouseRecord.setCallbackIsSuccess(Constant.Y);
						logger.info("回传SKU入库信息成功");
					} else {
						inWarehouseRecord.setCallbackIsSuccess(Constant.N);
						logger.info("回传SKU入库信息失败");
					}
				} else {
					logger.error("回传SKU入库信息 返回无指明成功与否");
				}
				// 更新入库记录的Callback 次数和成功状态
				inWarehouseRecordDao.updateInWarehouseRecordCallback(inWarehouseRecord);
			} catch (Exception e) {
				logger.error("发送回传SKU入库时发生异常:" + e.getMessage());
			}
		}
	}

	/**
	 * 回传出库称重给客户
	 */
	@Scheduled(cron = "0 0/10 8-23 * * ? ")
	@Override
	public void sendOutWarehouseWeightToCustomer() {
		List<Long> orderIdList = outWarehouseOrderDao.findCallbackSendWeightUnSuccessOrderId();
		logger.info("找到待回传SKU出库重量,订单总数:" + orderIdList.size());
		// 根据id 获取记录
		for (int i = 0; i < orderIdList.size(); i++) {
			Long outWarehouseOrderId = orderIdList.get(i);
			OutWarehouseOrder outWarehouseOrder = outWarehouseOrderDao.getOutWarehouseOrderById(outWarehouseOrderId);
			// 仓库
			Warehouse warehouse = warehouseDao.getWarehouseById(outWarehouseOrder.getWarehouseId());

			// 封装XML对象
			LogisticsEventsRequest logisticsEventsRequest = new LogisticsEventsRequest();
			LogisticsEvent logisticsEvent = new LogisticsEvent();
			// 事件头
			EventHeader eventHeader = new EventHeader();
			eventHeader.setEventType(EventType.WMS_GOODS_WEIGHT);
			eventHeader.setEventTime(DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss));
			// 仓库编码
			eventHeader.setEventSource(warehouse.getWarehouseNo());
			// CP_PARTNERFLAT 为 顺丰文档指定
			eventHeader.setEventTarget("CP_PARTNERFLAT");
			logisticsEvent.setEventHeader(eventHeader);
			// 事件body
			EventBody eventBody = new EventBody();
			// 客户订单号(顺丰交易id)
			TradeDetail tradeDetail = new TradeDetail();
			List<TradeOrder> tradeOrders = new ArrayList<TradeOrder>();
			TradeOrder tradeOrder = new TradeOrder();
			tradeOrder.setTradeOrderId(outWarehouseOrder.getCustomerReferenceNo());
			tradeOrders.add(tradeOrder);
			tradeDetail.setTradeOrders(tradeOrders);

			eventBody.setTradeDetail(tradeDetail);

			// 物流详情
			LogisticsDetail logisticsDetail = new LogisticsDetail();
			List<LogisticsOrder> logisticsOrders = new ArrayList<LogisticsOrder>();
			LogisticsOrder logisticsOrder = new LogisticsOrder();
			logisticsOrder.setLogisticsWeight(Weight.turnToG(outWarehouseOrder.getOutWarehouseWeight(), outWarehouseOrder.getWeightCode()));
			logisticsOrder.setLogisticsRemark("备注");
			logisticsOrder
					.setOccurTime(DateUtil.dateConvertString(new Date(outWarehouseOrder.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));

			logisticsOrders.add(logisticsOrder);
			logisticsDetail.setLogisticsOrders(logisticsOrders);

			eventBody.setLogisticsDetail(logisticsDetail);
			logisticsEvent.setEventBody(eventBody);
			logisticsEventsRequest.setLogisticsEvent(logisticsEvent);
			String xml = XmlUtil.toXml(LogisticsEventsRequest.class, logisticsEventsRequest);
			User user = userDao.getUserById(outWarehouseOrder.getUserIdOfCustomer());

			String msgSource = user.getOppositeMsgSource();
			List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
			basicNameValuePairs.add(new BasicNameValuePair("logistics_interface", xml));
			// 仓库编号
			basicNameValuePairs.add(new BasicNameValuePair("logistics_provider_id", warehouse.getWarehouseNo()));
			basicNameValuePairs.add(new BasicNameValuePair("msg_type", serviceNameSendWeight));
			basicNameValuePairs.add(new BasicNameValuePair("msg_source", msgSource));
			String dataDigest = StringUtil.encoderByMd5(xml + user.getOppositeToken());
			basicNameValuePairs.add(new BasicNameValuePair("data_digest", dataDigest));
			basicNameValuePairs.add(new BasicNameValuePair("version", "1.0"));
			String url = user.getOppositeServiceUrl();
			logger.info("回传SKU出库称重信息: url=" + url);
			logger.info("回传SKU出库称重信息: logistics_interface=" + xml);
			logger.info("回传SKU出库称重信息: data_digest=" + dataDigest + " msg_source=" + msgSource + " msg_type=" + serviceNameSendWeight
					+ " logistics_provider_id=" + warehouse.getWarehouseNo());
			try {
				String response = HttpUtil.postRequest(url, basicNameValuePairs);
				logger.info("顺丰返回:" + response);
				outWarehouseOrder.setCallbackSendWeighCount(outWarehouseOrder.getCallbackSendWeighCount() == null ? 1 : outWarehouseOrder
						.getCallbackSendWeighCount() + 1);
				Responses responses = (Responses) XmlUtil.toObject(response, Responses.class);
				if (responses == null) {
					logger.error("回传SKU出库称重信息 返回信息无法转换成Responses对象");
					continue;
				}
				List<Response> responseList = responses.getResponseItems();
				if (responseList != null && responseList.size() > 0) {
					if (StringUtil.isEqualIgnoreCase(responseList.get(0).getSuccess(), Constant.TRUE)) {
						outWarehouseOrder.setCallbackSendWeightIsSuccess(Constant.Y);
						outWarehouseOrder.setStatus(OutWarehouseOrderStatusCode.WCC);
						logger.info("回传SKU出库称重信息成功");
					} else {
						outWarehouseOrder.setCallbackSendWeightIsSuccess(Constant.N);
						logger.info("回传SKU出库称重信息失败");
					}
				} else {
					logger.error("回传SKU出库称重信息,返回无指明成功与否");
				}
				// 更新入库记录的Callback 次数和成功状态
				outWarehouseOrderDao.updateOutWarehouseOrderCallbackSendWeight(outWarehouseOrder);
			} catch (Exception e) {
				logger.error("回传SKU出库称重信息时发生异常:" + e.getMessage());
			}
		}
	}

	/**
	 * 回传出库状态给客户(出库的最后步骤)
	 */
	@Scheduled(cron = "0 0/15 * * * ? ")
	@Override
	public void sendOutWarehouseStatusToCustomer() {
		List<Long> orderIdList = outWarehouseOrderDao.findCallbackSendStatusUnSuccessOrderId();
		logger.info("找到待回传SKU出库状态,订单总数:" + orderIdList.size());
		// 根据id 获取记录
		for (int i = 0; i < orderIdList.size(); i++) {
			Long outWarehouseOrderId = orderIdList.get(i);
			OutWarehouseOrder outWarehouseOrder = outWarehouseOrderDao.getOutWarehouseOrderById(outWarehouseOrderId);
			// 仓库
			Warehouse warehouse = warehouseDao.getWarehouseById(outWarehouseOrder.getWarehouseId());

			// 封装XML对象
			LogisticsEventsRequest logisticsEventsRequest = new LogisticsEventsRequest();
			LogisticsEvent logisticsEvent = new LogisticsEvent();
			// 事件头
			EventHeader eventHeader = new EventHeader();
			eventHeader.setEventType(EventType.WMS_STOCKOUT_INFO);
			eventHeader.setEventTime(DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss));
			// 仓库编码
			eventHeader.setEventSource(warehouse.getWarehouseNo());
			// CP_PARTNERFLAT 为 顺丰文档指定
			eventHeader.setEventTarget("CP_PARTNERFLAT");
			logisticsEvent.setEventHeader(eventHeader);
			// 事件body
			EventBody eventBody = new EventBody();
			// 客户订单号(顺丰交易id)
			TradeDetail tradeDetail = new TradeDetail();
			List<TradeOrder> tradeOrders = new ArrayList<TradeOrder>();
			TradeOrder tradeOrder = new TradeOrder();
			tradeOrder.setTradeOrderId(outWarehouseOrder.getCustomerReferenceNo());
			tradeOrders.add(tradeOrder);
			tradeDetail.setTradeOrders(tradeOrders);

			eventBody.setTradeDetail(tradeDetail);

			// 物流详情
			LogisticsDetail logisticsDetail = new LogisticsDetail();
			List<LogisticsOrder> logisticsOrders = new ArrayList<LogisticsOrder>();
			LogisticsOrder logisticsOrder = new LogisticsOrder();
			logisticsOrder.setLogisticsWeight(Weight.turnToG(outWarehouseOrder.getOutWarehouseWeight(), outWarehouseOrder.getWeightCode()));
			logisticsOrder.setLogisticsRemark("出库,地点:" + warehouse.getCountryName() + "," + warehouse.getCity());
			// 出库
			logisticsOrder.setLogisticsCode("STOCKOUT");
			logisticsOrder
					.setOccurTime(DateUtil.dateConvertString(new Date(outWarehouseOrder.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			logisticsOrders.add(logisticsOrder);
			logisticsDetail.setLogisticsOrders(logisticsOrders);
			eventBody.setLogisticsDetail(logisticsDetail);
			logisticsEvent.setEventBody(eventBody);
			logisticsEventsRequest.setLogisticsEvent(logisticsEvent);
			String xml = XmlUtil.toXml(LogisticsEventsRequest.class, logisticsEventsRequest);
			User user = userDao.getUserById(outWarehouseOrder.getUserIdOfCustomer());

			String msgSource = user.getOppositeMsgSource();
			List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
			basicNameValuePairs.add(new BasicNameValuePair("logistics_interface", xml));
			// 仓库编号
			basicNameValuePairs.add(new BasicNameValuePair("logistics_provider_id", warehouse.getWarehouseNo()));
			basicNameValuePairs.add(new BasicNameValuePair("msg_type", serviceNameSendStatus));
			basicNameValuePairs.add(new BasicNameValuePair("msg_source", msgSource));
			String dataDigest = StringUtil.encoderByMd5(xml + user.getOppositeToken());
			basicNameValuePairs.add(new BasicNameValuePair("data_digest", dataDigest));
			basicNameValuePairs.add(new BasicNameValuePair("version", "1.0"));
			String url = user.getOppositeServiceUrl();
			logger.info("回传SKU出库状态信息: url=" + url);
			logger.info("回传SKU出库状态信息: logistics_interface=" + xml);
			logger.info("回传SKU出库状态信息: data_digest=" + dataDigest + " msg_source=" + msgSource + " msg_type=" + serviceNameSendStatus
					+ " logistics_provider_id=" + warehouse.getWarehouseNo());
			try {
				String response = HttpUtil.postRequest(url, basicNameValuePairs);
				logger.info("顺丰返回:" + response);
				outWarehouseOrder.setCallbackSendStatusCount(outWarehouseOrder.getCallbackSendStatusCount() == null ? 1 : outWarehouseOrder
						.getCallbackSendStatusCount() + 1);
				Responses responses = (Responses) XmlUtil.toObject(response, Responses.class);
				if (responses == null) {
					logger.error("回传SKU出库状态信息 返回信息无法转换成Responses对象");
					continue;
				}
				List<Response> responseList = responses.getResponseItems();
				if (responseList != null && responseList.size() > 0) {
					if (StringUtil.isEqualIgnoreCase(responseList.get(0).getSuccess(), Constant.TRUE)) {
						outWarehouseOrder.setCallbackSendStatusIsSuccess(Constant.Y);
						logger.info("回传SKU出库状态信息成功");
					} else {
						outWarehouseOrder.setCallbackSendStatusIsSuccess(Constant.N);
						logger.info("回传SKU出库状态信息失败");
					}
				} else {
					logger.error("回传SKU出库状态信息,返回无指明成功与否");
				}
				// 更新入库记录的Callback 次数和成功状态
				outWarehouseOrderDao.updateOutWarehouseOrderCallbackSendStatus(outWarehouseOrder);
			} catch (Exception e) {
				logger.error("回传SKU出库状态信息时发生异常:" + e.getMessage());
			}
		}
	}
}
