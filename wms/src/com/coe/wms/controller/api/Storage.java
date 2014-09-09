package com.coe.wms.controller.api;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coe.wms.pojo.api.response.Response;
import com.coe.wms.service.api.IStorageService;
import com.coe.wms.util.StreamUtil;
import com.coe.wms.util.XmlUtil;

/**
 * 仓配API
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/api/storage")
public class Storage {

	private static final Logger logger = Logger.getLogger(Storage.class);

	@Resource(name = "storageService")
	private IStorageService storageService;

	/**
	 * 创建入库订单(预报商品信息)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/createOrder", produces = "plain/text; charset=UTF-8", method = RequestMethod.POST)
	public String createOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String requestBody = StreamUtil.streamToString(request.getInputStream());
		logger.info("仓配创建订单 xml:" + requestBody);
		// 创建订单服务
		Response createOrderResponse = storageService.createOrder(requestBody);
		// 转成xml
		String xml = XmlUtil.toXml(Response.class, createOrderResponse);
		return xml;
	}
}
