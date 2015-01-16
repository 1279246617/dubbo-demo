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
import com.coe.wms.dao.warehouse.storage.IReportDao;
import com.coe.wms.dao.warehouse.storage.IReportTypeDao;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.warehouse.TrackingNo;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItemShelf;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus.OutWarehouseOrderStatusCode;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.model.warehouse.storage.record.ItemShelfInventory;
import com.coe.wms.model.warehouse.storage.record.OutShelf;
import com.coe.wms.service.inventory.IShelfService;
import com.coe.wms.service.storage.IOutWarehouseOrderService;
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

	@Resource(name = "outWarehouseOrderService")
	private IOutWarehouseOrderService outWarehouseOrderService;

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
		Response response = new Response();
		response.setSuccess(false);
		Map<String, String> map = (Map<String, String>) GsonUtil.toObject(content, Map.class);
		String barcode = map.get("barcode");// 条码
		String orderId = map.get("orderId");// 出库订单客户参考号
		String seatCode = map.get("seatCode");// 货位
		String quantity = map.get("quantity");// 数量
		if (StringUtil.isNull(orderId)) {
			response.setMessage("订单号不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		if (StringUtil.isNull(barcode)) {
			response.setMessage("商品条码不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		if (StringUtil.isNull(seatCode)) {
			response.setMessage("货位不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		if (StringUtil.isNull(quantity)) {
			response.setMessage("数量不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		OutWarehouseOrder param = new OutWarehouseOrder();
		param.setCustomerReferenceNo(orderId);
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(param, null, null);
		if (outWarehouseOrderList == null || outWarehouseOrderList.size() <= 0) {
			response.setMessage("该订单号找不到出库订单");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderList.get(0);
		if (!StringUtil.isEqual(OutWarehouseOrderStatusCode.WOS, outWarehouseOrder.getStatus())) {
			response.setMessage("该订单号对应订单非待捡货下架状态");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		// 查找预分配货位
		OutWarehouseOrderItemShelf outWarehouseOrderItemShelfParam = new OutWarehouseOrderItemShelf();
		outWarehouseOrderItemShelfParam.setOutWarehouseOrderId(outWarehouseOrder.getId());
		outWarehouseOrderItemShelfParam.setSeatCode(seatCode);
		// 货位和出库订单id查找预下架记录
		Long count = outWarehouseOrderItemShelfDao.countOutWarehouseOrderItemShelf(outWarehouseOrderItemShelfParam, null);
		if (count == 0) {
			response.setMessage("货位号错误,请查看捡货单的货位号");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		outWarehouseOrderItemShelfParam.setSku(barcode);
		// 加上条码条件查询预下架记录
		count = outWarehouseOrderItemShelfDao.countOutWarehouseOrderItemShelf(outWarehouseOrderItemShelfParam, null);
		if (count == 0) {
			response.setMessage("商品条码错误,请查看捡货单的商品条码");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		List<OutWarehouseOrderItemShelf> outWarehouseOrderItemShelfList = outWarehouseOrderItemShelfDao.findOutWarehouseOrderItemShelf(outWarehouseOrderItemShelfParam, null, null);
		OutWarehouseOrderItemShelf oItemShelf = outWarehouseOrderItemShelfList.get(0);
		if (StringUtil.isEqual(oItemShelf.getIsDone(), Constant.Y)) {
			response.setMessage("此订单的此商品已下架,请勿重复下架");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}

		if (oItemShelf.getQuantity() != Integer.valueOf(quantity)) {
			response.setMessage("商品数量错误,请查看捡货单的商品数量");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}

		// 更改预分配货物状态
		outWarehouseOrderItemShelfDao.updateOutWarehouseOrderItemShelfStatus(oItemShelf.getId(), Constant.Y);
		// 添加下架记录
		OutShelf outShelf = new OutShelf();
		outShelf.setBatchNo(oItemShelf.getBatchNo());
		outShelf.setCreatedTime(System.currentTimeMillis());
		outShelf.setCustomerReferenceNo(orderId);
		outShelf.setOutWarehouseOrderId(outWarehouseOrder.getId());
		outShelf.setQuantity(Integer.valueOf(oItemShelf.getQuantity()));
		outShelf.setSeatCode(oItemShelf.getSeatCode());
		outShelf.setSku(oItemShelf.getSku());
		outShelf.setUserIdOfCustomer(outWarehouseOrder.getUserIdOfCustomer());
		outShelf.setUserIdOfOperator(userIdOfOperator);
		outShelf.setWarehouseId(outWarehouseOrder.getWarehouseId());
		outShelfDao.saveOutShelf(outShelf);

		// 改变库位库存(ItemShelfInventory) ,不改变仓库sku库存(出货时改变ItemInventory)
		ItemShelfInventory itemShelfInventoryParam = new ItemShelfInventory();
		itemShelfInventoryParam.setBatchNo(oItemShelf.getBatchNo());
		itemShelfInventoryParam.setSeatCode(oItemShelf.getSeatCode());
		itemShelfInventoryParam.setSku(oItemShelf.getSku());
		itemShelfInventoryParam.setWarehouseId(outWarehouseOrder.getWarehouseId());
		itemShelfInventoryParam.setUserIdOfCustomer(outWarehouseOrder.getUserIdOfCustomer());
		List<ItemShelfInventory> itemShelfInventoryList = itemShelfInventoryDao.findItemShelfInventory(itemShelfInventoryParam, null, null);
		if (itemShelfInventoryList == null || itemShelfInventoryList.size() <= 0) {
			throw new ServiceException("找不到库位库存记录,条码:" + oItemShelf.getSku() + " 货位:" + oItemShelf.getSeatCode());
		}
		ItemShelfInventory itemShelfInventory = itemShelfInventoryList.get(0);
		int outQuantity = outShelf.getQuantity();// 更新货位库存数量
		itemShelfInventoryDao.updateItemShelfInventoryQuantity(itemShelfInventory.getId(), itemShelfInventory.getQuantity() - outQuantity);
		// 查找订单的预下架记录是否都已完成
		OutWarehouseOrderItemShelf oIshelfParam = new OutWarehouseOrderItemShelf();
		oIshelfParam.setOutWarehouseOrderId(outWarehouseOrder.getId());
		List<OutWarehouseOrderItemShelf> oIshelfList = outWarehouseOrderItemShelfDao.findOutWarehouseOrderItemShelf(oIshelfParam, null, null);
		boolean isDone = true;
		for (OutWarehouseOrderItemShelf temp : oIshelfList) {
			if (StringUtil.isEqual(temp.getIsDone(), Constant.N)) {
				isDone = false;
				break;
			}
		}
		if (isDone) { // 更新订单状态为称重
			outWarehouseOrderDao.updateOutWarehouseOrderStatus(outWarehouseOrder.getId(), OutWarehouseOrderStatusCode.WWW);
		}
		response.setSuccess(true);
		return response;
	}

	@Override
	public Response getOnShelfInfo(String content, Long userIdOfOperator) {
		Response response = new Response();
		response.setSuccess(false);
		Map<String, String> map = (Map<String, String>) GsonUtil.toObject(content, Map.class);
		String orderId = map.get("orderId");// 收货记录
		if (StringUtil.isNull(orderId)) {
			response.setMessage("收货记录不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		Long inWarehouseRecordId = Long.valueOf(orderId);
		int totalProductQuantity = 0;
		int onShelfProductQuantity = 0;
		int onShelfBarcodeQuantity = 0;
		// 收货明细
		InWarehouseRecordItem param = new InWarehouseRecordItem();
		param.setInWarehouseRecordId(inWarehouseRecordId);
		List<InWarehouseRecordItem> inWarehouseRecordItemList = inWarehouseRecordItemDao.findInWarehouseRecordItem(param, null, null);
		for (InWarehouseRecordItem item : inWarehouseRecordItemList) {
			totalProductQuantity += item.getQuantity();// 商品个数
			int onShelfQuantity = onShelfDao.countOnShelfSkuQuantity(inWarehouseRecordId, item.getSku());
			if (onShelfQuantity > 0) {
				onShelfBarcodeQuantity++;// 已上架条码数量
				onShelfProductQuantity += onShelfQuantity;// 已上架商品数量
			}
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("totalBarcodeQuantity", inWarehouseRecordItemList.size() + "");// 条码个数
		resultMap.put("totalProductQuantity", totalProductQuantity + "");// 商品个数
		resultMap.put("onShelfProductQuantity", onShelfProductQuantity + "");// 已上架商品数量
		resultMap.put("onShelfBarcodeQuantity", onShelfBarcodeQuantity + "");// 已上架条码数量
		String message = GsonUtil.toJson(resultMap);
		response.setMessage(message);
		response.setSuccess(true);
		return response;
	}

	@Override
	public Response getOutShelfInfo(String content, Long userIdOfOperator) {
		Response response = new Response();
		response.setSuccess(false);
		Map<String, String> map = (Map<String, String>) GsonUtil.toObject(content, Map.class);
		String orderId = map.get("orderId");// 收货记录
		if (StringUtil.isNull(orderId)) {
			response.setMessage("订单号不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		OutWarehouseOrder param = new OutWarehouseOrder();
		param.setCustomerReferenceNo(orderId);
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(param, null, null);
		if (outWarehouseOrderList == null || outWarehouseOrderList.size() <= 0) {
			response.setMessage("该订单号找不到出库订单");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderList.get(0);
		if (StringUtil.isEqual(OutWarehouseOrderStatusCode.WWC, outWarehouseOrder.getStatus())) {
			response.setMessage("该订单号对应订单为待仓库审核状态");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		if (StringUtil.isEqual(OutWarehouseOrderStatusCode.WPP, outWarehouseOrder.getStatus())) {
			response.setMessage("该订单号对应订单为待打印捡货单状态");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		int totalProductQuantity = 0;
		int outShelfProductQuantity = 0;
		int outShelfBarcodeQuantity = 0;
		OutWarehouseOrderItemShelf shelfParam = new OutWarehouseOrderItemShelf();
		shelfParam.setOutWarehouseOrderId(outWarehouseOrder.getId());
		List<OutWarehouseOrderItemShelf> outWarehouseOrderItemShelfList = outWarehouseOrderItemShelfDao.findOutWarehouseOrderItemShelf(shelfParam, null, null);
		for (OutWarehouseOrderItemShelf itemShelf : outWarehouseOrderItemShelfList) {
			totalProductQuantity += itemShelf.getQuantity();// 商品个数
			if (StringUtil.isEqual(itemShelf.getIsDone(), Constant.Y)) {
				outShelfBarcodeQuantity++;// 已下架条码数量
				outShelfProductQuantity += itemShelf.getQuantity();// 已下架商品数量
			}
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("totalBarcodeQuantity", outWarehouseOrderItemShelfList.size() + "");// 条码个数
		resultMap.put("totalProductQuantity", totalProductQuantity + "");// 商品个数
		resultMap.put("outShelfProductQuantity", outShelfProductQuantity + "");// 已下架商品数量
		resultMap.put("outShelfBarcodeQuantity", outShelfBarcodeQuantity + "");// 已下架条码数量

		String message = GsonUtil.toJson(resultMap);
		response.setMessage(message);
		response.setSuccess(true);
		return response;
	}

	@Override
	public Response getCoeTrackingNo(String content, Long userIdOfOperator) {
		Response response = new Response();
		response.setSuccess(false);
		TrackingNo trackingNo = trackingNoDao.getAvailableTrackingNoByType(TrackingNo.TYPE_COE);
		if (trackingNo == null) {
			response.setMessage("COE交接单号不足");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		response.setMessage(trackingNo.getTrackingNo());
		response.setSuccess(true);
		return response;
	}

	@Override
	public Response bindingOrder(String content, Long userIdOfOperator) {
		Response response = new Response();
		response.setSuccess(false);
		Map<String, String> map = (Map<String, String>) GsonUtil.toObject(content, Map.class);
		String coeTrackingNo = map.get("coeTrackingNo");// 交接单号
		if (StringUtil.isNull(coeTrackingNo)) {
			response.setMessage("COE 交接单号不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		String orderTrackingNo = map.get("orderTrackingNo");// 出库单号
		if (StringUtil.isNull(coeTrackingNo)) {
			response.setMessage("出库跟踪单号不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		List<TrackingNo> trackingNoList = trackingNoDao.findTrackingNo(coeTrackingNo, TrackingNo.TYPE_COE);
		if (trackingNoList == null || trackingNoList.size() == 0) {
			response.setMessage("COE交接单号不正确");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		TrackingNo coeTrackingNoObj = trackingNoList.get(0);
		if (StringUtil.isEqual(coeTrackingNoObj.getStatus(), String.valueOf(TrackingNo.STATUS_USED))) {
			response.setMessage("COE交接单号已经完成建包");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		Long coeTrackingNoId = coeTrackingNoObj.getId();

		Map<String, String> resultMap = outWarehouseOrderService.bindingOutWarehouseOrder(orderTrackingNo, userIdOfOperator, coeTrackingNoId, coeTrackingNo);
		if (StringUtil.isEqual(resultMap.get(Constant.STATUS), Constant.FAIL)) {
			response.setMessage(resultMap.get(Constant.MESSAGE));
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		response.setMessage("绑定成功");
		response.setSuccess(true);
		return response;
	}

	@Override
	public Response unBindingOrder(String content, Long userIdOfOperator) {
		Response response = new Response();
		response.setSuccess(false);
		Map<String, String> map = (Map<String, String>) GsonUtil.toObject(content, Map.class);
		String coeTrackingNo = map.get("coeTrackingNo");// 交接单号
		if (StringUtil.isNull(coeTrackingNo)) {
			response.setMessage("COE 交接单号不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		String orderTrackingNo = map.get("orderTrackingNo");// 出库单号
		if (StringUtil.isNull(coeTrackingNo)) {
			response.setMessage("出库跟踪单号不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		List<TrackingNo> trackingNoList = trackingNoDao.findTrackingNo(coeTrackingNo, TrackingNo.TYPE_COE);
		if (trackingNoList == null || trackingNoList.size() == 0) {
			response.setMessage("COE交接单号不正确");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		TrackingNo coeTrackingNoObj = trackingNoList.get(0);
		if (StringUtil.isEqual(coeTrackingNoObj.getStatus(), String.valueOf(TrackingNo.STATUS_USED))) {
			response.setMessage("COE交接单号已经完成建包");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		Long coeTrackingNoId = coeTrackingNoObj.getId();

		Map<String, String> resultMap = outWarehouseOrderService.unBindingOutWarehouseOrder(orderTrackingNo, userIdOfOperator, coeTrackingNoId, coeTrackingNo);
		if (StringUtil.isEqual(resultMap.get(Constant.STATUS), Constant.FAIL)) {
			response.setMessage(resultMap.get(Constant.MESSAGE));
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		response.setMessage("解除绑定成功");
		response.setSuccess(true);
		return response;
	}

	@Override
	public Response bindingOrderFinsh(String content, Long userIdOfOperator) {
		Response response = new Response();
		response.setSuccess(false);
		Map<String, String> map = (Map<String, String>) GsonUtil.toObject(content, Map.class);
		String coeTrackingNo = map.get("coeTrackingNo");// 交接单号
		if (StringUtil.isNull(coeTrackingNo)) {
			response.setMessage("COE 交接单号不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		List<TrackingNo> trackingNoList = trackingNoDao.findTrackingNo(coeTrackingNo, TrackingNo.TYPE_COE);
		if (trackingNoList == null || trackingNoList.size() == 0) {
			response.setMessage("COE交接单号不正确");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		TrackingNo coeTrackingNoObj = trackingNoList.get(0);
		if (StringUtil.isEqual(coeTrackingNoObj.getStatus(), String.valueOf(TrackingNo.STATUS_USED))) {
			response.setMessage("COE交接单号已经完成建包");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		Long coeTrackingNoId = coeTrackingNoObj.getId();
		Map<String, String> resultMap = outWarehouseOrderService.outWarehousePackageConfirm(coeTrackingNo, coeTrackingNoId, userIdOfOperator, false);
		if (StringUtil.isEqual(resultMap.get(Constant.STATUS), Constant.FAIL)) {
			response.setMessage(resultMap.get(Constant.MESSAGE));
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		response.setMessage("完成建包成功");
		response.setSuccess(true);
		return response;
	}

	/**
	 * 获取下架情况
	 */
	@Override
	public Response getOutShelfDetail(String content, Long userIdOfOperator) {
		Response response = new Response();
		response.setSuccess(false);
		Map<String, String> map = (Map<String, String>) GsonUtil.toObject(content, Map.class);
		String orderId = map.get("orderId");// 收货记录
		if (StringUtil.isNull(orderId)) {
			response.setMessage("订单号不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		OutWarehouseOrder param = new OutWarehouseOrder();
		param.setCustomerReferenceNo(orderId);
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(param, null, null);
		if (outWarehouseOrderList == null || outWarehouseOrderList.size() <= 0) {
			response.setMessage("该订单号找不到出库订单");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderList.get(0);
		if (StringUtil.isEqual(OutWarehouseOrderStatusCode.WWC, outWarehouseOrder.getStatus())) {
			response.setMessage("该订单号对应订单为待仓库审核状态");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		if (StringUtil.isEqual(OutWarehouseOrderStatusCode.WPP, outWarehouseOrder.getStatus())) {
			response.setMessage("该订单号对应订单为待打印捡货单状态");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();
		OutWarehouseOrderItemShelf shelfParam = new OutWarehouseOrderItemShelf();
		shelfParam.setOutWarehouseOrderId(outWarehouseOrder.getId());
		List<OutWarehouseOrderItemShelf> outWarehouseOrderItemShelfList = outWarehouseOrderItemShelfDao.findOutWarehouseOrderItemShelf(shelfParam, null, null);
		for (OutWarehouseOrderItemShelf itemShelf : outWarehouseOrderItemShelfList) {
			if (StringUtil.isEqual(itemShelf.getIsDone(), Constant.Y)) {
				continue;
			}
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("seatCode", itemShelf.getSeatCode());
			resultMap.put("barcode", itemShelf.getSku());
			resultMap.put("quantity", itemShelf.getQuantity() + "");
			resultMapList.add(resultMap);
		}
		String message = GsonUtil.toJson(resultMapList);
		response.setMessage(message);
		response.setSuccess(true);
		return response;
	}

	@Override
	public Response getOnShelfDetail(String content, Long userIdOfOperator) {
		Response response = new Response();
		response.setSuccess(false);
		Map<String, String> map = (Map<String, String>) GsonUtil.toObject(content, Map.class);
		String orderId = map.get("orderId");// 收货记录
		if (StringUtil.isNull(orderId)) {
			response.setMessage("收货记录不能为空");
			response.setReason(ErrorCode.B00_CODE);
			return response;
		}
		Long inWarehouseRecordId = Long.valueOf(orderId);
		List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();
		// 收货明细
		InWarehouseRecordItem param = new InWarehouseRecordItem();
		param.setInWarehouseRecordId(inWarehouseRecordId);
		List<InWarehouseRecordItem> inWarehouseRecordItemList = inWarehouseRecordItemDao.findInWarehouseRecordItem(param, null, null);
		for (InWarehouseRecordItem item : inWarehouseRecordItemList) {
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("barcode", item.getSku());
			int quantity = item.getQuantity();
			int onShelfQuantity = onShelfDao.countOnShelfSkuQuantity(inWarehouseRecordId, item.getSku());
			if (onShelfQuantity > 0) {
				quantity = quantity - onShelfQuantity;
			}
			resultMap.put("quantity", quantity + "");
			resultMapList.add(resultMap);
		}
		String message = GsonUtil.toJson(resultMapList);
		response.setMessage(message);
		response.setSuccess(true);
		return response;
	}
}
