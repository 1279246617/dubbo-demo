package com.coe.message.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coe.message.common.MD5Util;
import com.coe.message.entity.LoginUser;
import com.coe.message.service.ILoginUserService;

@Controller
@RequestMapping("/loginUser")
public class LoginUserController {
	@Autowired
	private ILoginUserService userService;

	/**
	 * 用户登录
	 * @param userName 登录用户名
	 * @param password 登录密码
	 */
	@RequestMapping("/doLogin")
	@ResponseBody
	public Map<String, Object> doLogin(LoginUser loginUser, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = false;
		String userName = loginUser.getUserName();
		String password = loginUser.getPassword();
		Integer isValid = loginUser.getIsValid();
		if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password) && isValid != null) {
			loginUser.setPassword(MD5Util.toMD5Code(password));
			List<LoginUser> userList = userService.getLoginUser(loginUser);
			int records = userList.size();
			if (records > 0) {
				flag = true;
				session.setAttribute("loginUser", userList.get(0));
			}
		}
		resultMap.put("flag", flag);
		return resultMap;
	}

	@RequestMapping("/toIndex")
	public String toIndex(HttpServletRequest request) {
		HttpSession session = request.getSession();
		LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
		if (loginUser != null) {
			return "/page/index.jsp";
		} else {
			return "/page/login.jsp";
		}
	}
}
