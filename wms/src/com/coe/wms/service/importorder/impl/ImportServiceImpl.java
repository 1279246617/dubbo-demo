package com.coe.wms.service.importorder.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderReceiverDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderSenderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehousePackageDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseRecordDao;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.warehouse.Shipway.ShipwayCode;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderStatus.InWarehouseOrderStatusCode;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderSender;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus.OutWarehouseOrderStatusCode;
import com.coe.wms.service.importorder.IImportService;
import com.coe.wms.util.Config;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.FileUtil;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.POIExcelUtil;
import com.coe.wms.util.StringUtil;

/**
 * 仓配服务
 * 
 * @author Administrator
 * 
 */
@Service("importService")
public class ImportServiceImpl implements IImportService {

	private static final Logger logger = Logger.getLogger(ImportServiceImpl.class);

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

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Resource(name = "config")
	private Config config;

	@Override
	public Map<String, Object> executeImportInWarehouseOrder(List<Map<String, String>> mapList, Long userIdOfCustomer, Long warehouseId, Long userIdOfOperator) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constant.STATUS, Constant.FAIL);
		// key:客户订单号, value:连续的行
		Map<String, List<Map<String, String>>> groupMap = new HashMap<String, List<Map<String, String>>>();
		// 按客户订单号 分组
		List<Map<String, String>> tempMapList = new ArrayList<Map<String, String>>();
		for (Map<String, String> map : mapList) {
			String customerReferenceNo = map.get("customerReferenceNo");
			if (StringUtil.isNotNull(customerReferenceNo)) {
				tempMapList = groupMap.get(customerReferenceNo);
				if (tempMapList == null) {
					tempMapList = new ArrayList<Map<String, String>>();
				}
				groupMap.put(customerReferenceNo, tempMapList);
			}
			tempMapList.add(map);
		}
		int saveOrderCount = 0;// 保存订单数量
		int saveItemCount = 0;// 保存订单物品数量
		// 迭代groupMap
		for (String customerReferenceNo : groupMap.keySet()) {
			InWarehouseOrder inWarehouseOrder = new InWarehouseOrder();
			inWarehouseOrder.setCustomerReferenceNo(customerReferenceNo);
			inWarehouseOrder.setUserIdOfCustomer(userIdOfCustomer);
			inWarehouseOrder.setWarehouseId(warehouseId);
			inWarehouseOrder.setStatus(InWarehouseOrderStatusCode.NONE);
			inWarehouseOrder.setCreatedTime(System.currentTimeMillis());
			inWarehouseOrder.setLogisticsType("1");
			inWarehouseOrder.setUserIdOfOperator(userIdOfOperator);
			List<Map<String, String>> orderItemMapList = groupMap.get(customerReferenceNo);
			List<InWarehouseOrderItem> orderItemList = new ArrayList<InWarehouseOrderItem>();
			for (Map<String, String> map : orderItemMapList) {
				if (StringUtil.isNotNull(map.get("carrierCode"))) {
					inWarehouseOrder.setCarrierCode(map.get("carrierCode"));
				}
				if (StringUtil.isNotNull(map.get("trackingNo"))) {
					inWarehouseOrder.setTrackingNo(map.get("trackingNo"));
				}
				if (StringUtil.isNotNull(map.get("inWarehouseType"))) {
					// 入库类型
				}
				InWarehouseOrderItem orderItem = new InWarehouseOrderItem();
				orderItem.setProductionBatchNo(map.get("productionBatchNo"));
				orderItem.setQuantity(Integer.valueOf(map.get("quantity")));
				orderItem.setSkuNo(map.get("skuNo"));
				orderItem.setSku(map.get("sku"));
				orderItem.setSkuName(map.get("productName"));
				orderItem.setSkuRemark(map.get("skuRemark"));
				orderItem.setSpecification(map.get("specification"));
				String validityTime = map.get("validityTime");
				if (StringUtil.isNotNull(validityTime)) {
					Date validityTimeDate = DateUtil.stringConvertDate(validityTime, DateUtil.yyyy_MM_dd);
					if (validityTimeDate != null) {
						orderItem.setValidityTime(validityTimeDate.getTime());
					}
				}
				orderItemList.add(orderItem);
			}
			Long inWarehouseOrderId = inWarehouseOrderDao.saveInWarehouseOrder(inWarehouseOrder);// 订单di;
			saveItemCount += inWarehouseOrderItemDao.saveBatchInWarehouseOrderItemWithOrderId(orderItemList, inWarehouseOrderId);
			saveOrderCount++;
		}
		resultMap.put(Constant.MESSAGE, "上传成功,保存订单数量:" + saveOrderCount + " , 商品明细数量:" + saveItemCount);
		resultMap.put(Constant.STATUS, Constant.SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, String> saveMultipartFile(Map<String, MultipartFile> fileMap, Long userIdOfCustomer, Long warehouseId, String uploadDir) throws ServiceException {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constant.STATUS, Constant.FAIL);
		try {
			FileUtil.mkdirs(uploadDir);
			MultipartFile multipartFile = fileMap.get("file");
			if (multipartFile == null || multipartFile.getOriginalFilename() == null) {
				resultMap.put(Constant.MESSAGE, "读取文件失败,请重新上传文件");
				return resultMap;
			}
			// 文件原始文件名
			String originalFilename = multipartFile.getOriginalFilename();
			// 系统保存文件名
			String storeFileName = userIdOfCustomer + "-" + warehouseId + "-" + System.currentTimeMillis() + "-" + originalFilename;
			String filePathAndName = uploadDir + "/" + storeFileName;
			resultMap.put("filePathAndName", filePathAndName);
			FileUtil.writeFileBinary(filePathAndName, FileUtil.readFileBinary(multipartFile.getInputStream()));
			resultMap.put(Constant.STATUS, Constant.SUCCESS);
		} catch (IOException e) {
			resultMap.put(Constant.MESSAGE, "存储文件失败,请重新上传文件");
			resultMap.put(Constant.STATUS, Constant.FAIL);
		}
		return resultMap;
	}

	/**
	 * 检查导入入库订单文件
	 */
	@Override
	public Map<String, Object> validateImportInWarehouseOrder(String filePathAndName) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constant.STATUS, Constant.FAIL);
		// 解析文件
		POIExcelUtil poiUtil = new POIExcelUtil();
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		List<ArrayList<String>> rows = poiUtil.readFile(filePathAndName);
		List<String> errors = new ArrayList<String>();
		for (int i = 0; i < rows.size(); i++) {
			if (i == 0) {
				continue;
			}
			String error = "第" + (i + 1) + "列";
			boolean isError = false;
			ArrayList<String> row = rows.get(i);
			Map<String, String> map = new HashMap<String, String>();
			for (int j = 0; j < row.size(); j++) {
				String cell = row.get(j);
				if (j == 0) {// 序号
					map.put("index", cell);
					if (StringUtil.isNotNull(cell) && i == 1) {
						error += " , 序号:" + cell;
					}
				}
				if (j == 1) {// 客户订单号
					map.put("customerReferenceNo", cell);
					if (StringUtil.isNull(cell) && i == 1) {
						error += " , 客户订单号必填";
						isError = true;
					} else {
						if (cell.length() > 100) {
							error += " , 客户订单号长度不能超过100";
							isError = true;
						}
						// 判断客户订单号是否重复
						InWarehouseOrder inWarehouseOrder = new InWarehouseOrder();
						inWarehouseOrder.setCustomerReferenceNo(cell);
						long count = inWarehouseOrderDao.countInWarehouseOrder(inWarehouseOrder, null);
						if (count >= 1) {
							error += " , 客户订单号重复";
							isError = true;
						}
					}
				}
				if (j == 2) {// 跟踪号码
					map.put("trackingNo", cell);
					if (StringUtil.isNull(cell) && i == 1) {
						error += " , 跟踪号码必填";
						isError = true;
					} else {
						if (cell.length() > 100) {
							error += " , 跟踪号码长度不能超过100";
							isError = true;
						}
					}
				}
				if (j == 3) {// 承运商
					map.put("carrierCode", cell);
					if (StringUtil.isNull(cell) && i == 1) {
						error += " , 承运商必填";
						isError = true;
					} else {
						if (cell.length() > 100) {
							error += " , 承运商长度不能超过100";
							isError = true;
						}
					}
				}
				if (j == 4) {// 入库类型
					if (StringUtil.isNotNull(cell)) {
						if (NumberUtil.isNumberic(cell)) {
							int num = Integer.parseInt(cell);
							if (num < 1 || num > 3) {
								error += " , 入库类型必须是[1,2,3]";
								isError = true;
							}
						}
					}
					map.put("inWarehouseType", cell);
				}
				if (j == 5) {// 商品SKU
					map.put("skuNo", cell);
				}
				if (j == 6) {// 商品条码
					map.put("sku", cell);
					if (StringUtil.isNull(cell)) {
						error += " ,  商品条码必填";
						isError = true;
					} else {
						if (cell.length() > 100) {
							error += " ,  商品条码长度不能超过100";
							isError = true;
						}
					}
				}
				if (j == 7) {// 商品名称
					map.put("productName", cell);
					if (StringUtil.isNull(cell)) {
						error += " , 商品名称必填";
						isError = true;
					} else {
						if (cell.length() > 100) {
							error += " , 商品名称长度不能超过100";
							isError = true;
						}
					}
				}
				if (j == 8) {// 规格型号
					map.put("specification", cell);
				}
				if (j == 9) {// 商品数量
					map.put("quantity", cell);
					if (StringUtil.isNull(cell)) {
						error += " , 商品数量必填";
						isError = true;
					} else {
						if (!NumberUtil.isNumberic(cell)) {
							error += " , 商品数量必须是正整数";
							isError = true;
						}
					}
				}
				if (j == 10) {// 有效期
					map.put("validityTime", cell);
					if (StringUtil.isNotNull(cell)) {
						if (DateUtil.stringConvertDate(cell, DateUtil.yyyy_MM_dd) == null) {
							error += " , 有效期正确格式如:" + DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_dd);
							isError = true;
						}
					}
				}
				if (j == 11) {// 生产批次
					map.put("productionBatchNo", cell);
				}
				if (j == 12) {// 商品备注
					map.put("skuRemark", cell);
				}
			}
			if (isError) {
				errors.add(error);
			}
			mapList.add(map);
		}
		if (errors.size() == 0) {
			resultMap.put(Constant.STATUS, Constant.SUCCESS);
		}
		resultMap.put(Constant.MESSAGE, "上传失败,必须所有内容无错误才能导入");
		resultMap.put("errors", errors);
		resultMap.put("rows", mapList);
		return resultMap;
	}

	/**
	 * 检查导入出库订单文件
	 */
	@Override
	public Map<String, Object> validateImportOutWarehouseOrder(String filePathAndName) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constant.STATUS, Constant.FAIL);
		// 解析文件
		POIExcelUtil poiUtil = new POIExcelUtil();
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		List<ArrayList<String>> rows = poiUtil.readFile(filePathAndName);
		List<String> errors = new ArrayList<String>();
		for (int i = 0; i < rows.size(); i++) {
			if (i == 0 || i == 1) {
				continue;
			}
			String error = "第" + (i + 1) + "列";
			boolean isError = false;
			ArrayList<String> row = rows.get(i);
			Map<String, String> map = new HashMap<String, String>();
			for (int j = 0; j < row.size(); j++) {
				String cell = row.get(j);
				if (j == 0) {// 序号
					map.put("index", cell);
					if (StringUtil.isNotNull(cell) && i == 1) {
						error += " , 序号:" + cell;
					}
				}
				if (j == 1) {// 客户订单号
					map.put("customerReferenceNo", cell);
					if (StringUtil.isNull(cell) && i == 1) {
						error += " , 客户订单号必填";
						isError = true;
					} else {
						if (cell.length() > 100) {
							error += " , 客户订单号长度不能超过100";
							isError = true;
						}
						// 判断客户订单号是否重复
						OutWarehouseOrder outWarehouseOrder = new OutWarehouseOrder();
						outWarehouseOrder.setCustomerReferenceNo(cell);
						long count = outWarehouseOrderDao.countOutWarehouseOrder(outWarehouseOrder, null);
						if (count >= 1) {
							error += " , 客户订单号重复";
							isError = true;
						}
					}
				}
				if (j == 2) {// 发货渠道
					map.put("shipway", cell);
					if (StringUtil.isNull(cell) && i == 1) {
						error += " , 发货渠道必填";
						isError = true;
					} else {
						if (!(StringUtil.isEqual(ShipwayCode.ETK, cell) || StringUtil.isEqual(ShipwayCode.SF, cell))) {
							error += " , 发货渠道必须是[" + ShipwayCode.ETK + "," + ShipwayCode.SF + "]";
							isError = true;
						}
					}
				}
				if (j == 3) {// 跟踪号码
					map.put("trackingNo", cell);
				}
				if (j == 4) {// 出库类型
					if (StringUtil.isNotNull(cell)) {
						if (NumberUtil.isNumberic(cell)) {
							int num = Integer.parseInt(cell);
							if (num < 1 || num > 3) {
								error += " , 出库类型必须是[1,2,3]";
								isError = true;
							}
						}
					}
					map.put("outWarehouseType", cell);
				}
				if (j == 5) {// 收件人名(必填)
					if (StringUtil.isNull(cell)) {
						error += " , 收件人名必填";
						isError = true;
					}
					map.put("receiverName", cell);
				}
				if (j == 6) {// 收件人街道1(必填)
					if (StringUtil.isNull(cell)) {
						error += " , 收件人街道1必填";
						isError = true;
					}
					map.put("receiverAddressline1", cell);
				}
				if (j == 7) {// 收件人街道2(可选)
					map.put("receiverAddressline2", cell);
				}
				if (j == 8) {// 收件人县区(可选)
					map.put("receiverCounty", cell);
				}
				if (j == 9) {// 收件人街道1(必填)
					if (StringUtil.isNull(cell)) {
						error += " , 收件人城市必填";
						isError = true;
					}
					map.put("receiverCity", cell);
				}
				if (j == 10) {// 收件人州省(必填)
					if (StringUtil.isNull(cell)) {
						error += " ,  收件人州省必填";
						isError = true;
					}
					map.put("receiverProvince", cell);
				}
				if (j == 11) {// 收件人国家(必填)
					if (StringUtil.isNull(cell)) {
						error += " ,  收件人州国家必填";
						isError = true;
					}
					map.put("receiverCountry", cell);
				}
				if (j == 12) {// 收件人邮编(必填)postal_code
					if (StringUtil.isNull(cell)) {
						error += " ,  收件人州邮编必填";
						isError = true;
					}
					map.put("receiverPostalCode", cell);
				}
				if (j == 13) {// 收件人手机(必填)
					if (StringUtil.isNull(cell)) {
						error += " ,  收件人手机必填";
						isError = true;
					}
					map.put("receiverMobile", cell);
				}
				if (j == 14) {// 收件人电话(可选)
					map.put("receiverPhone", cell);
				}
				if (j == 15) {// 发件人名(必填)
					if (StringUtil.isNull(cell)) {
						error += " ,  发件人名必填";
						isError = true;
					}
					map.put("senderName", cell);
				}
				if (j == 16) {// 发件人地址(必填)
					if (StringUtil.isNull(cell)) {
						error += " ,  发件人地址必填";
						isError = true;
					}
					map.put("senderAddress", cell);
				}
				if (j == 17) {// 商品条码(必填)
					if (StringUtil.isNull(cell)) {
						error += " ,  商品条码必填";
						isError = true;
					}
					map.put("barCode", cell);
				}
				if (j == 18) {// 出库数量(必填)
					if (StringUtil.isNull(cell)) {
						error += " ,  出库数量必填";
						isError = true;
					} else {
						if (!NumberUtil.isNumberic(cell)) {
							error += " ,  出库数量必须是正整数";
							isError = true;
						}
					}
					map.put("quantity", cell);
				}
				if (j == 19) {// 商品SKU(可选)
					map.put("sku", cell);
				}
				if (j == 20) {// 商品名称(可选)
					map.put("skuName", cell);
				}
				if (j == 21) {// 商品单价(分)(可选))
					map.put("skuUnitPrice", cell);
				}
				if (j == 21) {// 商品重量(克)(可选)
					map.put("skuNetWeight", cell);
				}
			}
			if (isError) {
				errors.add(error);
			}
			mapList.add(map);
		}
		if (errors.size() == 0) {
			resultMap.put(Constant.STATUS, Constant.SUCCESS);
		}
		resultMap.put(Constant.MESSAGE, "上传失败,必须所有内容无错误才能导入");
		resultMap.put("errors", errors);
		resultMap.put("rows", mapList);
		return resultMap;
	}

	@Override
	public Map<String, Object> executeImportOutWarehouseOrder(List<Map<String, String>> mapList, Long userIdOfCustomer, Long warehouseId, Long userIdOfOperator) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constant.STATUS, Constant.FAIL);
		// key:客户订单号, value:连续的行
		Map<String, List<Map<String, String>>> groupMap = new HashMap<String, List<Map<String, String>>>();
		// 按客户订单号 分组
		List<Map<String, String>> tempMapList = new ArrayList<Map<String, String>>();
		for (Map<String, String> map : mapList) {
			String customerReferenceNo = map.get("customerReferenceNo");
			if (StringUtil.isNotNull(customerReferenceNo)) {
				tempMapList = groupMap.get(customerReferenceNo);
				if (tempMapList == null) {
					tempMapList = new ArrayList<Map<String, String>>();
				}
				groupMap.put(customerReferenceNo, tempMapList);
			}
			tempMapList.add(map);
		}
		int saveOrderCount = 0;// 保存订单数量
		int saveItemCount = 0;// 保存订单物品数量
		// 迭代groupMap
		for (String customerReferenceNo : groupMap.keySet()) {
			OutWarehouseOrder outWarehouseOrder = new OutWarehouseOrder();// 订单主体
			outWarehouseOrder.setCustomerReferenceNo(customerReferenceNo);
			outWarehouseOrder.setUserIdOfCustomer(userIdOfCustomer);
			outWarehouseOrder.setWarehouseId(warehouseId);
			outWarehouseOrder.setStatus(OutWarehouseOrderStatusCode.WWC);
			outWarehouseOrder.setCreatedTime(System.currentTimeMillis());
			outWarehouseOrder.setUserIdOfOperator(userIdOfOperator);
			OutWarehouseOrderReceiver receiver = new OutWarehouseOrderReceiver();// 收件人
			OutWarehouseOrderSender sender = new OutWarehouseOrderSender();// 发件人
			List<Map<String, String>> orderItemMapList = groupMap.get(customerReferenceNo);
			List<OutWarehouseOrderItem> orderItemList = new ArrayList<OutWarehouseOrderItem>();
			for (Map<String, String> map : orderItemMapList) {
				if (StringUtil.isNotNull(map.get("trackingNo"))) {
					outWarehouseOrder.setTrackingNo(map.get("trackingNo"));
				}
				if (StringUtil.isNotNull(map.get("shipway"))) {
					outWarehouseOrder.setShipwayCode(map.get("shipway"));
				}
				if (StringUtil.isNotNull(map.get("outWarehouseType"))) {
					// 出库类型
				}
				if (StringUtil.isNotNull(map.get("receiverName"))) {
					receiver.setName(map.get("receiverName"));
				}
				if (StringUtil.isNotNull(map.get("receiverName"))) {
					receiver.setName(map.get("receiverName"));
				}
				if (StringUtil.isNotNull(map.get("receiverAddressline1"))) {
					receiver.setAddressLine1(map.get("receiverAddressline1"));
				}
				if (StringUtil.isNotNull(map.get("receiverAddressline2"))) {
					receiver.setAddressLine2(map.get("receiverAddressline2"));
				}
				if (StringUtil.isNotNull(map.get("receiverCounty"))) {
					receiver.setCounty(map.get("receiverCounty"));
				}
				if (StringUtil.isNotNull(map.get("receiverCity"))) {
					receiver.setCity(map.get("receiverCity"));
				}
				if (StringUtil.isNotNull(map.get("receiverProvince"))) {
					receiver.setStateOrProvince(map.get("receiverProvince"));
				}
				if (StringUtil.isNotNull(map.get("receiverCountry"))) {
					receiver.setCountryCode("CN");
					receiver.setCountryName("中国");
				}
				if (StringUtil.isNotNull(map.get("receiverPostalCode"))) {
					receiver.setPostalCode(map.get("receiverPostalCode"));
				}
				if (StringUtil.isNotNull(map.get("receiverMobile"))) {
					receiver.setMobileNumber(map.get("receiverMobile"));
				}
				if (StringUtil.isNotNull(map.get("receiverPhone"))) {
					receiver.setPhoneNumber(map.get("receiverPhone"));
				}
				if (StringUtil.isNotNull(map.get("senderName"))) {
					sender.setName(map.get("senderName"));
				}
				if (StringUtil.isNotNull(map.get("senderAddress"))) {
					sender.setAddressLine1(map.get("senderAddress"));
				}
				// 商品明细
				OutWarehouseOrderItem item = new OutWarehouseOrderItem();
				item.setSku(map.get("barCode"));// 商品条码
				item.setQuantity(Integer.valueOf(map.get("quantity")));
				item.setSkuNo(map.get("sku"));// 商品SKU
				item.setSkuName(map.get("skuName"));
				if (StringUtil.isNotNull(map.get("skuUnitPrice"))) {
					double skuUnitPrice = Double.valueOf(map.get("skuUnitPrice"));
					item.setSkuUnitPrice(skuUnitPrice);
				}
				if (StringUtil.isNotNull(map.get("skuNetWeight"))) {
					double skuNetWeight = Double.valueOf(map.get("skuNetWeight"));
					item.setSkuNetWeight(skuNetWeight);
				}
				orderItemList.add(item);
			}
			Long outWarehouseOrderId = outWarehouseOrderDao.saveOutWarehouseOrder(outWarehouseOrder);// 订单di;
			sender.setOutWarehouseOrderId(outWarehouseOrderId);
			receiver.setOutWarehouseOrderId(outWarehouseOrderId);
			outWarehouseOrderSenderDao.saveOutWarehouseOrderSender(sender);
			outWarehouseOrderReceiverDao.saveOutWarehouseOrderReceiver(receiver);
			saveItemCount += outWarehouseOrderItemDao.saveBatchOutWarehouseOrderItemWithOrderId(orderItemList, outWarehouseOrderId);
			saveOrderCount++;
		}
		resultMap.put(Constant.MESSAGE, "上传成功,保存订单数量:" + saveOrderCount + " , 商品明细数量:" + saveItemCount);
		resultMap.put(Constant.STATUS, Constant.SUCCESS);
		return resultMap;
	}
}
