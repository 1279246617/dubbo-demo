package com.coe.wms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.coe.wms.util.SessionConstant;

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
	public ModelAndView index(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ModelAndView view = new ModelAndView();

		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		Long userType = (Long) session.getAttribute(SessionConstant.USER_TYPE);
		String userName = (String) session.getAttribute(SessionConstant.USER_NAME);

		// 获取路径
		String ctxPath = request.getSession().getServletContext().getRealPath("/");
		System.out.println("ctxpath:" + ctxPath);

		// 根据用户类型去不同首页
		view.setViewName("index");
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
