package com.coe.wms.service.user.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.model.user.Index;
import com.coe.wms.model.user.User;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.SessionConstant;
import com.coe.wms.util.StringUtil;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Override
	public Map<String, String> checkUserLogin(String loginName, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(loginName)) {
			map.put(Constant.MESSAGE, "请输入帐号");
			return map;
		}
		if (StringUtil.isNull(password)) {
			map.put(Constant.MESSAGE, "请输入密码");
			return map;
		}
		loginName = loginName.trim();
		User user = userDao.findUserByLoginName(loginName);
		if (user == null) {
			// 帐号不存在
			map.put(Constant.MESSAGE, "帐号或密码不正确");
			return map;
		}
		if (!StringUtil.isEqual(password, user.getPassword())) {
			map.put(Constant.MESSAGE, "帐号或密码不正确");
			return map;
		}
		if (user.getStatus() == User.STATUS_DELETE) {
			map.put(Constant.MESSAGE, "帐号已删除");
			return map;
		}
		if (user.getStatus() == User.STATUS_FREEZE) {
			map.put(Constant.MESSAGE, "帐号已冻结");
			return map;
		}
		// 判断用户的父用户状态
		Long parentId = user.getParentId();
		if (parentId != null) {
			User parentUser = userDao.getUserById(parentId);
			if (parentUser.getStatus() == User.STATUS_DELETE) {
				map.put(Constant.MESSAGE, "主帐号已删除");
				return map;
			}
			if (parentUser.getStatus() == User.STATUS_FREEZE) {
				map.put(Constant.MESSAGE, "主帐号已冻结");
				return map;
			}
			map.put(SessionConstant.USER_PARENT_ID, String.valueOf(parentId));
		}
		// 成功,返回userId userName
		map.put(SessionConstant.USER_ID, String.valueOf(user.getId()));
		map.put(SessionConstant.USER_NAME, user.getUserName());
		map.put(SessionConstant.USER_TYPE, user.getUserType());
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	/**
	 * 根据用户类型 获取登录首页
	 */
	@Override
	public Index findIndexByUserType(String userType) {
		Index index = userDao.findIndexByUserType(userType);
		return index;
	}

	/**
	 * 根据用户类型 获取登录首页
	 */
	@Override
	public List<User> findUserByLikeLoginName(String loginName) {
		List<User> userList = new ArrayList<User>();
		if (StringUtil.isNull(loginName)) {
			return userList;
		}
		loginName = loginName.trim().toLowerCase();
		List<User> tempUserList = userDao.findAllUser();
		for (User tempUser : tempUserList) {
			// 相同
			if (tempUser.getLoginName().equalsIgnoreCase(loginName)) {
				userList.add(tempUser);
			} else if (tempUser.getLoginName().toLowerCase().contains(loginName)) {
				userList.add(tempUser);
			} else if (loginName.contains(tempUser.getLoginName().toLowerCase())) {
				userList.add(tempUser);
			}
		}
		return userList;
	}

	@Override
	public Long findUserIdByLoginName(String loginName) {
		return userDao.findUserIdByLoginName(loginName);
	}

	/**
	 * 根据id 获取用户
	 */
	@Override
	public User getUserById(Long userId) {
		User user = userDao.getUserById(userId);
		return user;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
