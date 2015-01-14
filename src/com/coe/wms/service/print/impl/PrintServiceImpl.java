package com.coe.wms.service.print.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.ISeatDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.dao.warehouse.shipway.IShipwayApiAccountDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordItemDao;
import com.coe.wms.dao.warehouse.storage.IItemInventoryDao;
import com.coe.wms.dao.warehouse.storage.IItemShelfInventoryDao;
import com.coe.wms.dao.warehouse.storage.IOnShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderAdditionalSfDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderReceiverDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderSenderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehousePackageDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehousePackageItemDao;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillDao;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillItemDao;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillOnShelfDao;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillStatusDao;
import com.coe.wms.dao.warehouse.transport.IOrderAdditionalSfDao;
import com.coe.wms.dao.warehouse.transport.IOrderDao;
import com.coe.wms.dao.warehouse.transport.IOrderReceiverDao;
import com.coe.wms.dao.warehouse.transport.IOrderSenderDao;
import com.coe.wms.dao.warehouse.transport.IOrderStatusDao;
import com.coe.wms.model.warehouse.Seat;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.shipway.Shipway;
import com.coe.wms.model.warehouse.shipway.Shipway.ShipwayCode;
import com.coe.wms.model.warehouse.shipway.ShipwayApiAccount;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderAdditionalSf;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItemShelf;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderSender;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus.OutWarehouseOrderStatusCode;
import com.coe.wms.model.warehouse.storage.record.OutWarehousePackage;
import com.coe.wms.model.warehouse.storage.record.OutWarehousePackageItem;
import com.coe.wms.model.warehouse.transport.FirstWaybill;
import com.coe.wms.model.warehouse.transport.FirstWaybillItem;
import com.coe.wms.model.warehouse.transport.FirstWaybillOnShelf;
import com.coe.wms.model.warehouse.transport.Order;
import com.coe.wms.model.warehouse.transport.OrderAdditionalSf;
import com.coe.wms.model.warehouse.transport.OrderReceiver;
import com.coe.wms.model.warehouse.transport.OrderSender;
import com.coe.wms.model.warehouse.transport.OrderStatus.OrderStatusCode;
import com.coe.wms.service.print.IPrintService;
import com.coe.wms.util.BarcodeUtil;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.StringUtil;

@Service("printService")
public class PrintServiceImpl implements IPrintService {

	private static final Logger logger = Logger.getLogger(PrintServiceImpl.class);

	@Resource(name = "warehouseDao")
	private IWarehouseDao warehouseDao;

	@Resource(name = "outWarehousePackageItemDao")
	private IOutWarehousePackageItemDao outWarehousePackageItemDao;

	@Resource(name = "seatDao")
	private ISeatDao seatDao;

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

	@Resource(name = "outWarehouseOrderItemShelfDao")
	private IOutWarehouseOrderItemShelfDao outWarehouseOrderItemShelfDao;

	@Resource(name = "outWarehouseOrderSenderDao")
	private IOutWarehouseOrderSenderDao outWarehouseOrderSenderDao;

	@Resource(name = "outWarehouseOrderAdditionalSfDao")
	private IOutWarehouseOrderAdditionalSfDao outWarehouseOrderAdditionalSfDao;

	@Resource(name = "outWarehouseOrderReceiverDao")
	private IOutWarehouseOrderReceiverDao outWarehouseOrderReceiverDao;

	@Resource(name = "inWarehouseRecordDao")
	private IInWarehouseRecordDao inWarehouseRecordDao;

	@Resource(name = "outWarehousePackageDao")
	private IOutWarehousePackageDao outWarehousePackageDao;

	@Resource(name = "inWarehouseRecordItemDao")
	private IInWarehouseRecordItemDao inWarehouseRecordItemDao;

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Resource(name = "itemInventoryDao")
	private IItemInventoryDao itemInventoryDao;

	@Resource(name = "itemShelfInventoryDao")
	private IItemShelfInventoryDao itemShelfInventoryDao;

	@Resource(name = "onShelfDao")
	private IOnShelfDao onShelfDao;

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
	private com.coe.wms.dao.warehouse.transport.IOutWarehousePackageDao transportPackageDao;

	@Resource(name = "transportPackageItemDao")
	private com.coe.wms.dao.warehouse.transport.IOutWarehousePackageItemDao transportPackageItemDao;

