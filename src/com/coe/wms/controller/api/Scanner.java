package com.coe.wms.controller.api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coe.scanner.pojo.ErrorCode;
import com.coe.scanner.pojo.MessageType;
import com.coe.scanner.pojo.Response;
import com.coe.wms.service.storage.IScannerService;
import com.coe.wms.util.GsonUtil;
import com.coe.wms.util.StringUtil;

@Controller
@RequestMapping("/api/scanner")
public class Scanner {

	@Resource(name = "scannerService")
	private IScannerService scannerService;

	@ResponseBody
	@RequestMapping(value = "/interface")
	public String scanner(HttpServletRequest request) {
		try {
			// 内容体
			String content = request.getParameter("content");
			// 用户名
			String loginName = request.getParameter("loginName");
			// 用户密码
			String password = request.getParameter("password");
			// 消息类型
			String messageType = request.getParameter("messageType");
			// 数字签名
			String sign = request.getParameter("sign");
			Response response = new Response();
			response.setSuccess(false);
			// 登录
			if (StringUtil.isEqualIgnoreCase(messageType, MessageType.LOGIN)) {
				response = scannerService.login(loginName, password);
			}
			// 跟踪单号查询订单id
			if (StringUtil.isEqualIgnoreCase(messageType, MessageType.GET_ORDER_ID)) {
				response = scannerService.getOrderId(content, loginName, password);
			}
			// 获取订单商品条码批次
			if (StringUtil.isEqualIgnoreCase(messageType, MessageType.GET_BATCH_NO)) {
				response = scannerService.getBatchNo(content, loginName, password);
			}
			// 上架
			if (StringUtil.isEqualIgnoreCase(messageType, MessageType.ON_SHELF)) {
				response = scannerService.onShelf(content, loginName, password);
			}
			// 下架
			if (StringUtil.isEqualIgnoreCase(messageType, MessageType.OUT_SHELF)) {
				response = scannerService.outShelf(content, loginName, password);
			}
			return GsonUtil.toJson(response);
		} catch (Exception e) {
			Response response = new Response();
			response.setMessage(ErrorCode.S04);
			response.setReason(ErrorCode.S04_CODE);
			response.setSuccess(false);
			return GsonUtil.toJson(response);
		}
	}
}
