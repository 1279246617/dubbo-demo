package com.coe.wms.service.print.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.user.IUserDao;
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
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderAdditionalSf;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItemShelf;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus.OutWarehouseOrderStatusCode;
import com.coe.wms.model.warehouse.storage.record.ItemShelfInventory;
import com.coe.wms.model.warehouse.storage.record.OnShelf;
import com.coe.wms.service.print.IPrintService;
import com.coe.wms.util.BarcodeUtil;
import com.coe.wms.util.Constant;
import com.coe.wms.util.StringUtil;

@Service("printService")
public class PrintServiceImpl implements IPrintService {

	private static final Logger logger = Logger.getLogger(PrintServiceImpl.class);

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

	@Resource(name = "inWarehouseRecordItemDao")
	private IInWarehouseRecordItemDao inWarehouseRecordItemDao;

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Resource(name = "itemInventoryDao")
	private IItemInventoryDao itemInventoryDao;

	@Resource(name = "itemShelftInventoryDao")
	private IItemShelfInventoryDao itemShelftInventoryDao;

	@Resource(name = "onShelfDao")
	private IOnShelfDao onShelfDao;

	@Override
	public Map<String, Object> getPrintPackageListData(Long outWarehouseOrderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderDao.getOutWarehouseOrderById(outWarehouseOrderId);
		// 等待仓库审核的订单 不能打印捡货单
		if (StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WWC)) {
			return null;
		}
		OutWarehouseOrderReceiver receiver = outWarehouseOrderReceiverDao.getOutWarehouseOrderReceiverByOrderId(outWarehouseOrderId);
		// 清单号 (出库订单主键)
		map.put("outWarehouseOrderId", String.valueOf(outWarehouseOrder.getId()));
		map.put("customerReferenceNo", outWarehouseOrder.getCustomerReferenceNo());
		// 创建图片
		String customerReferenceNoBarcodeData = BarcodeUtil.createCode128(outWarehouseOrder.getCustomerReferenceNo(), true, 12d);
		map.put("customerReferenceNoBarcodeData", customerReferenceNoBarcodeData);
		map.put("tradeRemark", outWarehouseOrder.getTradeRemark());
		map.put("logisticsRemark", outWarehouseOrder.getLogisticsRemark());
		map.put("receiverName", receiver.getName());
		map.put("receiverPhoneNumber", receiver.getPhoneNumber());
		map.put("receiverMobileNumber", receiver.getMobileNumber());
		int totalQuantity = 0;
		map.put("receiverMobileNumber", receiver.getMobileNumber());

		OutWarehouseOrderItem itemParam = new OutWarehouseOrderItem();
		itemParam.setOutWarehouseOrderId(outWarehouseOrderId);
		List<OutWarehouseOrderItem> items = outWarehouseOrderItemDao.findOutWarehouseOrderItem(itemParam, null, null);
		// 此出库订单是否已经打印过捡货单,用于判断是否需要预扣上架数量, true-需要, false-不需要
		boolean isPrinted = StringUtil.isEqual(Constant.Y, outWarehouseOrder.getIsPrinted());
		// 根据出库订单物品 SKU和数量,按批次,SKU查找上架表
		List<OutWarehouseOrderItemShelf> itemShelfList = new ArrayList<OutWarehouseOrderItemShelf>();
		if (!isPrinted) {
			for (OutWarehouseOrderItem item : items) {
				List<ItemShelfInventory> itemShelfInventoryList = itemShelftInventoryDao.findItemShelfInventoryForPreOutShelf(outWarehouseOrder.getUserIdOfCustomer(), outWarehouseOrder.getWarehouseId(), item.getSku());
				int needQuantity = item.getQuantity();// 需要预下架的产品数量
				for (ItemShelfInventory itemShelfInventory : itemShelfInventoryList) {
					Map<String, String> itemMap = new HashMap<String, String>();
					int quantity = 0;
					// 该库位的可用库存数量
					int availableQuantity = itemShelfInventory.getAvailableQuantity();
					boolean isBreak = false;
					if (availableQuantity > needQuantity) {
						// 如果此库位的可用库存大于需要的数量
						itemMap.put("quantity", needQuantity + "");
						quantity = needQuantity;
						// 无需再找下一个库位,更新可用库存
						availableQuantity = availableQuantity - needQuantity;
						isBreak = true;
					} else {
						// 否则此库位的可用库存全部使用,并继续找下一库位
						itemMap.put("quantity", availableQuantity + "");
						quantity = availableQuantity;
						// 需要下架的产品减去此货架可用库存
						needQuantity = needQuantity - availableQuantity;
						// 更新可用库存=0
						availableQuantity = 0;
						if (needQuantity <= 0) {
							isBreak = true;
						}
					}
					// 执行更新可用库存
					itemShelftInventoryDao.updateItemShelfInventoryAvailableQuantity(itemShelfInventory.getId(), availableQuantity);
					// 打印捡货单,记录出库订单对应的货位和物品.下次打印时 使用已经保存的货位和物品信息
					OutWarehouseOrderItemShelf itemShelf = OutWarehouseOrderItemShelf.createOutWarehouseOrderItemShelf(outWarehouseOrderId, quantity, itemShelfInventory.getSeatCode(), item.getSku(), item.getSkuName(), item.getSkuNetWeight(),
							item.getSkuPriceCurrency(), item.getSkuUnitPrice());
					itemShelfList.add(itemShelf);
					if (isBreak) {
						break;
					}
				}
			}
			// 更新为已经打印,并保存货位信息
			outWarehouseOrderItemShelfDao.saveBatchOutWarehouseOrderItemShelf(itemShelfList);
			outWarehouseOrderDao.updateOutWarehouseOrderIsPrinted(outWarehouseOrderId, Constant.Y);
		} else {
			// 如果已经打印过捡货单, 从上次打印保存的货位和SKU,数量信息 取出,返回前台
			OutWarehouseOrderItemShelf itemShelfParam = new OutWarehouseOrderItemShelf();
			itemShelfParam.setOutWarehouseOrderId(outWarehouseOrderId);
			itemShelfList = outWarehouseOrderItemShelfDao.findOutWarehouseOrderItemShelf(itemShelfParam, null, null);
		}

		map.put("items", itemShelfList);
		// 商品总数
		for (OutWarehouseOrderItemShelf itemShelfParam : itemShelfList) {
			totalQuantity += itemShelfParam.getQuantity();
		}
		map.put("totalQuantity", totalQuantity);
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
		// 创建条码
		String trackingNo = outWarehouseOrder.getTrackingNo();
		String trackingNoBarcodeData = BarcodeUtil.createCode128C(trackingNo, true, 53d);
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
}
