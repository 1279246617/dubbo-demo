package com.coe.wms.service.directprint.impl;

import java.util.ArrayList;
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
import com.coe.wms.model.warehouse.shipway.Shipway;
import com.coe.wms.model.warehouse.shipway.ShipwayApiAccount;
import com.coe.wms.model.warehouse.shipway.Shipway.ShipwayCode;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderAdditionalSf;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderSender;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus.OutWarehouseOrderStatusCode;
import com.coe.wms.service.directprint.IDirectPrintService;
import com.coe.wms.service.print.impl.PrintServiceImpl;
import com.coe.wms.util.BarcodeUtil;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.StringUtil;

@Service("directPrintService")
public class DirectPrintServicImpl implements IDirectPrintService {

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
	public Map<String, Object> getStorageSfShipLabelData(Long outWarehouseOrderId, Double height, Double barcodeScale) {
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
		// 顺丰标签使用
		String trackingNoBarcodeData = BarcodeUtil.createCode128Auto(trackingNo, false, 23d, 2d);
		map.put("trackingNoBarcodeData", trackingNoBarcodeData);
		map.put("shipwayExtra1", outWarehouseOrder.getShipwayExtra1());
		map.put("shipwayExtra2", outWarehouseOrder.getShipwayExtra2());
		// 清单号 (出库订单主键)
		map.put("outWarehouseOrderId", String.valueOf(outWarehouseOrder.getId()));
		map.put("customerReferenceNo", outWarehouseOrder.getCustomerReferenceNo());
		map.put("trackingNo", outWarehouseOrder.getTrackingNo());
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
		double totalPrice = 0;
		totalPrice = NumberUtil.div(totalPrice, 100d, 2);
		map.put("currency", "人民币");// 币种目前只有人民币
		map.put("totalPrice", totalPrice);
		return map;
	}

}
