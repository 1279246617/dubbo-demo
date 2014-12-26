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
import com.coe.wms.dao.warehouse.storage.IOutWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseRecordItemDao;
import com.coe.wms.dao.warehouse.transport.IBigPackageAdditionalSfDao;
import com.coe.wms.dao.warehouse.transport.IBigPackageDao;
import com.coe.wms.dao.warehouse.transport.IBigPackageReceiverDao;
import com.coe.wms.dao.warehouse.transport.IBigPackageSenderDao;
import com.coe.wms.dao.warehouse.transport.IBigPackageStatusDao;
import com.coe.wms.dao.warehouse.transport.ILittlePackageDao;
import com.coe.wms.dao.warehouse.transport.ILittlePackageItemDao;
import com.coe.wms.dao.warehouse.transport.ILittlePackageOnShelfDao;
import com.coe.wms.dao.warehouse.transport.ILittlePackageStatusDao;
import com.coe.wms.dao.warehouse.transport.IPackageRecordDao;
import com.coe.wms.dao.warehouse.transport.IPackageRecordItemDao;
import com.coe.wms.model.warehouse.Seat;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderAdditionalSf;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItemShelf;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderSender;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus.OutWarehouseOrderStatusCode;
import com.coe.wms.model.warehouse.storage.record.OutWarehousePackage;
import com.coe.wms.model.warehouse.storage.record.OutWarehouseRecordItem;
import com.coe.wms.model.warehouse.transport.BigPackage;
import com.coe.wms.model.warehouse.transport.BigPackageAdditionalSf;
import com.coe.wms.model.warehouse.transport.BigPackageReceiver;
import com.coe.wms.model.warehouse.transport.BigPackageSender;
import com.coe.wms.model.warehouse.transport.BigPackageStatus.BigPackageStatusCode;
import com.coe.wms.model.warehouse.transport.LittlePackage;
import com.coe.wms.model.warehouse.transport.LittlePackageItem;
import com.coe.wms.model.warehouse.transport.LittlePackageOnShelf;
import com.coe.wms.model.warehouse.transport.PackageRecord;
import com.coe.wms.model.warehouse.transport.PackageRecordItem;
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

	@Resource(name = "outWarehouseRecordItemDao")
	private IOutWarehouseRecordItemDao outWarehouseRecordItemDao;

	@Resource(name = "outWarehouseRecordDao")
	private IOutWarehouseRecordDao outWarehouseRecordDao;

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

	@Resource(name = "bigPackageAdditionalSfDao")
	private IBigPackageAdditionalSfDao bigPackageAdditionalSfDao;

	@Resource(name = "bigPackageDao")
	private IBigPackageDao bigPackageDao;

	@Resource(name = "bigPackageReceiverDao")
	private IBigPackageReceiverDao bigPackageReceiverDao;

	@Resource(name = "bigPackageSenderDao")
	private IBigPackageSenderDao bigPackageSenderDao;

	@Resource(name = "bigPackageStatusDao")
	private IBigPackageStatusDao bigPackageStatusDao;

	@Resource(name = "littlePackageItemDao")
	private ILittlePackageItemDao littlePackageItemDao;

	@Resource(name = "littlePackageDao")
	private ILittlePackageDao littlePackageDao;

	@Resource(name = "littlePackageStatusDao")
	private ILittlePackageStatusDao littlePackageStatusDao;

	@Resource(name = "littlePackageOnShelfDao")
	private ILittlePackageOnShelfDao littlePackageOnShelfDao;

	@Resource(name = "packageRecordDao")
	private IPackageRecordDao packageRecordDao;

	@Resource(name = "packageRecordItemDao")
	private IPackageRecordItemDao packageRecordItemDao;

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
		String customerReferenceNoBarcodeData = BarcodeUtil.createCode128(outWarehouseOrder.getCustomerReferenceNo(), true, 12d, null);
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
	public Map<String, Object> getPrintShipLabelData(Long outWarehouseOrderId) {
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
		// 创建条码
		String trackingNo = outWarehouseOrder.getTrackingNo();
		String trackingNoBarcodeData = BarcodeUtil.createCode128(trackingNo, true, 11d, null);
		map.put("trackingNoBarcodeData", trackingNoBarcodeData);
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
			String seatCodeBarcodeData = BarcodeUtil.createCode128(seat.getSeatCode(), false, 16d, 0.5d);
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
		OutWarehouseRecordItem outWarehouseShippingParam = new OutWarehouseRecordItem();
		outWarehouseShippingParam.setCoeTrackingNoId(coeTrackingNoId);
		List<OutWarehouseRecordItem> outWarehouseShippingList = outWarehouseRecordItemDao.findOutWarehouseRecordItem(outWarehouseShippingParam, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (OutWarehouseRecordItem outWarehouseShipping : outWarehouseShippingList) {
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
		OutWarehouseRecordItem outWarehouseShippingParam = new OutWarehouseRecordItem();
		outWarehouseShippingParam.setCoeTrackingNoId(coeTrackingNoId);
		List<OutWarehouseRecordItem> outWarehouseShippingList = outWarehouseRecordItemDao.findOutWarehouseRecordItem(outWarehouseShippingParam, null, null);
		// 總重量
		double totalWeight = 0d;
		int quantity = 0;
		for (OutWarehouseRecordItem outWarehouseShipping : outWarehouseShippingList) {
			OutWarehouseOrder outWarehouseOrder = outWarehouseOrderDao.getOutWarehouseOrderById(outWarehouseShipping.getOutWarehouseOrderId());
			totalWeight += outWarehouseOrder.getOutWarehouseWeight();
			quantity++;
		}
		String trackingNoBarcodeData = BarcodeUtil.createCode128(outWarehousePackage.getCoeTrackingNo(), false, 29d, 0.5d);
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
		String skuBarcodeData = BarcodeUtil.createCode128(sku, false, 9d, null);
		map.put("skuBarcodeData", skuBarcodeData);
		map.put("sku", sku);
		return map;
	}

	@Override
	public Map<String, Object> getPrintTransportShipLabedData(Long bigPackageId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.STATUS, Constant.FAIL);
		BigPackage bigPackage = bigPackageDao.getBigPackageById(bigPackageId);
		if (StringUtil.isEqual(bigPackage.getStatus(), BigPackageStatusCode.WWC) || StringUtil.isEqual(bigPackage.getStatus(), BigPackageStatusCode.WCI) || StringUtil.isEqual(bigPackage.getStatus(), BigPackageStatusCode.WCF)
				|| StringUtil.isEqual(bigPackage.getStatus(), BigPackageStatusCode.WRG) || StringUtil.isEqual(bigPackage.getStatus(), BigPackageStatusCode.WRP)) {
			return null;
		}
		BigPackageReceiver receiver = bigPackageReceiverDao.getBigPackageReceiverByPackageId(bigPackageId);
		BigPackageSender sender = bigPackageSenderDao.getBigPackageSenderByPackageId(bigPackageId);
		LittlePackage littlePackageParam = new LittlePackage();
		littlePackageParam.setBigPackageId(bigPackageId);
		BigPackageAdditionalSf additionalSf = bigPackageAdditionalSfDao.getBigPackageAdditionalSfByPackageId(bigPackageId);
		map.put("additionalSf", additionalSf);
		map.put("sender", sender);
		// 创建条码
		String trackingNo = bigPackage.getTrackingNo();
		if (StringUtil.isNull(trackingNo)) {
			return null;
		}
		String trackingNoBarcodeData = BarcodeUtil.createCode128(trackingNo, true, 11d, null);
		map.put("trackingNoBarcodeData", trackingNoBarcodeData);
		// 清单号 (出库订单主键)
		map.put("outWarehouseOrderId", String.valueOf(bigPackage.getId()));
		map.put("customerReferenceNo", bigPackage.getCustomerReferenceNo());
		map.put("tradeRemark", bigPackage.getTradeRemark());
		map.put("logisticsRemark", bigPackage.getRemark());
		map.put("receiverName", receiver.getName());
		map.put("receiver", receiver);
		map.put("receiverPhoneNumber", receiver.getPhoneNumber());
		map.put("receiverMobileNumber", receiver.getMobileNumber());
		Integer totalQuantity = 0;

		LittlePackageItem littlePackageItemParam = new LittlePackageItem();
		littlePackageItemParam.setBigPackageId(bigPackageId);
		List<LittlePackageItem> items = littlePackageItemDao.findLittlePackageItem(littlePackageItemParam, null, null);
		for (LittlePackageItem item : items) {
			totalQuantity += item.getQuantity();
		}
		map.put("items", items);
		// 寄托物品数量
		map.put("totalQuantity", totalQuantity);
		// 总重量
		map.put("totalWeight", bigPackage.getOutWarehouseWeight());
		String shipwayCode = bigPackage.getShipwayCode();// 出货渠道
		map.put("shipwayCode", shipwayCode);
		return map;
	}

	@Override
	public Map<String, Object> getPrintTransportPackageListData(Long bigPackageId) {
		Map<String, Object> map = new HashMap<String, Object>();
		BigPackage bigPackage = bigPackageDao.getBigPackageById(bigPackageId);
		// 未完成收货不能打印捡货单
		if (StringUtil.isEqual(bigPackage.getStatus(), BigPackageStatusCode.WWC) || StringUtil.isEqual(bigPackage.getStatus(), BigPackageStatusCode.WCI) || StringUtil.isEqual(bigPackage.getStatus(), BigPackageStatusCode.WCF)
				|| StringUtil.isEqual(bigPackage.getStatus(), BigPackageStatusCode.WRG) || StringUtil.isEqual(bigPackage.getStatus(), BigPackageStatusCode.WRP)) {
			return null;
		}
		// 修改状态到等待称重打单
		bigPackageDao.updateBigPackageStatus(bigPackageId, BigPackageStatusCode.WWW);

		BigPackageReceiver receiver = bigPackageReceiverDao.getBigPackageReceiverByPackageId(bigPackageId);
		map.put("customerReferenceNo", bigPackage.getCustomerReferenceNo());
		BigPackageAdditionalSf bigPackageAdditionalSf = bigPackageAdditionalSfDao.getBigPackageAdditionalSfByPackageId(bigPackageId);
		if (bigPackageAdditionalSf != null) {
			map.put("customerOrderNo", bigPackageAdditionalSf.getCustomerOrderId());
		}
		// 创建图片
		String customerReferenceNoBarcodeData = BarcodeUtil.createCode128(bigPackage.getCustomerReferenceNo(), true, 12d, null);
		map.put("customerReferenceNoBarcodeData", customerReferenceNoBarcodeData);
		map.put("tradeRemark", bigPackage.getTradeRemark());
		map.put("trackingNo", bigPackage.getTrackingNo());
		map.put("logisticsRemark", bigPackage.getRemark());
		map.put("receiverName", receiver.getName());
		map.put("receiverPhoneNumber", receiver.getPhoneNumber());
		map.put("receiverMobileNumber", receiver.getMobileNumber());
		map.put("receiverMobileNumber", receiver.getMobileNumber());

		LittlePackageOnShelf littlePackageOnShelfParam = new LittlePackageOnShelf();
		littlePackageOnShelfParam.setBigPackageId(bigPackageId);
		List<LittlePackageOnShelf> littlePackageOnShelfList = littlePackageOnShelfDao.findLittlePackageOnShelf(littlePackageOnShelfParam, null, null);
		// 商品总数
		int totalQuantity = 0;
		double totalPrice = 0d;
		for (LittlePackageOnShelf littlePackageOnShelf : littlePackageOnShelfList) {
			LittlePackageItem littlePackageItem = new LittlePackageItem();
			littlePackageItem.setLittlePackageId(littlePackageOnShelf.getLittlePackageId());
			List<LittlePackageItem> littlePackageItemList = littlePackageItemDao.findLittlePackageItem(littlePackageItem, null, null);
			for (LittlePackageItem item : littlePackageItemList) {
				totalQuantity += item.getQuantity();
				if (item.getSkuUnitPrice() != null) {
					totalPrice += (item.getQuantity() * item.getSkuUnitPrice() / 100);
				}
			}
			littlePackageOnShelf.setLittlePackageItemList(littlePackageItemList);
		}
		map.put("totalQuantity", totalQuantity);
		map.put("totalPrice", totalPrice);
		map.put("littlePackageOnShelfList", littlePackageOnShelfList);
		return map;
	}

	@Override
	public List<Map<String, String>> getPrintTransportEIRData(Long coeTrackingNoId) {
		PackageRecordItem itemParam = new PackageRecordItem();
		itemParam.setCoeTrackingNoId(coeTrackingNoId);
		List<PackageRecordItem> itemParamList = packageRecordItemDao.findPackageRecordItem(itemParam, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (PackageRecordItem item : itemParamList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("ourWarehouseOrderTrackingNo", item.getBigPackageTrackingNo());
			Long outWarehouseOrderId = item.getBigPackageId();
			BigPackage bigPackage = bigPackageDao.getBigPackageById(outWarehouseOrderId);
			if (bigPackage == null) {
				continue;
			}
			map.put("warehouseId", bigPackage.getWarehouseId() + "");
			map.put("customerReferenceNo", bigPackage.getCustomerReferenceNo());
			map.put("outWarehouseWeight", bigPackage.getOutWarehouseWeight() + "");
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
		PackageRecord recordParam = new PackageRecord();
		recordParam.setCoeTrackingNoId(coeTrackingNoId);
		List<PackageRecord> packageRecordList = packageRecordDao.findPackageRecord(recordParam, null, null);
		if (packageRecordList == null || packageRecordList.size() == 0) {
			return map;
		}
		PackageRecord record = packageRecordList.get(0);
		// 出貨詳情
		PackageRecordItem recordItemParam = new PackageRecordItem();
		recordItemParam.setCoeTrackingNoId(coeTrackingNoId);
		List<PackageRecordItem> packageRecordItemList = packageRecordItemDao.findPackageRecordItem(recordItemParam, null, null);
		// 總重量
		double totalWeight = 0d;
		int quantity = 0;
		for (PackageRecordItem item : packageRecordItemList) {
			BigPackage bigPackage = bigPackageDao.getBigPackageById(item.getBigPackageId());
			totalWeight += bigPackage.getOutWarehouseWeight();
			quantity++;
		}
		String trackingNoBarcodeData = BarcodeUtil.createCode128(record.getCoeTrackingNo(), false, 29d, 0.5d);
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
