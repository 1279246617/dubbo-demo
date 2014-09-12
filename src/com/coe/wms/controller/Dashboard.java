package com.coe.wms.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.coe.wms.util.SessionConstant;

/**
 * 仪表盘
 * 
 * 统计用户数, 运单数, 财务
 * 
 * @author Administrator
 * 
 */

@Controller				
@RequestMapping("/dashboard")
public class Dashboard {
	/**
	 * 仪表盘主页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		// 项目基础路径
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.addObject("userId", userId);
		view.setViewName("dashboard/main");
		return view;
	}
}
