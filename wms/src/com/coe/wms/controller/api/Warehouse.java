package com.coe.wms.controller.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coe.wms.pojo.api.warehouse.ErrorCode;
import com.coe.wms.pojo.api.warehouse.EventBody;
import com.coe.wms.pojo.api.warehouse.EventType;
import com.coe.wms.pojo.api.warehouse.Response;
import com.coe.wms.pojo.api.warehouse.Responses;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.service.transport.ITransportService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.StringUtil;
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

	@Resource(name = "transportService")
	private ITransportService transportService;

	@ResponseBody
	@RequestMapping(value = "/interface")
	public String warehouse(HttpServletRequest request, HttpServletResponse response) {
		try {
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
			logger.info("dataDigest:" + dataDigest + " msgType:" + msgType + " msgSource:" + msgSource + " msgId:" + msgId + " version:" + version);

			String responseXml = null;
			// 验证参数
			Map<String, String> validateResultMap = storageService.warehouseInterfaceValidate(logisticsInterface, msgSource, dataDigest, msgType, msgId, version);
			if (StringUtil.isEqual(validateResultMap.get(Constant.STATUS), Constant.FAIL)) {
				responseXml = validateResultMap.get(Constant.MESSAGE);

				logger.warn("responseXml:" + responseXml);
				return responseXml;
			}
			// 消息所属客户
			Long userIdOfCustomer = Long.valueOf(validateResultMap.get(Constant.USER_ID_OF_CUSTOMER));

			// 获取事件类型
			Map<String, Object> eventTypeMap = storageService.warehouseInterfaceEventType(logisticsInterface, userIdOfCustomer, dataDigest, msgType, msgId, version);
			// 根据事件类型(eventType)分到不同方法处理
			if (!StringUtil.isEqual((String) eventTypeMap.get(Constant.STATUS), Constant.SUCCESS)) {
				responseXml = (String) eventTypeMap.get(Constant.MESSAGE);

				logger.warn("responseXml:" + responseXml);
				return responseXml;
			}
			// 成功获取事件类型
			String eventType = (String) eventTypeMap.get("eventType");
			// 事件目标(仓库编码)
			String eventTarget = (String) eventTypeMap.get("eventTarget");
			// 把事件主体交给各服务方法处理
			EventBody eventBody = (EventBody) eventTypeMap.get("eventBody");

			// 仓配
			if (StringUtil.isEqualIgnoreCase(EventType.LOGISTICS_SKU_STOCKIN_INFO, eventType)) {// 创建入库订单
				responseXml = storageService.warehouseInterfaceSaveInWarehouseOrder(eventBody, userIdOfCustomer, eventTarget);
			}

			if (StringUtil.isEqualIgnoreCase(EventType.LOGISTICS_SKU_PAID, eventType)) {// 创建出库订单
				responseXml = storageService.warehouseInterfaceSaveOutWarehouseOrder(eventBody, userIdOfCustomer, eventTarget);
			}

			if (StringUtil.isEqualIgnoreCase(EventType.LOGISTICS_SEND_SKU, eventType)) { // 确认出库订单
				responseXml = storageService.warehouseInterfaceConfirmOutWarehouseOrder(eventBody, userIdOfCustomer, eventTarget);
			}

			// 转运
			if (StringUtil.isEqualIgnoreCase(EventType.LOGISTICS_TRADE_PAID, eventType)) { // 创建转运订单
				responseXml = transportService.warehouseInterfaceSaveTransportOrder(eventBody, userIdOfCustomer, eventTarget);
			}

			// 转运确认出库(确认重量)
			if (StringUtil.isEqualIgnoreCase(EventType.LOGISTICS_SEND_GOODS, eventType)) { // 转运确认出库
				responseXml = transportService.warehouseInterfaceConfirmTransportOrder(eventBody, userIdOfCustomer, eventTarget);
			}

			logger.warn("eventType:" + eventType + "  responseXml:" + responseXml);
			return responseXml;
		} catch (Exception e) {
			e.printStackTrace();
			Responses responses = new Responses();
			List<Response> responseItems = new ArrayList<Response>();

			Response serviceResponse = new Response();
			serviceResponse.setSuccess(Constant.FALSE);
			serviceResponse.setReason(ErrorCode.S13_CODE);
			serviceResponse.setReasonDesc(e.getMessage());

			responseItems.add(serviceResponse);
			responses.setResponseItems(responseItems);
			String responseXml = XmlUtil.toXml(Responses.class, responses);
			logger.warn("API异常:" + "  responseXml:" + responseXml);
			return responseXml;
		}
	}
}
