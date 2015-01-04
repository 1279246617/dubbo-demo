package com.coe.wms.service.transport.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.dao.warehouse.shipway.IShipwayDao;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillDao;
import com.coe.wms.dao.warehouse.transport.IOrderPackageDao;
import com.coe.wms.dao.warehouse.transport.IOrderPackageStatusDao;
import com.coe.wms.dao.warehouse.transport.impl.FirstWaybillDaoImpl;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.transport.FirstWaybill;
import com.coe.wms.model.warehouse.transport.OrderPackageStatus;
import com.coe.wms.service.transport.IOrderPackageService;
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
@Service("orderPackageService")
public class OrderPackageServiceImpl implements IOrderPackageService {

	private static final Logger logger = Logger.getLogger(OrderPackageServiceImpl.class);

	@Resource(name = "warehouseDao")
	private IWarehouseDao warehouseDao;

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Resource(name = "config")
	private Config config;

	@Resource(name = "orderPackageStatusDao")
	private IOrderPackageStatusDao orderPackageStatusDao;

	@Resource(name = "orderPackageDao")
	private IOrderPackageDao orderPackageDao;

	@Resource(name = "firstWaybillDao")
	private IFirstWaybillDao firstWaybillDao;

	@Resource(name = "shipwayDao")
	private IShipwayDao shipwayDao;

	@Override
	public List<OrderPackageStatus> findAllOrderPackageStatus() throws ServiceException {
		return orderPackageStatusDao.findAllOrderPackageStatus();
	}

	/**
	 * 获取转运订单列表数据
	 */
	@Override
	public Pagination getOrderPackageData(com.coe.wms.model.warehouse.transport.OrderPackage param, Map<String, String> moreParam, Pagination pagination) {
		List<com.coe.wms.model.warehouse.transport.OrderPackage> orderList = orderPackageDao.findOrderPackage(param, moreParam, pagination);
		List<Object> list = new ArrayList<Object>();
		for (com.coe.wms.model.warehouse.transport.OrderPackage order : orderList) {
			FirstWaybill firstWaybillParam = new FirstWaybill();
			firstWaybillParam.setOrderPackageId(order.getId());
			List<FirstWaybill> firstWaybillList = firstWaybillDao.findFirstWaybill(firstWaybillParam, null, null);
			Map<String, Object> map = new HashMap<String, Object>();
			Long orderId = order.getId();
			map.put("id", orderId);
			if (order.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(order.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			if (firstWaybillList != null && firstWaybillList.size() >= 1) {
				FirstWaybill firstWaybill = firstWaybillList.get(0);
				if (firstWaybill.getReceivedTime() != null) {
					map.put("receivedTime", DateUtil.dateConvertString(new Date(firstWaybill.getReceivedTime()), DateUtil.yyyy_MM_ddHHmmss));
				}
				map.put("carrierCode", firstWaybill.getCarrierCode());
				// 回传收货
				if (StringUtil.isEqual(firstWaybill.getCallbackIsSuccess(), Constant.Y)) {
					map.put("callbackSendStatusIsSuccess", "成功");
				} else {
					if (firstWaybill.getCallbackCount() != null && firstWaybill.getCallbackCount() > 0) {
						map.put("callbackSendStatusCount", "失败次数:" + firstWaybill.getCallbackCount());
					} else {
						map.put("callbackSendStatusCount", "未回传");
					}
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
			map.put("trackingNo", order.getTrackingNo());
			OrderPackageStatus orderStatus = orderPackageStatusDao.findOrderPackageStatusByCode(order.getStatus());
			if (orderStatus != null) {
				map.put("status", orderStatus.getCn());
			}
			list.add(map);
		}
		pagination.total = orderPackageDao.countOrderPackage(param, moreParam);
		pagination.rows = list;
		return pagination;
	}
}
