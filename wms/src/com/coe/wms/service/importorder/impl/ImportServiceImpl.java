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
	public Map<String, Object> executeImportInWarehouseOrder(List<Map<String, String>> mapList, String userLoginName, Long warehouseId) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constant.STATUS, Constant.FAIL);

		return resultMap;
	}

	@Override
	public Map<String, String> saveMultipartFile(Map<String, MultipartFile> fileMap, String userLoginName, Long warehouseId, String uploadDir) throws ServiceException {
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
			String storeFileName = userLoginName + "-" + warehouseId + "-" + System.currentTimeMillis() + "-" + originalFilename;
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
			String error = "列标:" + (i + 1);
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
				} else if (j == 1) {// 客户订单号
					map.put("customerReferenceNo", cell);
					if (StringUtil.isNull(cell) && i == 1) {
						error += " , 客户订单号必填";
						isError = true;
					}
				} else if (j == 2) {// 跟踪号码
					map.put("trackingNo", cell);
					if (StringUtil.isNull(cell) && i == 1) {
						error += " , 跟踪号码必填";
						isError = true;
					}
				} else if (j == 3) {// 承运商
					map.put("carrierCode", cell);
					if (StringUtil.isNull(cell) && i == 1) {
						error += " , 承运商必填";
						isError = true;
					}
				} else if (j == 4) {// 入库类型
					map.put("inWarehouseType", cell);

				} else if (j == 5) {// 产品SKU
					map.put("sku", cell);
					if (StringUtil.isNull(cell)) {
						error += " , 产品SKU必填";
						isError = true;
					}
				} else if (j == 6) {// 产品编号
					map.put("skuNo", cell);

				} else if (j == 7) {// 产品名称
					map.put("productName", cell);
					if (StringUtil.isNull(cell)) {
						error += " , 产品名称必填";
						isError = true;
					}
				} else if (j == 8) {// 规格型号
					map.put("specification", cell);

				} else if (j == 9) {// 产品数量
					map.put("quantity", cell);
					if (StringUtil.isNull(cell)) {
						error += " , 产品数量必填";
						isError = true;
					} else {
						if (!NumberUtil.isNumberic(cell)) {
							error += " , 产品数量必须是正整数";
							isError = true;
						}
					}
				} else if (j == 10) {// 有效期
					map.put("validityTime", cell);
					if (StringUtil.isNotNull(cell)) {
						error += " , 有效期正确格式如:" + DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_dd);
						isError = true;
					}
				} else if (j == 11) {// 生产批次
					map.put("productionBatchNo", cell);

				} else if (j == 12) {// 产品备注
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
		resultMap.put("errors", errors);
		resultMap.put("rows", mapList);
		return resultMap;
	}
}
