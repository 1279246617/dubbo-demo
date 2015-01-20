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
import com.coe.wms.dao.warehouse.transport.IOrderPackageDao;
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
import com.coe.wms.model.warehouse.shipway.Shipway.ShipwayCode;
import com.coe.wms.model.warehouse.shipway.ShipwayApiAccount;
import com.coe.wms.model.warehouse.transport.FirstWaybill;
import com.coe.wms.model.warehouse.transport.FirstWaybillItem;
import com.coe.wms.model.warehouse.transport.FirstWaybillOnShelf;
import com.coe.wms.model.warehouse.transport.FirstWaybillStatus.FirstWaybillStatusCode;
import com.coe.wms.model.warehouse.transport.Order;
import com.coe.wms.model.warehouse.transport.OrderPackageStatus.OrderPackageStatusCode;
import com.coe.wms.model.warehouse.transport.OrderReceiver;
import com.coe.wms.model.warehouse.transport.OrderSender;
import com.coe.wms.model.warehouse.transport.OrderStatus;
import com.coe.wms.model.warehouse.transport.OrderStatus.OrderStatusCode;
import com.coe.wms.model.warehouse.transport.OutWarehousePackage;
import com.coe.wms.model.warehouse.transport.OutWarehousePackageItem;
import com.coe.wms.service.transport.IOrderService;
import com.coe.wms.util.Config;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;

/**
 * 仓配服务
 * 
 * @author Administrator
 * 
 */
@Service("orderService")
public class OrderServiceImpl implements IOrderService {

	private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

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

	@Resource(name = "orderPackageDao")
	private IOrderPackageDao orderPackageDao;

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
	public List<OrderStatus> findAllOrderStatus() throws ServiceException {
		return orderStatusDao.findAllOrderStatus();
	}

