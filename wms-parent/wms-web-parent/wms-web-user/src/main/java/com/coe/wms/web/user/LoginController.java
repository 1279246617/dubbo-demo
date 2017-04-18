package com.coe.wms.web.user;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.plugin.model.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.coe.wms.common.web.AbstractController;
import com.coe.wms.common.web.model.Result;
import com.coe.wms.facade.user.entity.User;
import com.coe.wms.facade.user.service.UserFacade;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/user/login")
@Api(description = "登入版块", value = "/user/login")
public class LoginController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Reference(version = "1.1.1",retries=0)
	private UserFacade userFacade;

	/**
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loginAction", method = RequestMethod.GET)
	@ApiOperation(value = "用户登入", httpMethod = "GET")
	public Result loginAction(@ApiParam(value = "用户账户", name = "loginName", required = true) @RequestParam String loginName,
			@ApiParam(value = "用户密码", name = "password", required = true) @RequestParam String password) {
		logger.debug("user login loginName:{} password:{}", loginName, password);
		Pager<User> pager = userFacade.list(1, 10);
		return Result.success(pager);
	}

	/**
	 * 登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/login");
		return mv;
	}

	@ResponseBody
	@RequestMapping("/test")
	public Result showMenu() {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("status", "success");
		return Result.success(resultMap);
	}
}
