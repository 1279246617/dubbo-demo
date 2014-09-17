package com.coe.wms.service.user;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coe.wms.model.user.Index;
import com.coe.wms.model.user.User;

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

	/**
	 * 根据用户类型找用户首页
	 * 
	 * @param userType
	 * @return
	 */
	public Index findIndexByUserType(String userType);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserById(Long userId);

	/**
	 * 登录名 like 查询用户
	 * 
	 * @param loginName
	 * @return
	 */
	public List<User> findUserByLikeLoginName(String loginName);
}
