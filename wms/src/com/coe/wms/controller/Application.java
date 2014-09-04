package com.coe.wms.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.coe.wms.model.user.User;
import com.coe.wms.util.SessionConstant;
import com.coe.wms.util.StringUtil;

@Controller
@RequestMapping("/")
public class Application {

	/**
	 * 项目启动完成接受第一个请求时 初始化 baseUrl
	 */
	private static String baseUrl = null;

	/**
	 * baseUrl的key
	 */
	private static String baseUrlName = "baseUrl";

	private static final Logger logger = Logger.getLogger(Application.class);

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		String userName = (String) session.getAttribute(SessionConstant.USER_NAME);
		// 用户类型. 简单区分是客户还是操作员. 用于登录时 跳转到不同的首页. 与具体权限无关
		String userType = (String) session.getAttribute(SessionConstant.USER_TYPE);
		logger.info("userId:" + userId + "  userName:" + userName + " userType:" + userType);

		ModelAndView view = new ModelAndView();
		if (StringUtil.isEqual(userType, User.USER_TYPE_ADMIN)) {
			
			view.setViewName("redirect:/customerIndex.do");
		} else if (StringUtil.isEqual(userType, User.USER_TYPE_ADMIN)) {
			view.setViewName("redirect:/customerIndex.do");
		}
		view.setViewName("index");
		return view;
	}

	/**
	 * 客户首页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/customerIndex", method = RequestMethod.GET)
	public ModelAndView customerIndex(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();

		view.setViewName("customerIndex");
		return view;
	}

	public static String getBaseUrl() {
		return baseUrl;
	}

	public static void setBaseUrl(String baseUrl) {
		Application.baseUrl = baseUrl;
	}

	public static String getBaseUrlName() {
		return baseUrlName;
	}

	public static void setBaseUrlName(String baseUrlName) {
		Application.baseUrlName = baseUrlName;
	}
}
