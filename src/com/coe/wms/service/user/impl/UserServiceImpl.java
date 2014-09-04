package com.coe.wms.service.user.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.model.user.User;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.Constant;
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
			map.put(Constant.MESSAGE, "帐号不存在");
			return map;
		}
		if (!StringUtil.isEqual(password, user.getPassword())) {
			map.put(Constant.MESSAGE, "密码不正确");
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
		// 成功,返回userId userName
		map.put(Constant.SESSION_USER_ID, String.valueOf(user.getId()));
		map.put(Constant.SESSION_USER_NAME, user.getUserName());
		
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
