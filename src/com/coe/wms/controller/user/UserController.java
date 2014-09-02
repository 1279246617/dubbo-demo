package com.coe.wms.controller.user;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.coe.wms.controller.Application;
import com.coe.wms.service.user.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	@Resource(name = "userService")
	private IUserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("user/login");
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());

		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	public Map<String, Object> loginCheck(HttpServletRequest request, String loginName, String password) {

		request.getSession().setMaxInactiveInterval(20);// 20秒
		// request.getSession().setAttribute("user", user);
		return null;
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
