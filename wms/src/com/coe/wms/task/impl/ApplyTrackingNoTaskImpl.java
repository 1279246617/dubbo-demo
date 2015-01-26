package com.coe.wms.task.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.coe.etk.api.Client;
import com.coe.etk.api.request.Item;
import com.coe.etk.api.request.Receiver;
import com.coe.etk.api.request.Sender;
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
import com.coe.wms.dao.warehouse.transport.IFirstWaybillDao;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillItemDao;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillStatusDao;
import com.coe.wms.dao.warehouse.transport.IOrderAdditionalSfDao;
import com.coe.wms.dao.warehouse.transport.IOrderDao;
import com.coe.wms.dao.warehouse.transport.IOrderReceiverDao;
import com.coe.wms.dao.warehouse.transport.IOrderSenderDao;
import com.coe.wms.dao.warehouse.transport.IOrderStatusDao;
import com.coe.wms.model.unit.Currency.CurrencyCode;
import com.coe.wms.model.warehouse.shipway.Shipway.ShipwayCode;
import com.coe.wms.model.warehouse.transport.FirstWaybillItem;
import com.coe.wms.model.warehouse.transport.Order;
import com.coe.wms.model.warehouse.transport.OrderReceiver;
import com.coe.wms.model.warehouse.transport.OrderSender;
import com.coe.wms.task.IApplyTrackingNoTask;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.StringUtil;

@Component
public class ApplyTrackingNoTaskImpl implements IApplyTrackingNoTask {

	private static final Logger logger = Logger.getLogger(ApplyTrackingNoTaskImpl.class);

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

	/**
	 * 
	 * 2点,仓配订单申请单号
	 * 
	 */
//	@Scheduled(cron = "0 0 3 * * ? ")
	@Override
	public void storageOrderApplyTrackingNo() {

	}

	/**
	 * 转运订单申请单号
	 */
//	@Scheduled(cron = "0 30 3 * * ? ")
	@Override
	public void transportOrderApplyTrackingNo() {
		// 1出单号为空,并且状态是未审核
		List<Long> orderIds = orderDao.findUnCheckAndTackingNoIsNullOrderId();
		for (int i = 0; i < orderIds.size(); i++) {
			Long orderId = orderIds.get(i);
			Order order = orderDao.getOrderById(orderId);
			OrderReceiver orderReceiver = orderReceiverDao.getOrderReceiverByOrderId(orderId);
			OrderSender orderSender = orderSenderDao.getOrderSenderByOrderId(orderId);
			// 出货渠道是ETK
			if (StringUtil.isEqual(order.getShipwayCode(), ShipwayCode.ETK)) {
				com.coe.etk.api.request.Order apiOrder = new com.coe.etk.api.request.Order();
				apiOrder.setCurrency(CurrencyCode.CNY);
				apiOrder.setCustomerNo("sam");// 测试
				apiOrder.setReferenceId(order.getCustomerReferenceNo());// 客户参考号
				FirstWaybillItem itemParam = new FirstWaybillItem();
				itemParam.setOrderId(orderId);
				List<FirstWaybillItem> firstWaybillItems = firstWaybillItemDao.findFirstWaybillItem(itemParam, null, null);
				List<Item> items = new ArrayList<Item>();
				for (FirstWaybillItem firstWaybillItem : firstWaybillItems) {
					Item item = new Item();
					item.setItemDescription(firstWaybillItem.getSkuName());// 报关描述
					double price = 10d;// 报关价值
					if (firstWaybillItem.getSkuUnitPrice() != null) {
						price = NumberUtil.div(firstWaybillItem.getSkuUnitPrice(), 100d, 2);// 分转元
					}
					item.setItemPrice(price);
					item.setItemQuantity(firstWaybillItem.getQuantity() == null ? 1 : firstWaybillItem.getQuantity());// 报关数量
					double weight = 0.1d;// 报关重量
					if (firstWaybillItem.getSkuNetWeight() != null) {
						weight = firstWaybillItem.getSkuNetWeight();
					}
					item.setItemWeight(weight);
					items.add(item);
				}
				apiOrder.setItems(items);
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
				apiOrder.setReceiver(receiver);
				// 发件人
				Sender sender = new Sender();
				sender.setSenderAddress(orderSender.getAddressLine1());
				sender.setSenderName(orderSender.getName());
				sender.setSenderPhone(orderReceiver.getPhoneNumber());
				apiOrder.setSender(sender);

				Client client = new Client();
				client.setToken("c587efdfcb6e4cd3");
				client.setTokenKey("b5e3d9769218deb3");
				client.setUrl("http://58.96.174.216:8080/coeimport/orderApi");
				client.applyTrackingNo(apiOrder);
			}
		}
	}

}
