package com.coe.wms.service.user.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.service.user.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Override
	public Map<String, String> checkUserLogin(String loginName, String password) {

		return null;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
