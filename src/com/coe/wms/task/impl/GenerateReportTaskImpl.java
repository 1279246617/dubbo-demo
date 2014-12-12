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

import com.coe.wms.dao.product.IProductDao;
import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordItemDao;
import com.coe.wms.dao.warehouse.storage.IItemDailyInventoryDao;
import com.coe.wms.dao.warehouse.storage.IItemInventoryDao;
import com.coe.wms.dao.warehouse.storage.IOnShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderReceiverDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderSenderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseRecordItemDao;
import com.coe.wms.dao.warehouse.storage.IReportDao;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.report.Report;
import com.coe.wms.model.warehouse.report.ReportType.ReportTypeCode;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.model.warehouse.storage.record.ItemDailyInventory;
import com.coe.wms.model.warehouse.storage.record.ItemInventory;
import com.coe.wms.model.warehouse.storage.record.OutWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.OutWarehouseRecordItem;
import com.coe.wms.task.IGenerateReportTask;
import com.coe.wms.util.Config;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.FileUtil;
import com.coe.wms.util.NumberUtil;
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

	@Resource(name = "outWarehouseRecordDao")
	private IOutWarehouseRecordDao outWarehouseRecordDao;

	@Resource(name = "outWarehouseRecordItemDao")
	private IOutWarehouseRecordItemDao outWarehouseRecordItemDao;

	@Resource(name = "itemInventoryDao")
	private IItemInventoryDao itemInventoryDao;

	@Resource(name = "itemDailyInventoryDao")
	private IItemDailyInventoryDao itemDailyInventoryDao;

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Resource(name = "onShelfDao")
	private IOnShelfDao onShelfDao;

	@Resource(name = "reportDao")
	private IReportDao reportDao;

	@Resource(name = "productDao")
	private IProductDao productDao;

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
	private static final String[] OUT_WAREHOUSE_REPORT_HEAD = { "序号", "出库时间", "仓库编号", "客户编号", "单据类型", "订单来源", "出库订单号", "客户订单号", "运单编号", "快递公司", "商品单价", "总金额(不含运费)", "运费", "重量", "体积重量", "收货人姓名", "收货省", "收货市", "收货区", "收货人地址", "收货人电话", "SKU编码", "商品名称",
			"SKU数量", "SKU条码", "批次号", "备注" };

	private static final String INVENTORY_REPORT_SHEET_TITLE = "库存报表";
	/**
	 * 库存日报表 EXCEL头
	 */
	private static final String[] INVENTORY_REPORT_HEAD = { "序号", "仓库编号", "结转日期", "货主", "SKU编码", "商品条码", "商品名称", "批次号", "前日结余", "当日收货数量", "当日发货数量", "当日盘点调整数量", "当日剩余数量" };

	/**
	 * 生成入库日报表
	 * 
	 * 每天凌晨1点统计昨天入库
	 */
	@Scheduled(cron = "0 0 1 * * ? ")
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
					String filePathAndName = filePath + user.getLoginName() + "-" + IN_WAREHOUSE_REPORT_SHEET_TITLE + "-" + date + "-" + warehouseId + ".xls";
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
							row[1] = DateUtil.dateConvertString(new Date(record.getCreatedTime()), DateUtil.yyyy_MM_dd);// 入库时间
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
					report.setReportName("入库日报表-" + warehouse.getWarehouseName() + "-" + user.getUserName() + "-" + date);
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

	/**
	 * 生成出库日报表
	 * 
	 * 每天凌晨2点统计昨天出库
	 */
	@Scheduled(cron = "0 0 2 * * ? ")
	@Override
	public void outWarehouseReport() {
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
		logger.info("出库报表:起始时间:" + startTime + " 终止时间:" + endTime);

		// 查找所有状态是OK的客户
		User userParam = new User();
		userParam.setUserType(User.USER_TYPE_CUSTOMER);
		userParam.setStatus(User.STATUS_OK);
		List<User> userList = userDao.findUser(userParam);
		logger.debug("出库报表:查到客户数:" + userList.size());
		// 根据客户查找出库记录
		Map<String, String> moreParam = new HashMap<String, String>();
		moreParam.put("createdTimeStart", startTime);
		moreParam.put("createdTimeEnd", endTime);
		// 仓库
		List<Warehouse> warehouseList = warehouseDao.findAllWarehouse();
		for (Warehouse warehouse : warehouseList) {
			Long warehouseId = warehouse.getId();
			// 按仓库为每个用户生成出库报表
			for (User user : userList) {
				try {
					Long userIdOfCustomer = user.getId();
					OutWarehouseRecord recordParam = new OutWarehouseRecord();
					recordParam.setWarehouseId(warehouseId);
					recordParam.setUserIdOfCustomer(userIdOfCustomer);// 查找指定客户,仓库的出库记录
					List<OutWarehouseRecord> outWarehouseRecordList = outWarehouseRecordDao.findOutWarehouseRecord(recordParam, moreParam, null);
					if (outWarehouseRecordList == null || outWarehouseRecordList.size() <= 0) {
						continue;
					}
					String filePath = config.getRuntimeFilePath() + "/report/";
					FileUtil.mkdirs(filePath);
					// 文件保存地址
					String filePathAndName = filePath + user.getLoginName() + "-" + OUT_WAREHOUSE_REPORT_SHEET_TITLE + "-" + date + "-" + warehouseId + ".xls";
					int index = 0;
					List<String[]> rows = new ArrayList<String[]>();
					for (OutWarehouseRecord record : outWarehouseRecordList) {// 迭代出货记录
						Long coeTrackingNoId = record.getCoeTrackingNoId();
						OutWarehouseRecordItem itemParam = new OutWarehouseRecordItem();
						// 出货主单和出货明细记录通过coe交接单号id关联
						itemParam.setCoeTrackingNoId(coeTrackingNoId);
						List<OutWarehouseRecordItem> recordItemList = outWarehouseRecordItemDao.findOutWarehouseRecordItem(itemParam, null, null);
						for (OutWarehouseRecordItem recordItem : recordItemList) {
							Long outWarehouseOrderId = recordItem.getOutWarehouseOrderId();
							// 出库订单
							OutWarehouseOrder order = outWarehouseOrderDao.getOutWarehouseOrderById(outWarehouseOrderId);
							// 收件人
							OutWarehouseOrderReceiver receiver = outWarehouseOrderReceiverDao.getOutWarehouseOrderReceiverByOrderId(outWarehouseOrderId);
							// 查找出库订单明细
							OutWarehouseOrderItem orderItemParam = new OutWarehouseOrderItem();
							orderItemParam.setOutWarehouseOrderId(outWarehouseOrderId);
							List<OutWarehouseOrderItem> orderItems = outWarehouseOrderItemDao.findOutWarehouseOrderItem(orderItemParam, null, null);
							for (OutWarehouseOrderItem orderItem : orderItems) {
								index++;
								String[] row = new String[27];
								row[0] = index + "";// 序号
								row[1] = DateUtil.dateConvertString(new Date(record.getCreatedTime()), DateUtil.yyyy_MM_dd);// 出库时间
								row[2] = warehouse.getWarehouseNo();// 仓库编号
								row[3] = user.getLoginName();// 客户编号
								row[4] = "销售出库单";// 单据类型 待完善
								row[5] = user.getUserName();// 订单来源
								row[6] = order.getId() + "";// 出库订单号
								row[7] = order.getCustomerReferenceNo() + "";// 客户订单号
								row[8] = order.getTrackingNo();// 运单编号
								row[9] = order.getShipwayCode();// 快递公司
								row[10] = NumberUtil.div(orderItem.getSkuUnitPrice(), 100d).toString();// 商品单价,目前出库订单sku单价单位是分(CNF)
								Double unit = NumberUtil.div(orderItem.getSkuUnitPrice(), 100d);
								row[11] = NumberUtil.getNumPrecision(unit * orderItem.getQuantity(), 2).toString(); // 总金额(不含运费)
								row[12] = "0";// 运费
								row[13] = NumberUtil.getNumPrecision(order.getOutWarehouseWeight(), 2) + ""; // 重量
								row[14] = "";// 体积重量
								row[15] = receiver.getName();// 收货人姓名
								row[16] = receiver.getStateOrProvince();// 收货省
								row[17] = receiver.getCity();// 收货市
								row[18] = receiver.getCounty();// 收货区
								String address = receiver.getAddressLine1() == null ? "" : (receiver.getAddressLine1() + " ");
								address += (receiver.getAddressLine2() == null ? "" : receiver.getAddressLine2() + " ");
								address += (receiver.getPostalCode() == null ? "" : receiver.getPostalCode());
								row[19] = address;// 收货人地址

								String phone = receiver.getPhoneNumber() == null ? "" : (receiver.getPhoneNumber() + " ");
								phone += (receiver.getMobileNumber() == null ? "" : receiver.getMobileNumber());
								row[20] = phone;
								row[21] = "";// SKU编码
								row[22] = orderItem.getSkuName();// 商品名称
								row[23] = orderItem.getQuantity() + "";// SKU数量
								row[24] = orderItem.getSku();// SKU条码
								row[25] = "";// 批次号
								row[26] = "";// 备注
								rows.add(row);
							}
						}
					}
					Report report = new Report();
					report.setCreatedTime(current);
					report.setRemark(user.getLoginName());
					report.setReportName("出库日报表-" + warehouse.getWarehouseName() + "-" + user.getUserName() + "-" + date);
					report.setReportType(ReportTypeCode.OUT_WAREHOUSE_REPORT);
					report.setUserIdOfCustomer(userIdOfCustomer);
					report.setWarehouseId(warehouseId);
					report.setFilePath(filePathAndName);
					Long reportId = reportDao.saveReport(report);
					logger.info("出库报表Id:" + reportId + "  创建文件:" + filePathAndName);
					POIExcelUtil.createExcel(OUT_WAREHOUSE_REPORT_SHEET_TITLE, OUT_WAREHOUSE_REPORT_HEAD, rows, filePathAndName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 生成日结库存记录
	 * 
	 * 每天凌晨2点30分统计昨日库存
	 */
	@Scheduled(cron = "0 30 2 * * ? ")
	@Override
	public void dailyInventory() {
		Calendar calendar = Calendar.getInstance();
		// 开始时间
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		String date = DateUtil.dateConvertString(new Date(calendar.getTimeInMillis()), DateUtil.yyyy_MM_dd);
		logger.info("日结库存记录:统计日期:" + date);
		// 查找所有状态是OK的客户
		User userParam = new User();
		userParam.setUserType(User.USER_TYPE_CUSTOMER);
		userParam.setStatus(User.STATUS_OK);
		List<User> userList = userDao.findUser(userParam);
		// 仓库
		List<Warehouse> warehouseList = warehouseDao.findAllWarehouse();
		for (Warehouse warehouse : warehouseList) {
			Long warehouseId = warehouse.getId();
			// 按仓库为每个用户生成入库报表
			for (User user : userList) {
				Long userIdOfCustomer = user.getId();
				ItemInventory itemInventoryParam = new ItemInventory();
				itemInventoryParam.setUserIdOfCustomer(userIdOfCustomer);
				itemInventoryParam.setWarehouseId(warehouseId);

				List<ItemInventory> itemInventoryList = itemInventoryDao.findItemInventory(itemInventoryParam, null, null);
				if (itemInventoryList == null || itemInventoryList.size() <= 0) {
					continue;
				}
				for (ItemInventory inventory : itemInventoryList) {
					itemDailyInventoryDao.addItemDailyInventory(warehouseId, userIdOfCustomer, inventory.getSku(), inventory.getQuantity(), inventory.getAvailableQuantity(), date);
				}
			}
		}
	}

	/**
	 * 生成库存日报表
	 * 
	 * 每天凌晨3点统计库存
	 */
	@Scheduled(cron = "0 0 3 * * ? ")
	@Override
	public void inventoryReport() {
		Long current = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		// 开始时间
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		String startTime = DateUtil.dateConvertString(new Date(calendar.getTimeInMillis()), DateUtil.yyyy_MM_ddHHmmss);

		String date = DateUtil.dateConvertString(new Date(calendar.getTimeInMillis()), DateUtil.yyyy_MM_dd);
		// 库存记录日期的前一天, 跟服务器时间隔2天
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		String yesterday = DateUtil.dateConvertString(new Date(calendar.getTimeInMillis()), DateUtil.yyyy_MM_dd);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		// 终止时间
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		String endTime = DateUtil.dateConvertString(new Date(calendar.getTimeInMillis()), DateUtil.yyyy_MM_ddHHmmss);
		logger.info("库存报表:起始时间:" + startTime + " 终止时间:" + endTime);

		// 查找所有状态是OK的客户
		User userParam = new User();
		userParam.setUserType(User.USER_TYPE_CUSTOMER);
		userParam.setStatus(User.STATUS_OK);
		List<User> userList = userDao.findUser(userParam);
		logger.debug("库存报表:查到客户数:" + userList.size());
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
					ItemDailyInventory inventoryParam = new ItemDailyInventory();
					inventoryParam.setUserIdOfCustomer(userIdOfCustomer);
					inventoryParam.setWarehouseId(warehouseId);
					inventoryParam.setInventoryDate(date);
					List<ItemDailyInventory> inventoryList = itemDailyInventoryDao.findItemDailyInventory(inventoryParam, null, null);
					if (inventoryList == null || inventoryList.size() <= 0) {
						continue;
					}
					String filePath = config.getRuntimeFilePath() + "/report/";
					FileUtil.mkdirs(filePath);
					// 文件保存地址
					String filePathAndName = filePath + user.getLoginName() + "-" + INVENTORY_REPORT_SHEET_TITLE + "-" + date + "-" + warehouseId + ".xls";
					List<String[]> rows = new ArrayList<String[]>();
					int index = 0;
					for (ItemDailyInventory inventory : inventoryList) {// 迭代库存记录
						index++;
						String[] row = new String[13];
						row[0] = index + "";// 序号
						row[1] = warehouse.getWarehouseNo();// 仓库编号
						row[2] = inventory.getInventoryDate().replaceAll("-", ""); // 结转日期
						row[3] = user.getUserName();// 货主
						String barcode = inventory.getSku();
						// 根据商品条码获取商品SKU
						row[4] = productDao.findProductSkuByBarcode(userIdOfCustomer, barcode);
						row[5] = barcode;// 商品条码
						// 目前无SKU库..只能从如库订单中查找SKU产品名
						String skuName = inWarehouseOrderItemDao.getSkuNameByCustomerIdAndSku(inventory.getSku(), userIdOfCustomer);
						row[6] = skuName;// 商品名称
						row[7] = "";// 批次号 奶粉必填，其他没有则不用填
						// 查前日结余
						ItemDailyInventory yesterdayParam = new ItemDailyInventory();
						yesterdayParam.setUserIdOfCustomer(userIdOfCustomer);
						yesterdayParam.setWarehouseId(warehouseId);
						yesterdayParam.setSku(inventory.getSku());
						yesterdayParam.setInventoryDate(yesterday);
						List<ItemDailyInventory> yesterdayInventoryList = itemDailyInventoryDao.findItemDailyInventory(yesterdayParam, null, null);
						if (yesterdayInventoryList != null && yesterdayInventoryList.size() > 0) {
							row[8] = yesterdayInventoryList.get(0).getQuantity() + "";// 前日结余
						} else {
							row[8] = "";
						}
						Long inWarehouseItemSkuQuantity = inWarehouseRecordItemDao.countItemSkuQuantity(startTime, endTime, inventory.getSku(), userIdOfCustomer, warehouseId);
						row[9] = inWarehouseItemSkuQuantity + "";// 当日收货数量
						// 计算当日发货数量
						List<Long> orderIds = outWarehouseRecordItemDao.getOutWarehouseOrderIdsByRecordTime(startTime, endTime, userIdOfCustomer, warehouseId);
						Long outWarehouseItemSkuQuantity = outWarehouseOrderItemDao.sumSkuQuantityByOrderIdAndSku(orderIds, inventory.getSku());
						row[10] = outWarehouseItemSkuQuantity + "";
						row[11] = "";// 当日盘点调整数量
						row[12] = inventory.getQuantity() + "";// 当日剩余数量
						rows.add(row);
					}
					Report report = new Report();
					report.setCreatedTime(current);
					report.setRemark(user.getLoginName());
					report.setReportName("库存报表-" + warehouse.getWarehouseName() + "-" + user.getUserName() + "-" + date);
					report.setReportType(ReportTypeCode.INVENTORY_REPORT);
					report.setUserIdOfCustomer(userIdOfCustomer);
					report.setWarehouseId(warehouseId);
					report.setFilePath(filePathAndName);
					Long reportId = reportDao.saveReport(report);
					logger.info("库存报表Id:" + reportId + "  创建文件:" + filePathAndName);
					POIExcelUtil.createExcel(INVENTORY_REPORT_SHEET_TITLE, INVENTORY_REPORT_HEAD, rows, filePathAndName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
