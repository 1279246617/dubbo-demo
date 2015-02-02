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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.coe.wms.service.transport.IOrderPackageService#getOrderPackageData
	 * (com.coe.wms.model.warehouse.transport.OrderPackage, java.util.Map,
	 * com.coe.wms.util.Pagination)
	 */
	@Override
	public Pagination getOrderPackageData(com.coe.wms.model.warehouse.transport.OrderPackage param, Map<String, String> moreParam, Pagination pagination) {
		List<com.coe.wms.model.warehouse.transport.OrderPackage> orderList = orderPackageDao.findOrderPackage(param, moreParam, pagination);
		List<Object> list = new ArrayList<Object>();
		for (com.coe.wms.model.warehouse.transport.OrderPackage orderPackage : orderList) {
			FirstWaybill firstWaybillParam = new FirstWaybill();
			firstWaybillParam.setOrderPackageId(orderPackage.getId());
			List<FirstWaybill> firstWaybillList = firstWaybillDao.findFirstWaybill(firstWaybillParam, null, null);
			Map<String, Object> map = new HashMap<String, Object>();
			Long orderId = orderPackage.getId();
			map.put("id", orderId);
			if (orderPackage.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(orderPackage.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			if (firstWaybillList != null && firstWaybillList.size() >= 1) {
				FirstWaybill firstWaybill = firstWaybillList.get(0);
				if (firstWaybill.getReceivedTime() != null) {
					map.put("receivedTime", DateUtil.dateConvertString(new Date(firstWaybill.getReceivedTime()), DateUtil.yyyy_MM_ddHHmmss));
				}
				map.put("carrierCode", firstWaybill.getCarrierCode());
				map.put("trackingNo", firstWaybill.getTrackingNo());
				// 回传收货
				if (StringUtil.isEqual(firstWaybill.getCallbackIsSuccess(), Constant.Y)) {
					map.put("callbackSendStatusIsSuccess", "成功");
				} else {
					if (firstWaybill.getCallbackCount() != null && firstWaybill.getCallbackCount() > 0) {
						map.put("callbackSendStatusIsSuccess", "失败次数:" + firstWaybill.getCallbackCount());
					} else {
						map.put("callbackSendStatusIsSuccess", "未回传");
					}
				}
			}
			if (StringUtil.isNotNull(orderPackage.getCheckResult())) {
				if (StringUtil.isEqual(orderPackage.getCheckResult(), "SECURITY")) {
					map.put("checkResult", "拒收(安全不通过)");
				} else if (StringUtil.isEqual(orderPackage.getCheckResult(), "OTHER_REASON")) {
					map.put("checkResult", "拒收(其他不通过)");
				} else if (StringUtil.isEqual(orderPackage.getCheckResult(), "SUCCESS")) {
					map.put("checkResult", "接件(审核已通过)");
				} else {
					map.put("checkResult", orderPackage.getCheckResult());
				}
			} else {
				map.put("checkResult", "");
			}
			// 回传审核
			if (StringUtil.isEqual(orderPackage.getCallbackSendCheckIsSuccess(), Constant.Y)) {
				map.put("callbackSendCheckIsSuccess", "成功");
			} else {
				if (orderPackage.getCallbackSendCheckCount() != null && orderPackage.getCallbackSendCheckCount() > 0) {
					map.put("callbackSendCheckIsSuccess", "失败次数:" + orderPackage.getCallbackSendCheckCount());
				} else {
					map.put("callbackSendCheckIsSuccess", "未回传");
				}
			}
			// 查询用户名
			User user = userDao.getUserById(orderPackage.getUserIdOfCustomer());
			map.put("userNameOfCustomer", user.getLoginName());
			map.put("customerReferenceNo", orderPackage.getCustomerReferenceNo());
			if (NumberUtil.greaterThanZero(orderPackage.getWarehouseId())) {
				Warehouse warehouse = warehouseDao.getWarehouseById(orderPackage.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
				}
			}
			map.put("remark", orderPackage.getRemark());
			OrderPackageStatus orderStatus = orderPackageStatusDao.findOrderPackageStatusByCode(orderPackage.getStatus());
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