	/**
	 * 获取转运订单列表数据
	 */
	@Override
	public Pagination getOrderData(com.coe.wms.model.warehouse.transport.Order param, Map<String, String> moreParam, Pagination pagination) {
		List<com.coe.wms.model.warehouse.transport.Order> orderList = orderDao.findOrder(param, moreParam, pagination);
		List<Object> list = new ArrayList<Object>();
		for (com.coe.wms.model.warehouse.transport.Order order : orderList) {
			Map<String, Object> map = new HashMap<String, Object>();
			Long orderId = order.getId();
			map.put("id", orderId);
			if (order.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(order.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			map.put("shipwayCode", order.getShipwayCode());
			if (StringUtil.isNotNull(order.getCheckResult())) {
				if (StringUtil.isEqual(order.getCheckResult(), "SECURITY")) {
					map.put("checkResult", "拒收(安全不通过)");
				} else if (StringUtil.isEqual(order.getCheckResult(), "OTHER_REASON")) {
					map.put("checkResult", "拒收(其他不通过)");
				} else if (StringUtil.isEqual(order.getCheckResult(), "SUCCESS")) {
					map.put("checkResult", "接件(审核已通过)");
				} else {
					map.put("checkResult", order.getCheckResult());
				}
			} else {
				map.put("checkResult", "");
			}
			// 回传审核
			if (StringUtil.isEqual(order.getCallbackSendCheckIsSuccess(), Constant.Y)) {
				map.put("callbackSendCheckIsSuccess", "成功");
			} else {
				if (order.getCallbackSendCheckCount() != null && order.getCallbackSendCheckCount() > 0) {
					map.put("callbackSendCheckIsSuccess", "失败次数:" + order.getCallbackSendCheckCount());
				} else {
					map.put("callbackSendCheckIsSuccess", "未回传");
				}
			}
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
			if (StringUtil.isEqual(Order.TRANSPORT_TYPE_J, order.getTransportType())) {
				map.put("transportType", "集货转运");
			}
			if (StringUtil.isEqual(Order.TRANSPORT_TYPE_Z, order.getTransportType())) {
				map.put("transportType", "直接转运");
			}
			map.put("remark", order.getRemark());
			map.put("trackingNo", order.getTrackingNo());
			map.put("tradeType", order.getTradeType());
			OrderStatus orderStatus = orderStatusDao.findOrderStatusByCode(order.getStatus());
			if (orderStatus != null) {
				map.put("status", orderStatus.getCn());
			}

			// 收件人信息
			OrderReceiver orderReceiver = orderReceiverDao.getOrderReceiverByPackageId(orderId);
			if (orderReceiver != null) {
				map.put("receiverAddressLine1", orderReceiver.getAddressLine1());
				map.put("receiverAddressLine2", orderReceiver.getAddressLine2());
				map.put("receiverCity", orderReceiver.getCity());
				map.put("receiverCompany", orderReceiver.getCompany());
				map.put("receiverCountryCode", orderReceiver.getCountryCode());
				map.put("receiverCountryName", orderReceiver.getCountryName());
				map.put("receiverCounty", orderReceiver.getCounty());
				map.put("receiverEmail", orderReceiver.getEmail());
				map.put("receiverFirstName", orderReceiver.getFirstName());
				map.put("receiverLastName", orderReceiver.getLastName());
				map.put("receiverMobileNumber", orderReceiver.getMobileNumber());
				map.put("receiverName", orderReceiver.getName());
				map.put("receiverPhoneNumber", orderReceiver.getPhoneNumber());
				map.put("receiverPostalCode", orderReceiver.getPostalCode());
				map.put("receiverStateOrProvince", orderReceiver.getStateOrProvince());
			}
			// 发件人
			OrderSender orderSender = orderSenderDao.getOrderSenderByPackageId(orderId);
			if (orderSender != null) {
				map.put("senderName", orderSender.getName());
			}
			// 物品明细(目前仅展示SKU*数量)
			String firstWaybills = "";
			FirstWaybill firstWaybillParam = new FirstWaybill();
			firstWaybillParam.setOrderId(orderId);
			List<FirstWaybill> firstWaybillList = firstWaybillDao.findFirstWaybill(firstWaybillParam, null, null);
			for (FirstWaybill firstWaybill : firstWaybillList) {
				firstWaybills += firstWaybill.getTrackingNo() + " ; ";
			}
			map.put("firstWaybills", firstWaybills);
			list.add(map);
		}
		pagination.total = orderDao.countOrder(param, moreParam);
		pagination.rows = list;
		return pagination;
	}

	@Override
	public Map<String, String> checkOrder(String orderIds, String checkResult, Long userIdOfOperator) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(orderIds)) {
			map.put(Constant.MESSAGE, "转运订单id(orderIds)为空,无法处理");
			return map;
		}
		if (StringUtil.isNull(checkResult)) {
			map.put(Constant.MESSAGE, "审核结果(checkResult)为空,无法处理");
			return map;
		}
		int updateQuantity = 0;//
		int noUpdateQuantity = 0;// 因非待审核状态,未更新
		String orderIdArr[] = orderIds.split(",");
		for (String orderId : orderIdArr) {
			if (StringUtil.isNull(orderId)) {
				continue;
			}
			Long orderIdLong = Long.valueOf(orderId);
			// 查询订单的当前状态
			String oldStatus = orderDao.getOrderStatus(orderIdLong);
			// 如果不是等待审核状态的订单,直接跳过
			if (!StringUtil.isEqual(oldStatus, OrderStatusCode.WWC)) {
				noUpdateQuantity++;
				continue;
			}
			// 更改状态为审核中
			orderDao.updateOrderStatus(orderIdLong, OrderStatusCode.WCI);
			// SUCCESS SECURITY 包裹安全监测不通过 OTHER_REASON 其他异常
			orderDao.updateOrderCheckResult(orderIdLong, checkResult);
			updateQuantity++;
		}
		map.put(Constant.MESSAGE, "审核执行中:" + updateQuantity + "个转运订单,  审核无效:" + noUpdateQuantity + "个非待审核状态转运订单");
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public Map<String, String> submitInWarehouse(String trackingNo, String remark, Long userIdOfOperator, Long warehouseId, Long firstWaybillId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(trackingNo)) {
			map.put(Constant.MESSAGE, "请输入跟踪单号.");
			return map;
		}
		FirstWaybill firstWaybill = firstWaybillDao.getFirstWaybillById(firstWaybillId);
		// 转运大包处理
		if (firstWaybill.getOrderPackageId() != null && firstWaybill.getOrderId() == null) {
			if (!StringUtil.isEqual(firstWaybill.getStatus(), FirstWaybillStatusCode.WWR)) {
				// 非待收货状态
				map.put(Constant.MESSAGE, "该转运大包非待收货状态,请输入新的跟踪单号");
				return map;
			}
			firstWaybill.setStatus(FirstWaybillStatusCode.WSR);
			firstWaybill.setReceivedTime(System.currentTimeMillis());
			// 保存货位,状态,时间
			firstWaybillDao.receivedFirstWaybill(firstWaybill);
			orderPackageDao.updateOrderPackageStatus(firstWaybill.getOrderPackageId(), OrderPackageStatusCode.SUCCESS);
			map.put(Constant.STATUS, "3");
			map.put(Constant.MESSAGE, "该转运大包收货成功,请注意需要拆包");
			return map;
		}
		if (!StringUtil.isEqual(firstWaybill.getStatus(), FirstWaybillStatusCode.WWR)) {
			// 非待收货状态
			map.put(Constant.MESSAGE, "该转运订单非待收货状态,请输入新的跟踪单号");
			return map;
		}
		// 更新为已收货前,查找空闲的转运业务专用货位
		// 查找其他小包是否已经上架
		FirstWaybillOnShelf onShelfParam = new FirstWaybillOnShelf();
		onShelfParam.setOrderId(firstWaybill.getOrderId());
		List<FirstWaybillOnShelf> firstWaybillOnShelfList = firstWaybillOnShelfDao.findFirstWaybillOnShelf(onShelfParam, null, null);
		String seatCode = null;
		if (firstWaybillOnShelfList != null && firstWaybillOnShelfList.size() > 0) {
			FirstWaybillOnShelf firstWaybillOnShelf = firstWaybillOnShelfList.get(0);
			seatCode = firstWaybillOnShelf.getSeatCode();
		}
		if (StringUtil.isNull(seatCode)) {
			seatCode = firstWaybillOnShelfDao.findSeatCodeForOnShelf(firstWaybill.getTransportType());
		}
		if (StringUtil.isNull(seatCode)) {
			// 货位不足,收货失败
			map.put(Constant.MESSAGE, "转运货位不足,请添加货位再收货");
			return map;
		}
		firstWaybill.setSeatCode(seatCode);// 收货预分配货位 真正的货位信息要在上架记录寻找
		// 更改为已收货, 待添加操作日志
		firstWaybill.setStatus(FirstWaybillStatusCode.WSR);
		firstWaybill.setReceivedTime(System.currentTimeMillis());
		// 保存货位,状态,时间
		firstWaybillDao.receivedFirstWaybill(firstWaybill);
		// 判断order下的所有firstWaybill是否已经全部收货
		FirstWaybill firstWaybillParam = new FirstWaybill();
		firstWaybillParam.setOrderId(firstWaybill.getOrderId());
		List<FirstWaybill> firstWaybillList = firstWaybillDao.findFirstWaybill(firstWaybillParam, null, null);
		boolean isReceived = true;// 小包是否已经全部收货
		for (FirstWaybill temp : firstWaybillList) {
			if (StringUtil.isEqual(temp.getStatus(), FirstWaybillStatusCode.WWR)) {
				isReceived = false;
			}
		}
		if (isReceived) {
			orderDao.updateOrderStatus(firstWaybill.getOrderId(), OrderStatusCode.WOS);// 收货完成
		} else {
			orderDao.updateOrderStatus(firstWaybill.getOrderId(), OrderStatusCode.WRP);
		}
		// 保存预分配货位上架记录
		FirstWaybillOnShelf onShelf = new FirstWaybillOnShelf();
		onShelf.setCreatedTime(System.currentTimeMillis());
		onShelf.setFirstWaybillId(firstWaybillId);
		onShelf.setOrderId(firstWaybill.getOrderId());
		onShelf.setSeatCode(seatCode);
		onShelf.setStatus(FirstWaybillOnShelf.STATUS_PRE_ON_SHELF);
		onShelf.setTrackingNo(trackingNo);
		onShelf.setUserIdOfCustomer(firstWaybill.getUserIdOfCustomer());
		onShelf.setUserIdOfOperator(userIdOfOperator);
		onShelf.setWarehouseId(warehouseId);
		firstWaybillOnShelfDao.saveFirstWaybillOnShelf(onShelf);
		map.put("seatCode", seatCode);
		map.put("orderId", "" + firstWaybill.getOrderId());

		if (StringUtil.isEqual(firstWaybill.getTransportType(), Order.TRANSPORT_TYPE_J)) {// 集货转运
			map.put(Constant.MESSAGE, "集货转运订单收货成功,请继续收货");
			map.put(Constant.STATUS, Constant.SUCCESS);
		} else if (StringUtil.isEqual(firstWaybill.getTransportType(), Order.TRANSPORT_TYPE_Z)) {// 直接转运
			// map.put(Constant.MESSAGE, "直接转运订单收货成功,请称重打单");
			// 2014-12-23取消直接转运订单称重打单, 后台修改提示内容, 前台修改操作流程
			map.put(Constant.MESSAGE, "直接转运订单收货成功,请继续收货");
			map.put(Constant.STATUS, "2");
			Order order = orderDao.getOrderById(firstWaybill.getOrderId());
			map.put("shipwayCode", order.getShipwayCode());
			map.put("trackingNo", order.getTrackingNo());
			map.put("seatCode", seatCode);
		}
		return map;
	}

