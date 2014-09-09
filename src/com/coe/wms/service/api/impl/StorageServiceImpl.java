package com.coe.wms.service.api.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.controller.user.UserController;
import com.coe.wms.pojo.api.response.Response;
import com.coe.wms.service.api.IStorageService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.StringUtil;

@Service("storageService")
public class StorageServiceImpl implements IStorageService {
	private static final Logger logger = Logger.getLogger(StorageServiceImpl.class);

	@Override
	public Response createOrder(String xml) {
		Response response = new Response();
		response.setVersion("1");
		// 默认失败
		response.setSucceeded(Constant.FALSE);
		if (StringUtil.isNull(xml)) {
			response.setDescription("Xml 内容不能为空.");
			return response;
		}
		response.setSucceeded(Constant.SUCCESS);
		
		return response;
	}
}
