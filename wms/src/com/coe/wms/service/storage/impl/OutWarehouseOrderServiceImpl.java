package com.coe.wms.service.storage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.etk.api.Client;
import com.coe.etk.api.request.Order;
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
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordStatusDao;
import com.coe.wms.dao.warehouse.storage.IItemInventoryDao;
import com.coe.wms.dao.warehouse.storage.IItemShelfInventoryDao;
import com.coe.wms.dao.warehouse.storage.IOnShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutShelfDao;
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
import com.coe.wms.dao.warehouse.storage.IReportDao;
import com.coe.wms.dao.warehouse.storage.IReportTypeDao;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.unit.Currency.CurrencyCode;
import com.coe.wms.model.unit.Weight.WeightCode;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.TrackingNo;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.shipway.ShipwayApiAccount;
import com.coe.wms.model.warehouse.shipway.Shipway.ShipwayCode;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItemShelf;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderSender;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus.OutWarehouseOrderStatusCode;
import com.coe.wms.model.warehouse.storage.record.ItemInventory;
import com.coe.wms.model.warehouse.storage.record.ItemShelfInventory;
import com.coe.wms.model.warehouse.storage.record.OutWarehousePackage;
import com.coe.wms.model.warehouse.storage.record.OutWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.OutWarehouseRecordItem;
import com.coe.wms.service.storage.IOutWarehouseOrderService;
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
@Service("outWarehouseOrderService")
public class OutWarehouseOrderServiceImpl implements IOutWarehouseOrderService {

	private static final Logger logger = Logger.getLogger(OutWarehouseOrderServiceImpl.class);

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
	@Resource(name = "inWarehouseOrderDao")
	private IInWarehouseOrderDao inWarehouseOrderDao;

	@Resource(name = "inWarehouseOrderStatusDao")
	private IInWarehouseOrderStatusDao inWarehouseOrderStatusDao;

	@Resource(name = "inWarehouseRecordStatusDao")
	private IInWarehouseRecordStatusDao inWarehouseRecordStatusDao;

	@Resource(name = "outWarehouseOrderItemShelfDao")
	private IOutWarehouseOrderItemShelfDao outWarehouseOrderItemShelfDao;

	@Resource(name = "inWarehouseOrderItemDao")
	private IInWarehouseOrderItemDao inWarehouseOrderItemDao;

	@Resource(name = "outWarehouseOrderDao")
	private IOutWarehouseOrderDao outWarehouseOrderDao;

	@Resource(name = "outWarehouseRecordDao")
	private IOutWarehouseRecordDao outWarehouseRecordDao;

	@Resource(name = "outWarehousePackageDao")
	private IOutWarehousePackageDao outWarehousePackageDao;

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

	@Resource(name = "outWarehouseRecordItemDao")
	private IOutWarehouseRecordItemDao outWarehouseRecordItemDao;

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Resource(name = "itemInventoryDao")
	private IItemInventoryDao itemInventoryDao;

	@Resource(name = "itemShelfInventoryDao")
	private IItemShelfInventoryDao itemShelfInventoryDao;

	@Resource(name = "outWarehouseOrderAdditionalSfDao")
	private IOutWarehouseOrderAdditionalSfDao outWarehouseOrderAdditionalSfDao;

	@Resource(name = "productDao")
	private IProductDao productDao;

	@Resource(name = "config")
	private Config config;

	@Resource(name = "shipwayDao")
	private IShipwayDao shipwayDao;

	@Resource(name = "shipwayApiAccountDao")
	private IShipwayApiAccountDao shipwayApiAccountDao;

