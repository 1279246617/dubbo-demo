package com.coe.message.service;

import java.util.List;

import com.coe.message.entity.LoginUser;

/**登录用户接口*/
public interface ILoginUserService {
	/**根据用户名和密码查询用户*/
	public List<LoginUser> getLoginUser(LoginUser loginUser);
}
