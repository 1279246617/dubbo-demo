package com.coe.wms.controller.api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coe.wms.pojo.api.warehouse.LogisticsEventsRequest;
import com.coe.wms.service.api.IStorageService;
import com.coe.wms.util.XmlUtil;

/**
 * 仓库业务API
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/api/warehouse")
public class Warehouse {

	private Logger logger = Logger.getLogger(Warehouse.class);

	@Resource(name = "storageService")
	private IStorageService storageService;

	@ResponseBody
	@RequestMapping(value = "/interface")
	public String warehouse(HttpServletRequest request, HttpServletResponse response) {
		// 消息内容
		String logisticsInterface = request.getParameter("logistics_interface");
		// 签名(签名参数名 顺丰未指定)
		String key = request.getParameter("key");
		// 消息签名
		String dataDigest = request.getParameter("data_digest");
		// 消息类型,根据消息类型判断业务类型
		String msgType = request.getParameter("msg_type");
		// 消息ID
		String msgId = request.getParameter("msg_id");
		// 版本 (2014-09-23)当前1.0
		String version = request.getParameter("version");
		logger.info("logisticsInterface:" + logisticsInterface);
		logger.info("dataDigest:" + dataDigest);
		logger.info("msgType:" + msgType);
		
		String xml = storageService.warehouseInterface(logisticsInterface, key, dataDigest, msgType, msgId, version);
		return xml;
	}
}
