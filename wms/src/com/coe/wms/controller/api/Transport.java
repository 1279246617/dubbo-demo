package com.coe.wms.controller.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 转运API
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/api/transport")
public class Transport {

	/**
	 * 创建转运订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/createOrder", method = RequestMethod.POST)
	public String createOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		return null;
	}
}
