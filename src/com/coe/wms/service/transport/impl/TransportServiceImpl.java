package com.coe.wms.service.transport.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.etk.api.Client;
import com.coe.etk.api.request.Receiver;
import com.coe.etk.api.request.Sender;
import com.coe.etk.api.response.ResponseItems;
import com.coe.wms.dao.product.IProductDao;
import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.ISeatDao;
import com.coe.wms.dao.warehouse.IShelfDao;
import com.coe.wms.dao.warehouse.ITrackingNoDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.dao.warehouse.shipway.IShipwayApiAccountDao;
import com.coe.wms.dao.warehouse.shipway.IShipwayDao;
import com.coe.wms.dao.warehouse.storage.IOnShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutShelfDao;
import com.coe.wms.dao.warehouse.storage.IReportDao;
import com.coe.wms.dao.warehouse.storage.IReportTypeDao;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillDao;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillItemDao;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillOnShelfDao;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillStatusDao;
import com.coe.wms.dao.warehouse.transport.IOrderAdditionalSfDao;
import com.coe.wms.dao.warehouse.transport.IOrderDao;
import com.coe.wms.dao.warehouse.transport.IOrderReceiverDao;
import com.coe.wms.dao.warehouse.transport.IOrderSenderDao;
import com.coe.wms.dao.warehouse.transport.IOrderStatusDao;
import com.coe.wms.dao.warehouse.transport.IOutWarehousePackageDao;
import com.coe.wms.dao.warehouse.transport.IOutWarehousePackageItemDao;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.unit.Currency.CurrencyCode;
import com.coe.wms.model.unit.Weight.WeightCode;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.TrackingNo;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.shipway.Shipway;
import com.coe.wms.model.warehouse.shipway.Shipway.ShipwayCode;
import com.coe.wms.model.warehouse.shipway.ShipwayApiAccount;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.model.warehouse.storage.record.OutWarehouseRecord;
import com.coe.wms.model.warehouse.transport.FirstWaybill;
import com.coe.wms.model.warehouse.transport.FirstWaybillItem;
import com.coe.wms.model.warehouse.transport.FirstWaybillOnShelf;
import com.coe.wms.model.warehouse.transport.FirstWaybillStatus;
import com.coe.wms.model.warehouse.transport.FirstWaybillStatus.FirstWaybillStatusCode;
import com.coe.wms.model.warehouse.transport.Order;
import com.coe.wms.model.warehouse.transport.OrderAdditionalSf;
import com.coe.wms.model.warehouse.transport.OrderReceiver;
import com.coe.wms.model.warehouse.transport.OrderSender;
import com.coe.wms.model.warehouse.transport.OrderStatus;
import com.coe.wms.model.warehouse.transport.OrderStatus.OrderStatusCode;
import com.coe.wms.model.warehouse.transport.OutWarehousePackage;
import com.coe.wms.model.warehouse.transport.OutWarehousePackageItem;
import com.coe.wms.pojo.api.warehouse.Buyer;
import com.coe.wms.pojo.api.warehouse.ClearanceDetail;
import com.coe.wms.pojo.api.warehouse.ErrorCode;
import com.coe.wms.pojo.api.warehouse.EventBody;
import com.coe.wms.pojo.api.warehouse.Item;
import com.coe.wms.pojo.api.warehouse.LogisticsDetail;
import com.coe.wms.pojo.api.warehouse.LogisticsOrder;
import com.coe.wms.pojo.api.warehouse.Response;
import com.coe.wms.pojo.api.warehouse.Responses;
import com.coe.wms.pojo.api.warehouse.SenderDetail;
import com.coe.wms.pojo.api.warehouse.TradeDetail;
import com.coe.wms.pojo.api.warehouse.TradeOrder;
import com.coe.wms.service.transport.ITransportService;
import com.coe.wms.util.Config;
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
@Service("transportService")
public class TransportServiceImpl implements ITransportService {

	private static final Logger logger = Logger.getLogger(TransportServiceImpl.class);

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

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Resource(name = "productDao")
	private IProductDao productDao;

	@Resource(name = "config")
	private Config config;

	@Resource(name = "orderAdditionalSfDao")
	private IOrderAdditionalSfDao orderAdditionalSfDao;

	@Resource(name = "orderDao")
	private IOrderDao orderDao;

	@Resource(name = "orderReceiverDao")
	private IOrderReceiverDao orderReceiverDao;

	@Resource(name = "orderSenderDao")
	private IOrderSenderDao orderSenderDao;

	@Resource(name = "orderStatusDao")
	private IOrderStatusDao orderStatusDao;

	@Resource(name = "firstWaybillItemDao")
	private IFirstWaybillItemDao firstWaybillItemDao;

	@Resource(name = "firstWaybillDao")
	private IFirstWaybillDao firstWaybillDao;

	@Resource(name = "firstWaybillStatusDao")
	private IFirstWaybillStatusDao firstWaybillStatusDao;

	@Resource(name = "firstWaybillOnShelfDao")
	private IFirstWaybillOnShelfDao firstWaybillOnShelfDao;

	@Resource(name = "transportPackageDao")
	private IOutWarehousePackageDao transportPackageDao;

