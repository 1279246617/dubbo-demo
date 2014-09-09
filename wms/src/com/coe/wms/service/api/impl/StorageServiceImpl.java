package com.coe.wms.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.warehouse.storage.IPackageDao;
import com.coe.wms.dao.warehouse.storage.IPackageItemDao;
import com.coe.wms.model.warehouse.storage.PackageItem;
import com.coe.wms.pojo.api.response.Response;
import com.coe.wms.pojo.api.warehouse.InOrder;
import com.coe.wms.pojo.api.warehouse.InOrderItem;
import com.coe.wms.service.api.IStorageService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.StringUtil;

@Service("storageService")
public class StorageServiceImpl implements IStorageService {

	private static final Logger logger = Logger.getLogger(StorageServiceImpl.class);

	@Resource(name = "packageDao")
	private IPackageDao packageDao;

	@Resource(name = "packageItemDao")
	private IPackageItemDao packageItemDao;

	@Override
	public Response createOrder(String xml) {
		Response response = new Response();
		response.setSucceeded(Constant.FALSE);
		if (StringUtil.isNull(xml)) {
			response.setDescription("Xml 内容不能为空.");
			return response;
		}
		Long userId = 1l;
		// api入库订单pojo 与顺丰api xml格式一致
		InOrder order = null;
		// List<Item> itemList = order.getItemList();
		order = new InOrder();
		order.setHandoverNumber("RA123123123CN");
		order.setSignature("aaaaaaa");
		order.setTimestamp(DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss));
		List<InOrderItem> itemList = new ArrayList<InOrderItem>();
		InOrderItem item = new InOrderItem();
		item.setCount(3);
		item.setItemId("aaaaaaaa");

		// pojo 转换 model 保存入数据库
		com.coe.wms.model.warehouse.storage.Package pag = new com.coe.wms.model.warehouse.storage.Package();
		pag.setCreatedTime(System.currentTimeMillis());
		// 大包号, 如果无传,则根据用户id和时间
		pag.setPackageNo(order.getHandoverNumber());
		pag.setPackageTrackingNo(order.getHandoverNumber());
		pag.setUserId(userId);
		pag.setSmallPackageQuantity(itemList.size());
		// 大包id
		long packageId = packageDao.savePackage(pag);

		// 商品明细
		List<PackageItem> packageItemList = new ArrayList<PackageItem>();
		for (InOrderItem inOrderItem : itemList) {
			PackageItem packageItem = new PackageItem();
			packageItem.setPackageId(packageId);

			packageItem.setQuantity(inOrderItem.getCount());
			packageItem.setSku(inOrderItem.getItemId());

			packageItemList.add(packageItem);
		}
		packageItemDao.saveBatchPackageItem(packageItemList);

		response.setSucceeded(Constant.SUCCESS);
		return response;
	}
}
