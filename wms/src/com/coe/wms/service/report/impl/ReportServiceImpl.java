package com.coe.wms.service.report.impl;

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
import com.coe.wms.dao.warehouse.IShelfDao;
import com.coe.wms.dao.warehouse.ITrackingNoDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
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
import com.coe.wms.dao.warehouse.storage.IOutWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseRecordItemDao;
import com.coe.wms.dao.warehouse.storage.IReportDao;
import com.coe.wms.dao.warehouse.storage.IReportTypeDao;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.report.Report;
import com.coe.wms.model.warehouse.report.ReportType;
import com.coe.wms.service.report.IReportService;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;

/**
 * 仓配服务
 * 
 * @author Administrator
 * 
 */
@Service("reportService")
public class ReportServiceImpl implements IReportService {

	private static final Logger logger = Logger.getLogger(ReportServiceImpl.class);

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

	@Override
	public List<ReportType> findAllReportType() throws ServiceException {
		return reportTypeDao.findAllReportType();
	}

	@Override
	public Pagination getListReportData(Report param, Map<String, String> moreParam, Pagination page) {
		List<Report> reportList = reportDao.findReport(param, moreParam, page);
		List<Object> list = new ArrayList<Object>();
		for (Report report : reportList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", report.getId());
			if (report.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(report.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			// 查询用户名
			User user = userDao.getUserById(report.getUserIdOfCustomer());
			map.put("userLoginNameOfCustomer", user.getLoginName());
			// 查询操作员
			if (NumberUtil.greaterThanZero(report.getUserIdOfOperator())) {
				User userOfOperator = userDao.getUserById(report.getUserIdOfOperator());
				map.put("userLoginNameOfOperator", userOfOperator.getLoginName());
			}
			map.put("reportName", report.getReportName());
			ReportType type = reportTypeDao.findReportTypeByCode(report.getReportType());
			map.put("reportType", type.getCn());
			if (NumberUtil.greaterThanZero(report.getWarehouseId())) {
				Warehouse warehouse = warehouseDao.getWarehouseById(report.getWarehouseId());
				map.put("warehouse", warehouse.getWarehouseName());
			}
			map.put("remark", report.getRemark() == null ? "" : report.getRemark());
			list.add(map);
		}
		page.total = reportDao.countReport(param, moreParam);
		page.rows = list;
		return page;
	}

	@Override
	public Report getReportById(Long reportId) throws ServiceException {
		return reportDao.getReportById(reportId);
	}
}
