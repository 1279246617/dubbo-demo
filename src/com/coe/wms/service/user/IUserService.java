package com.coe.wms.service.user;

import java.util.Map;

import org.apache.log4j.Logger;

public interface IUserService {

	static final Logger logger = Logger.getLogger(IUserService.class);

	/**
	 * 检查用户登录
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 */
	public Map<String, String> checkUserLogin(String loginName, String password);
}
