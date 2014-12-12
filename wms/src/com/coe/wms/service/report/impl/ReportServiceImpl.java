package com.coe.wms.service.report.impl;

import java.io.IOException;
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
import com.coe.wms.model.warehouse.report.ReportType.ReportTypeCode;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.model.warehouse.storage.record.OutWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.OutWarehouseRecordItem;
import com.coe.wms.service.report.IReportService;
import com.coe.wms.util.Config;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.FileUtil;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.POIExcelUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;

/**
 * 仓配服务
 * 
 * @author Administrator
 * 
 */
@Service("reportService")
public class ReportServiceImpl implements IReportService {

	private static final Logger logger = Logger.getLogger(ReportServiceImpl.class);

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

	@Resource(name = "config")
	private Config config;

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

	@Override
	public Map<String, String> checkAddReport(String userLoginNameOfCustomer, Long warehouseId, String reportType, String reportName, String inWarehouseTimeStart, String inWarehouseTimeEnd, String outWarehouseTimeStart, String outWarehouseTimeEnd,
			Long userIdOfOperator) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(userLoginNameOfCustomer)) {
			map.put(Constant.MESSAGE, "请输入客户帐号");
			return map;
		}
		if (StringUtil.isNull(reportType)) {
			map.put(Constant.MESSAGE, "请输入报表类型");
			return map;
		}
		Long userIdOfCustomer = userDao.findUserIdByLoginName(userLoginNameOfCustomer);
		if (userIdOfCustomer == null) {
			map.put(Constant.MESSAGE, "请输入有效的客户帐号");
			return map;
		}
		if (StringUtil.isEqual(reportType, ReportTypeCode.IN_WAREHOUSE_REPORT)) {
			if (StringUtil.isNull(inWarehouseTimeStart) || StringUtil.isNull(inWarehouseTimeEnd)) {
				map.put(Constant.MESSAGE, "请输入入库时间");
				return map;
			}
		}
		if (StringUtil.isEqual(reportType, ReportTypeCode.OUT_WAREHOUSE_REPORT)) {
			if (StringUtil.isNull(outWarehouseTimeStart) || StringUtil.isNull(outWarehouseTimeEnd)) {
				map.put(Constant.MESSAGE, "请输入出库时间");
				return map;
			}
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public Map<String, String> addInWarehouseReport(Long userIdOfCustomer, Long warehouseId, String reportName, String inWarehouseTimeStart, String inWarehouseTimeEnd) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		Long current = System.currentTimeMillis();
		User user = userDao.getUserById(userIdOfCustomer);
		Warehouse warehouse = warehouseDao.getWarehouseById(warehouseId);
		try {
			InWarehouseRecord recordParam = new InWarehouseRecord();
			recordParam.setWarehouseId(warehouseId);
			recordParam.setUserIdOfCustomer(userIdOfCustomer);// 查找指定客户,仓库的入库记录
			Map<String, String> moreParam = new HashMap<String, String>();
			moreParam.put("createdTimeStart", inWarehouseTimeStart);
			moreParam.put("createdTimeEnd", inWarehouseTimeEnd);
			List<InWarehouseRecord> inWarehouseRecordList = inWarehouseRecordDao.findInWarehouseRecord(recordParam, moreParam, null);
			String filePath = config.getRuntimeFilePath() + "/report/";
			FileUtil.mkdirs(filePath);
			// 文件保存地址
			String filePathAndName = filePath + user.getLoginName() + "-" + IN_WAREHOUSE_REPORT_SHEET_TITLE + "-" + current + "-" + warehouseId + ".xls";
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
					// 由于收货记录 无记录sku对应的商品名,
					// 并且未建立sku商品库.目前从收货记录的sku查找商品名,只能通过次种方式查找...
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
			if (StringUtil.isNotNull(reportName)) {
				report.setReportName(reportName);
			} else {
				report.setReportName("入库日报表-" + warehouse.getWarehouseName() + "-" + user.getUserName() + "-" + inWarehouseTimeStart + "-" + inWarehouseTimeEnd);
			}
			report.setReportType(ReportTypeCode.IN_WAREHOUSE_REPORT);
			report.setUserIdOfCustomer(userIdOfCustomer);
			report.setWarehouseId(warehouseId);
			report.setFilePath(filePathAndName);
			Long reportId = reportDao.saveReport(report);
			logger.info("入库报表Id:" + reportId + "  创建文件:" + filePathAndName);
			POIExcelUtil.createExcel(IN_WAREHOUSE_REPORT_SHEET_TITLE, IN_WAREHOUSE_REPORT_HEAD, rows, filePathAndName);
			map.put("reportId", reportId + "");
			map.put("reportName", report.getReportName());
			map.put(Constant.MESSAGE, "创建报表成功,自动报表下载");
		} catch (IOException e) {
			logger.error("创建报表IO异常:" + e, e);
			map.put(Constant.MESSAGE, "创建报表异常,生成报表失败");
			return map;
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public Map<String, String> addOutWarehouseReport(Long userIdOfCustomer, Long warehouseId, String reportName, String outWarehouseTimeStart, String outWarehouseTimeEnd) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		Long current = System.currentTimeMillis();
		User user = userDao.getUserById(userIdOfCustomer);
		Warehouse warehouse = warehouseDao.getWarehouseById(warehouseId);

		try {
			Map<String, String> moreParam = new HashMap<String, String>();
			moreParam.put("createdTimeStart", outWarehouseTimeStart);
			moreParam.put("createdTimeEnd", outWarehouseTimeEnd);
			OutWarehouseRecord recordParam = new OutWarehouseRecord();
			recordParam.setWarehouseId(warehouseId);
			recordParam.setUserIdOfCustomer(userIdOfCustomer);// 查找指定客户,仓库的出库记录
			List<OutWarehouseRecord> outWarehouseRecordList = outWarehouseRecordDao.findOutWarehouseRecord(recordParam, moreParam, null);
			String filePath = config.getRuntimeFilePath() + "/report/";
			FileUtil.mkdirs(filePath);
			// 文件保存地址
			String filePathAndName = filePath + user.getLoginName() + "-" + OUT_WAREHOUSE_REPORT_SHEET_TITLE + "-" + current + "-" + warehouseId + ".xls";
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
			if (StringUtil.isNotNull(reportName)) {
				report.setReportName(reportName);
			} else {
				report.setReportName("出库日报表-" + warehouse.getWarehouseName() + "-" + user.getUserName() + "-" + outWarehouseTimeStart + "-" + outWarehouseTimeEnd);
			}
			report.setReportType(ReportTypeCode.OUT_WAREHOUSE_REPORT);
			report.setUserIdOfCustomer(userIdOfCustomer);
			report.setWarehouseId(warehouseId);
			report.setFilePath(filePathAndName);
			Long reportId = reportDao.saveReport(report);
			logger.info("出库报表Id:" + reportId + "  创建文件:" + filePathAndName);
			POIExcelUtil.createExcel(OUT_WAREHOUSE_REPORT_SHEET_TITLE, OUT_WAREHOUSE_REPORT_HEAD, rows, filePathAndName);
			map.put("reportId", reportId + "");
			map.put("reportName", report.getReportName());
			map.put(Constant.MESSAGE, "创建报表成功,自动报表下载");
		} catch (IOException e) {
			logger.error("创建报表IO异常:" + e, e);
			map.put(Constant.MESSAGE, "创建报表异常,生成报表失败");
			return map;
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}
}
