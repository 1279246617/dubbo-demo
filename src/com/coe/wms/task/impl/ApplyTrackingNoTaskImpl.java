package com.coe.wms.task.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.coe.etk.api.Client;
import com.coe.etk.api.request.Item;
import com.coe.etk.api.request.Order;
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
import com.coe.wms.dao.warehouse.transport.IBigPackageAdditionalSfDao;
import com.coe.wms.dao.warehouse.transport.IBigPackageDao;
import com.coe.wms.dao.warehouse.transport.IBigPackageReceiverDao;
import com.coe.wms.dao.warehouse.transport.IBigPackageSenderDao;
import com.coe.wms.dao.warehouse.transport.IBigPackageStatusDao;
import com.coe.wms.dao.warehouse.transport.ILittlePackageDao;
import com.coe.wms.dao.warehouse.transport.ILittlePackageItemDao;
import com.coe.wms.dao.warehouse.transport.ILittlePackageStatusDao;
import com.coe.wms.model.unit.Currency.CurrencyCode;
import com.coe.wms.model.warehouse.Shipway.ShipwayCode;
import com.coe.wms.model.warehouse.transport.BigPackage;
import com.coe.wms.model.warehouse.transport.BigPackageReceiver;
import com.coe.wms.model.warehouse.transport.BigPackageSender;
import com.coe.wms.model.warehouse.transport.LittlePackageItem;
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

	/**
	 * 
	 * 2点,仓配订单申请单号
	 * 
	 */
	@Scheduled(cron = "0 0 3 * * ? ")
	@Override
	public void storageOrderApplyTrackingNo() {

	}

	/**
	 * 转运订单申请单号
	 */
	@Scheduled(cron = "0 30 3 * * ? ")
	@Override
	public void transportOrderApplyTrackingNo() {
		// 1出单号为空,并且状态是未审核
		List<Long> bigPackageIds = bigPackageDao.findUnCheckAndTackingNoIsNullBigPackageId();
		for (int i = 0; i < bigPackageIds.size(); i++) {
			Long bigPackageId = bigPackageIds.get(i);
			BigPackage bigPackage = bigPackageDao.getBigPackageById(bigPackageId);
			BigPackageReceiver bigPackageReceiver = bigPackageReceiverDao.getBigPackageReceiverByPackageId(bigPackageId);
			BigPackageSender bigPackageSender = bigPackageSenderDao.getBigPackageSenderByPackageId(bigPackageId);
			// 出货渠道是ETK
			if (StringUtil.isEqual(bigPackage.getShipwayCode(), ShipwayCode.ETK)) {
				Order order = new Order();
				order.setCurrency(CurrencyCode.CNY);
				order.setCustomerNo("sam");// 测试
				order.setReferenceId(bigPackage.getCustomerReferenceNo());// 客户参考号
				LittlePackageItem itemParam = new LittlePackageItem();
				itemParam.setBigPackageId(bigPackageId);
				List<LittlePackageItem> littlePackageItems = littlePackageItemDao.findLittlePackageItem(itemParam, null, null);
				List<Item> items = new ArrayList<Item>();
				for (LittlePackageItem littlePackageItem : littlePackageItems) {
					Item item = new Item();
					item.setItemDescription(littlePackageItem.getSkuName());// 报关描述
					double price = 10d;// 报关价值
					if (littlePackageItem.getSkuUnitPrice() != null) {
						price = NumberUtil.div(littlePackageItem.getSkuUnitPrice(), 100d, 2);// 分转元
					}
					item.setItemPrice(price);
					item.setItemQuantity(littlePackageItem.getQuantity() == null ? 1 : littlePackageItem.getQuantity());// 报关数量
					double weight = 0.1d;// 报关重量
					if (littlePackageItem.getSkuNetWeight() != null) {
						weight = littlePackageItem.getSkuNetWeight();
					}
					item.setItemWeight(weight);
					items.add(item);
				}
				order.setItems(items);
				// 收件人
				Receiver receiver = new Receiver();
				receiver.setReceiverAddress1(bigPackageReceiver.getAddressLine1());
				receiver.setReceiverAddress2(bigPackageReceiver.getAddressLine2());
				receiver.setReceiverCity(bigPackageReceiver.getCity());
				receiver.setReceiverCode(bigPackageReceiver.getPostalCode());
				receiver.setReceiverName(bigPackageReceiver.getName());
				receiver.setReceiverCountry(bigPackageReceiver.getCountryCode());
				receiver.setReceiverPhone(bigPackageReceiver.getPhoneNumber());
				receiver.setReceiverProvince(bigPackageReceiver.getStateOrProvince());
				order.setReceiver(receiver);
				// 发件人
				Sender sender = new Sender();
				sender.setSenderAddress(bigPackageSender.getAddressLine1());
				sender.setSenderName(bigPackageSender.getName());
				sender.setSenderPhone(bigPackageReceiver.getPhoneNumber());
				order.setSender(sender);

				Client client = new Client();
				client.setToken("c587efdfcb6e4cd3");
				client.setTokenKey("b5e3d9769218deb3");
				client.setUrl("http://58.96.174.216:8080/coeimport/orderApi");
				client.applyTrackingNo(order);
			}
		}
	}

}
