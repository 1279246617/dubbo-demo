package com.coe.wms.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.coe.wms.controller.Application;
import com.coe.wms.util.SessionConstant;

public class SessionCheckInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(SessionCheckInterceptor.class.toString());

	/**
	 * 不拦截的url
	 */
	private List<String> allowUrls;

	/**
	 * 被拦截并拒绝时,跳转至
	 */
	private String redirectUrl;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("UTF-8");
		String url = request.getServletPath();
		for (String allowUrl : allowUrls) {
			if (url.contains(allowUrl)) {
				// 放过 无需过滤的url
				return true;
			}
		}
		// 判断是否登录
		HttpSession session = request.getSession();
		if (session.getAttribute(SessionConstant.USER_ID) != null) {
			return true;
		}
		logger.debug("url:" + url + "      redirectUrl:" + Application.getBaseUrl() + redirectUrl);
		response.sendRedirect(Application.getBaseUrl() + redirectUrl);
		return false;
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public List<String> getAllowUrls() {
		return allowUrls;
	}

	public void setAllowUrls(List<String> allowUrls) {
		this.allowUrls = allowUrls;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}