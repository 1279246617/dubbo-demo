package com.coe.wms.service.api.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.warehouse.storage.IPackageDao;
import com.coe.wms.dao.warehouse.storage.IPackageItemDao;
import com.coe.wms.pojo.api.response.Response;
import com.coe.wms.service.api.IStorageService;
import com.coe.wms.util.Constant;
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
		
		com.coe.wms.model.warehouse.storage.Package pag = new com.coe.wms.model.warehouse.storage.Package();
		pag.setCreatedTime(System.currentTimeMillis());
		pag.setPackageNo("1");
		pag.setPackageTrackingNo("2");
		
		packageDao.savePackage(pag);

		response.setSucceeded(Constant.SUCCESS);

		return response;
	}
}
