package com.coe.wms.controller.api;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.StringUtil;

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
		// 消息签名
		String dataDigest = request.getParameter("data_digest");
		// 消息类型,根据消息类型判断业务类型
		String msgType = request.getParameter("msg_type");
		// 消息来源(客户)
		String msgSource = request.getParameter("msg_source");
		// 消息ID
		String msgId = request.getParameter("msg_id");
		// 版本 (2014-09-23)当前1.0
		String version = request.getParameter("version");
		logger.info("logisticsInterface:" + logisticsInterface);
		logger.info("dataDigest:" + dataDigest + " msgType:" + msgType + " msgSource:" + msgSource + " msgId:" + msgId
				+ " version:" + version);
		// 验证参数
		Map<String, String> map = storageService.warehouseInterfaceValidate(logisticsInterface, msgSource, dataDigest,
				msgType, msgId, version);
		if (StringUtil.isEqual(map.get(Constant.STATUS), Constant.FAIL)) {
			return map.get(Constant.MESSAGE);
		}
		// 消息所属客户
		Long userIdOfCustomer = Long.valueOf(map.get(Constant.USER_ID_OF_CUSTOMER));
		// 根据事件类型(eventType)分到不同方法处理
		return storageService.warehouseInterface(logisticsInterface, userIdOfCustomer, dataDigest, msgType, msgId,
				version);
	}
}
