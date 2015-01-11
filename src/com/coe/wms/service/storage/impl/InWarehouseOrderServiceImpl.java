package com.coe.wms.service.storage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.product.IProductDao;
import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.ISeatDao;
import com.coe.wms.dao.warehouse.IShelfDao;
import com.coe.wms.dao.warehouse.ITrackingNoDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
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
import com.coe.wms.dao.warehouse.storage.IReportDao;
import com.coe.wms.dao.warehouse.storage.IReportTypeDao;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.product.Product;
import com.coe.wms.model.unit.Currency.CurrencyCode;
import com.coe.wms.model.unit.Weight;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderStatus;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordStatus;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordStatus.InWarehouseRecordStatusCode;
import com.coe.wms.service.storage.IInWarehouseOrderService;
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
@Service("inWarehouseOrderService")
public class InWarehouseOrderServiceImpl implements IInWarehouseOrderService {

	private static final Logger logger = Logger.getLogger(InWarehouseOrderServiceImpl.class);

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

	/**
	 * 根据入库订单id, 查找入库物品明细
	 * 
	 * @param orderId
	 * @param pagination
	 * @return
	 */
	@Override
	public Pagination getInWarehouseOrderItemData(Long orderId, Pagination pagination) {
		InWarehouseOrderItem param = new InWarehouseOrderItem();
		param.setOrderId(orderId);
		List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(param, null, pagination);

		return pagination;
	}

	/**
	 * 根据入库订单id, 查找入库物品明细
	 * 
	 * @param orderId
	 * @param pagination
	 * @return
	 */
	@Override
	public List<InWarehouseOrderItem> getInWarehouseOrderItem(Long orderId) {
		InWarehouseOrderItem param = new InWarehouseOrderItem();
		param.setOrderId(orderId);
		List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(param, null, null);
		return inWarehouseOrderItemList;
	}

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

