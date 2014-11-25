package com.coe.wms.task.impl;

import java.io.IOException;
import java.util.ArrayList;
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
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.report.Report;
import com.coe.wms.model.warehouse.report.ReportType.ReportTypeCode;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.task.IGenerateReportTask;
import com.coe.wms.util.Config;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.FileUtil;
import com.coe.wms.util.POIExcelUtil;

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

	@Resource(name = "config")
	private Config config;

	private static final String IN_WAREHOUSE_REPORT_SHEET_TITLE = "入库报表";
	/**
	 * 入库记录日报表 EXCEL头
	 */
	private static final String[] IN_WAREHOUSE_REPORT_HEAD = { "序号", "入库时间", "仓库编号", "客户编号", "单据类型", "入库单号", "客户订单号", "SKU编码", "SKU条码", "商品名称", "批次号", "订单数量", "入库数量", "包装单位", "SKU体积", "SKU重量", "备注" };

	private static final String OUT_WAREHOUSE_REPORT_SHEET_TITLE = "出库报表";
	/**
	 * 出库记录日报表 EXCEL头
	 */
	private static final String[] OUT_WAREHOUSE_REPORT_HEAD = { "序号", "出库时间", "仓库编号", "客户编号", "单据类型", "订单来源", "出库订单号", "客户订单号", "运单编号", "快递公司", "重量", "体积重量", "收货人姓名", "收货省", "收货市", "收货区", "收货人地址", "收货人电话", "SKU编码", "商品名称", "SKU条码", "批次号", "SKU数量",
			"备注" };

	private static final String INVENTORY_REPORT_SHEET_TITLE = "库存报表";
	/**
	 * 库存日报表 EXCEL头
	 */
	private static final String[] INVENTORY_REPORT_HEAD = { "结转日期", "货主", "SKU编码", "商品条码", "商品名称", "批次号", "前日结余", "当日收货数量", "当日发货数量", "当日盘点调整数量", "当日剩余数量" };

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
		startTime = "2014-10-10 00:00:00";
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
			// 按仓库为每个用户生成入库报表
			for (User user : userList) {
				try {
					Long userIdOfCustomer = user.getId();
					InWarehouseRecord recordParam = new InWarehouseRecord();
					recordParam.setWarehouseId(warehouseId);
					recordParam.setUserIdOfCustomer(userIdOfCustomer);// 查找指定客户,仓库的入库记录
					List<InWarehouseRecord> inWarehouseRecordList = inWarehouseRecordDao.findInWarehouseRecord(recordParam, moreParam, null);
					if (inWarehouseRecordList == null || inWarehouseRecordList.size() <= 0) {
						continue;
					}
					String filePath = config.getRuntimeFilePath() + "/report/";
					FileUtil.mkdirs(filePath);
					// 文件保存地址
					String filePathAndName = filePath + user.getLoginName() + "-" + IN_WAREHOUSE_REPORT_SHEET_TITLE + "-" + date + ".xls";
					List<String[]> rows = new ArrayList<String[]>();
					int index = 0;
					for (InWarehouseRecord record : inWarehouseRecordList) {// 迭代收货记录
						Long inWarehouseOrderId = record.getInWarehouseOrderId();
						InWarehouseOrder order = inWarehouseOrderDao.getInWarehouseOrderById(inWarehouseOrderId);
						InWarehouseRecordItem itemParam = new InWarehouseRecordItem();
						itemParam.setInWarehouseRecordId(record.getId());
						List<InWarehouseRecordItem> recordItemList = inWarehouseRecordItemDao.findInWarehouseRecordItem(itemParam, null, null);
						for (InWarehouseRecordItem recordItem : recordItemList) {
							InWarehouseOrderItem orderItemParam = new InWarehouseOrderItem();
							orderItemParam.setOrderId(inWarehouseOrderId);
							orderItemParam.setSku(recordItem.getSku());
							// 由于收货记录 无记录sku对应的产品名,
							// 并且未建立sku产品库.目前从收货记录的sku查找产品名,只能通过次种方式查找...
							// 解决方法:建立sku库
							List<InWarehouseOrderItem> orderItems = inWarehouseOrderItemDao.findInWarehouseOrderItem(orderItemParam, null, null);
							InWarehouseOrderItem orderItem = orderItems.get(0);
							index++;
							String[] row = new String[17];
							row[0] = index + "";// 序号
							row[1] = DateUtil.dateConvertString(new Date(record.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss);// 入库时间
							row[2] = warehouse.getWarehouseNo();// 仓库编号
							row[3] = user.getLoginName();// 客户编号
							row[4] = "采购入库单";// 单据类型 待完善
							row[5] = record.getId().toString();// 入库单号(流水号)
							row[6] = order.getCustomerReferenceNo();// 客户订单号
							row[7] = "";// SKU编码
							row[8] = recordItem.getSku();// SKU条码
							row[9] = orderItem.getSkuName();// 商品名称
							row[10] = "";// 奶粉批次
							row[11] = orderItem.getQuantity() + "";// 订单数量
							row[12] = recordItem.getQuantity() + "";// 入库数量
							row[13] = "罐";// 包装单位 待建立sku库
							row[14] = "";// SKU体积
							row[15] = "";// SKU重量
							row[16] = "";// 备注
							rows.add(row);
						}
					}

					Report report = new Report();
					report.setCreatedTime(current);
					report.setRemark(user.getLoginName());
					report.setReportName("入库日报表-" + date + "-" + user.getLoginName());
					report.setReportType(ReportTypeCode.IN_WAREHOUSE_REPORT);
					report.setUserIdOfCustomer(userIdOfCustomer);
					report.setWarehouseId(warehouseId);
					report.setFilePath(filePathAndName);
					Long reportId = reportDao.saveReport(report);
					logger.info("入库报表Id:" + reportId + "  创建文件:" + filePathAndName);
					POIExcelUtil.createExcel(IN_WAREHOUSE_REPORT_SHEET_TITLE, IN_WAREHOUSE_REPORT_HEAD, rows, filePathAndName);
				} catch (IOException e) {
					e.printStackTrace();
				}
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