	@Resource(name = "transportPackageItemDao")
	private IOutWarehousePackageItemDao transportPackageItemDao;

	@Resource(name = "shipwayDao")
	private IShipwayDao shipwayDao;

	@Resource(name = "shipwayApiAccountDao")
	private IShipwayApiAccountDao shipwayApiAccountDao;

	@Override
	public String warehouseInterfaceSaveTransportOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException {
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
		String tradeType = tradeDetail.getTradeType();// 交易类型. 用于区分流连还是海淘
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
		// 小包
		LogisticsDetail logisticsDetail = eventBody.getLogisticsDetail();
		if (logisticsDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventBody对象获取LogisticsDetail对象得到Null");
			return XmlUtil.toXml(responses);
		}
		ClearanceDetail clearanceDetail = eventBody.getClearanceDetail();
		if (clearanceDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventBody对象获取ClearanceDetail对象得到Null");
			return XmlUtil.toXml(responses);
		}
		List<LogisticsOrder> logisticsOrders = logisticsDetail.getLogisticsOrders();
		if (logisticsOrders == null || logisticsOrders.size() == 0) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsDetail对象获取LogisticsOrders对象得到Null");
			return XmlUtil.toXml(responses);
		}
		// 2014-12-09顺丰确认 多个 logisticsOrder的SenderDetail 是一样的. 所以仅需保存一份
		LogisticsOrder logisticsOrderFirst = logisticsOrders.get(0);
		SenderDetail senderDetail = logisticsOrderFirst.getSenderDetail();
		if (senderDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsOrders对象获取SenderDetail对象得到Null");
			return XmlUtil.toXml(responses);
		}
		Warehouse warehouse = warehouseDao.getWarehouseByNo(warehouseNo);
		if (warehouse == null) {
			response.setReason(ErrorCode.B0003_CODE);
			response.setReasonDesc("根据仓库编号(eventTarget)获取仓库得到Null");
			return XmlUtil.toXml(responses);
		}
		Long warehouseId = warehouse.getId();
		// 检测大包是否重复
		com.coe.wms.model.warehouse.transport.Order orderParam = new com.coe.wms.model.warehouse.transport.Order();
		orderParam.setCustomerReferenceNo(customerReferenceNo);
		Long count = orderDao.countOrder(orderParam, null);
		if (count > 0) {
			response.setReason(ErrorCode.B0200_CODE);
			response.setReasonDesc("客户订单号(tradeOrderId)重复,保存失败");
			return XmlUtil.toXml(responses);
		}
		// 创建大包
		Order order = new Order();
		order.setTradeType(tradeType);
		order.setCreatedTime(System.currentTimeMillis());
		order.setCustomerReferenceNo(customerReferenceNo);
		order.setTradeRemark(tradeRemark);
		order.setStatus(OrderStatusCode.WWC);
		order.setUserIdOfCustomer(userIdOfCustomer);
		order.setWarehouseId(warehouseId);
		// 顺丰指定,出货运单号和渠道
		order.setTrackingNo(clearanceDetail.getMailNo());
		order.setShipwayCode(clearanceDetail.getCarrierCode());
		if (logisticsOrders.size() == 1) {
			// 直接转运
			order.setTransportType(Order.TRANSPORT_TYPE_Z);
		} else {
			// 集货转运
			order.setTransportType(Order.TRANSPORT_TYPE_J);
		}
		Long orderId = orderDao.saveOrder(order);// 保存大包,得到大包id
		// // 顺丰标签附加内容
		OrderAdditionalSf additionalSf = new OrderAdditionalSf();
		// 顺丰指定,出货运单号和渠道
		additionalSf.setCarrierCode(clearanceDetail.getCarrierCode());
		additionalSf.setCustId(clearanceDetail.getCustId());
		additionalSf.setDeliveryCode(clearanceDetail.getDeliveryCode());
		additionalSf.setMailNo(clearanceDetail.getMailNo());
		additionalSf.setPayMethod(clearanceDetail.getPayMethod());
		additionalSf.setSenderAddress(clearanceDetail.getSenderAddress());
		additionalSf.setShipperCode(clearanceDetail.getShipperCode());
		additionalSf.setOrderId(orderId);
		orderAdditionalSfDao.saveOrderAdditionalSf(additionalSf);

		// 大包收件人
		OrderReceiver orderReceiver = new OrderReceiver();
		orderReceiver.setAddressLine1(buyer.getStreetAddress());
		orderReceiver.setCity(buyer.getCity());
		orderReceiver.setCountryCode(OutWarehouseOrderReceiver.CN);
		orderReceiver.setCountryName(OutWarehouseOrderReceiver.CN_VALUE);
		orderReceiver.setCounty(buyer.getDistrict());
		orderReceiver.setEmail(buyer.getEmail());
		orderReceiver.setName(buyer.getName());
		orderReceiver.setPhoneNumber(buyer.getPhone());
		orderReceiver.setPostalCode(buyer.getZipCode());
		orderReceiver.setStateOrProvince(buyer.getProvince());
		orderReceiver.setMobileNumber(buyer.getMobile());
		orderReceiver.setOrderId(orderId);
		orderReceiverDao.saveOrderReceiver(orderReceiver); // 保存收件人

