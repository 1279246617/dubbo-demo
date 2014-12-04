package com.coe.wms.service.importorder.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.coe.wms.dao.product.IProductDao;
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
import com.coe.wms.dao.warehouse.storage.IOutWarehousePackageDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseRecordItemDao;
import com.coe.wms.dao.warehouse.storage.IReportDao;
import com.coe.wms.dao.warehouse.storage.IReportTypeDao;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.service.importorder.IImportService;
import com.coe.wms.util.Config;
import com.coe.wms.util.Constant;
import com.coe.wms.util.FileUtil;
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
	public Map<String, Object> executeImportInWarehouseOrder(Map<String, MultipartFile> fileMap, String userLoginName, Long warehouseId) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constant.STATUS, Constant.FAIL);
		try {
			if (StringUtil.isNull(userLoginName)) {
				resultMap.put(Constant.MESSAGE, "请输入客户帐号");
				return resultMap;
			}
			if (warehouseId == null) {
				resultMap.put(Constant.MESSAGE, "请选择到货仓库");
				return resultMap;
			}
			String uploadDir = config.getRuntimeFilePath() + "/order/import";
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
			FileUtil.writeFileBinary(filePathAndName, FileUtil.readFileBinary(multipartFile.getInputStream()));
			// 解析文件
			POIExcelUtil poiUtil = new POIExcelUtil();
			List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
			List<ArrayList<String>> rows = poiUtil.readFile(filePathAndName);
			for (int i = 0; i < rows.size(); i++) {
				if (i == 0) {
					continue;
				}
				ArrayList<String> row = rows.get(i);
				Map<String, String> map = new HashMap<String, String>();
				for (int j = 0; j < row.size(); j++) {
					String cell = row.get(j);
					if (j == 0) {// 序号(可选)
						map.put("index", cell);
					} else if (j == 1) {//
						map.put("index", cell);
					} else if (j == 2) {//
						map.put("index", cell);
					} else if (j == 3) {//
						map.put("index", cell);
					} else if (j == 4) {//
						map.put("index", cell);
					} else if (j == 5) {//
						map.put("index", cell);
					} else if (j == 6) {//
						map.put("index", cell);
					} else if (j == 7) {//
						map.put("index", cell);
					} else if (j == 8) {//
						map.put("index", cell);
					} else if (j == 9) {//
						map.put("index", cell);
					} else if (j == 10) {//
						map.put("index", cell);
					} else if (j == 11) {//
						map.put("index", cell);
					}
				}
				mapList.add(map);
			}
			// 只有所有格式无错,完整导入所有数据才返回成功,其他情况一律返回失败,前台显示失败的数据
			resultMap.put(Constant.STATUS, Constant.SUCCESS);
		} catch (IOException e) {
			resultMap.put(Constant.MESSAGE, "存储文件失败,请重新上传文件");
			resultMap.put(Constant.STATUS, Constant.FAIL);
		}
		return resultMap;
	}
}
