package com.coe.wms.service.fee.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.coe.wms.dao.fee.IShipwayTaxDao;
import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.fee.ShipwayTax;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.pojo.api.warehouse.EventBody;
import com.coe.wms.pojo.api.warehouse.EventHeader;
import com.coe.wms.pojo.api.warehouse.EventType;
import com.coe.wms.pojo.api.warehouse.LogisticsDetail;
import com.coe.wms.pojo.api.warehouse.LogisticsEvent;
import com.coe.wms.pojo.api.warehouse.LogisticsEventsRequest;
import com.coe.wms.pojo.api.warehouse.LogisticsOrder;
import com.coe.wms.pojo.api.warehouse.Response;
import com.coe.wms.pojo.api.warehouse.Responses;
import com.coe.wms.pojo.api.warehouse.TradeDetail;
import com.coe.wms.pojo.api.warehouse.TradeOrder;
import com.coe.wms.service.fee.IShipwayTaxService;
import com.coe.wms.task.impl.CallCustomerTaskImpl;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.HttpUtil;
import com.coe.wms.util.StringUtil;
import com.coe.wms.util.XmlUtil;

public class ShipwayTaxServiceImpl implements IShipwayTaxService {
	private static final Logger logger = Logger.getLogger(ShipwayTaxServiceImpl.class);

	@Resource(name = "warehouseDao")
	private IWarehouseDao warehouseDao;

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Resource(name = "shipwayTaxDao")
	private IShipwayTaxDao shipwayTaxDao;

	@Override
	public Map<String, String> saveAddShipwayTax(ShipwayTax shipwayTax) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> sendShipwayTax(ShipwayTax shipwayTax) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		String orderType = shipwayTax.getOrderType();
		// 仓库
		Long warehouseId = 1l;
		String customerReferenceNo = shipwayTax.getCustomerReferenceNo();
		String timeNow = DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss);
		Warehouse warehouse = warehouseDao.getWarehouseById(warehouseId);
		LogisticsEventsRequest logisticsEventsRequest = new LogisticsEventsRequest();
		LogisticsEvent logisticsEvent = new LogisticsEvent();
		// 事件头
		EventHeader eventHeader = new EventHeader();
		eventHeader.setEventType(EventType.WMS_ORDER_TAX);
		eventHeader.setEventTime(timeNow);
		eventHeader.setEventSource(warehouse.getWarehouseNo());
		eventHeader.setEventTarget("CP_PARTNERFLAT");
		logisticsEvent.setEventHeader(eventHeader);

		// 事件body
		EventBody eventBody = new EventBody();

		// 交易详情
		TradeDetail tradeDetail = new TradeDetail();
		List<TradeOrder> tradeOrders = new ArrayList<TradeOrder>();
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setTradeOrderId(customerReferenceNo);
		tradeOrder.setOccurTime(timeNow);
		// 税费
		tradeOrder.setTardeOrderTaxValue("2000");
		tradeOrder.setTardeOrderTaxValueUnit("CNF");
		// 成交金额
		tradeOrder.setTardeOrderValue("20000");
		tradeOrder.setTardeOrderValueUnit("CNF");
		tradeOrders.add(tradeOrder);
		tradeDetail.setTradeOrders(tradeOrders);
		eventBody.setTradeDetail(tradeDetail);

		// 物流详情
		LogisticsDetail logisticsDetail = new LogisticsDetail();
		List<LogisticsOrder> logisticsOrders = new ArrayList<LogisticsOrder>();
		LogisticsOrder logisticsOrder = new LogisticsOrder();
		logisticsOrders.add(logisticsOrder);
		logisticsDetail.setLogisticsOrders(logisticsOrders);
		eventBody.setLogisticsDetail(logisticsDetail);

		logisticsEvent.setEventBody(eventBody);
		logisticsEventsRequest.setLogisticsEvent(logisticsEvent);
		String xml = XmlUtil.toXml(logisticsEventsRequest);
		User user = userDao.getUserById(shipwayTax.getUserIdOfCustomer());
		String msgSource = user.getOppositeToken();
		String url = user.getOppositeServiceUrl();

		if (StringUtil.isNull(msgSource) || StringUtil.isNull(url)) {
			// 订单所属用户无回调地址,直接设置为成功
			shipwayTax.setSendCount(0);// 回调次数0,是非API回调特有
			shipwayTax.setSendIsSuccess(Constant.Y);
			shipwayTaxDao.updateShipwayTax(shipwayTax);
			return map;
		}
		List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
		basicNameValuePairs.add(new BasicNameValuePair("logistics_interface", xml));
		// 仓库编号
		basicNameValuePairs.add(new BasicNameValuePair("logistics_provider_id", warehouse.getWarehouseNo()));
		basicNameValuePairs.add(new BasicNameValuePair("msg_type", "logistics.event.wms.tax"));
		basicNameValuePairs.add(new BasicNameValuePair("msg_source", msgSource));
		String dataDigest = StringUtil.encoderByMd5(xml + user.getOppositeSecretKey());
		basicNameValuePairs.add(new BasicNameValuePair("data_digest", dataDigest));
		basicNameValuePairs.add(new BasicNameValuePair("version", "1.0"));
		logger.info("回传税费信息: url=" + url);
		logger.info("回传税费信息: logistics_interface=" + xml);
		try {
			String response = HttpUtil.postRequest(url, basicNameValuePairs);
			logger.info("顺丰返回:" + response);
			shipwayTax.setSendCount(shipwayTax.getSendCount() == null ? 1 : shipwayTax.getSendCount() + 1);
			Responses responses = (Responses) XmlUtil.toObject(response, Responses.class);
			if (responses == null) {
				logger.error("回传税费信息 返回信息无法转换成Responses对象");
				return map;
			}
			List<Response> responseList = responses.getResponseItems();
			if (responseList != null && responseList.size() > 0) {
				if (StringUtil.isEqualIgnoreCase(responseList.get(0).getSuccess(), Constant.TRUE)) {
					shipwayTax.setSendIsSuccess(Constant.Y);
					logger.info("回传税费信息成功");
				} else {
					shipwayTax.setSendIsSuccess(Constant.N);
					logger.info("回传税费信息失败");
				}
			} else {
				shipwayTax.setSendIsSuccess(Constant.N);
				logger.info("回传税费信息 返回无指明成功与否");
			}
			// 更新入库记录的Send 次数和成功状态
			shipwayTaxDao.updateShipwayTax(shipwayTax);
		} catch (Exception e) {
			logger.error("发送回传税费时发生异常:" + e.getMessage());
		}
		return map;
	}

}
