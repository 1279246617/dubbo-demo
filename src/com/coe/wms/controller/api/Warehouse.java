package com.coe.wms.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 仓库业务API
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/api/warehouse")
public class Warehouse {

	@ResponseBody
	@RequestMapping(value = "/")
	public String warehouse(HttpServletRequest request, HttpServletResponse response) {
		//消息内容
		String logisticsInterface = request.getParameter("logistics_interface");
		//消息签名
		String dataDigest = request.getParameter("data_digest");
		//消息类型（无大小写区分）
		String msgType = request.getParameter("msg_type");
		//消息ID
		String msgId = request.getParameter("msg_id");
		//版本 (2014-09-23)当前1.0
		String version = request.getParameter("version");
		
		
		String xml = null;
		
		return xml;
	}
}
