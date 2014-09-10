package com.coe.wms.service.api.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.warehouse.storage.IPackageDao;
import com.coe.wms.dao.warehouse.storage.IPackageItemDao;
import com.coe.wms.model.warehouse.storage.PackageItem;
import com.coe.wms.model.warehouse.storage.PackageStatus.PackageStatusCode;
import com.coe.wms.pojo.api.response.Response;
import com.coe.wms.pojo.api.warehouse.InOrder;
import com.coe.wms.pojo.api.warehouse.InOrderItem;
import com.coe.wms.pojo.api.warehouse.OutOrder;
import com.coe.wms.pojo.api.warehouse.OutOrderItem;
import com.coe.wms.service.api.IStorageService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.StringUtil;
import com.coe.wms.util.XmlUtil;

/**
 * 仓配服务
 * 
 * @author Administrator
 * 
 */
@Service("storageService")
public class StorageServiceImpl implements IStorageService {

	private static final Logger logger = Logger.getLogger(StorageServiceImpl.class);

	@Resource(name = "packageDao")
	private IPackageDao packageDao;

	@Resource(name = "packageItemDao")
	private IPackageItemDao packageItemDao;

	/**
	 * 入库预报
	 */
	@Override
	public Response inWarehouse(String xml) {
		Response response = new Response();
		response.setSucceeded(Constant.FALSE);
		if (StringUtil.isNull(xml)) {
			response.setDescription(Response.DESCRIPTION_EMPTY);
			return response;
		}
		Long userId = 1l;
		InOrder order = (InOrder) XmlUtil.toObject(xml, OutOrder.class);
		List<InOrderItem> itemList = order.getItemList();
		// pojo 转换 model 保存入数据库
		com.coe.wms.model.warehouse.storage.Package pag = order.changeToPackage();
		// 大包id
		long packageId = packageDao.savePackage(pag);
		// 商品明细
		List<PackageItem> packageItemList = new ArrayList<PackageItem>();
		for (InOrderItem inOrderItem : itemList) {
			PackageItem packageItem = inOrderItem.changeToPackageItem(packageId);
			packageItemList.add(packageItem);
		}
		int changeSize = packageItemDao.saveBatchPackageItem(packageItemList);

		logger.info("入库大包号:" + pag.getPackageNo() + " 入库SKU数量:" + changeSize);
		response.setSucceeded(Constant.SUCCESS);
		return response;
	}

	/**
	 * 出库
	 */
	@Override
	public Response outWarehouse(String xml) {
		Response response = new Response();
		response.setSucceeded(Constant.FALSE);
		if (StringUtil.isNull(xml)) {
			response.setDescription(Response.DESCRIPTION_EMPTY);
			return response;
		}
		OutOrder order = (OutOrder) XmlUtil.toObject(xml, OutOrder.class);
		List<OutOrderItem> itemList = order.getItemList();

		return response;
	}
}
