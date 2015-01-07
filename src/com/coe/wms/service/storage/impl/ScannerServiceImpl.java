package com.coe.wms.service.storage.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.scanner.pojo.ErrorCode;
import com.coe.scanner.pojo.Response;
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
import com.coe.wms.dao.warehouse.storage.IOutWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseRecordItemDao;
import com.coe.wms.dao.warehouse.storage.IReportDao;
import com.coe.wms.dao.warehouse.storage.IReportTypeDao;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.service.inventory.IShelfService;
import com.coe.wms.service.storage.IScannerService;
import com.coe.wms.util.Config;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.GsonUtil;
import com.coe.wms.util.StringUtil;

/**
 * 仓配服务
 * 
 * @author Administrator
 * 
 */
@Service("scannerService")
public class ScannerServiceImpl implements IScannerService {

	private static final Logger logger = Logger.getLogger(ScannerServiceImpl.class);

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

	@Resource(name = "productDao")
	private IProductDao productDao;

	@Resource(name = "config")
	private Config config;

	@Resource(name = "shipwayDao")
	private IShipwayDao shipwayDao;

	@Resource(name = "shelfService")
	private IShelfService shelfService;

	@Override
	public Response getOrderId(String content, Long userIdOfOperator) {
		Response response = new Response();
		response.setSuccess(false);
		Map<String, String> map = (Map<String, String>) GsonUtil.toObject(content, Map.class);
		String trackingNo = map.get("trackingNo");// 跟踪单号
		if (StringUtil.isNull(trackingNo)) {
			response.setMessage("跟踪单号不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		// 上架,根据跟踪单号查询收货记录
		InWarehouseRecord param = new InWarehouseRecord();
		param.setTrackingNo(trackingNo);
		List<Map<String, String>> mapList = shelfService.checkInWarehouseRecord(param);
		if (mapList.size() < 1) {
			response.setMessage("该单号无收货记录");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		List<Map<String, String>> mapList2 = new ArrayList<Map<String, String>>();
		for (Map<String, String> map1 : mapList) {
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("trackingNo", trackingNo);
			map2.put("orderId", map1.get("recordId"));// 收货记录id
			String time = DateUtil.dateConvertString(DateUtil.stringConvertDate(map1.get("createdTime"), DateUtil.yyyy_MM_ddHHmmss), "dd-HH:mm");
			String description = "收货时间:" + time + "  状态:" + map1.get("status");
			map2.put("description", description);
			mapList2.add(map2);
		}
		String message = GsonUtil.toJson(mapList2);
		response.setMessage(message);
		response.setSuccess(true);
		return response;
	}

	@Override
	public Response getBatchNo(String content, Long userIdOfOperator) {
		Response response = new Response();
		response.setSuccess(false);
		Map<String, String> map = (Map<String, String>) GsonUtil.toObject(content, Map.class);
		String barcode = map.get("barcode");// 跟踪单号
		String orderId = map.get("orderId");// 收货记录
		if (StringUtil.isNull(barcode)) {
			response.setMessage("商品条码不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		if (StringUtil.isNull(orderId)) {
			response.setMessage("收货记录不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		InWarehouseRecordItem inWarehouseRecordItem = new InWarehouseRecordItem();
		inWarehouseRecordItem.setInWarehouseRecordId(Long.valueOf(orderId));
		inWarehouseRecordItem.setSku(barcode);
		List<String> list = inWarehouseRecordItemDao.findInWarehouseBatchNo(inWarehouseRecordItem);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (String temp : list) {
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("batchNo", temp);
			mapList.add(map1);
		}
		String message = GsonUtil.toJson(mapList);
		response.setMessage(message);
		response.setSuccess(true);
		return response;
	}

	@Override
	public Response onShelf(String content, Long userIdOfOperator) {
		Response response = new Response();
		response.setSuccess(false);
		Map<String, String> map = (Map<String, String>) GsonUtil.toObject(content, Map.class);
		String barcode = map.get("barcode");// 条码
		String orderId = map.get("orderId");// 收货记录
		String seatCode = map.get("seatCode");// 货位
		String quantity = map.get("quantity");// 货位
		String batchNo = map.get("batchNo");// 批次
		if (StringUtil.isNull(orderId)) {
			response.setMessage("收货记录不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		if (StringUtil.isNull(quantity)) {
			response.setMessage("收货数量不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		Map<String, String> serviceResult = shelfService.saveOnShelvesItem(barcode, Integer.valueOf(quantity), seatCode, Long.valueOf(orderId), userIdOfOperator);
		// 失败
		if (!StringUtil.isEqual(serviceResult.get(Constant.STATUS), Constant.SUCCESS)) {
			response.setMessage(serviceResult.get(Constant.MESSAGE));
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		response.setSuccess(true);
		return response;
	}

	@Override
	public Response outShelf(String content, Long userIdOfOperator) {
		// TODO Auto-generated method stub
		return null;
	}

}
