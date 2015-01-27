package com.coe.wms.service.transport.impl;

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
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.transport.FirstWaybill;
import com.coe.wms.model.warehouse.transport.FirstWaybillItem;
import com.coe.wms.model.warehouse.transport.FirstWaybillOnShelf;
import com.coe.wms.model.warehouse.transport.FirstWaybillStatus;
import com.coe.wms.model.warehouse.transport.FirstWaybillStatus.FirstWaybillStatusCode;
import com.coe.wms.model.warehouse.transport.Order;
import com.coe.wms.model.warehouse.transport.OrderStatus.OrderStatusCode;
import com.coe.wms.model.warehouse.transport.OutWarehousePackage;
import com.coe.wms.model.warehouse.transport.OutWarehousePackageItem;
import com.coe.wms.service.transport.IFirstWaybillService;
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
@Service("firstWaybillService")
public class FirstWaybillServiceImpl implements IFirstWaybillService {

	private static final Logger logger = Logger.getLogger(FirstWaybillServiceImpl.class);

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
	public List<Map<String, Object>> getFirstWaybillItems(Long orderId) throws ServiceException {
		FirstWaybill param = new FirstWaybill();
		param.setOrderId(orderId);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<FirstWaybill> firstWaybillList = firstWaybillDao.findFirstWaybill(param, null, null);
		for (FirstWaybill firstWaybill : firstWaybillList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("trackingNo", firstWaybill.getTrackingNo());
			map.put("poNo", firstWaybill.getPoNo());
			map.put("carrierCode", firstWaybill.getCarrierCode());
			FirstWaybillStatus firstWaybillStatus = firstWaybillStatusDao.findFirstWaybillStatusByCode(firstWaybill.getStatus());
			if (firstWaybillStatus != null) {
				map.put("status", firstWaybillStatus.getCn());
			}
			if (firstWaybill.getReceivedTime() != null) {
				String receivedTime = DateUtil.dateConvertString(new Date(firstWaybill.getReceivedTime()), DateUtil.yyyy_MM_ddHHmmss);
				map.put("receivedTime", receivedTime);
			} else {
				map.put("receivedTime", "");
			}
			if (firstWaybill.getCreatedTime() != null) {
				String createdTime = DateUtil.dateConvertString(new Date(firstWaybill.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss);
				map.put("createdTime", createdTime);
			}
			if (StringUtil.isEqual(firstWaybill.getCallbackIsSuccess(), Constant.Y)) {
				map.put("callbackIsSuccess", "成功");
			} else {
				if (firstWaybill.getCallbackCount() != null && firstWaybill.getCallbackCount() > 0) {
					map.put("callbackIsSuccess", "失败次数:" + firstWaybill.getCallbackCount());
				} else {
					map.put("callbackIsSuccess", "未回传");
				}
			}
			// 获取小包内物品详情
			FirstWaybillItem firstWaybillItemParam = new FirstWaybillItem();
			firstWaybillItemParam.setFirstWaybillId(firstWaybill.getId());
			List<FirstWaybillItem> firstWaybillItemList = firstWaybillItemDao.findFirstWaybillItem(firstWaybillItemParam, null, null);
			map.put("firstWaybillItemList", firstWaybillItemList);
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public List<FirstWaybillStatus> findAllFirstWaybillStatus() throws ServiceException {
		return firstWaybillStatusDao.findAllFirstWaybillStatus();
	}

	@Override
	public Pagination getFirstWaybillData(FirstWaybill param, Map<String, String> moreParam, Pagination page) throws ServiceException {
		List<FirstWaybill> firstWaybillList = firstWaybillDao.findFirstWaybill(param, moreParam, page);
		List<Object> list = new ArrayList<Object>();
		for (FirstWaybill firstWaybill : firstWaybillList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", firstWaybill.getId());
			if (firstWaybill.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(firstWaybill.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			if (firstWaybill.getReceivedTime() != null) {
				map.put("receivedTime", DateUtil.dateConvertString(new Date(firstWaybill.getReceivedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			if (firstWaybill.getOrderId() != null) {
				String orderStatus = orderDao.getOrderStatus(firstWaybill.getOrderId());
				map.put("orderStatus", orderStatusDao.findOrderStatusByCode(orderStatus).getCn());
			}
			if (firstWaybill.getOrderPackageId() != null) {
				String orderStatus = orderPackageDao.getOrderPackageStatus(firstWaybill.getOrderPackageId());
				map.put("orderStatus", orderStatusDao.findOrderStatusByCode(orderStatus).getCn());
			}
			map.put("trackingNo", firstWaybill.getTrackingNo());
			map.put("seatCode", firstWaybill.getSeatCode());
			map.put("carrierCode", firstWaybill.getCarrierCode());
			// 回传审核
			if (StringUtil.isEqual(firstWaybill.getCallbackIsSuccess(), Constant.Y)) {
				map.put("callbackIsSuccess", "成功");
			} else {
				if (firstWaybill.getCallbackCount() != null && firstWaybill.getCallbackCount() > 0) {
					map.put("callbackIsSuccess", "失败次数:" + firstWaybill.getCallbackCount());
				} else {
					map.put("callbackIsSuccess", "未回传");
				}
			}
			// 查询用户名
			User user = userDao.getUserById(firstWaybill.getUserIdOfCustomer());
			map.put("userNameOfCustomer", user.getLoginName());
			if (firstWaybill.getUserIdOfOperator() != null) {
				User operator = userDao.getUserById(firstWaybill.getUserIdOfOperator());
				map.put("userNameOfOperator", operator.getLoginName());
			}
			map.put("poNo", firstWaybill.getPoNo());
			if (NumberUtil.greaterThanZero(firstWaybill.getWarehouseId())) {
				Warehouse warehouse = warehouseDao.getWarehouseById(firstWaybill.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
				}
			}
			if (StringUtil.isEqual(Order.TRANSPORT_TYPE_J, firstWaybill.getTransportType())) {
				map.put("transportType", "集货转运");
			}
			if (StringUtil.isEqual(Order.TRANSPORT_TYPE_Z, firstWaybill.getTransportType())) {
				map.put("transportType", "直接转运");
			}
			if (StringUtil.isEqual(Order.TRANSPORT_TYPE_P, firstWaybill.getTransportType())) {
				map.put("transportType", "大包头程");
			}
			map.put("remark", firstWaybill.getRemark());
			FirstWaybillStatus firstWaybillStatus = firstWaybillStatusDao.findFirstWaybillStatusByCode(firstWaybill.getStatus());
			if (firstWaybillStatus != null) {
				map.put("status", firstWaybillStatus.getCn());
			}
			// 物品明细(目前仅展示SKU*数量)
			String items = "";
			FirstWaybillItem firstWaybillItemParam = new FirstWaybillItem();
			firstWaybillItemParam.setFirstWaybillId(firstWaybill.getId());
			List<FirstWaybillItem> firstWaybillItemList = firstWaybillItemDao.findFirstWaybillItem(firstWaybillItemParam, null, null);
			for (FirstWaybillItem firstWaybillItem : firstWaybillItemList) {
				items += firstWaybillItem.getSku() + " * " + firstWaybillItem.getQuantity() + " ; ";
			}
			map.put("items", items);
			list.add(map);
		}
		page.total = firstWaybillDao.countFirstWaybill(param, moreParam);
		page.rows = list;
		return page;
	}

	@Override
	public List<FirstWaybillItem> getFirstWaybillItemsByFirstWaybillId(Long firstWaybillId) throws ServiceException {
		FirstWaybillItem firstWaybillItemParam = new FirstWaybillItem();
		firstWaybillItemParam.setFirstWaybillId(firstWaybillId);
		List<FirstWaybillItem> firstWaybillItemList = firstWaybillItemDao.findFirstWaybillItem(firstWaybillItemParam, null, null);
		return firstWaybillItemList;
	}

	@Override
	public List<Map<String, String>> checkReceivedFirstWaybill(FirstWaybill param) {
		List<FirstWaybill> firstWaybillList = firstWaybillDao.findFirstWaybill(param, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (FirstWaybill firstWaybill : firstWaybillList) {
			Map<String, String> map = new HashMap<String, String>();
			Long userId = firstWaybill.getUserIdOfCustomer();
			User user = userDao.getUserById(userId);
			map.put("firstWaybillId", String.valueOf(firstWaybill.getId()));
			map.put("userLoginName", user.getLoginName());
			map.put("trackingNo", firstWaybill.getTrackingNo());
			map.put("carrierCode", firstWaybill.getCarrierCode());
			String time = DateUtil.dateConvertString(new Date(firstWaybill.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss);
			map.put("createdTime", time);
			FirstWaybillStatus status = firstWaybillStatusDao.findFirstWaybillStatusByCode(firstWaybill.getStatus());
			if (status != null) {
				map.put("status", status.getCn());
			}
			// 检查上架状态
			FirstWaybillOnShelf onshelf = firstWaybillOnShelfDao.findFirstWaybillOnShelfByFirstWaybillId(firstWaybill.getId());
			if (onshelf != null) {
				if (StringUtil.isEqual(onshelf.getStatus(), FirstWaybillOnShelf.STATUS_ON_SHELF)) {
					map.put("onShelfstatus", "已上架");
				}
				if (StringUtil.isEqual(onshelf.getStatus(), FirstWaybillOnShelf.STATUS_OUT_SHELF)) {
					map.put("onShelfstatus", "已下架");
				}
				if (StringUtil.isEqual(onshelf.getStatus(), FirstWaybillOnShelf.STATUS_PRE_ON_SHELF)) {
					map.put("onShelfstatus", "未上架");
				}
			}
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public Map<String, String> saveFirstWaybillOnShelves(Long userIdOfOperator, Long firstWaybillId, String seatCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(seatCode)) {
			map.put(Constant.MESSAGE, "请输入货位号");
			return map;
		}
		// 查询订单状态是否是待上架
		FirstWaybill firstWaybill = firstWaybillDao.getFirstWaybillById(firstWaybillId);
		if (StringUtil.isEqual(firstWaybill.getStatus(), FirstWaybillStatusCode.WSR)) {
			map.put(Constant.MESSAGE, "该订单等待回传收货给客户,不能上架");
			return map;
		}
		if (!StringUtil.isEqual(firstWaybill.getStatus(), FirstWaybillStatusCode.WOS)) {
			map.put(Constant.MESSAGE, "该订单非待上架状态,不能上架");
			return map;
		}
		// 查找小包最新上架记录
		FirstWaybillOnShelf onshelf = firstWaybillOnShelfDao.findFirstWaybillOnShelfByFirstWaybillId(firstWaybillId);
		if (onshelf == null) {
			map.put(Constant.MESSAGE, "预分配货位丢失,请联系管理员");
			return map;
		}
		if (StringUtil.isEqual(onshelf.getStatus(), FirstWaybillOnShelf.STATUS_ON_SHELF)) {
			map.put(Constant.MESSAGE, "该订单已上架,不能重复上架");
			return map;
		}
		if (!StringUtil.isEqual(onshelf.getSeatCode(), seatCode)) {
			map.put(Constant.MESSAGE, "输入的货位号与预分配货位号不相同,不能上架");
			return map;
		}
		firstWaybill.setStatus(FirstWaybillStatusCode.W_OUT_S);
		firstWaybillDao.updateFirstWaybillStatus(firstWaybill);

		onshelf.setStatus(FirstWaybillOnShelf.STATUS_ON_SHELF);
		// 否则修改为已上架
		firstWaybillOnShelfDao.updateFirstWaybillOnShelf(onshelf);

		// 判断bigpackage下是不是所有littlepackage都已经上架
		FirstWaybill firstWaybillParam = new FirstWaybill();
		firstWaybillParam.setOrderId(firstWaybill.getOrderId());
		List<FirstWaybill> firstWaybillList = firstWaybillDao.findFirstWaybill(firstWaybillParam, null, null);
		boolean isReceived = true;// 小包是否已经全部收货
		for (FirstWaybill temp : firstWaybillList) {
			if (!StringUtil.isEqual(temp.getStatus(), FirstWaybillStatusCode.W_OUT_S)) {
				isReceived = false;
			}
		}
		if (isReceived) {
			// 判断大包当前状态是否是收货完成,待上架完成
			String status = orderDao.getOrderStatus(firstWaybill.getOrderId());
			if (StringUtil.isEqual(status, OrderStatusCode.WOS)) {
				orderDao.updateOrderStatus(firstWaybill.getOrderId(), OrderStatusCode.WWP);// 上架完成待打印捡货
			}
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public Pagination getFirstWaybillOnShelfData(FirstWaybillOnShelf param, Map<String, String> moreParam, Pagination page) throws ServiceException {
		List<FirstWaybillOnShelf> firstWaybillOnShelfList = firstWaybillOnShelfDao.findFirstWaybillOnShelf(param, moreParam, page);
		List<Object> list = new ArrayList<Object>();
		for (FirstWaybillOnShelf firstWaybillOnShelf : firstWaybillOnShelfList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", firstWaybillOnShelf.getId());
			if (firstWaybillOnShelf.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(firstWaybillOnShelf.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			map.put("trackingNo", firstWaybillOnShelf.getTrackingNo());
			map.put("seatCode", firstWaybillOnShelf.getSeatCode());
			// 查询用户名
			User user = userDao.getUserById(firstWaybillOnShelf.getUserIdOfCustomer());
			map.put("userNameOfCustomer", user.getLoginName());
			if (firstWaybillOnShelf.getUserIdOfOperator() != null) {
				User operator = userDao.getUserById(firstWaybillOnShelf.getUserIdOfOperator());
				map.put("userNameOfOperator", operator.getLoginName());
			}
			if (NumberUtil.greaterThanZero(firstWaybillOnShelf.getWarehouseId())) {
				Warehouse warehouse = warehouseDao.getWarehouseById(firstWaybillOnShelf.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
				}
			}
			if (StringUtil.isEqual(firstWaybillOnShelf.getStatus(), FirstWaybillOnShelf.STATUS_ON_SHELF)) {
				map.put("status", "已上架");
			} else if (StringUtil.isEqual(firstWaybillOnShelf.getStatus(), FirstWaybillOnShelf.STATUS_OUT_SHELF)) {
				map.put("status", "已下架");
			} else if (StringUtil.isEqual(firstWaybillOnShelf.getStatus(), FirstWaybillOnShelf.STATUS_PRE_ON_SHELF)) {
				map.put("status", "预上架");
			}
			Order order = orderDao.getOrderById(firstWaybillOnShelf.getOrderId());
			String transportType = order.getTransportType();
			if (StringUtil.isEqual(transportType, Order.TRANSPORT_TYPE_J)) {
				map.put("transportType", "集货转运");
			} else if (StringUtil.isEqual(transportType, Order.TRANSPORT_TYPE_Z)) {
				map.put("transportType", "直接转运");
			}
			map.put("outWarehouseTrackingNo", order.getTrackingNo());
			map.put("outWarehouseShipwayCode", order.getShipwayCode());
			list.add(map);
		}
		page.total = firstWaybillOnShelfDao.countFirstWaybillOnShelf(param, moreParam);
		page.rows = list;
		return page;
	}

	@Override
	public List<Map<String, String>> getOutWarehousePackageItemByOutWarehousePackageId(Long packageId) {
		OutWarehousePackage packageRecord = transportPackageDao.getOutWarehousePackageById(packageId);
		OutWarehousePackageItem param = new OutWarehousePackageItem();
		param.setCoeTrackingNoId(packageRecord.getCoeTrackingNoId());
		List<OutWarehousePackageItem> packageRecordItemList = transportPackageItemDao.findOutWarehousePackageItem(param, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (OutWarehousePackageItem item : packageRecordItemList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderId", item.getOrderId() + "");
			map.put("trackingNo", item.getOrderTrackingNo());
			User user = userDao.getUserById(item.getUserIdOfCustomer());
			map.put("customer", user.getLoginName());
			Order order = orderDao.getOrderById(item.getOrderId());
			map.put("weight", order.getOutWarehouseWeight() + "");
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public Map<String, String> checkFirstWaybill(Long orderId, String trackingNo) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		FirstWaybill firstWaybill = new FirstWaybill();
		firstWaybill.setOrderId(orderId);
		firstWaybill.setTrackingNo(trackingNo);
		Long count = firstWaybillDao.countFirstWaybill(firstWaybill, null);
		if (count >= 1) {
			map.put(Constant.STATUS, Constant.SUCCESS);// 成功
		} else {
			map.put(Constant.MESSAGE, "转运订单找不到此小包");
		}
		return map;
	}
}