	@Override
	public Map<String, String> orderSubmitWeight(Long userIdOfOperator, Long orderId, Double weight) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (weight == null || weight <= 0) {
			map.put(Constant.MESSAGE, "请先输入装箱重量");
			return map;
		}
		// 查询大包是否已经称重,不可重复称重
		Order order = orderDao.getOrderById(orderId);
		if (order.getOutWarehouseWeight() != null) {
			map.put(Constant.MESSAGE, "该转运订单已经称重,不能更改重量");
			return map;
		}
		// 集货转运订单,非待称重状态不可称重
		// 直接转运订单:收货(大包:收货完成,小包:待发送入库) 称重(大包:待发送重量 ,小包:无变化) 打印捡货单:无需做上架就可以打
		if (StringUtil.isEqual(order.getTransportType(), Order.TRANSPORT_TYPE_J)) {
			if (!StringUtil.isEqual(order.getStatus(), OrderStatusCode.WWW)) {
				map.put(Constant.MESSAGE, "该集货转运订单非待称重状态,不能更改重量");
				return map;
			}
		}
		if (StringUtil.isEqual(order.getTransportType(), Order.TRANSPORT_TYPE_Z)) {
			// 直接转运订单,在几个状态都可以称重
			if (!(StringUtil.isEqual(order.getStatus(), OrderStatusCode.WOS) || StringUtil.isEqual(order.getStatus(), OrderStatusCode.WWP) || StringUtil.isEqual(order.getStatus(), OrderStatusCode.WWW))) {
				map.put(Constant.MESSAGE, "该直接转运订单非待称重状态,不能更改重量");
				return map;
			}
		}
		order.setOutWarehouseWeight(weight);
		order.setStatus(OrderStatusCode.WSW);
		order.setWeightCode(WeightCode.KG);
		order.setUserIdOfOperator(userIdOfOperator);
		orderDao.updateOrderWeight(order);
		// 小包修改下架 :已下架合包
		firstWaybillDao.updateFirstWaybillStatus(orderId, FirstWaybillStatusCode.SUCCESS);
		// 修改上架记录为已下架
		firstWaybillOnShelfDao.updateFirstWaybillOnShelf(orderId, FirstWaybillOnShelf.STATUS_OUT_SHELF);
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public Map<String, String> orderWeightSubmitCustomerReferenceNo(String customerReferenceNo, Long userIdOfOperator) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(customerReferenceNo)) {
			map.put(Constant.MESSAGE, "请输入客户订单号(扫描捡货单上的客户订单号条码即可)");
			return map;
		}
		Order param = new Order();
		param.setCustomerReferenceNo(customerReferenceNo);
		List<Order> orderList = orderDao.findOrder(param, null, null);
		if (orderList == null || orderList.size() <= 0) {
			map.put(Constant.MESSAGE, "根据该客户订单号找不到转运订单,请输入正确客户订单号");
			return map;
		}
		Order order = orderList.get(0);
		Long orderId = order.getId();
		map.put("orderId", orderId.toString());
		FirstWaybill firstWaybillParam = new FirstWaybill();
		firstWaybillParam.setOrderId(orderId);
		List<String> trackingNoList = firstWaybillDao.findFirstWaybillTrackingNos(orderId);
		String trackingNos = "";
		for (String trackingNo : trackingNoList) {
			trackingNos += trackingNo + ",";
		}
		if (trackingNos.indexOf(",") >= 0) {
			trackingNos = trackingNos.substring(0, trackingNos.length() - 1);
		}
		map.put("trackingNos", trackingNos);
		OrderStatus status = orderStatusDao.findOrderStatusByCode(order.getStatus());
		map.put("orderStatus", status.getCn());
		map.put("shipwayCode", order.getShipwayCode());
		map.put("trackingNo", order.getTrackingNo());
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public Map<String, Object> outWarehousePackageEnterCoeTrackingNo(String coeTrackingNo) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.STATUS, Constant.FAIL);
		OutWarehousePackageItem packageRecordItem = new OutWarehousePackageItem();
		packageRecordItem.setCoeTrackingNo(coeTrackingNo);
		List<OutWarehousePackageItem> packageRecordItemList = transportPackageItemDao.findOutWarehousePackageItem(packageRecordItem, null, null);
		List<TrackingNo> trackingNos = trackingNoDao.findTrackingNo(coeTrackingNo, TrackingNo.TYPE_COE);
		// 暂不处理,单号可能重复问题
		if (trackingNos == null || trackingNos.size() <= 0) {
			map.put(Constant.MESSAGE, "该COE交接单号无效,请输入新单号");
			return map;
		}
		TrackingNo trackingNo = trackingNos.get(0);
		if (StringUtil.isEqual(trackingNo.getStatus(), TrackingNo.STATUS_USED + "")) {
			map.put(Constant.MESSAGE, "该COE交接单号已经使用,请输入新单号");
			return map;
		}
		map.put("coeTrackingNo", trackingNo);
		map.put(Constant.STATUS, Constant.SUCCESS);
		map.put("packageRecordItemList", packageRecordItemList);
		return map;
	}

	/**
	 * 出货检查每个跟踪号是否有效,并保存到出货单OutWarehousePackageItem表;
	 * 
	 */
	@Override
	public Map<String, String> checkOutWarehousePackage(String trackingNo, Long userIdOfOperator, Long coeTrackingNoId, String coeTrackingNo, String addOrSub, String orderIds) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(trackingNo)) {
			map.put(Constant.MESSAGE, "请输入出货跟踪单号");
			return map;
		}
		Order param = new Order();
		param.setTrackingNo(trackingNo);
		List<Order> orderList = orderDao.findOrder(param, null, null);
		if (orderList == null || orderList.size() == 0) {
			map.put(Constant.MESSAGE, "查询不到转运订单,请重新输入转运订单出货跟踪单号");
			return map;
		}
		if (orderList.size() > 1) {
			map.put(Constant.MESSAGE, "查询不到唯一的转运订单,暂无法处理");
			// 找到多个出库订单的情况,待处理
			// map.put(Constant.MESSAGE, "查询到多个出库订单,请输入客户订单号");
			return map;
		}
		Order order = orderList.get(0);
		// 只有顺丰确认出库,顺丰已确认的订单 可以出库
		if (StringUtil.isEqual(order.getStatus(), OrderStatusCode.WWO)) {
			Long orderId = order.getId();
			map.put("orderId", orderId + "");
			if (StringUtil.isEqual(addOrSub, "1")) {
				// 检查出库订单 是否已经和COE交接单号绑定
				OutWarehousePackageItem checkTrackingNoParam = new OutWarehousePackageItem();
				checkTrackingNoParam.setOrderId(orderId);
				Long countTrackingNoResult = transportPackageItemDao.countOutWarehousePackageItem(checkTrackingNoParam, null);
				if (countTrackingNoResult > 0) {
					// 说明该出库订单已经和其他COE交接单号绑定了,不能再绑定此单号
					map.put(Constant.MESSAGE, "该转运订单已经绑定的了其他COE交接单号");
					return map;
				}
				// 保存到OutWarehouseShipping,但不改变出库订单的状态.
				// 只有当操作员点击完成出货总单才改变一个COE单号下面对应的所有出库订单的状态
				OutWarehousePackageItem packageRecordItem = new OutWarehousePackageItem();
				packageRecordItem.setCoeTrackingNo(coeTrackingNo);
				packageRecordItem.setCoeTrackingNoId(coeTrackingNoId);
				packageRecordItem.setCreatedTime(System.currentTimeMillis());
				packageRecordItem.setOrderTrackingNo(trackingNo);
				packageRecordItem.setOrderId(order.getId());
				packageRecordItem.setUserIdOfCustomer(order.getUserIdOfCustomer());
				packageRecordItem.setUserIdOfOperator(userIdOfOperator);
				packageRecordItem.setWarehouseId(order.getWarehouseId());
				long packageRecordItemId = transportPackageItemDao.saveOutWarehousePackageItem(packageRecordItem);
				map.put("packageRecordItemId", packageRecordItemId + "");
				map.put(Constant.STATUS, Constant.SUCCESS);
			} else {
				// 1 = 添加出货运单号 ,2 是减去
				// 根据出货运单号+coe单号查找出货记录
				OutWarehousePackageItem packageRecordItem = new OutWarehousePackageItem();
				packageRecordItem.setCoeTrackingNoId(coeTrackingNoId);
				packageRecordItem.setCoeTrackingNo(coeTrackingNo);
				packageRecordItem.setOrderTrackingNo(trackingNo);
				List<OutWarehousePackageItem> packageRecordItemList = transportPackageItemDao.findOutWarehousePackageItem(packageRecordItem, null, null);
				String packageRecordItemIds = "";
				int sub = 0;
				for (OutWarehousePackageItem item : packageRecordItemList) {
					transportPackageItemDao.deleteOutWarehousePackageItemById(item.getId());
					// 加#是为了 jquery可以直接$("#id1,#id2,#id3,#id4")
					packageRecordItemIds += ("#" + item.getId() + ",");
					orderIds = orderIds.replaceAll(item.getOrderId() + "\\|\\|", "");
					sub++;
				}
				map.put("sub", sub + "");
				map.put("packageRecordItemIds", packageRecordItemIds);
				map.put("orderIds", orderIds);
				map.put(Constant.STATUS, "2");
			}
		} else if (StringUtil.isEqual(order.getStatus(), OrderStatusCode.SUCCESS)) {
			map.put(Constant.MESSAGE, "转运订单当前状态已经是出库成功");
		} else if (StringUtil.isEqual(order.getStatus(), OrderStatusCode.WCC)) {
			map.put(Constant.MESSAGE, "转运订单当前状态是等待客户确认出库,不能出库");
		} else if (StringUtil.isEqual(order.getStatus(), OrderStatusCode.WSW)) {
			map.put(Constant.MESSAGE, "转运订单当前状态是等待发送出库重量给客户,不能出库");
		} else {
			map.put(Constant.MESSAGE, "转运订单当前状态不能扫描建包出库");
		}
		return map;
	}

	@Override
	public Map<String, String> outWarehousePackageConfirm(String coeTrackingNo, Long coeTrackingNoId, String orderIds, Long userIdOfOperator) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(orderIds)) {
			map.put(Constant.MESSAGE, "请输入转运订单跟踪单号再按完成出货建包!");
			return map;
		}
		// 前端用||分割多个跟踪单号
		String orderIdsArray[] = orderIds.split("\\|\\|");
		if (orderIdsArray.length < 1) {
			map.put(Constant.MESSAGE, "请输入转运订单跟踪单号再按完成出货建包!");
			return map;
		}
		if (StringUtil.isNull(coeTrackingNo)) {
			map.put(Constant.MESSAGE, "请输入COE交接单号,或刷新页面重试!");
			return map;
		}
		TrackingNo trackingNo = trackingNoDao.getTrackingNoById(coeTrackingNoId);
		if (trackingNo == null) {
			map.put(Constant.MESSAGE, "该COE交接单号无效,请输入新单号");
			return map;
		}
		if (StringUtil.isEqual(trackingNo.getStatus(), TrackingNo.STATUS_USED + "")) {
			map.put(Constant.MESSAGE, "该COE交接单号已经使用,请输入新单号");
			return map;
		}
		Long userIdOfCustomer = null;
		Long warehouseId = null;
		// 迭代,检查跟踪号
		for (String orderId : orderIdsArray) {
			if (StringUtil.isNotNull(orderId)) {
				Long orderIdLong = Long.valueOf(orderId);
				if (userIdOfCustomer == null) {
					Order order = orderDao.getOrderById(orderIdLong);
					userIdOfCustomer = order.getUserIdOfCustomer();
					warehouseId = order.getWarehouseId();
				}
			}
		}
		if (userIdOfCustomer == null) {
			map.put(Constant.MESSAGE, "请输入转运订单跟踪单号再按完成出货建包!");
			return map;
		}
		OutWarehousePackage packageRecord = new OutWarehousePackage();
		packageRecord.setCoeTrackingNo(coeTrackingNo);
		packageRecord.setCoeTrackingNoId(coeTrackingNoId);
		packageRecord.setCreatedTime(System.currentTimeMillis());
		packageRecord.setUserIdOfCustomer(userIdOfCustomer);
		packageRecord.setUserIdOfOperator(userIdOfOperator);
		packageRecord.setWarehouseId(warehouseId);
		packageRecord.setIsShiped(Constant.N);
		transportPackageDao.saveOutWarehousePackage(packageRecord);

		// 标记coe单号已经使用
		trackingNoDao.usedTrackingNo(coeTrackingNoId);
		// 返回新COE单号,供下一批出库
		TrackingNo nextTrackingNo = trackingNoDao.getAvailableTrackingNoByType(TrackingNo.TYPE_COE);
		if (nextTrackingNo == null) {
			map.put(Constant.MESSAGE, "本次转运订单建包已完成,但COE单号不足,不能继续操作建包!");
			map.put(Constant.STATUS, "2");
			map.put("coeTrackingNo", "");
			map.put("coeTrackingNoId", "");
			return map;
		}
		trackingNoDao.lockTrackingNo(nextTrackingNo.getId());
		map.put("coeTrackingNo", nextTrackingNo.getTrackingNo());
		map.put("coeTrackingNoId", nextTrackingNo.getId().toString());
		map.put(Constant.STATUS, Constant.SUCCESS);
		map.put(Constant.MESSAGE, "完成转运订单建包成功,请继续下一批!");
		return map;
	}

	@Override
	public Map<String, String> outWarehouseShippingConfirm(String coeTrackingNo, Long userIdOfOperator) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(coeTrackingNo)) {
			map.put(Constant.MESSAGE, "请输入COE交接单号");
			return map;
		}
		List<TrackingNo> trackingNoList = trackingNoDao.findTrackingNo(coeTrackingNo, TrackingNo.TYPE_COE);
		if (trackingNoList == null || trackingNoList.size() < 1) {
			map.put(Constant.MESSAGE, "该COE交接单号无效,请输入新单号");
			return map;
		}
		TrackingNo trackingNo = trackingNoList.get(0);
		Long coeTrackingNoId = trackingNo.getId();
		// 根据出库交接单号查询建包记录.
		OutWarehousePackage packageRecordParam = new OutWarehousePackage();
		packageRecordParam.setCoeTrackingNoId(coeTrackingNoId);
		List<OutWarehousePackage> packageRecordList = transportPackageDao.findOutWarehousePackage(packageRecordParam, null, null);
		if (packageRecordList == null || packageRecordList.size() <= 0) {
			map.put(Constant.MESSAGE, "没有找到出库建包记录,请先完成建包");
			return map;
		}
		// 出库记录
		OutWarehousePackage packageRecord = packageRecordList.get(0);
		if (StringUtil.isEqual(packageRecord.getIsShiped(), Constant.Y)) {
			map.put(Constant.MESSAGE, "该交接单号对应大包已经出库,请勿重复操作");
			return map;
		}
		transportPackageDao.updateOutWarehousePackageIsShiped(packageRecord.getId(), Constant.Y, System.currentTimeMillis());
		// 根据coe交接单号 获取建包记录,获取每个出库订单(小包)
		OutWarehousePackageItem itemParam = new OutWarehousePackageItem();
		itemParam.setCoeTrackingNoId(coeTrackingNoId);
		List<OutWarehousePackageItem> outWarehouseRecordItemList = transportPackageItemDao.findOutWarehousePackageItem(itemParam, null, null);
		// 迭代,检查跟踪号
		for (OutWarehousePackageItem recordItem : outWarehouseRecordItemList) {
			Long orderId = recordItem.getOrderId();
			orderDao.updateOrderStatus(orderId, OrderStatusCode.SUCCESS);
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		map.put(Constant.MESSAGE, "完成出货总单成功,请继续下一批!");
		return map;
	}

	/**
	 * 获取出库建包记录
	 */
	@Override
	public Pagination getOutWarehousePackageData(OutWarehousePackage outWarehousePackage, Map<String, String> moreParam, Pagination page) {
		List<OutWarehousePackage> packageRecordList = transportPackageDao.findOutWarehousePackage(outWarehousePackage, moreParam, page);
		List<Object> list = new ArrayList<Object>();
		for (OutWarehousePackage packageRecord : packageRecordList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", packageRecord.getId());
			if (packageRecord.getCreatedTime() != null) {
				map.put("packageTime", DateUtil.dateConvertString(new Date(packageRecord.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			if (StringUtil.isEqual(packageRecord.getIsShiped(), Constant.Y)) {
				map.put("isShipped", "已发货");
				map.put("shippedTime", DateUtil.dateConvertString(new Date(packageRecord.getShippedTime()), DateUtil.yyyy_MM_ddHHmmss));
			} else {
				map.put("isShipped", "未发货");
			}
			// 查询用户名
			User user = userDao.getUserById(packageRecord.getUserIdOfCustomer());
			map.put("userLoginNameOfCustomer", user.getLoginName());
			// 查询操作员
			if (NumberUtil.greaterThanZero(packageRecord.getUserIdOfOperator())) {
				User userOfOperator = userDao.getUserById(packageRecord.getUserIdOfOperator());
				map.put("userLoginNameOfOperator", userOfOperator.getLoginName());
			}
			map.put("coeTrackingNo", packageRecord.getCoeTrackingNo());
			map.put("coeTrackingNoId", packageRecord.getCoeTrackingNoId());
			if (NumberUtil.greaterThanZero(packageRecord.getWarehouseId())) {
				Warehouse warehouse = warehouseDao.getWarehouseById(packageRecord.getWarehouseId());
				map.put("warehouse", warehouse.getWarehouseName());
			}
			map.put("remark", packageRecord.getRemark() == null ? "" : packageRecord.getRemark());
			OutWarehousePackageItem param = new OutWarehousePackageItem();
			param.setCoeTrackingNoId(packageRecord.getCoeTrackingNoId());
			List<OutWarehousePackageItem> itemList = transportPackageItemDao.findOutWarehousePackageItem(param, null, null);
			Integer quantity = 0;
			String orders = "";
			for (OutWarehousePackageItem item : itemList) {
				orders += item.getOrderTrackingNo() + " ; ";
				quantity++;
			}
			map.put("orders", orders);
			map.put("quantity", quantity);
			list.add(map);
		}
		page.total = transportPackageDao.countOutWarehousePackage(outWarehousePackage, moreParam);
		page.rows = list;
		return page;
	}

	@Override
	public Map<String, String> saveOutWarehousePackageRemark(String remark, Long id) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		if (transportPackageDao.updateOutWarehousePackageRemark(id, remark) > 0) {
			map.put(Constant.STATUS, Constant.SUCCESS);
		} else {
			map.put(Constant.STATUS, Constant.FAIL);
		}
		return map;
	}

	@Override
	public Map<String, String> applyTrackingNo(Long orderId) throws ServiceException {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constant.STATUS, Constant.FAIL);
		if (orderId == null) {
			return resultMap;
		}
		Order order = orderDao.getOrderById(orderId);
		if (StringUtil.isNotNull(order.getTrackingNo())) {
			resultMap.put(Constant.MESSAGE, "该订单已有跟踪单号,申请失效");
			return resultMap;
		}
		OrderReceiver orderReceiver = orderReceiverDao.getOrderReceiverByPackageId(orderId);
		OrderSender orderSender = orderSenderDao.getOrderSenderByPackageId(orderId);
		// 出货渠道是ETK
		if (StringUtil.isEqual(order.getShipwayCode(), ShipwayCode.ETK)) {
			resultMap = applyEtkTrackingNo(order, orderReceiver, orderSender);
		}
		// 出货渠道顺丰
		if (StringUtil.isEqual(order.getShipwayCode(), ShipwayCode.SF)) {
			resultMap = applySFTrackingNo(order, orderReceiver, orderSender);
		}
		return resultMap;
	}

	/**
	 * 申请ETK跟踪单号
	 * 
	 * @param order
	 * @param orderReceiver
	 * @param orderSender
	 * @return
	 */
	private Map<String, String> applyEtkTrackingNo(Order order, OrderReceiver orderReceiver, OrderSender orderSender) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		com.coe.etk.api.request.Order etkOrder = new com.coe.etk.api.request.Order();
		ShipwayApiAccount shipwayApiAccount = shipwayApiAccountDao.getShipwayApiAccountByUserId(order.getUserIdOfCustomer(), order.getShipwayCode());
		if (shipwayApiAccount == null) {
			map.put(Constant.MESSAGE, "此订单所属用户缺少ETK API配置信息");
			return map;
		}
		etkOrder.setCurrency(CurrencyCode.CNY);
		etkOrder.setCustomerNo(shipwayApiAccount.getApiAccount());// 测试
		etkOrder.setReferenceId(order.getCustomerReferenceNo());// 客户参考号
		FirstWaybillItem itemParam = new FirstWaybillItem();
		itemParam.setOrderId(order.getId());
		List<FirstWaybillItem> firstWaybillItems = firstWaybillItemDao.findFirstWaybillItem(itemParam, null, null);
		List<com.coe.etk.api.request.Item> items = new ArrayList<com.coe.etk.api.request.Item>();
		for (FirstWaybillItem firstWaybillItem : firstWaybillItems) {
			com.coe.etk.api.request.Item item = new com.coe.etk.api.request.Item();
			item.setItemDescription(firstWaybillItem.getSkuName());// 报关描述
			double price = 10d;// 报关价值
			if (firstWaybillItem.getSkuUnitPrice() != null) {
				price = NumberUtil.div(firstWaybillItem.getSkuUnitPrice(), 100d, 2);// 分转元
			}
			item.setItemPrice(price);
			item.setItemQuantity(firstWaybillItem.getQuantity() == null ? 1 : firstWaybillItem.getQuantity());// 报关数量
			double weight = 0.1d;// 报关重量
			if (firstWaybillItem.getSkuNetWeight() != null) {
				weight = (firstWaybillItem.getSkuNetWeight() / 1000);
			}
			item.setItemWeight(weight);
			items.add(item);
		}
		etkOrder.setItems(items);
		// 收件人
		Receiver receiver = new Receiver();
		receiver.setReceiverAddress1(orderReceiver.getAddressLine1());
		receiver.setReceiverAddress2(orderReceiver.getAddressLine2());
		receiver.setReceiverCity(orderReceiver.getCity());
		receiver.setReceiverCode(orderReceiver.getPostalCode());
		receiver.setReceiverName(orderReceiver.getName());
		receiver.setReceiverCountry(orderReceiver.getCountryCode());
		receiver.setReceiverPhone(orderReceiver.getPhoneNumber());
		receiver.setReceiverProvince(orderReceiver.getStateOrProvince());
		etkOrder.setReceiver(receiver);
		// 发件人
		Sender sender = new Sender();
		sender.setSenderAddress(orderSender.getAddressLine1());
		sender.setSenderName(orderSender.getName());
		sender.setSenderPhone(orderReceiver.getPhoneNumber());
		etkOrder.setSender(sender);

		Client client = new Client();
		client.setToken(shipwayApiAccount.getToken());
		client.setTokenKey(shipwayApiAccount.getTokenKey());
		client.setUrl(shipwayApiAccount.getUrl());
		com.coe.etk.api.response.Responses responses = client.applyTrackingNo(etkOrder);
		if (responses == null) {
			map.put(Constant.MESSAGE, "对方系统返回非法XML格式");
			return map;
		}
		ResponseItems responseItems = responses.getResponseItems();
		if (responseItems == null) {
			map.put(Constant.MESSAGE, "对方系统返回非法XML格式");
			return map;
		}
		com.coe.etk.api.response.Response response = responseItems.getResponse();
		if (response == null) {
			map.put(Constant.MESSAGE, "对方系统返回非法XML格式");
			return map;
		}
		if (StringUtil.isEqualIgnoreCase(response.getSuccess(), Constant.FALSE)) {
			map.put(Constant.MESSAGE, response.getErrorInfo());
			return map;
		}
		// 成功
		String trackingNo = response.getTrackingNo();
		String zoneCode = response.getZoneCode();
		map.put("zoneCode", zoneCode);
		map.put(Constant.MESSAGE, trackingNo);
		map.put(Constant.STATUS, Constant.SUCCESS);
		if (StringUtil.isNotNull(trackingNo)) {
			order.setTrackingNo(trackingNo);
			order.setShipwayExtra1(zoneCode);// ETK分区号
			orderDao.updateOrderTrackingNo(order);
		} else {
			map.put(Constant.MESSAGE, "对方系统返回成功,但返回空的ETK单号");
			map.put(Constant.STATUS, Constant.FAIL);
		}
		return map;
	}

	/**
	 * 申请SF跟踪单号
	 * 
	 * @param order
	 * @param orderReceiver
	 * @param orderSender
	 * @return
	 */
	private Map<String, String> applySFTrackingNo(Order order, OrderReceiver orderReceiver, OrderSender orderSender) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		map.put(Constant.MESSAGE, "未支持对顺丰渠道申请单号");
		return map;
	}
}
