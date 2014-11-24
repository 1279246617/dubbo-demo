package com.coe.wms.task.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordItemDao;
import com.coe.wms.dao.warehouse.storage.IOnShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderReceiverDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderSenderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IReportDao;
import com.coe.wms.dao.warehouse.storage.impl.ReportDaoImpl;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.report.Report;
import com.coe.wms.model.warehouse.report.ReportType.ReportTypeCode;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.task.IGenerateReportTask;
import com.coe.wms.util.DateUtil;

@Component
public class GenerateReportTaskImpl implements IGenerateReportTask {

	private static final Logger logger = Logger.getLogger(OnShelvesTaskImpl.class);

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

	@Resource(name = "onShelfDao")
	private IOnShelfDao onShelfDao;

	@Resource(name = "reportDao")
	private IReportDao reportDao;

	/**
	 * 生成入库日报表
	 * 
	 * 每天凌晨00:00:00
	 */
	@Scheduled(cron = "59 * * * * ? ")
	@Override
	public void inWarehouseReport() {
		Long current = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		// 开始时间
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		String startTime = DateUtil.dateConvertString(new Date(calendar.getTimeInMillis()), DateUtil.yyyy_MM_ddHHmmss);
		String date = DateUtil.dateConvertString(new Date(calendar.getTimeInMillis()), DateUtil.yyyy_MM_dd);
		// 终止时间
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		String endTime = DateUtil.dateConvertString(new Date(calendar.getTimeInMillis()), DateUtil.yyyy_MM_ddHHmmss);

		logger.info("入库报表:起始时间:" + startTime + " 终止时间:" + endTime);

		// 查找所有状态是OK的客户
		User userParam = new User();
		userParam.setUserType(User.USER_TYPE_CUSTOMER);
		userParam.setStatus(User.STATUS_OK);
		List<User> userList = userDao.findUser(userParam);
		logger.debug("入库报表:查到客户数:" + userList.size());
		// 根据客户查找入库记录
		Map<String, String> moreParam = new HashMap<String, String>();
		moreParam.put("createdTimeStart", startTime);
		moreParam.put("createdTimeEnd", endTime);
		// 仓库
		List<Warehouse> warehouseList = warehouseDao.findAllWarehouse();
		for (Warehouse warehouse : warehouseList) {
			Long warehouseId = warehouse.getId();
			for (User user : userList) {// 按仓库为每个用户生成入库报表
				Long userIdOfCustomer = user.getId();
				InWarehouseRecord recordParam = new InWarehouseRecord();
				recordParam.setWarehouseId(warehouseId);
				recordParam.setUserIdOfCustomer(userIdOfCustomer);// 查找指定客户,仓库的入库记录
				List<InWarehouseRecord> inWarehouseRecordList = inWarehouseRecordDao.findInWarehouseRecord(recordParam, moreParam, null);
				if (inWarehouseRecordList == null || inWarehouseRecordList.size() <= 0) {
					continue;
				}
				for (InWarehouseRecord record : inWarehouseRecordList) {// 迭代收货记录
					InWarehouseRecordItem itemParam = new InWarehouseRecordItem();
					itemParam.setInWarehouseRecordId(record.getId());
					List<InWarehouseRecordItem> recordItemList = inWarehouseRecordItemDao.findInWarehouseRecordItem(itemParam, null, null);
						
					
				}

				String filePath = "";
				Report report = new Report();
				report.setCreatedTime(current);
				report.setRemark(user.getLoginName());
				report.setReportName("入库日报表-" + date + "-" + user.getLoginName());
				report.setReportType(ReportTypeCode.IN_WAREHOUSE_REPORT);
				report.setUserIdOfCustomer(userIdOfCustomer);
				report.setWarehouseId(warehouseId);
				report.setFilePath(filePath);
				Long reportId = reportDao.saveReport(report);
				logger.info("入库报表Id:" + reportId);
			}
		}
	}

	@Override
	public void outWarehouseReport() {

	}

	@Override
	public void inventoryReport() {

	}

}