	@Resource(name = "shipwayApiAccountDao")
	private IShipwayApiAccountDao shipwayApiAccountDao;

	@Override
	public Map<String, Object> getPrintPackageListData(Long outWarehouseOrderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderDao.getOutWarehouseOrderById(outWarehouseOrderId);
		// 等待仓库审核的订单 不能打印捡货单
		if (StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WWC)) {
			return null;
		}
		OutWarehouseOrderReceiver receiver = outWarehouseOrderReceiverDao.getOutWarehouseOrderReceiverByOrderId(outWarehouseOrderId);
		map.put("outWarehouseOrderId", String.valueOf(outWarehouseOrder.getId()));
		map.put("customerReferenceNo", outWarehouseOrder.getCustomerReferenceNo());
		OutWarehouseOrderAdditionalSf outWarehouseOrderAdditionalSf = outWarehouseOrderAdditionalSfDao.getOutWarehouseOrderAdditionalSfByOrderId(outWarehouseOrderId);
		if (outWarehouseOrderAdditionalSf != null) {
			map.put("customerOrderNo", outWarehouseOrderAdditionalSf.getOrderId());
		}
		// 创建图片
		String customerReferenceNoBarcodeData = BarcodeUtil.createCode128(outWarehouseOrder.getCustomerReferenceNo(), true, 12d, null, null, null);
		map.put("customerReferenceNoBarcodeData", customerReferenceNoBarcodeData);
		map.put("tradeRemark", outWarehouseOrder.getTradeRemark());
		map.put("trackingNo", outWarehouseOrder.getTrackingNo());
		map.put("logisticsRemark", outWarehouseOrder.getLogisticsRemark());
		map.put("receiverName", receiver.getName());
		map.put("receiverPhoneNumber", receiver.getPhoneNumber());
		map.put("receiverMobileNumber", receiver.getMobileNumber());
		map.put("receiverMobileNumber", receiver.getMobileNumber());
		// 如果已经打印过捡货单, 从上次打印保存的货位和SKU,数量信息 取出,返回前台
		OutWarehouseOrderItemShelf itemShelfParam = new OutWarehouseOrderItemShelf();
		itemShelfParam.setOutWarehouseOrderId(outWarehouseOrderId);
		List<OutWarehouseOrderItemShelf> itemShelfList = outWarehouseOrderItemShelfDao.findOutWarehouseOrderItemShelf(itemShelfParam, null, null);
		map.put("items", itemShelfList);
		// 商品总数
		int totalQuantity = 0;
		double totalPrice = 0d;
		for (OutWarehouseOrderItemShelf itemShelf : itemShelfList) {
			totalQuantity += itemShelf.getQuantity();
			if (itemShelf.getSkuUnitPrice() != null) {
				totalPrice += (itemShelf.getQuantity() * itemShelf.getSkuUnitPrice() / 100);
			}
		}
		map.put("totalQuantity", totalQuantity);
		map.put("totalPrice", totalPrice);
		if (outWarehouseOrder.getPrintedCount() == null) {
			outWarehouseOrderDao.updateOutWarehouseOrderPrintedCount(outWarehouseOrderId, 1);
		} else {
			outWarehouseOrderDao.updateOutWarehouseOrderPrintedCount(outWarehouseOrderId, outWarehouseOrder.getPrintedCount() + 1);
		}
		// 如果当前订单状态是等待打印
		if (StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WPP)) {
			// 更新状态为已经打印捡货单,等待下架
			outWarehouseOrderDao.updateOutWarehouseOrderStatus(outWarehouseOrderId, OutWarehouseOrderStatusCode.WOS);
		}
		return map;
	}

	@Override
	public Map<String, Object> getPrintSeatCodeLabelData(Long seatId) {
		Seat param = new Seat();
		param.setId(seatId);
		List<Seat> seatList = seatDao.findSeat(param, null);
		Map<String, Object> map = new HashMap<String, Object>();
		if (seatList != null && seatList.size() > 0) {
			Seat seat = seatList.get(0);
			map.put("seatCode", seat.getSeatCode());
			// 创建图片
			String seatCodeBarcodeData = BarcodeUtil.createCode128(seat.getSeatCode(), false, 16d, 0.5d, null, null);
			map.put("seatCodeBarcodeData", seatCodeBarcodeData);
			// 仓库
			Warehouse warehouse = warehouseDao.getWarehouseById(seat.getWarehouseId());
			if (warehouse != null) {
				map.put("warehouse", warehouse.getWarehouseName());
			}
		}
		return map;
	}

	@Override
	public List<Map<String, String>> getOutWarehouseShippings(Long coeTrackingNoId) {
		OutWarehousePackageItem outWarehouseShippingParam = new OutWarehousePackageItem();
		outWarehouseShippingParam.setCoeTrackingNoId(coeTrackingNoId);
		List<OutWarehousePackageItem> outWarehouseShippingList = outWarehousePackageItemDao.findOutWarehousePackageItem(outWarehouseShippingParam, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (OutWarehousePackageItem outWarehouseShipping : outWarehouseShippingList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("ourWarehouseOrderTrackingNo", outWarehouseShipping.getOutWarehouseOrderTrackingNo());
			Long outWarehouseOrderId = outWarehouseShipping.getOutWarehouseOrderId();
			OutWarehouseOrder outWarehouseOrder = outWarehouseOrderDao.getOutWarehouseOrderById(outWarehouseOrderId);
			if (outWarehouseOrder == null) {
				continue;
			}
			map.put("warehouseId", outWarehouseOrder.getWarehouseId() + "");
			map.put("customerReferenceNo", outWarehouseOrder.getCustomerReferenceNo());
			map.put("outWarehouseWeight", outWarehouseOrder.getOutWarehouseWeight() + "");
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 打印coe交接单,根据建包记录
	 */
	@Override
	public Map<String, Object> getPrintCoeLabelData(Long coeTrackingNoId) {
		Map<String, Object> map = new HashMap<String, Object>();
		OutWarehousePackage outWarehousePackageParam = new OutWarehousePackage();
		outWarehousePackageParam.setCoeTrackingNoId(coeTrackingNoId);
		List<OutWarehousePackage> outWarehousePackageList = outWarehousePackageDao.findOutWarehousePackage(outWarehousePackageParam, null, null);
		if (outWarehousePackageList == null || outWarehousePackageList.size() == 0) {
			return map;
		}
		OutWarehousePackage outWarehousePackage = outWarehousePackageList.get(0);
		// 出貨詳情
		OutWarehousePackageItem outWarehouseShippingParam = new OutWarehousePackageItem();
		outWarehouseShippingParam.setCoeTrackingNoId(coeTrackingNoId);
		List<OutWarehousePackageItem> outWarehouseShippingList = outWarehousePackageItemDao.findOutWarehousePackageItem(outWarehouseShippingParam, null, null);
		// 總重量
		double totalWeight = 0d;
		int quantity = 0;
		for (OutWarehousePackageItem outWarehouseShipping : outWarehouseShippingList) {
			OutWarehouseOrder outWarehouseOrder = outWarehouseOrderDao.getOutWarehouseOrderById(outWarehouseShipping.getOutWarehouseOrderId());
			totalWeight += outWarehouseOrder.getOutWarehouseWeight();
			quantity++;
		}
		String trackingNoBarcodeData = BarcodeUtil.createCode128(outWarehousePackage.getCoeTrackingNo(), false, 29d, 0.5d, null, null);
		map.put("coeTrackingNoBarcodeData", trackingNoBarcodeData);
		map.put("totalWeight", NumberUtil.getNumPrecision(totalWeight, 3));
		map.put("quantity", quantity);
		map.put("outWarehouseRecord", outWarehousePackage);
		map.put("shipdate", DateUtil.dateConvertString(new Date(outWarehousePackage.getCreatedTime()), DateUtil.yyyy_MM_dd));
		return map;
	}

	@Override
	public Map<String, Object> getPrintSkuBarcodeData(String sku) {
		Map<String, Object> map = new HashMap<String, Object>();
		String skuBarcodeData = BarcodeUtil.createCode128(sku, false, 9d, null, null, null);
		map.put("skuBarcodeData", skuBarcodeData);
		map.put("sku", sku);
		return map;
	}

	@Override
	public Map<String, Object> getPrintShipLabelData(Long outWarehouseOrderId, Double height, Double barcodeScale) {
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderDao.getOutWarehouseOrderById(outWarehouseOrderId);
		// 等待仓库审核的订单 不能打印捡货单
		if (StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WWC)) {
			return null;
		}
		OutWarehouseOrderReceiver receiver = outWarehouseOrderReceiverDao.getOutWarehouseOrderReceiverByOrderId(outWarehouseOrderId);
		OutWarehouseOrderSender sender = outWarehouseOrderSenderDao.getOutWarehouseOrderSenderByOrderId(outWarehouseOrderId);
		OutWarehouseOrderItem itemParam = new OutWarehouseOrderItem();
		itemParam.setOutWarehouseOrderId(outWarehouseOrderId);
		List<OutWarehouseOrderItem> items = outWarehouseOrderItemDao.findOutWarehouseOrderItem(itemParam, null, null);
		// 根据批次排序,找到货位
		// 顺丰label 内容
		OutWarehouseOrderAdditionalSf additionalSf = outWarehouseOrderAdditionalSfDao.getOutWarehouseOrderAdditionalSfByOrderId(outWarehouseOrderId);
		Map<String, Object> map = new HashMap<String, Object>();
		if (additionalSf != null) {
			map.put("additionalSf", additionalSf);
		}
		if (sender != null) {
			map.put("sender", sender);
		}
		map.put("shipwayCode", outWarehouseOrder.getShipwayCode());
		// 创建条码
		String trackingNo = outWarehouseOrder.getTrackingNo();
		outWarehouseOrder.getShipwayExtra1();
		String etkTrackingNo = Shipway.getEtkTrackingNoPrintFormat(trackingNo);// ETK跟踪号打印格式
		map.put("etkTrackingNo", etkTrackingNo);
		if (StringUtil.isEqual(outWarehouseOrder.getShipwayCode(), ShipwayCode.ETK)) {
			ShipwayApiAccount shipwayApiAccount = shipwayApiAccountDao.getShipwayApiAccountByUserId(outWarehouseOrder.getUserIdOfCustomer(), ShipwayCode.ETK);
			if (shipwayApiAccount != null) {
				map.put("customerNo", shipwayApiAccount.getApiAccount());// ETK客户帐号
			}
		}
		// 顺丰标签使用
		String trackingNoBarcodeData = BarcodeUtil.createCode128(trackingNo, true, height, null, null, barcodeScale);
		map.put("trackingNoBarcodeData", trackingNoBarcodeData);
		// etk标签使用
		String trackingNoBarcodeData2 = BarcodeUtil.createCode128(trackingNo, false, 14d, null, null, null);
		map.put("trackingNoBarcodeData2", trackingNoBarcodeData2);
		map.put("shipwayExtra1", outWarehouseOrder.getShipwayExtra1());
		map.put("shipwayExtra2", outWarehouseOrder.getShipwayExtra2());
		// 清单号 (出库订单主键)
		map.put("outWarehouseOrderId", String.valueOf(outWarehouseOrder.getId()));
		map.put("customerReferenceNo", outWarehouseOrder.getCustomerReferenceNo());
		map.put("tradeRemark", outWarehouseOrder.getTradeRemark());
		map.put("logisticsRemark", outWarehouseOrder.getLogisticsRemark());
		map.put("receiverName", receiver.getName());
		map.put("receiver", receiver);
		map.put("receiverPhoneNumber", receiver.getPhoneNumber());
		map.put("receiverMobileNumber", receiver.getMobileNumber());
		Integer totalQuantity = 0;
		for (OutWarehouseOrderItem item : items) {
			totalQuantity += item.getQuantity();
		}
		// 寄托物品数量
		map.put("totalQuantity", totalQuantity);
		// 总重量
		map.put("totalWeight", outWarehouseOrder.getOutWarehouseWeight());
		map.put("items", items);
		List<Map<String, String>> etkCustoms = new ArrayList<Map<String, String>>();
		double totalPrice = 0;
		int i = 0;
		for (OutWarehouseOrderItem item : items) {
			totalQuantity += item.getQuantity();
			totalPrice += item.getSkuUnitPrice();// 目前全部是人民币分
			i++;
			if (i <= 3) {// ETK只打印3行报关信息
				Map<String, String> customs = new HashMap<String, String>();
				String skuName = item.getSkuName();
				if (StringUtil.isNotNull(skuName)) {
					if (skuName.length() > 10) {
						skuName = skuName.substring(0, 10);
					}
				}
				customs.put("skuName", skuName);
				customs.put("customsWeight", "1.01");
				customs.put("customsValue", "2.01");
				customs.put("quantity", "5");
				customs.put("totalvalue", "15");
				etkCustoms.add(customs);
			}
		}
		// ETK标签固定是3行报关信息,所以控制etkCustoms size=3
		if (etkCustoms.size() == 0) {
			Map<String, String> customs = new HashMap<String, String>();
			etkCustoms.add(customs);
		}
		if (etkCustoms.size() == 1) {
			Map<String, String> customs = new HashMap<String, String>();
			etkCustoms.add(customs);
		}
		if (etkCustoms.size() == 2) {
			Map<String, String> customs = new HashMap<String, String>();
			etkCustoms.add(customs);
		}
		map.put("etkCustoms", etkCustoms);
		totalPrice = NumberUtil.div(totalPrice, 100d, 2);
		map.put("currency", "人民币");// 币种目前只有人民币
		map.put("totalPrice", totalPrice);
		String shipwayCode = outWarehouseOrder.getShipwayCode();// 出货渠道
		map.put("shipwayCode", shipwayCode);
		return map;
	}

	@Override
	public Map<String, Object> getPrintTransportShipLabedData(Long orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.STATUS, Constant.FAIL);
		Order order = orderDao.getOrderById(orderId);
		if (StringUtil.isEqual(order.getStatus(), OrderStatusCode.WWC) || StringUtil.isEqual(order.getStatus(), OrderStatusCode.WCI) || StringUtil.isEqual(order.getStatus(), OrderStatusCode.WCF)
				|| StringUtil.isEqual(order.getStatus(), OrderStatusCode.WRG) || StringUtil.isEqual(order.getStatus(), OrderStatusCode.WRP)) {
			return null;
		}
		OrderReceiver receiver = orderReceiverDao.getOrderReceiverByPackageId(orderId);
		OrderSender sender = orderSenderDao.getOrderSenderByPackageId(orderId);
		FirstWaybill firstWaybillParam = new FirstWaybill();
		firstWaybillParam.setOrderId(orderId);
		OrderAdditionalSf additionalSf = orderAdditionalSfDao.getOrderAdditionalSfByOrderId(orderId);
		map.put("additionalSf", additionalSf);
		map.put("sender", sender);
		// 创建条码
		String trackingNo = order.getTrackingNo();
		if (StringUtil.isNull(trackingNo)) {
			return null;
		}
		String etkTrackingNo = Shipway.getEtkTrackingNoPrintFormat(trackingNo);// ETK跟踪号打印格式
		map.put("etkTrackingNo", etkTrackingNo);
		if (StringUtil.isEqual(order.getShipwayCode(), ShipwayCode.ETK)) {
			ShipwayApiAccount shipwayApiAccount = shipwayApiAccountDao.getShipwayApiAccountByUserId(order.getUserIdOfCustomer(), ShipwayCode.ETK);
			if (shipwayApiAccount != null) {
				map.put("customerNo", shipwayApiAccount.getApiAccount());// ETK客户帐号
			}
		}
		String trackingNoBarcodeData = BarcodeUtil.createCode128(trackingNo, true, 11d, null, null, null);
		map.put("trackingNoBarcodeData", trackingNoBarcodeData);

		String trackingNoBarcodeData2 = BarcodeUtil.createCode128(trackingNo, false, 14d, null, null, null);
		map.put("trackingNoBarcodeData2", trackingNoBarcodeData2);
		// 清单号 (出库订单主键)
		map.put("outWarehouseOrderId", String.valueOf(order.getId()));
		map.put("customerReferenceNo", order.getCustomerReferenceNo());
		map.put("tradeRemark", order.getTradeRemark());
		map.put("logisticsRemark", order.getRemark());
		map.put("shipwayExtra1", order.getShipwayExtra1());
		map.put("shipwayExtra2", order.getShipwayExtra2());
		map.put("receiverName", receiver.getName());
		map.put("receiver", receiver);
		map.put("receiverPhoneNumber", receiver.getPhoneNumber());
		map.put("receiverMobileNumber", receiver.getMobileNumber());
		if (StringUtil.isNotNull(receiver.getMobileNumber())) {
			map.put("phoneOrMobileNumber", receiver.getMobileNumber());
		} else {
			map.put("phoneOrMobileNumber", receiver.getPhoneNumber());
		}
		Integer totalQuantity = 0;
		double totalPrice = 0d;
		FirstWaybillItem firstWaybillItemParam = new FirstWaybillItem();
		firstWaybillItemParam.setOrderId(orderId);
		List<Map<String, String>> etkCustoms = new ArrayList<Map<String, String>>();
		List<FirstWaybillItem> items = firstWaybillItemDao.findFirstWaybillItem(firstWaybillItemParam, null, null);
		int i = 0;
		for (FirstWaybillItem item : items) {
			totalQuantity += item.getQuantity();
			totalPrice += item.getSkuUnitPrice();// 目前全部是人民币分
			i++;
			if (i <= 3) {// ETK只打印3行报关信息
				Map<String, String> customs = new HashMap<String, String>();
				String skuName = item.getSkuName();
				if (StringUtil.isNotNull(skuName)) {
					if (skuName.length() > 10) {
						skuName = skuName.substring(0, 10);
					}
				}
				customs.put("skuName", skuName);
				customs.put("customsWeight", "1.01");
				customs.put("customsValue", "2.01");
				customs.put("quantity", "5");
				customs.put("totalvalue", "15");
				etkCustoms.add(customs);
			}
		}
		// ETK标签固定是3行报关信息,所以控制etkCustoms size=3
		if (etkCustoms.size() == 0) {
			Map<String, String> customs = new HashMap<String, String>();
			etkCustoms.add(customs);
		}
		if (etkCustoms.size() == 1) {
			Map<String, String> customs = new HashMap<String, String>();
			etkCustoms.add(customs);
		}
		if (etkCustoms.size() == 2) {
			Map<String, String> customs = new HashMap<String, String>();
			etkCustoms.add(customs);
		}
		map.put("etkCustoms", etkCustoms);
		totalPrice = NumberUtil.div(totalPrice, 100d, 2);
		map.put("currency", "人民币");// 币种目前只有人民币
		map.put("totalPrice", totalPrice);
		map.put("items", items);
		// 寄托物品数量
		map.put("totalQuantity", totalQuantity);
		// 总重量
		map.put("totalWeight", order.getOutWarehouseWeight());
		String shipwayCode = order.getShipwayCode();// 出货渠道
		map.put("shipwayCode", shipwayCode);
		return map;
	}

	@Override
	public Map<String, Object> getPrintTransportPackageListData(Long orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Order order = orderDao.getOrderById(orderId);
		// 未完成收货不能打印捡货单
		if (StringUtil.isEqual(order.getStatus(), OrderStatusCode.WWC) || StringUtil.isEqual(order.getStatus(), OrderStatusCode.WCI) || StringUtil.isEqual(order.getStatus(), OrderStatusCode.WCF)
				|| StringUtil.isEqual(order.getStatus(), OrderStatusCode.WRG) || StringUtil.isEqual(order.getStatus(), OrderStatusCode.WRP)) {
			return null;
		}
		// 修改状态到等待称重打单
		orderDao.updateOrderStatus(orderId, OrderStatusCode.WWW);

		OrderReceiver receiver = orderReceiverDao.getOrderReceiverByPackageId(orderId);
		map.put("customerReferenceNo", order.getCustomerReferenceNo());
		OrderAdditionalSf orderAdditionalSf = orderAdditionalSfDao.getOrderAdditionalSfByOrderId(orderId);
		if (orderAdditionalSf != null) {
			map.put("customerOrderNo", orderAdditionalSf.getCustomerOrderId());
		}
		// 创建图片
		String customerReferenceNoBarcodeData = BarcodeUtil.createCode128(order.getCustomerReferenceNo(), true, 12d, null, null, null);
		map.put("customerReferenceNoBarcodeData", customerReferenceNoBarcodeData);
		map.put("tradeRemark", order.getTradeRemark());
		map.put("trackingNo", order.getTrackingNo());
		map.put("logisticsRemark", order.getRemark());
		map.put("receiverName", receiver.getName());
		map.put("receiverPhoneNumber", receiver.getPhoneNumber());
		map.put("receiverMobileNumber", receiver.getMobileNumber());
		map.put("receiverMobileNumber", receiver.getMobileNumber());

		FirstWaybillOnShelf firstWaybillOnShelfParam = new FirstWaybillOnShelf();
		firstWaybillOnShelfParam.setOrderId(orderId);
		List<FirstWaybillOnShelf> firstWaybillOnShelfList = firstWaybillOnShelfDao.findFirstWaybillOnShelf(firstWaybillOnShelfParam, null, null);
		// 商品总数
		int totalQuantity = 0;
		double totalPrice = 0d;
		for (FirstWaybillOnShelf firstWaybillOnShelf : firstWaybillOnShelfList) {
			FirstWaybillItem firstWaybillItem = new FirstWaybillItem();
			firstWaybillItem.setFirstWaybillId(firstWaybillOnShelf.getFirstWaybillId());
			List<FirstWaybillItem> firstWaybillItemList = firstWaybillItemDao.findFirstWaybillItem(firstWaybillItem, null, null);
			for (FirstWaybillItem item : firstWaybillItemList) {
				totalQuantity += item.getQuantity();
				if (item.getSkuUnitPrice() != null) {
					totalPrice += (item.getQuantity() * item.getSkuUnitPrice() / 100);
				}
			}
			firstWaybillOnShelf.setFirstWaybillItemList(firstWaybillItemList);
		}
		map.put("totalQuantity", totalQuantity);
		map.put("totalPrice", totalPrice);
		map.put("firstWaybillOnShelfList", firstWaybillOnShelfList);
		return map;
	}

	@Override
	public List<Map<String, String>> getPrintTransportEIRData(Long coeTrackingNoId) {
		com.coe.wms.model.warehouse.transport.OutWarehousePackageItem itemParam = new com.coe.wms.model.warehouse.transport.OutWarehousePackageItem();
		itemParam.setCoeTrackingNoId(coeTrackingNoId);
		List<com.coe.wms.model.warehouse.transport.OutWarehousePackageItem> itemParamList = transportPackageItemDao.findOutWarehousePackageItem(itemParam, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (com.coe.wms.model.warehouse.transport.OutWarehousePackageItem item : itemParamList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("ourWarehouseOrderTrackingNo", item.getOrderTrackingNo());
			Long outWarehouseOrderId = item.getOrderId();
			Order order = orderDao.getOrderById(outWarehouseOrderId);
			if (order == null) {
				continue;
			}
			map.put("warehouseId", order.getWarehouseId() + "");
			map.put("customerReferenceNo", order.getCustomerReferenceNo());
			map.put("outWarehouseWeight", order.getOutWarehouseWeight() + "");
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 打印coe交接单,根据建包记录
	 */
	@Override
	public Map<String, Object> getPrintTransportCoeLabelData(Long coeTrackingNoId) {
		Map<String, Object> map = new HashMap<String, Object>();
		com.coe.wms.model.warehouse.transport.OutWarehousePackage recordParam = new com.coe.wms.model.warehouse.transport.OutWarehousePackage();
		recordParam.setCoeTrackingNoId(coeTrackingNoId);
		List<com.coe.wms.model.warehouse.transport.OutWarehousePackage> packageRecordList = transportPackageDao.findOutWarehousePackage(recordParam, null, null);
		if (packageRecordList == null || packageRecordList.size() == 0) {
			return map;
		}
		com.coe.wms.model.warehouse.transport.OutWarehousePackage record = packageRecordList.get(0);
		// 出貨詳情
		com.coe.wms.model.warehouse.transport.OutWarehousePackageItem recordItemParam = new com.coe.wms.model.warehouse.transport.OutWarehousePackageItem();
		recordItemParam.setCoeTrackingNoId(coeTrackingNoId);
		List<com.coe.wms.model.warehouse.transport.OutWarehousePackageItem> packageRecordItemList = transportPackageItemDao.findOutWarehousePackageItem(recordItemParam, null, null);
		// 總重量
		double totalWeight = 0d;
		int quantity = 0;
		for (com.coe.wms.model.warehouse.transport.OutWarehousePackageItem item : packageRecordItemList) {
			Order order = orderDao.getOrderById(item.getOrderId());
			totalWeight += order.getOutWarehouseWeight();
			quantity++;
		}
		String trackingNoBarcodeData = BarcodeUtil.createCode128(record.getCoeTrackingNo(), false, 29d, null, null, null);
		map.put("coeTrackingNoBarcodeData", trackingNoBarcodeData);
		map.put("totalWeight", NumberUtil.getNumPrecision(totalWeight, 3));
		map.put("quantity", quantity);
		map.put("outWarehouseRecord", record);
		if (record.getShippedTime() != null) {
			map.put("shipdate", DateUtil.dateConvertString(new Date(record.getCreatedTime()), DateUtil.yyyy_MM_dd));
		} else {
			map.put("shipdate", DateUtil.dateConvertString(new Date(record.getCreatedTime()), DateUtil.yyyy_MM_dd));
		}
		return map;
	}
}
