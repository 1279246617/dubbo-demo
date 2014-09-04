package com.coe.wms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.coe.wms.controller.Application;

/**
 * 获取项目基础路径,供html 使用绝对路径引用静态文件
 * 
 * @author Administrator
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(BaseInterceptor.class.toString());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("UTF-8");
		String baseUrl = request.getRequestURI().replace(request.getServletPath(), "");
		logger.debug(baseUrl);
		if (Application.getBaseUrl() == null) {
			if (baseUrl.endsWith("/")) {
				baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
			}
			Application.setBaseUrl(baseUrl);
		}
		return true;
	}
}