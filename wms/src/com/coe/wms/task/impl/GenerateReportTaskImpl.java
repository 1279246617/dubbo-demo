package com.coe.wms.task.impl;

import java.util.Calendar;
import java.util.List;

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
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.report.Report;
import com.coe.wms.model.warehouse.report.ReportType.ReportTypeCode;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.task.IGenerateReportTask;

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

	/**
	 * 生成入库日报表
	 * 
	 * 每天凌晨00:00:00
	 */
	@Scheduled(cron = "0 0/2 0 * * ? ")
	@Override
	public void inWarehouseReport() {
		Long current = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);

		User user = new User();
		List<User> userList = userDao.findAllUser();
		System.out.println(calendar.getTime());

		Report report = new Report();
		report.setCreatedTime(current);

		report.setRemark("测试日报表");
		report.setReportName("入库日报表");
		report.setReportType(ReportTypeCode.IN_WAREHOUSE_REPORT);

		InWarehouseRecord record;
		InWarehouseRecordItem item;

	}

	@Override
	public void outWarehouseReport() {

	}

	@Override
	public void inventoryReport() {

	}

}