	@Override
	public Map<String, String> checkInWarehouseRecordItem(String itemSku, Integer itemQuantity, Long warehouseId, Long inWarehouseRecordId, Long userIdOfOperator) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(itemSku)) {
			map.put(Constant.MESSAGE, "请输入商品条码.");
			return map;
		}
		itemSku = itemSku.trim();
		if (itemQuantity == null) {
			map.put(Constant.MESSAGE, "请输入商品数量.");
			return map;
		}
		Long orderId = inWarehouseRecordDao.getInWarehouseOrderIdByRecordId(inWarehouseRecordId);
		if (orderId == null) {
			map.put(Constant.MESSAGE, "找不到入库订单Id.");
			return map;
		}
		// 总数量
		int totalQuantity = inWarehouseOrderItemDao.countInWarehouseOrderItemSkuQuantityByOrderId(orderId, itemSku);
		// 该订单的该条码的总已经收货数量
		int totalReceivedQuantity = inWarehouseRecordItemDao.countInWarehouseItemSkuQuantityByOrderId(orderId, itemSku);
		// 未收货数量
		int unReceivedquantity = totalQuantity - totalReceivedQuantity;
		// 如果本次收货数量大于未收货数量,返回校验失败
		if (itemQuantity > unReceivedquantity) {
			map.put(Constant.STATUS, "2");
			map.put(Constant.MESSAGE, "此条码对应商品未收货数量是:" + unReceivedquantity + ",您确认此次收货数量:" + itemQuantity + "吗?");
			return map;
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	/**
	 * 保存入库明细
	 */
	@Override
	public Map<String, String> saveInWarehouseRecordItem(String itemSku, Integer itemQuantity, String itemRemark, Long warehouseId, Long inWarehouseRecordId, Long userIdOfOperator, String isConfirm) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(itemSku)) {
			map.put(Constant.MESSAGE, "请输入商品条码.");
			return map;
		}
		itemSku = itemSku.trim();
		if (itemQuantity == null) {
			map.put(Constant.MESSAGE, "请输入商品数量.");
			return map;
		}
		Long orderId = inWarehouseRecordDao.getInWarehouseOrderIdByRecordId(inWarehouseRecordId);
		if (orderId == null) {
			map.put(Constant.MESSAGE, "找不到入库订单Id.");
			return map;
		}
		// 检查该SKU是否存在入库订单中
		InWarehouseOrderItem inWarehouseOrderItemParam = new InWarehouseOrderItem();
		inWarehouseOrderItemParam.setSku(itemSku);
		inWarehouseOrderItemParam.setOrderId(orderId);
		List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(inWarehouseOrderItemParam, null, null);
		if (inWarehouseOrderItemList.size() <= 0) {
			// 2014-12-02 改成无预报时判断是否
			// 入库订单是否直有一个明细.如果只有一个明细,并且无sku的情况,视为薄库存.把操作员扫描的sku更新到预报中
			InWarehouseOrderItem orderItemParam2 = new InWarehouseOrderItem();
			orderItemParam2.setOrderId(orderId);
			List<InWarehouseOrderItem> orderItems = inWarehouseOrderItemDao.findInWarehouseOrderItem(orderItemParam2, null, null);
			if (orderItems.size() == 1 && StringUtil.isNull(orderItems.get(0).getSku())) {
				// isConfirm = 'N' 表示不确认是否绑定,弹出询问框
				if (StringUtil.isEqual(isConfirm, Constant.N)) {
					map.put(Constant.STATUS, "2");// 该订单是薄库存情况,你确定将此SKU绑定到商品吗?
					return map;
				}
				// 薄库存,把sku更新到物品明细记录
				long updateCount = inWarehouseOrderItemDao.saveInWarehouseOrderItemSku(orderItems.get(0).getId(), itemSku);
				inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(inWarehouseOrderItemParam, null, null);
			} else {
				map.put(Constant.MESSAGE, "该商品条码在此订单中无预报,且不符合薄库存情况,请补齐商品条码");
				return map;
			}
		}
		InWarehouseOrderItem orderItem = inWarehouseOrderItemList.get(0);
		map.put(Constant.STATUS, Constant.SUCCESS);
		// 查询入库主单信息,用于更新库存
		InWarehouseRecord inWarehouseRecord = inWarehouseRecordDao.getInWarehouseRecordById(inWarehouseRecordId);
		Long userIdOfCustomer = inWarehouseRecord.getUserIdOfCustomer();

		// 检查该SKU是否已经存在,已经存在则直接改变数量(同一个入库主单,同一个SKU只允许一个收货明细)
		InWarehouseRecordItem param = new InWarehouseRecordItem();
		param.setInWarehouseRecordId(inWarehouseRecordId);
		param.setSku(itemSku);
		List<InWarehouseRecordItem> inWarehouseRecordItemList = inWarehouseRecordItemDao.findInWarehouseRecordItem(param, null, null);
		if (inWarehouseRecordItemList.size() > 0) {// (同一个入库主单,同一个SKU只允许一个收货明细),更新入库明细数量
			// 返回入库主单的id
			Long recordItemId = inWarehouseRecordItemList.get(0).getId();
			map.put("id", "" + recordItemId);
			int newQuantity = inWarehouseRecordItemList.get(0).getQuantity() + itemQuantity;
			int updateCount = inWarehouseRecordItemDao.updateInWarehouseRecordItemReceivedQuantity(recordItemId, newQuantity);
			// 更新入库明细成功,则添加库存
			if (updateCount > 0) {
				itemInventoryDao.addItemInventory(warehouseId, userIdOfCustomer, inWarehouseRecord.getBatchNo(), itemSku, itemQuantity);
			}
			return map;
		}
		InWarehouseRecordItem inWarehouseRecordItem = new InWarehouseRecordItem();
		inWarehouseRecordItem.setCreatedTime(System.currentTimeMillis());
		inWarehouseRecordItem.setInWarehouseRecordId(inWarehouseRecordId);
		inWarehouseRecordItem.setQuantity(itemQuantity);
		inWarehouseRecordItem.setRemark(itemRemark);
		inWarehouseRecordItem.setSku(itemSku);
		inWarehouseRecordItem.setSkuNo(orderItem.getSkuNo());
		inWarehouseRecordItem.setUserIdOfOperator(userIdOfOperator);
		// 返回id
		long id = inWarehouseRecordItemDao.saveInWarehouseRecordItem(inWarehouseRecordItem);
		map.put("id", "" + id);
		// 保存成功,添加库存
		if (id > 0) {
			itemInventoryDao.addItemInventory(warehouseId, userIdOfCustomer, inWarehouseRecord.getBatchNo(), itemSku, itemQuantity);
		}
		// 入库订单物品加入商品库 --------------开始
		Product productParam = new Product();
		productParam.setBarcode(itemSku);// 根据商品条码查询产品库, 同一个客户下的商品条码不能重复
		productParam.setUserIdOfCustomer(userIdOfCustomer);
		Long countProduct = productDao.countProduct(productParam, null);
		if (countProduct > 0) {
			return map;
		}
		// sku未存在,新增
		Product product = new Product();
		product.setCreatedTime(System.currentTimeMillis());
		product.setCurrency(CurrencyCode.CNY);
		product.setModel(orderItem.getSpecification());
		product.setIsNeedBatchNo(Constant.N);
		product.setProductName(orderItem.getSkuName());
		product.setWarehouseSku(orderItem.getSkuNo());
		product.setSku(orderItem.getSkuNo());
		product.setBarcode(itemSku);
		product.setUserIdOfCustomer(userIdOfCustomer);
		productDao.addProduct(product);
		// 入库订单物品加入商品库 --------------结束
		return map;
	}

	/**
	 * 查找入库订单
	 */
	@Override
	public List<InWarehouseOrder> findInWarehouseOrder(InWarehouseOrder inWarehouseOrder, Map<String, String> moreParam, Pagination page) {
		List<InWarehouseOrder> inWarehouseOrderList = inWarehouseOrderDao.findInWarehouseOrder(inWarehouseOrder, moreParam, page);
		return inWarehouseOrderList;
	}

	/***
	 * 根据入库订单 找用户id
	 */
	@Override
	public List<User> findUserByInWarehouseOrder(List<InWarehouseOrder> inWarehouseOrderList) {
		List<User> userList = new ArrayList<User>();
		for (InWarehouseOrder inWarehouseOrder : inWarehouseOrderList) {
			if (inWarehouseOrder.getUserIdOfCustomer() == null) {
				continue;
			}
			boolean bool = true;
			for (User user : userList) {
				if (user.getId() == inWarehouseOrder.getUserIdOfCustomer()) {
					bool = false;
					break;
				}
			}
			if (bool) {
				User user = userDao.getUserById(inWarehouseOrder.getUserIdOfCustomer());
				userList.add(user);
			}
		}
		return userList;
	}

	/**
	 * 保存入库记录主单
	 */
	@Override
	public Map<String, String> saveInWarehouseRecord(String trackingNo, String remark, Long userIdOfOperator, Long warehouseId, Long inWarehouseOrderId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(trackingNo)) {
			map.put(Constant.MESSAGE, "请输入跟踪单号.");
			return map;
		}

		Long userIdOfCustomer = inWarehouseOrderDao.getUserIdByInWarehouseOrderId(inWarehouseOrderId);
		InWarehouseRecord inWarehouseRecord = new InWarehouseRecord();
		inWarehouseRecord.setCreatedTime(System.currentTimeMillis());
		inWarehouseRecord.setTrackingNo(trackingNo);
		inWarehouseRecord.setUserIdOfCustomer(userIdOfCustomer);
		inWarehouseRecord.setInWarehouseOrderId(inWarehouseOrderId);
		inWarehouseRecord.setRemark(remark);
		inWarehouseRecord.setUserIdOfOperator(userIdOfOperator);
		// 2014-12-23修改成新入库状态,界面未提交保存,不能发送入库给顺丰
		inWarehouseRecord.setStatus(InWarehouseRecordStatusCode.NEW);
		// 创建批次号
		String batchNo = InWarehouseRecord.generateBatchNo(null, null, Constant.SYMBOL_UNDERLINE, trackingNo, null, null);
		inWarehouseRecord.setBatchNo(batchNo);
		inWarehouseRecord.setWarehouseId(warehouseId);
		// 返回id
		long id = inWarehouseRecordDao.saveInWarehouseRecord(inWarehouseRecord);
		map.put("id", "" + id);
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	/**
	 * 获取入库记录明细数据
	 */
	@Override
	public Pagination getInWarehouseRecordItemData(Long inWarehouseRecordId, Pagination pagination) {
		InWarehouseRecord inWarehouseRecord = inWarehouseRecordDao.getInWarehouseRecordById(inWarehouseRecordId);
		Long inWarehouseOrderId = inWarehouseRecord.getInWarehouseOrderId();
		Long warehouseId = inWarehouseRecord.getWarehouseId();

		InWarehouseOrderItem param = new InWarehouseOrderItem();
		param.setOrderId(inWarehouseOrderId);
		List<InWarehouseOrderItem> orderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(param, null, null);
		List<Object> list = new ArrayList<Object>();
		for (InWarehouseOrderItem orderItem : orderItemList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderItemId", orderItem.getId());
			map.put("sku", orderItem.getSku());
			map.put("skuNo", orderItem.getSkuNo());
			map.put("totalQuantity", orderItem.getQuantity());
			int totalReceivedQuantity = inWarehouseRecordItemDao.countInWarehouseItemSkuQuantityByOrderId(inWarehouseOrderId, orderItem.getSku());
			map.put("totalReceivedQuantity", totalReceivedQuantity);
			int unReceivedquantity = orderItem.getQuantity() - totalReceivedQuantity;
			map.put("unReceivedquantity", unReceivedquantity);
			// 根据SKU查询收货记录的物品明细
			InWarehouseRecordItem inWarehouseRecordItemParam = new InWarehouseRecordItem();
			inWarehouseRecordItemParam.setInWarehouseRecordId(inWarehouseRecordId);
			inWarehouseRecordItemParam.setSku(orderItem.getSku());
			List<InWarehouseRecordItem> recordItemList = inWarehouseRecordItemDao.findInWarehouseRecordItem(inWarehouseRecordItemParam, null, null);
			if (recordItemList != null && recordItemList.size() > 0) {
				InWarehouseRecordItem recordItem = recordItemList.get(0);
				if (recordItem.getCreatedTime() != null) {
					map.put("createdTime", DateUtil.dateConvertString(new Date(recordItem.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
				}
				map.put("receivedQuantity", recordItem.getQuantity());
				map.put("remark", recordItem.getRemark() == null ? "" : recordItem.getRemark());
				if (NumberUtil.greaterThanZero(recordItem.getUserIdOfOperator())) {
					User user = userDao.getUserById(recordItem.getUserIdOfOperator());
					map.put("userLoginNameOfOperator", user.getLoginName());
				}
				Warehouse warehouse = warehouseDao.getWarehouseById(warehouseId);
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
				}
			}
			list.add(map);
		}
		pagination.total = inWarehouseOrderItemDao.countInWarehouseOrderItem(param, null);
		pagination.rows = list;
		return pagination;
	}

	/**
	 * 获取出库订单数据
	 */
	@Override
	public Pagination getInWarehouseOrderData(InWarehouseOrder inWarehouseOrder, Map<String, String> moreParam, Pagination pagination) {
		List<InWarehouseOrder> inWarehouseOrderList = inWarehouseOrderDao.findInWarehouseOrder(inWarehouseOrder, moreParam, pagination);
		List<Object> list = new ArrayList<Object>();
		for (InWarehouseOrder order : inWarehouseOrderList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", order.getId());
			if (order.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(order.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			// 查询用户名
			User user = userDao.getUserById(order.getUserIdOfCustomer());
			map.put("userNameOfCustomer", user.getLoginName());
			map.put("trackingNo", order.getTrackingNo());
			map.put("customerReferenceNo", order.getCustomerReferenceNo());
			// 承运商
			map.put("carrierCode", order.getCarrierCode());
			if (order.getWeight() != null) {
				map.put("weight", Weight.gTurnToKg(order.getWeight()));
			}

			if (order.getWarehouseId() != null) {
				Warehouse warehouse = warehouseDao.getWarehouseById(order.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
				}
			}
			map.put("remark", order.getRemark());
			InWarehouseOrderStatus inWarehouseOrderStatus = inWarehouseOrderStatusDao.findInWarehouseOrderStatusByCode(order.getStatus());
			if (inWarehouseOrderStatus != null) {
				map.put("status", inWarehouseOrderStatus.getCn());
			}
			InWarehouseOrderItem param = new InWarehouseOrderItem();
			param.setOrderId(order.getId());
			List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(param, null, null);
			String skus = "";
			for (InWarehouseOrderItem item : inWarehouseOrderItemList) {
				skus += item.getSku() + "*" + item.getQuantity() + " ";
			}
			map.put("skus", skus);
			list.add(map);
		}
		pagination.total = inWarehouseOrderDao.countInWarehouseOrder(inWarehouseOrder, moreParam);
		pagination.rows = list;
		return pagination;
	}

	/**
	 * 获取入库记录
	 */
	@Override
	public Pagination getInWarehouseRecordData(InWarehouseRecord inWarehouseRecord, Map<String, String> moreParam, Pagination pagination) {
		List<InWarehouseRecord> inWarehouseRecordList = inWarehouseRecordDao.findInWarehouseRecord(inWarehouseRecord, moreParam, pagination);
		List<Object> list = new ArrayList<Object>();
		for (InWarehouseRecord record : inWarehouseRecordList) {
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
			map.put("trackingNo", record.getTrackingNo());
			InWarehouseOrder inWarehouseOrder = inWarehouseOrderDao.getInWarehouseOrderById(record.getInWarehouseOrderId());
			if (inWarehouseOrder != null) {
				map.put("customerReferenceNo", inWarehouseOrder.getCustomerReferenceNo());
			}
			map.put("inWarehouseOrderId", record.getInWarehouseOrderId());
			map.put("batchNo", record.getBatchNo());
			if (NumberUtil.greaterThanZero(record.getWarehouseId())) {
				Warehouse warehouse = warehouseDao.getWarehouseById(record.getWarehouseId());
				map.put("warehouse", warehouse.getWarehouseName());
			}
			map.put("remark", record.getRemark());
			InWarehouseRecordItem param = new InWarehouseRecordItem();
			param.setInWarehouseRecordId(record.getId());
			List<InWarehouseRecordItem> inWarehouseRecordItemList = inWarehouseRecordItemDao.findInWarehouseRecordItem(param, null, null);
			Integer receivedQuantity = 0;
			String skus = "";
			for (InWarehouseRecordItem item : inWarehouseRecordItemList) {
				skus += item.getSku() + "*" + item.getQuantity() + " ";
				receivedQuantity += item.getQuantity();
			}
			map.put("skus", skus);
			map.put("receivedQuantity", receivedQuantity);
			// 预报物品数量,根据跟踪单号找入库订单
			Long orderItemsize = inWarehouseOrderDao.countInWarehouseOrderItemByInWarehouseOrderId(record.getInWarehouseOrderId());
			map.put("quantity", orderItemsize);

			String status = "";
			if (record.getStatus() != null) {
				InWarehouseRecordStatus inWarehouseRecordStatus = inWarehouseRecordStatusDao.findInWarehouseRecordStatusByCode(record.getStatus());
				if (inWarehouseRecordStatus != null) {
					status = inWarehouseRecordStatus.getCn();
				}
			}
			map.put("status", status);
			// 回传成功
			if (StringUtil.isEqual(record.getCallbackIsSuccess(), Constant.Y)) {
				map.put("callbackIsSuccess", "成功");
			} else {
				if (record.getCallbackCount() != null && record.getCallbackCount() > 0) {
					map.put("callbackIsSuccess", "失败次数:" + record.getCallbackCount());
				} else {
					map.put("callbackIsSuccess", "未回传");
				}
			}
			list.add(map);
		}
		pagination.total = inWarehouseRecordDao.countInWarehouseRecord(inWarehouseRecord, moreParam);
		pagination.rows = list;
		return pagination;
	}

	@Override
	public List<Map<String, String>> checkInWarehouseOrder(InWarehouseOrder inWarehouseOrder) {
		List<InWarehouseOrder> inWarehouseOrderList = inWarehouseOrderDao.findInWarehouseOrder(inWarehouseOrder, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (InWarehouseOrder order : inWarehouseOrderList) {
			Map<String, String> map = new HashMap<String, String>();
			Long userId = order.getUserIdOfCustomer();
			User user = userDao.getUserById(userId);
			map.put("inWarehouseOrderId", String.valueOf(order.getId()));
			map.put("userLoginName", user.getLoginName());
			map.put("trackingNo", order.getTrackingNo());
			map.put("customerReferenceNo", order.getCustomerReferenceNo());
			map.put("carrierCode", order.getCarrierCode());
			String time = DateUtil.dateConvertString(new Date(order.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss);
			map.put("createdTime", time);
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public List<Map<String, String>> getInWarehouseRecordItemMapByRecordId(Long recordId) {
		InWarehouseRecordItem param = new InWarehouseRecordItem();
		param.setInWarehouseRecordId(recordId);
		List<InWarehouseRecordItem> inWarehouseRecordItemList = inWarehouseRecordItemDao.findInWarehouseRecordItem(param, null, null);

		// 获取入库订单id
		Long inWarehouseOrderId = inWarehouseRecordDao.getInWarehouseOrderIdByRecordId(recordId);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (InWarehouseRecordItem item : inWarehouseRecordItemList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("sku", item.getSku());
			// 获取sku商品名
			InWarehouseOrderItem orderItemParam = new InWarehouseOrderItem();
			orderItemParam.setOrderId(inWarehouseOrderId);
			orderItemParam.setSku(item.getSku());
			List<InWarehouseOrderItem> inWarehouseOrderItemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(orderItemParam, null, null);
			if (inWarehouseOrderItemList != null && inWarehouseOrderItemList.size() == 1) {
				map.put("skuName", inWarehouseOrderItemList.get(0).getSkuName());
				map.put("skuNo", inWarehouseOrderItemList.get(0).getSkuNo());
				// 预报数量
				map.put("orderQuantity", inWarehouseOrderItemList.get(0).getQuantity() + "");
			} else {
				map.put("skuName", "");
				map.put("skuNo", "");
				map.put("orderQuantity", "");
			}
			map.put("quantity", NumberUtil.intToString(item.getQuantity()));
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public Pagination getInWarehouseRecordItemListData(Map<String, String> moreParam, Pagination pagination) {
		List<Map<String, Object>> recordItemList = inWarehouseRecordItemDao.getInWarehouseRecordItemListData(moreParam, pagination);
		List<Object> list = new ArrayList<Object>();
		for (Map<String, Object> recordItem : recordItemList) {
			Long warehouseId = (Long) recordItem.get("warehouse_id");
			Long userIdOfOperator = (Long) recordItem.get("user_id_of_operator");
			Long userIdOfCustomer = (Long) recordItem.get("user_id_of_customer");
			Long createdTime = (Long) recordItem.get("created_time");
			if (createdTime != null) {
				String time = DateUtil.dateConvertString(new Date(createdTime), DateUtil.yyyy_MM_ddHHmmss);
				recordItem.put("createdTime", time);
			}
			// 查询用户名
			User userOfOperator = userDao.getUserById(Long.valueOf(userIdOfOperator));
			recordItem.put("userLoginNameOfOperator", userOfOperator.getLoginName());
			// 查询用户名
			User userOfCustomer = userDao.getUserById(Long.valueOf(userIdOfCustomer));
			recordItem.put("userLoginNameOfCustomer", userOfCustomer.getLoginName());
			Warehouse warehouse = warehouseDao.getWarehouseById(Long.valueOf(warehouseId));
			if (warehouse != null) {
				recordItem.put("warehouse", warehouse.getWarehouseName());
			}
			list.add(recordItem);
		}
		pagination.total = inWarehouseRecordItemDao.countInWarehouseRecordItemList(moreParam);
		pagination.rows = list;
		return pagination;
	}

	@Override
	public Map<String, String> saveInWarehouseOrderRemark(String remark, Long id) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		if (inWarehouseOrderDao.updateInWarehouseOrderRemark(id, remark) > 0) {
			map.put(Constant.STATUS, Constant.SUCCESS);
		} else {
			map.put(Constant.STATUS, Constant.FAIL);
		}
		return map;
	}

	@Override
	public Map<String, String> saveInWarehouseRecordRemark(String remark, Long id) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		if (inWarehouseRecordDao.updateInWarehouseRecordRemark(id, remark) > 0) {
			map.put(Constant.STATUS, Constant.SUCCESS);
		} else {
			map.put(Constant.STATUS, Constant.FAIL);
		}
		return map;
	}

	@Override
	public Map<String, String> saveInWarehouseOrderItemSku(Long id, String sku) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtil.isNotNull(sku)) {
			sku = sku.trim();
			inWarehouseOrderItemDao.saveInWarehouseOrderItemSku(id, sku);
			map.put(Constant.STATUS, Constant.SUCCESS);
			return map;
		}
		map.put(Constant.STATUS, Constant.FAIL);
		return map;
	}

	@Override
	public List<InWarehouseOrderStatus> findAllInWarehouseOrderStatus() throws ServiceException {
		return inWarehouseOrderStatusDao.findAllInWarehouseOrderStatus();
	}

	@Override
	public Map<String, String> submitInWarehouseRecord(Long inWarehouseRecordId) {
		InWarehouseRecord inWarehouseRecord = new InWarehouseRecord();
		inWarehouseRecord.setId(inWarehouseRecordId);
		inWarehouseRecord.setStatus(InWarehouseRecordStatusCode.NONE);
		Map<String, String> map = new HashMap<String, String>();
		if (inWarehouseRecordDao.updateInWarehouseRecordStatus(inWarehouseRecord) > 0) {
			map.put(Constant.STATUS, Constant.SUCCESS);
		} else {
			map.put(Constant.STATUS, Constant.FAIL);
		}
		return map;
	}

	/**
	 * 获取入库记录
	 */
	@Override
	public Pagination getInWarehouseRecordOnShelfData(Long userIdOfCustomer, Long warehouseId, String trackingNo, String batchNo, String sku, String receivedTimeStart, String receivedTimeEnd, Pagination page) {
		List<Map<String, Object>> inWarehouseRecordList = inWarehouseRecordDao.findInWarehouseRecordOnShelf(userIdOfCustomer, warehouseId, trackingNo, batchNo, sku, receivedTimeStart, receivedTimeStart, page);
		List<Object> list = new ArrayList<Object>();
		for (Map<String, Object> map : inWarehouseRecordList) {
			if (map.get("receivedTime") != null) {
				map.put("receivedTime", DateUtil.dateConvertString(new Date((Long) map.get("receivedTime")), DateUtil.yyyy_MM_ddHHmmss));
			}
			if (map.get("onShelfTime") != null) {
				map.put("onShelfTime", DateUtil.dateConvertString(new Date((Long) map.get("onShelfTime")), DateUtil.yyyy_MM_ddHHmmss));
			}
			if (map.get("userIdOfCustomer") != null) {
				User user = userDao.getUserById((Long) map.get("userIdOfCustomer"));
				map.put("userLoginNameOfCustomer", user.getLoginName());
			}
			if (map.get("userIdOfOperator") != null) {
				User user = userDao.getUserById((Long) map.get("userIdOfOperator"));
				map.put("userLoginNameOfOperator", user.getLoginName());
			}
			list.add(map);
		}
		page.total = inWarehouseRecordDao.countInWarehouseRecordOnShelf(userIdOfCustomer, warehouseId, trackingNo, batchNo, sku, receivedTimeStart, receivedTimeStart);
		page.rows = list;
		return page;
	}
}
