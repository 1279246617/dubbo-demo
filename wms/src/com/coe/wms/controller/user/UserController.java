package com.coe.wms.controller.user;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.coe.wms.controller.Application;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.Config;
import com.coe.wms.util.Constant;
import com.coe.wms.util.StringUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	@Resource(name = "userService")
	private IUserService userService;

	@Resource(name = "config")
	private Config config;

	/**
	 * cookie名
	 */
	private final String COOKIE_NAME_LOGIN_NAME = "LOGIN_NAME";
	private final String COOKIE_NAME_LOGIN_PASSWORD = "LOGIN_PASSWORD";
	private final String COOKIE_NAME_REMEMBER_ME = "REMEMBER_ME";

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("user/login");
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		// 从cookie 取出帐号密码
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (StringUtil.isEqual(cookie.getName(), COOKIE_NAME_LOGIN_NAME)) {
					view.addObject("loginName", cookie.getValue());
					continue;
				}
				if (StringUtil.isEqual(cookie.getName(), COOKIE_NAME_LOGIN_PASSWORD)) {
					view.addObject("loginPassword", cookie.getValue());
					continue;
				}
				if (StringUtil.isEqual(cookie.getName(), COOKIE_NAME_REMEMBER_ME)) {
					view.addObject("rememberMe", cookie.getValue());
				}
			}
		}
		return view;
	}

	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	public ModelAndView loginCheck(HttpServletRequest request, HttpServletResponse response, String loginName,
			String loginPassword, String rememberMe) throws IOException {
		// 处理cookie
		Cookie cookieLoginName = new Cookie(COOKIE_NAME_LOGIN_NAME, loginName);
		Cookie cookieLoginPassword = new Cookie(COOKIE_NAME_LOGIN_PASSWORD, loginPassword);
		Cookie cookieRememberMe = new Cookie(COOKIE_NAME_REMEMBER_ME, rememberMe);
		if (StringUtil.isEqual(rememberMe, Constant.Y)) {
			// cookie过期时间
			cookieLoginName.setMaxAge(config.getCookieMaxAgeLoginName());
			cookieLoginPassword.setMaxAge(config.getCookieMaxAgeLoginPassword());
			cookieRememberMe.setMaxAge(config.getCookieMaxAgeRememberMe());
		} else {
			// 如果用户登录时选择不记住, 清除登录cookie
			cookieLoginName.setMaxAge(0);
			cookieLoginPassword.setMaxAge(0);
			cookieRememberMe.setMaxAge(0);
		}
		response.addCookie(cookieLoginName);
		response.addCookie(cookieLoginPassword);
		response.addCookie(cookieRememberMe);
		// 验证登录
		Map<String, String> map = userService.checkUserLogin(loginName, loginPassword);
		if (StringUtil.isEqual(map.get(Constant.STATUS), Constant.FAIL)) {
			ModelAndView view = new ModelAndView();
			// 验证失败 去登录页
			view.setViewName("user/login");
			view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
			view.addObject(Constant.MESSAGE, map.get(Constant.MESSAGE));
			view.addObject("rememberMe", rememberMe);
			logger.info("loginName:" + loginName + " loginPassword:" + loginPassword + " :" + map.get(Constant.MESSAGE));
			return view;
		}
		// 设置session 包含用户id,用户名
		request.getSession().setAttribute(Constant.SESSION_USER_ID, map.get(Constant.SESSION_USER_ID));
		request.getSession().setAttribute(Constant.SESSION_USER_NAME, map.get(Constant.SESSION_USER_NAME));
		request.getSession().setMaxInactiveInterval(config.getSessionMaxInactiveInterval());
		// 登录成功 进入index 方法, index 通过用户类型判断,进入不同首页
		response.sendRedirect(Application.getBaseUrl() + "/index.do");
		return null;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