	public List<Map<String, String>> getInWarehouseOrderItemMap(Long orderId) {
		InWarehouseOrderItem param = new InWarehouseOrderItem();
		param.setOrderId(orderId);
		List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(param, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (InWarehouseOrderItem item : inWarehouseOrderItemList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("sku", item.getSku());
			map.put("skuName", item.getSkuName());
			map.put("skuNo", item.getSkuNo() == null ? "" : item.getSkuNo());
			map.put("quantity", NumberUtil.intToString(item.getQuantity()));
			int receivedQuantity = inWarehouseRecordItemDao.countInWarehouseItemSkuQuantityByOrderId(orderId, item.getSku());
			map.put("receivedQuantity", receivedQuantity + "");
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 获取出库订单列表数据
	 */
	@Override
	public Pagination getOutWarehouseOrderData(OutWarehouseOrder outWarehouseOrder, Map<String, String> moreParam, Pagination pagination) {
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(outWarehouseOrder, moreParam, pagination);
		List<Object> list = new ArrayList<Object>();
		for (OutWarehouseOrder order : outWarehouseOrderList) {
			Map<String, Object> map = new HashMap<String, Object>();
			Long outWarehouseOrderId = order.getId();
			map.put("id", order.getId());
			if (order.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(order.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			map.put("shipwayCode", order.getShipwayCode());
			map.put("trackingNo", order.getTrackingNo());
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
			map.put("remark", order.getRemark());
			map.put("printedCount", order.getPrintedCount());
			OutWarehouseOrderStatus outWarehouseOrderStatus = outWarehouseOrderStatusDao.findOutWarehouseOrderStatusByCode(order.getStatus());
			if (outWarehouseOrderStatus != null) {
				map.put("status", outWarehouseOrderStatus.getCn());
			}
			// 收件人信息
			OutWarehouseOrderReceiver outWarehouseOrderReceiver = outWarehouseOrderReceiverDao.getOutWarehouseOrderReceiverByOrderId(outWarehouseOrderId);
			if (outWarehouseOrderReceiver != null) {
				map.put("receiverAddressLine1", outWarehouseOrderReceiver.getAddressLine1());
				map.put("receiverAddressLine2", outWarehouseOrderReceiver.getAddressLine2());
				map.put("receiverCity", outWarehouseOrderReceiver.getCity());
				map.put("receiverCompany", outWarehouseOrderReceiver.getCompany());
				map.put("receiverCountryCode", outWarehouseOrderReceiver.getCountryCode());
				map.put("receiverCountryName", outWarehouseOrderReceiver.getCountryName());
				map.put("receiverCounty", outWarehouseOrderReceiver.getCounty());
				map.put("receiverEmail", outWarehouseOrderReceiver.getEmail());
				map.put("receiverFirstName", outWarehouseOrderReceiver.getFirstName());
				map.put("receiverLastName", outWarehouseOrderReceiver.getLastName());
				map.put("receiverMobileNumber", outWarehouseOrderReceiver.getMobileNumber());
				map.put("receiverName", outWarehouseOrderReceiver.getName());
				map.put("receiverPhoneNumber", outWarehouseOrderReceiver.getPhoneNumber());
				map.put("receiverPostalCode", outWarehouseOrderReceiver.getPostalCode());
				map.put("receiverStateOrProvince", outWarehouseOrderReceiver.getStateOrProvince());
			}
			// 发件人
			OutWarehouseOrderSender outWarehouseOrderSender = outWarehouseOrderSenderDao.getOutWarehouseOrderSenderByOrderId(outWarehouseOrderId);
			if (outWarehouseOrderSender != null) {
				map.put("senderName", outWarehouseOrderSender.getName());
			}
			// 物品明细(目前仅展示SKU*数量)
			String itemStr = "";
			OutWarehouseOrderItem outWarehouseOrderItemParam = new OutWarehouseOrderItem();
			outWarehouseOrderItemParam.setOutWarehouseOrderId(outWarehouseOrderId);
			List<OutWarehouseOrderItem> outWarehouseOrderItemList = outWarehouseOrderItemDao.findOutWarehouseOrderItem(outWarehouseOrderItemParam, null, null);
			for (OutWarehouseOrderItem outWarehouseOrderItem : outWarehouseOrderItemList) {
				itemStr += outWarehouseOrderItem.getSku() + "*" + outWarehouseOrderItem.getQuantity() + " ";
			}
			map.put("items", itemStr);
			list.add(map);
		}
		pagination.total = outWarehouseOrderDao.countOutWarehouseOrder(outWarehouseOrder, moreParam);
		pagination.rows = list;
		return pagination;
	}

	@Override
	public Map<String, String> checkOutWarehouseOrder(String orderIds, Integer checkResult, Long userIdOfOperator) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(orderIds)) {
			map.put(Constant.MESSAGE, "出库订单id(orderIds)为空,无法处理");
			return map;
		}
		if (checkResult == null) {
			map.put(Constant.MESSAGE, "审核结果(checkResult)为空,无法处理");
			return map;
		}

		int updateQuantity = 0;
		int noUpdateQuantity = 0;
		int notEnougnQuantity = 0;
		String orderIdArr[] = orderIds.split(",");
		for (String orderId : orderIdArr) {
			if (StringUtil.isNull(orderId)) {
				continue;
			}
			Long orderIdLong = Long.valueOf(orderId);
			// 查询订单的当前状态
			String oldStatus = outWarehouseOrderDao.getOutWarehouseOrderStatus(orderIdLong);
			// 如果不是等待审核状态的订单,直接跳过
			if (!StringUtil.isEqual(oldStatus, OutWarehouseOrderStatusCode.WWC)) {
				noUpdateQuantity++;
				continue;
			}

			// ==========================================================================================================================================================================
			// 分配从库位库存找商品库位,预生成打印捡货单需要的信息
			List<OutWarehouseOrderItemShelf> outWarehouseOrderItemShelfList = new ArrayList<OutWarehouseOrderItemShelf>();
			List<ItemShelfInventory> waitUpdateavAilableQuantityList = new ArrayList<ItemShelfInventory>();
			OutWarehouseOrder outWarehouseOrder = outWarehouseOrderDao.getOutWarehouseOrderById(orderIdLong);
			OutWarehouseOrderItem itemParam = new OutWarehouseOrderItem();
			itemParam.setOutWarehouseOrderId(orderIdLong);
			List<OutWarehouseOrderItem> outWarehouseOrderItemList = outWarehouseOrderItemDao.findOutWarehouseOrderItem(itemParam, null, null);
			boolean isNotEnough = false;
			for (OutWarehouseOrderItem item : outWarehouseOrderItemList) {
				// 根据出库订单物品 SKU和数量,按批次,SKU查找上架表
				List<ItemShelfInventory> itemShelfInventoryList = itemShelfInventoryDao.findItemShelfInventoryForPreOutShelf(outWarehouseOrder.getUserIdOfCustomer(), outWarehouseOrder.getWarehouseId(), item.getSku());
				int needQuantity = item.getQuantity();// 需要预下架的商品数量
				int isEnoughQuantity = needQuantity;// 循环执行完后,isEnoughQuantity大于0,代表可用库存不足,审核失败
				for (ItemShelfInventory itemShelfInventory : itemShelfInventoryList) {
					int quantity = 0;
					// 该库位的可用库存数量
					int availableQuantity = itemShelfInventory.getAvailableQuantity();
					boolean isBreak = false;
					if (availableQuantity > needQuantity) {
						// 如果此库位的可用库存大于需要的数量
						quantity = needQuantity;
						// 无需再找下一个库位,更新可用库存
						availableQuantity = availableQuantity - needQuantity;
						isBreak = true;
					} else {
						// 否则此库位的可用库存全部使用,并继续找下一库位
						// 需要下架的商品减去此货架可用库存
						needQuantity = needQuantity - availableQuantity;
						quantity = availableQuantity;
						// 更新可用库存=0
						availableQuantity = 0;
						if (needQuantity <= 0) {
							isBreak = true;
						}
					}
					isEnoughQuantity = isEnoughQuantity - quantity;
					// 执行更新可用库存
					itemShelfInventory.setAvailableQuantity(availableQuantity);
					waitUpdateavAilableQuantityList.add(itemShelfInventory);
					// 打印捡货单,记录出库订单对应的货位和物品.下次打印时 使用已经保存的货位和物品信息
					OutWarehouseOrderItemShelf outWarehouseOrderItemShelf = OutWarehouseOrderItemShelf.createOutWarehouseOrderItemShelf(orderIdLong, quantity, itemShelfInventory.getSeatCode(), item.getSku(), item.getSkuName(),
							item.getSkuNetWeight(), item.getSkuPriceCurrency(), item.getSkuUnitPrice(), itemShelfInventory.getBatchNo(), item.getSpecification());
					outWarehouseOrderItemShelfList.add(outWarehouseOrderItemShelf);
					if (isBreak) {
						break;
					}
				}
				// 如果所有库位的可用库存都不足,不允许出库,审核失败
				if (isEnoughQuantity > 0) {
					isNotEnough = true;
				}
			}

			if (!isNotEnough) {
				// 更新库位的可用库存
				itemShelfInventoryDao.updateBatchItemShelfInventoryAvailableQuantity(waitUpdateavAilableQuantityList);
				// 保存打印捡货单需要的库位和物品信息
				outWarehouseOrderItemShelfDao.saveBatchOutWarehouseOrderItemShelf(outWarehouseOrderItemShelfList);
				// COE审核通过,等待打印捡货单
				int updateResult = outWarehouseOrderDao.updateOutWarehouseOrderStatus(orderIdLong, OutWarehouseOrderStatusCode.WPP);
				updateQuantity++;
			} else {
				notEnougnQuantity++;
			}
			// ==========================================================================================================================================================================
		}
		map.put(Constant.MESSAGE, "审核通过:" + updateQuantity + "个订单,  审核不通过:(" + noUpdateQuantity + "个非待审核状态订单," + notEnougnQuantity + "个库存不足订单)");
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public List<OutWarehouseOrderStatus> findAllOutWarehouseOrderStatus() throws ServiceException {
		return outWarehouseOrderStatusDao.findAllOutWarehouseOrderStatus();
	}

	/**
	 * 根据入库订单id, 查找入库物品明细
	 * 
	 * @param orderId
	 * @param pagination
	 * @return
	 */
	@Override
	public List<OutWarehouseOrderItem> getOutWarehouseOrderItem(Long orderId) {
		OutWarehouseOrderItem param = new OutWarehouseOrderItem();
		param.setOutWarehouseOrderId(orderId);
		List<OutWarehouseOrderItem> outWarehouseOrderItemList = outWarehouseOrderItemDao.findOutWarehouseOrderItem(param, null, null);
		return outWarehouseOrderItemList;
	}

	/**
	 * 出货检查每个跟踪号是否有效,并保存到出货单OutWarehouseShipping表;
	 * 
	 */
	@Override
	public Map<String, String> checkOutWarehouseShipping(String trackingNo, Long userIdOfOperator, Long coeTrackingNoId, String coeTrackingNo, String addOrSub, String orderIds) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(trackingNo)) {
			map.put(Constant.MESSAGE, "请输入出货跟踪单号");
			return map;
		}
		OutWarehouseOrder param = new OutWarehouseOrder();
		param.setTrackingNo(trackingNo);
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(param, null, null);
		if (outWarehouseOrderList == null || outWarehouseOrderList.size() == 0) {
			map.put(Constant.MESSAGE, "查询不到出库订单,请重新输入出货跟踪单号");
			return map;
		}
		if (outWarehouseOrderList.size() > 1) {
			map.put(Constant.MESSAGE, "查询不到唯一的出库订单,暂无法处理");
			// 找到多个出库订单的情况,待处理
			// map.put(Constant.MESSAGE, "查询到多个出库订单,请输入客户订单号");
			return map;
		}
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderList.get(0);
		// 只有顺丰确认出库,顺丰已确认的订单 可以出库
		if (StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WWO)) {
			Long outWarehouseOrderId = outWarehouseOrder.getId();
			map.put("orderId", outWarehouseOrderId + "");
			if (StringUtil.isEqual(addOrSub, "1")) {
				// 检查出库订单 是否已经和COE交接单号绑定
				OutWarehouseRecordItem checkTrackingNoParam = new OutWarehouseRecordItem();
				checkTrackingNoParam.setOutWarehouseOrderId(outWarehouseOrderId);
				Long countTrackingNoResult = outWarehouseRecordItemDao.countOutWarehouseRecordItem(checkTrackingNoParam, null);
				if (countTrackingNoResult > 0) {
					// 说明该出库订单已经和其他COE交接单号绑定了,不能再绑定此单号
					map.put(Constant.MESSAGE, "该出库订单已经绑定的了其他COE交接单号");
					return map;
				}
				// 保存到OutWarehouseShipping,但不改变出库订单的状态.
				// 只有当操作员点击完成出货总单才改变一个COE单号下面对应的所有出库订单的状态
				OutWarehouseRecordItem outWarehouseRecordItem = new OutWarehouseRecordItem();
				outWarehouseRecordItem.setCoeTrackingNo(coeTrackingNo);
				outWarehouseRecordItem.setCoeTrackingNoId(coeTrackingNoId);
				outWarehouseRecordItem.setCreatedTime(System.currentTimeMillis());
				outWarehouseRecordItem.setOutWarehouseOrderTrackingNo(trackingNo);
				outWarehouseRecordItem.setOutWarehouseOrderId(outWarehouseOrder.getId());
				outWarehouseRecordItem.setUserIdOfCustomer(outWarehouseOrder.getUserIdOfCustomer());
				outWarehouseRecordItem.setUserIdOfOperator(userIdOfOperator);
				outWarehouseRecordItem.setWarehouseId(outWarehouseOrder.getWarehouseId());
				long outShippingId = outWarehouseRecordItemDao.saveOutWarehouseRecordItem(outWarehouseRecordItem);
				map.put("outWarehouseShippingId", outShippingId + "");
				map.put(Constant.STATUS, Constant.SUCCESS);
			} else {
				// 1 = 添加出货运单号 ,2 是减去
				// 根据出货运单号+coe单号查找出货记录
				OutWarehouseRecordItem shippingParam = new OutWarehouseRecordItem();
				shippingParam.setCoeTrackingNoId(coeTrackingNoId);
				shippingParam.setCoeTrackingNo(coeTrackingNo);
				shippingParam.setOutWarehouseOrderTrackingNo(trackingNo);
				List<OutWarehouseRecordItem> outWarehouseShippingList = outWarehouseRecordItemDao.findOutWarehouseRecordItem(shippingParam, null, null);
				String deleteShippingIds = "";
				int sub = 0;
				for (OutWarehouseRecordItem shipping : outWarehouseShippingList) {
					outWarehouseRecordItemDao.deleteOutWarehouseRecordItemById(shipping.getId());
					// 加#是为了 jquery可以直接$("#id1,#id2,#id3,#id4")
					deleteShippingIds += ("#" + shipping.getId() + ",");
					orderIds = orderIds.replaceAll(shipping.getOutWarehouseOrderId() + "\\|\\|", "");
					sub++;
				}
				map.put("sub", sub + "");
				map.put("deleteShippingIds", deleteShippingIds);
				map.put("orderIds", orderIds);
				map.put(Constant.STATUS, "2");
			}
		} else if (StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.SUCCESS)) {
			map.put(Constant.MESSAGE, "出库订单当前状态已经是出库成功");
		} else if (StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WCC)) {
			map.put(Constant.MESSAGE, "出库订单当前状态是等待客户确认出库,不能出库");
		} else if (StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WSW)) {
			map.put(Constant.MESSAGE, "出库订单当前状态是等待发送出库重量给客户,不能出库");
		} else {
			map.put(Constant.MESSAGE, "出库订单当前状态不能出库");
		}
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
		OutWarehousePackage outWarehousePackage = new OutWarehousePackage();
		outWarehousePackage.setCoeTrackingNoId(coeTrackingNoId);
		Long countOutWarehousePackage = outWarehousePackageDao.countOutWarehousePackage(outWarehousePackage, null);
		if (countOutWarehousePackage <= 0) {
			map.put(Constant.MESSAGE, "没有找到出库建包记录,请先完成建包");
			return map;
		}
		// 出库记录
		OutWarehouseRecord outWarehouseRecord = new OutWarehouseRecord();
		outWarehouseRecord.setCoeTrackingNoId(coeTrackingNoId);
		Long countOutWarehouseRecord = outWarehouseRecordDao.countOutWarehouseRecord(outWarehouseRecord, null);
		if (countOutWarehouseRecord >= 1) {
			map.put(Constant.MESSAGE, "该交接单号对应大包已经出库,请勿重复操作");
			return map;
		}

		Long userIdOfCustomer = null;
		Long warehouseId = null;
		// 根据coe交接单号 获取建包记录,获取每个出库订单(小包)
		OutWarehouseRecordItem itemParam = new OutWarehouseRecordItem();
		itemParam.setCoeTrackingNoId(coeTrackingNoId);
		List<OutWarehouseRecordItem> outWarehouseRecordItemList = outWarehouseRecordItemDao.findOutWarehouseRecordItem(itemParam, null, null);
		// 迭代,检查跟踪号
		for (OutWarehouseRecordItem recordItem : outWarehouseRecordItemList) {
			// 改变状态 ,发送到哲盟
			Long orderId = recordItem.getOutWarehouseOrderId();
			// logger.info("出货,待发送到哲盟新系统的出库订单id: = " + orderId);

			if (userIdOfCustomer == null) {
				OutWarehouseOrder outWarehouseOrder = outWarehouseOrderDao.getOutWarehouseOrderById(orderId);
				userIdOfCustomer = outWarehouseOrder.getUserIdOfCustomer();
				warehouseId = outWarehouseOrder.getWarehouseId();
			}
			outWarehouseOrderDao.updateOutWarehouseOrderStatus(orderId, OutWarehouseOrderStatusCode.SUCCESS);
			// 更新出库成功,并改变商品批次库存
			// --------------------------------------------------------------------------------------------------------------------------------------------
			// 查找下架时的批次,货位,sku,数量记录
			OutWarehouseOrderItemShelf outWarehouseOrderItemShelfParam = new OutWarehouseOrderItemShelf();
			outWarehouseOrderItemShelfParam.setOutWarehouseOrderId(orderId);
			List<OutWarehouseOrderItemShelf> outWarehouseOrderItemShelfList = outWarehouseOrderItemShelfDao.findOutWarehouseOrderItemShelf(outWarehouseOrderItemShelfParam, null, null);
			for (OutWarehouseOrderItemShelf oItemShelf : outWarehouseOrderItemShelfList) {
				ItemInventory inventoryParam = new ItemInventory();
				inventoryParam.setSku(oItemShelf.getSku());
				inventoryParam.setBatchNo(oItemShelf.getBatchNo());
				inventoryParam.setWarehouseId(warehouseId);
				inventoryParam.setUserIdOfCustomer(userIdOfCustomer);
				List<ItemInventory> itemInventoryList = itemInventoryDao.findItemInventory(inventoryParam, null, null);
				if (itemInventoryList != null && itemInventoryList.size() > 0) {
					ItemInventory itemInventory = itemInventoryList.get(0);
					int outQuantity = oItemShelf.getQuantity();
					int updateCount = itemInventoryDao.updateItemInventoryQuantity(itemInventory.getId(), itemInventory.getQuantity() - outQuantity);
					if (updateCount <= 0) {
						map.put(Constant.MESSAGE, "执行商品批次库存更新失败,出库不成功");// 待添加事务回滚
					}
				} else {
					map.put(Constant.MESSAGE, "找不到商品批次库存,出库不成功");// 待添加事务回滚
				}
			}
			// 更新库存结束----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		}
		// 保存出库记录
		outWarehouseRecord.setCoeTrackingNo(coeTrackingNo);
		outWarehouseRecord.setCreatedTime(System.currentTimeMillis());
		outWarehouseRecord.setUserIdOfCustomer(userIdOfCustomer);
		outWarehouseRecord.setUserIdOfOperator(userIdOfOperator);
		outWarehouseRecord.setWarehouseId(warehouseId);
		outWarehouseRecordDao.saveOutWarehouseRecord(outWarehouseRecord);
		map.put(Constant.STATUS, Constant.SUCCESS);
		map.put(Constant.MESSAGE, "完成出货总单成功,请继续下一批!");
		return map;
	}

	@Override
	public Map<String, String> outWarehousePackageConfirm(String coeTrackingNo, Long coeTrackingNoId, String orderIds, Long userIdOfOperator) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(orderIds)) {
			map.put(Constant.MESSAGE, "请输入出货跟踪单号再按完成出货建包!");
			return map;
		}
		// 前端用||分割多个跟踪单号
		String orderIdsArray[] = orderIds.split("\\|\\|");
		if (orderIdsArray.length < 1) {
			map.put(Constant.MESSAGE, "请输入出货跟踪单号再按完成出货建包!");
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
					OutWarehouseOrder outWarehouseOrder = outWarehouseOrderDao.getOutWarehouseOrderById(orderIdLong);
					userIdOfCustomer = outWarehouseOrder.getUserIdOfCustomer();
					warehouseId = outWarehouseOrder.getWarehouseId();
				}
			}
		}
		if (userIdOfCustomer == null) {
			map.put(Constant.MESSAGE, "请输入出货跟踪单号再按完成出货建包!");
			return map;
		}

		// 保存出库建包记录
		OutWarehousePackage outWarehousePackage = new OutWarehousePackage();
		outWarehousePackage.setCoeTrackingNo(coeTrackingNo);
		outWarehousePackage.setCoeTrackingNoId(coeTrackingNoId);
		outWarehousePackage.setCreatedTime(System.currentTimeMillis());
		outWarehousePackage.setUserIdOfCustomer(userIdOfCustomer);
		outWarehousePackage.setUserIdOfOperator(userIdOfOperator);
		outWarehousePackage.setWarehouseId(warehouseId);
		outWarehousePackageDao.saveOutWarehousePackage(outWarehousePackage);
		// 标记coe单号已经使用
		trackingNoDao.usedTrackingNo(coeTrackingNoId);
		// 返回新COE单号,供下一批出库
		TrackingNo nextTrackingNo = trackingNoDao.getAvailableTrackingNoByType(TrackingNo.TYPE_COE);
		if (nextTrackingNo == null) {
			map.put(Constant.MESSAGE, "本次出货建包已完成,但COE单号不足,不能继续操作建包!");
			map.put(Constant.STATUS, "2");
			map.put("coeTrackingNo", "");
			map.put("coeTrackingNoId", "");
			return map;
		}
		trackingNoDao.lockTrackingNo(nextTrackingNo.getId());
		map.put("coeTrackingNo", nextTrackingNo.getTrackingNo());
		map.put("coeTrackingNoId", nextTrackingNo.getId().toString());
		map.put(Constant.STATUS, Constant.SUCCESS);
		map.put(Constant.MESSAGE, "完成出货建包成功,请继续下一批!");
		return map;
	}

	@Override
	public Map<String, Object> outWarehouseSubmitCustomerReferenceNo(String customerReferenceNo, Long userIdOfOperator) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.STATUS, Constant.FAIL);

		// 根据客户参考好查询出库订单customerReferenceNo
		OutWarehouseOrder param = new OutWarehouseOrder();
		param.setCustomerReferenceNo(customerReferenceNo);
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(param, null, null);
		if (outWarehouseOrderList == null || outWarehouseOrderList.size() == 0) {
			map.put(Constant.MESSAGE, "根据客户订单号找不到出库订单,请重新输入");
			return map;
		}
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderList.get(0);
		map.put("shipwayCode", outWarehouseOrder.getShipwayCode());
		map.put("trackingNo", outWarehouseOrder.getTrackingNo());
		map.put("outWarehouseOrderId", outWarehouseOrder.getId());
		OutWarehouseOrderStatus outWarehouseOrderStatus = outWarehouseOrderStatusDao.findOutWarehouseOrderStatusByCode(outWarehouseOrder.getStatus());
		if (outWarehouseOrderStatus != null) {
			map.put("outWarehouseOrderStatus", outWarehouseOrderStatus.getCn());
		}
		// 查询出库订单SKU
		OutWarehouseOrderItem outWarehouseOrderItemParam = new OutWarehouseOrderItem();
		outWarehouseOrderItemParam.setOutWarehouseOrderId(outWarehouseOrder.getId());
		List<OutWarehouseOrderItem> outWarehouseOrderItemList = outWarehouseOrderItemDao.findOutWarehouseOrderItem(outWarehouseOrderItemParam, null, null);
		map.put("outWarehouseOrderItemList", outWarehouseOrderItemList);
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public Map<String, Object> outWarehouseSubmitWeight(String customerReferenceNo, Double weight, Long userIdOfOperator) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.STATUS, Constant.FAIL);
		// 根据客户参考好查询出库订单customerReferenceNo
		if (StringUtil.isNull(customerReferenceNo)) {
			map.put(Constant.MESSAGE, "请先输入客户订单号");
			return map;
		}
		if (weight == null || weight <= 0) {
			map.put(Constant.MESSAGE, "重量必须是大于0的数字");
		}
		OutWarehouseOrder param = new OutWarehouseOrder();
		param.setCustomerReferenceNo(customerReferenceNo);
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(param, null, null);
		if (outWarehouseOrderList == null || outWarehouseOrderList.size() == 0) {
			map.put(Constant.MESSAGE, "根据客户订单号找不到出库订单,请重新输入");
			return map;
		}
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderList.get(0);
		if (!(StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WWW) || StringUtil.isEqual(outWarehouseOrder.getStatus(), OutWarehouseOrderStatusCode.WSW))) {
			map.put(Constant.MESSAGE, "出库订单当前状态不允许改变出库总重量");
			return map;
		}
		// 称重,并改变订单状态为已经称重,等待回传给顺丰
		outWarehouseOrder.setStatus(OutWarehouseOrderStatusCode.WSW);
		outWarehouseOrder.setOutWarehouseWeight(weight);
		outWarehouseOrder.setWeightCode(WeightCode.KG);
		int updateCount = outWarehouseOrderDao.updateOutWarehouseOrderWeight(outWarehouseOrder);
		if (updateCount > 0) {
			map.put(Constant.STATUS, Constant.SUCCESS);
		} else {
			map.put(Constant.MESSAGE, "执行数据库更新时失败,数据库返回更新行数:" + updateCount);
		}
		return map;
	}

	@Override
	public TrackingNo getCoeTrackingNoforOutWarehouseShipping() throws ServiceException {
		TrackingNo trackingNo = trackingNoDao.getAvailableTrackingNoByType(TrackingNo.TYPE_COE);
		if (trackingNo == null) {
			return null;
		}
		// lock
		trackingNoDao.lockTrackingNo(trackingNo.getId());
		return trackingNo;
	}

	@Override
	public Map<String, String> saveOutWarehouseRecordRemark(String remark, Long id) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		if (outWarehouseRecordDao.updateOutWarehouseRecordRemark(id, remark) > 0) {
			map.put(Constant.STATUS, Constant.SUCCESS);
		} else {
			map.put(Constant.STATUS, Constant.FAIL);
		}
		return map;
	}

	@Override
	public Map<String, String> saveOutWarehousePackageRemark(String remark, Long id) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		if (outWarehousePackageDao.updateOutWarehousePackageRemark(id, remark) > 0) {
			map.put(Constant.STATUS, Constant.SUCCESS);
		} else {
			map.put(Constant.STATUS, Constant.FAIL);
		}
		return map;
	}

	@Override
	public Map<String, Object> outWarehouseShippingEnterCoeTrackingNo(String coeTrackingNo) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.STATUS, Constant.FAIL);
		OutWarehouseRecordItem outWarehouseShipping = new OutWarehouseRecordItem();
		outWarehouseShipping.setCoeTrackingNo(coeTrackingNo);
		List<OutWarehouseRecordItem> outWarehouseShippingList = outWarehouseRecordItemDao.findOutWarehouseRecordItem(outWarehouseShipping, null, null);
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
		map.put("outWarehouseShippingList", outWarehouseShippingList);
		return map;
	}

	/**
	 * 获取出库记录
	 */
	@Override
	public Pagination getOutWarehouseRecordData(OutWarehouseRecord outWarehouseRecord, Map<String, String> moreParam, Pagination page) {
		List<OutWarehouseRecord> outWarehouseRecordList = outWarehouseRecordDao.findOutWarehouseRecord(outWarehouseRecord, moreParam, page);
		List<Object> list = new ArrayList<Object>();
		for (OutWarehouseRecord record : outWarehouseRecordList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", record.getId());
			if (record.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(record.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			// 查询用户名
			User user = userDao.getUserById(record.getUserIdOfCustomer());
			map.put("userLoginNameOfCustomer", user.getLoginName());
			// 查询操作员
			if (NumberUtil.greaterThanZero(record.getUserIdOfOperator())) {
				User userOfOperator = userDao.getUserById(record.getUserIdOfOperator());
				map.put("userLoginNameOfOperator", userOfOperator.getLoginName());
			}
			map.put("coeTrackingNo", record.getCoeTrackingNo());
			map.put("coeTrackingNoId", record.getCoeTrackingNoId());
			if (NumberUtil.greaterThanZero(record.getWarehouseId())) {
				Warehouse warehouse = warehouseDao.getWarehouseById(record.getWarehouseId());
				map.put("warehouse", warehouse.getWarehouseName());
			}
			map.put("remark", record.getRemark() == null ? "" : record.getRemark());
			OutWarehouseRecordItem param = new OutWarehouseRecordItem();
			param.setCoeTrackingNoId(record.getCoeTrackingNoId());
			List<OutWarehouseRecordItem> outWarehouseShippingList = outWarehouseRecordItemDao.findOutWarehouseRecordItem(param, null, null);
			Integer quantity = 0;
			String orders = "";
			for (OutWarehouseRecordItem item : outWarehouseShippingList) {
				orders += item.getOutWarehouseOrderTrackingNo() + " ; ";
				quantity++;
			}
			map.put("orders", orders);
			map.put("quantity", quantity);
			list.add(map);
		}
		page.total = outWarehouseRecordDao.countOutWarehouseRecord(outWarehouseRecord, moreParam);
		page.rows = list;
		return page;
	}

	/**
	 * 获取出库建包记录
	 */
	@Override
	public Pagination getOutWarehousePackageData(OutWarehousePackage outWarehousePackage, Map<String, String> moreParam, Pagination page) {
		List<OutWarehousePackage> outWarehousePackageList = outWarehousePackageDao.findOutWarehousePackage(outWarehousePackage, moreParam, page);
		List<Object> list = new ArrayList<Object>();
		for (OutWarehousePackage oPackage : outWarehousePackageList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", oPackage.getId());
			if (oPackage.getCreatedTime() != null) {
				map.put("packageTime", DateUtil.dateConvertString(new Date(oPackage.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			OutWarehouseRecord recordParam = new OutWarehouseRecord();
			recordParam.setCoeTrackingNoId(oPackage.getCoeTrackingNoId());
			List<OutWarehouseRecord> recordList = outWarehouseRecordDao.findOutWarehouseRecord(recordParam, null, null);
			if (recordList != null && recordList.size() >= 1) {
				map.put("isShipped", "已发货");
				OutWarehouseRecord record = recordList.get(0);
				if (record.getCreatedTime() != null) {
					map.put("shippedTime", DateUtil.dateConvertString(new Date(record.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
				}
			} else {
				map.put("isShipped", "未发货");
			}
			// 查询用户名
			User user = userDao.getUserById(oPackage.getUserIdOfCustomer());
			map.put("userLoginNameOfCustomer", user.getLoginName());
			// 查询操作员
			if (NumberUtil.greaterThanZero(oPackage.getUserIdOfOperator())) {
				User userOfOperator = userDao.getUserById(oPackage.getUserIdOfOperator());
				map.put("userLoginNameOfOperator", userOfOperator.getLoginName());
			}
			map.put("coeTrackingNo", oPackage.getCoeTrackingNo());
			map.put("coeTrackingNoId", oPackage.getCoeTrackingNoId());
			if (NumberUtil.greaterThanZero(oPackage.getWarehouseId())) {
				Warehouse warehouse = warehouseDao.getWarehouseById(oPackage.getWarehouseId());
				map.put("warehouse", warehouse.getWarehouseName());
			}
			map.put("remark", oPackage.getRemark() == null ? "" : oPackage.getRemark());
			OutWarehouseRecordItem param = new OutWarehouseRecordItem();
			param.setCoeTrackingNoId(oPackage.getCoeTrackingNoId());
			List<OutWarehouseRecordItem> outWarehouseShippingList = outWarehouseRecordItemDao.findOutWarehouseRecordItem(param, null, null);
			Integer quantity = 0;
			String orders = "";
			for (OutWarehouseRecordItem item : outWarehouseShippingList) {
				orders += item.getOutWarehouseOrderTrackingNo() + " ; ";
				quantity++;
			}
			map.put("orders", orders);
			map.put("quantity", quantity);
			list.add(map);
		}
		page.total = outWarehousePackageDao.countOutWarehousePackage(outWarehousePackage, moreParam);
		page.rows = list;
		return page;
	}

	@Override
	public List<Map<String, String>> getOutWarehouseRecordItemMapByRecordId(Long recordId) {
		OutWarehouseRecord outWarehouseRecord = outWarehouseRecordDao.getOutWarehouseRecordById(recordId);
		OutWarehouseRecordItem param = new OutWarehouseRecordItem();
		param.setCoeTrackingNoId(outWarehouseRecord.getCoeTrackingNoId());
		List<OutWarehouseRecordItem> outWarehouseShippingList = outWarehouseRecordItemDao.findOutWarehouseRecordItem(param, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (OutWarehouseRecordItem item : outWarehouseShippingList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderId", item.getOutWarehouseOrderId() + "");
			map.put("trackingNo", item.getOutWarehouseOrderTrackingNo());
			User user = userDao.getUserById(item.getUserIdOfCustomer());
			map.put("customer", user.getLoginName());
			OutWarehouseOrder outWarehouseOrder = outWarehouseOrderDao.getOutWarehouseOrderById(item.getOutWarehouseOrderId());
			map.put("weight", outWarehouseOrder.getOutWarehouseWeight() + "");
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public List<Map<String, String>> getOutWarehouseRecordItemByPackageId(Long packageId) {
		OutWarehousePackage outWarehousePackage = outWarehousePackageDao.getOutWarehousePackageById(packageId);
		OutWarehouseRecordItem param = new OutWarehouseRecordItem();
		param.setCoeTrackingNoId(outWarehousePackage.getCoeTrackingNoId());
		List<OutWarehouseRecordItem> outWarehouseShippingList = outWarehouseRecordItemDao.findOutWarehouseRecordItem(param, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (OutWarehouseRecordItem item : outWarehouseShippingList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderId", item.getOutWarehouseOrderId() + "");
			map.put("trackingNo", item.getOutWarehouseOrderTrackingNo());
			User user = userDao.getUserById(item.getUserIdOfCustomer());
			map.put("customer", user.getLoginName());
			OutWarehouseOrder outWarehouseOrder = outWarehouseOrderDao.getOutWarehouseOrderById(item.getOutWarehouseOrderId());
			map.put("weight", outWarehouseOrder.getOutWarehouseWeight() + "");
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public Map<String, String> executeSearchOutWarehouseOrder(String nos, String noType) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(nos)) {
			map.put(Constant.MESSAGE, "请输入单号");
			return map;
		}
		if (!(StringUtil.isEqual(noType, "1") || StringUtil.isEqual(noType, "2"))) {
			map.put(Constant.MESSAGE, "单号类型必须是 客户订单号或出货运单号");
			return map;
		}
		String noArray[] = StringUtil.splitW(nos);
		// 不可以
		String unAbleNos = "";
		int unAbleNoCount = 0;
		int orderCount = 0;
		String allNos = "";// 单号全部返回到页面,
		for (String no : noArray) {
			if (StringUtil.isNull(no)) {
				continue;
			}
			allNos += no + ",";
			OutWarehouseOrder param = new OutWarehouseOrder();
			if (StringUtil.isEqual(noType, "1")) {// 客户单号
				param.setCustomerReferenceNo(no);
			} else if (StringUtil.isEqual(noType, "2")) {// 顺丰运单号
				param.setTrackingNo(no);
			}
			long count = outWarehouseOrderDao.countOutWarehouseOrder(param, null);
			if (count <= 0) {
				// 单号不可以查到出库订单
				unAbleNos += no + ",";
				unAbleNoCount++;
			} else {
				orderCount += count;
			}
		}
		if (unAbleNos.endsWith(",")) {
			unAbleNos = unAbleNos.substring(0, unAbleNos.length() - 1);
		}
		map.put("unAbleNos", unAbleNos);
		map.put("allNos", allNos);
		map.put("orderCount", orderCount + "");
		map.put("unAbleNoCount", unAbleNoCount + "");
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public Map<String, String> applyTrackingNo(Long orderId) throws ServiceException {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constant.STATUS, Constant.FAIL);
		if (orderId == null || orderId == 0) {
			resultMap.put(Constant.MESSAGE, "出库订单Id不能为空");
			return resultMap;
		}
		OutWarehouseOrder order = outWarehouseOrderDao.getOutWarehouseOrderById(orderId);
		OutWarehouseOrderReceiver receiver = outWarehouseOrderReceiverDao.getOutWarehouseOrderReceiverByOrderId(orderId);
		OutWarehouseOrderSender sender = outWarehouseOrderSenderDao.getOutWarehouseOrderSenderByOrderId(orderId);
		OutWarehouseOrderItem itemParam = new OutWarehouseOrderItem();
		itemParam.setOutWarehouseOrderId(orderId);
		List<OutWarehouseOrderItem> itemList = outWarehouseOrderItemDao.findOutWarehouseOrderItem(itemParam, null, null);
		// 出货渠道是ETK
		if (StringUtil.isEqual(order.getShipwayCode(), ShipwayCode.ETK)) {
			resultMap = applyEtkTrackingNo(order, receiver, sender, itemList);
		}
		// 出货渠道顺丰
		if (StringUtil.isEqual(order.getShipwayCode(), ShipwayCode.SF)) {
			resultMap = applySFTrackingNo(order, receiver, sender, itemList);
		}
		return resultMap;
	}

	private Map<String, String> applyEtkTrackingNo(OutWarehouseOrder outWarehouseOrder, OutWarehouseOrderReceiver outWarehouseOrderReceiver, OutWarehouseOrderSender outWarehouseOrderSender, List<OutWarehouseOrderItem> itemList) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		Order etkOrder = new Order();
		etkOrder.setCurrency(CurrencyCode.CNY);
		ShipwayApiAccount shipwayApiAccount = shipwayApiAccountDao.getShipwayApiAccountByUserId(outWarehouseOrder.getUserIdOfCustomer(), outWarehouseOrder.getShipwayCode());
		if (shipwayApiAccount == null) {
			map.put(Constant.MESSAGE, "此订单所属用户缺少ETK API配置信息");
			return map;
		}
		etkOrder.setCustomerNo(shipwayApiAccount.getApiAccount());
		etkOrder.setReferenceId(outWarehouseOrder.getCustomerReferenceNo());// 客户参考号
		List<com.coe.etk.api.request.Item> items = new ArrayList<com.coe.etk.api.request.Item>();
		for (OutWarehouseOrderItem orderItem : itemList) {
			com.coe.etk.api.request.Item item = new com.coe.etk.api.request.Item();
			item.setItemDescription(orderItem.getSkuName());// 报关描述
			double price = 10d;// 报关价值
			if (orderItem.getSkuUnitPrice() != null) {
				price = NumberUtil.div(orderItem.getSkuUnitPrice(), 100d, 2);// 分转元
			}
			item.setItemPrice(price);
			item.setItemQuantity(orderItem.getQuantity() == null ? 1 : orderItem.getQuantity());// 报关数量
			double weight = 0.1d;// 报关重量
			if (orderItem.getSkuNetWeight() != null) {
				weight = (orderItem.getSkuNetWeight() / 1000);
			}
			item.setItemWeight(weight);
			items.add(item);
		}
		etkOrder.setItems(items);
		// 收件人
		Receiver receiver = new Receiver();
		receiver.setReceiverAddress1(outWarehouseOrderReceiver.getAddressLine1());
		receiver.setReceiverAddress2(outWarehouseOrderReceiver.getAddressLine2());
		receiver.setReceiverCity(outWarehouseOrderReceiver.getCity());
		receiver.setReceiverCode(outWarehouseOrderReceiver.getPostalCode());
		receiver.setReceiverName(outWarehouseOrderReceiver.getName());
		receiver.setReceiverCountry(outWarehouseOrderReceiver.getCountryCode());
		receiver.setReceiverPhone(outWarehouseOrderReceiver.getPhoneNumber());
		receiver.setReceiverProvince(outWarehouseOrderReceiver.getStateOrProvince());
		etkOrder.setReceiver(receiver);
		// 发件人
		Sender sender = new Sender();
		sender.setSenderAddress(outWarehouseOrderSender.getAddressLine1());
		sender.setSenderName(outWarehouseOrderSender.getName());
		sender.setSenderPhone(outWarehouseOrderReceiver.getPhoneNumber());
		etkOrder.setSender(sender);
		Client client = new Client();
		client.setToken(shipwayApiAccount.getToken());
		client.setTokenKey(shipwayApiAccount.getTokenKey());
		client.setUrl(shipwayApiAccount.getUrl());
		com.coe.etk.api.response.Responses responses = client.applyTrackingNo(etkOrder);
		if (responses == null) {
			map.put(Constant.STATUS, Constant.FAIL);
			map.put(Constant.MESSAGE, "对方系统返回非法XML格式");
			return map;
		}
		ResponseItems responseItems = responses.getResponseItems();
		if (responseItems == null) {
			map.put(Constant.STATUS, Constant.FAIL);
			map.put(Constant.MESSAGE, "对方系统返回非法XML格式");
			return map;
		}
		com.coe.etk.api.response.Response response = responseItems.getResponse();
		if (response == null) {
			map.put(Constant.STATUS, Constant.FAIL);
			map.put(Constant.MESSAGE, "对方系统返回非法XML格式");
			return map;
		}
		if (StringUtil.isEqualIgnoreCase(response.getSuccess(), Constant.FALSE)) {
			map.put(Constant.STATUS, Constant.FAIL);
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
			outWarehouseOrder.setTrackingNo(trackingNo);
			outWarehouseOrder.setShipwayExtra1(zoneCode);// ETK分区号
			outWarehouseOrderDao.updateOutWarehouseOrderTrackingNo(outWarehouseOrder);
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
	private Map<String, String> applySFTrackingNo(OutWarehouseOrder outWarehouseOrder, OutWarehouseOrderReceiver outWarehouseOrderReceiver, OutWarehouseOrderSender outWarehouseOrderSender, List<OutWarehouseOrderItem> itemList) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		map.put(Constant.MESSAGE, "未支持对顺丰渠道申请单号");
		return map;
	}
}