		// 发件人信息
		OrderSender orderSender = new OrderSender();
		orderSender.setAddressLine1(senderDetail.getStreetAddress());
		orderSender.setCity(senderDetail.getCity());
		orderSender.setCountryCode(senderDetail.getCountry());
		orderSender.setCountryName(senderDetail.getCountry());
		orderSender.setCounty(senderDetail.getDistrict());
		orderSender.setEmail(senderDetail.getEmail());
		orderSender.setName(senderDetail.getName());
		orderSender.setPhoneNumber(senderDetail.getPhone());
		orderSender.setPostalCode(senderDetail.getZipCode());
		orderSender.setStateOrProvince(senderDetail.getProvince());
		orderSender.setMobileNumber(senderDetail.getMobile());
		orderSender.setOrderId(orderId);
		orderSenderDao.saveOrderSender(orderSender);
		// tradeOrder的商品详情
		List<Item> items = tradeOrder.getItems();
		// 保存小包
		for (int i = 0; i < logisticsOrders.size(); i++) {
			LogisticsOrder logisticsOrder = logisticsOrders.get(i);
			if (logisticsOrder == null) {
				continue;
			}
			// 小包, 到货运单
			FirstWaybill firstWaybill = new FirstWaybill();
			firstWaybill.setOrderId(orderId);
			firstWaybill.setCarrierCode(logisticsOrder.getCarrierCode());
			firstWaybill.setCreatedTime(System.currentTimeMillis());
			firstWaybill.setPoNo(logisticsOrder.getPoNo());
			firstWaybill.setTrackingNo(logisticsOrder.getMailNo());
			firstWaybill.setStatus(FirstWaybillStatusCode.WWR);
			firstWaybill.setUserIdOfCustomer(userIdOfCustomer);
			firstWaybill.setWarehouseId(warehouseId);
			firstWaybill.setRemark(logisticsOrder.getLogisticsRemark());
			firstWaybill.setOrderId(orderId);
			// 转运类型
			firstWaybill.setTransportType(order.getTransportType());
			Long firstWaybillId = firstWaybillDao.saveFirstWaybill(firstWaybill);
			// 小包裹物品id
			String itemsIncluded = logisticsOrder.getItemsIncluded();
			if (StringUtil.isNull(itemsIncluded)) {
				continue;
			}
			String[] itemIds = itemsIncluded.split(",");
			for (String itemIncluded : itemIds) {
				for (Item item : items) {
					String itemId = item.getItemId();
					if (!StringUtil.isEqualIgnoreCase(itemIncluded, itemId)) {
						continue;
					}
					FirstWaybillItem firstWaybillItem = new FirstWaybillItem();
					firstWaybillItem.setSku(itemId);
					firstWaybillItem.setQuantity(item.getItemQuantity() == null ? 1 : item.getItemQuantity());
					firstWaybillItem.setFirstWaybillId(firstWaybillId);
					firstWaybillItem.setOrderId(orderId);
					firstWaybillItem.setSkuName(item.getItemName());
					firstWaybillItem.setRemark(item.getItemRemark());
					if (NumberUtil.isDecimal(item.getItemUnitPrice()) || NumberUtil.isNumberic(item.getItemUnitPrice())) {
						firstWaybillItem.setSkuUnitPrice(Double.valueOf(item.getItemUnitPrice()));
					}
					firstWaybillItem.setSkuPriceCurrency(CurrencyCode.CNF);
					firstWaybillItem.setSpecification(item.getSpecification());
					if (NumberUtil.isDecimal(item.getNetWeight()) || NumberUtil.isNumberic(item.getNetWeight())) {
						firstWaybillItem.setSkuNetWeight(Double.valueOf(item.getNetWeight()));
					}
					firstWaybillItemDao.saveFirstWaybillItem(firstWaybillItem);
				}
			}
		}
		response.setSuccess(Constant.TRUE);
		return XmlUtil.toXml(responses);
	}

	@Override
	public String warehouseInterfaceConfirmTransportOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException {
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
		// 根据客户订单号 (traderOrderId)查找转运订单,修改WWO:待出库操作
		Order param = new Order();
		param.setCustomerReferenceNo(customerReferenceNo);
		List<Order> orderList = orderDao.findOrder(param, null, null);
		if (orderList == null || orderList.size() == 0) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeOrder对象获取tradeOrderId得到Null");
			return XmlUtil.toXml(responses);
		}
		Order order = orderList.get(0);
		if (!StringUtil.isEqual(order.getStatus(), OrderStatusCode.WCC)) {
			response.setReason(ErrorCode.B0100_CODE);
			response.setReasonDesc("订单当前状态非待客户核重状态");
			return XmlUtil.toXml(responses);
		}
		orderDao.updateOrderStatus(order.getId(), OrderStatusCode.WWO);
		response.setSuccess(Constant.TRUE);
		return XmlUtil.toXml(responses);
	}

	@Override
	public List<Shipway> findAllShipway() throws ServiceException {
		return shipwayDao.findAllShipway();
	}
}
