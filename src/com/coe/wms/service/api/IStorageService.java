package com.coe.wms.service.api;

import java.util.Map;

import org.apache.log4j.Logger;


/**
 * 仓配 api service层
 * @author Administrator
 *
 */
public interface IStorageService {

	static final Logger logger = Logger.getLogger(IStorageService.class);

	/**
	 * 解析顺丰仓配入库订单请求xml内容 返回 xml内容对应pojo
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 */
	public Map<String, String> checkUserLogin(String loginName, String password);
 
}
