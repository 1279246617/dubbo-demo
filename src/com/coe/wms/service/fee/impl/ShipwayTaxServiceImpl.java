package com.coe.wms.service.fee.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.fee.ShipwayTax;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.pojo.api.warehouse.EventBody;
import com.coe.wms.pojo.api.warehouse.EventHeader;
import com.coe.wms.pojo.api.warehouse.EventType;
import com.coe.wms.pojo.api.warehouse.LogisticsDetail;
import com.coe.wms.pojo.api.warehouse.LogisticsEvent;
import com.coe.wms.pojo.api.warehouse.LogisticsEventsRequest;
import com.coe.wms.pojo.api.warehouse.LogisticsOrder;
import com.coe.wms.pojo.api.warehouse.TradeDetail;
import com.coe.wms.pojo.api.warehouse.TradeOrder;
import com.coe.wms.service.fee.IShipwayTaxService;
import com.coe.wms.util.DateUtil;

public class ShipwayTaxServiceImpl implements IShipwayTaxService {

	@Resource(name = "warehouseDao")
	private IWarehouseDao warehouseDao;

	@Override
	public Map<String, String> saveAddShipwayTax(ShipwayTax shipwayTax) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> sendShipwayTax(ShipwayTax shipwayTax) throws ServiceException {
		String orderType = shipwayTax.getOrderType();
		// 仓库
		Long warehouseId = null;
		String customerReferenceNo = null;
		Warehouse warehouse = warehouseDao.getWarehouseById(warehouseId);

		LogisticsEventsRequest logisticsEventsRequest = new LogisticsEventsRequest();
		LogisticsEvent logisticsEvent = new LogisticsEvent();
		// 事件头
		EventHeader eventHeader = new EventHeader();
		eventHeader.setEventType(EventType.WMS_ORDER_TAX);
		eventHeader.setEventTime(DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss));
		// 仓库编码
		eventHeader.setEventSource(warehouse.getWarehouseNo());
		// CP_PARTNERFLAT 为 顺丰文档指定
		eventHeader.setEventTarget("CP_PARTNERFLAT");
		logisticsEvent.setEventHeader(eventHeader);

		// 事件body
		EventBody eventBody = new EventBody();
		
		// 交易详情
		TradeDetail tradeDetail = new TradeDetail();
		List<TradeOrder> tradeOrders = new ArrayList<TradeOrder>();
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setTradeOrderId(customerReferenceNo);
		tradeOrders.add(tradeOrder);
//		<tradeOrderId>72364825924</tradeOrderId>
//		<occurTime>2013-08-24 08:00:00</occurTime>
//								<tardeOrderValue>1200</tardeOrderValue>
//								<tardeOrderValueUnit>CNY</tardeOrderValueUnit>
//								<tardeOrderValue>120</tardeOrderValue>
//								<tardeOrderValueUnit>CNY</tardeOrderValueUnit>

		
		tradeDetail.setTradeOrders(tradeOrders);
		eventBody.setTradeDetail(tradeDetail);

		// 物流详情
		LogisticsDetail logisticsDetail = new LogisticsDetail();
		List<LogisticsOrder> logisticsOrders = new ArrayList<LogisticsOrder>();
		LogisticsOrder logisticsOrder = new LogisticsOrder();
		logisticsOrders.add(logisticsOrder);
		logisticsDetail.setLogisticsOrders(logisticsOrders);
		eventBody.setLogisticsDetail(logisticsDetail);
		//
		logisticsEvent.setEventBody(eventBody);
		logisticsEventsRequest.setLogisticsEvent(logisticsEvent);
		return null;
	}

}
