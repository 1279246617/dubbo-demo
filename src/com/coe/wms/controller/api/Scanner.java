package com.coe.wms.controller.api;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coe.scanner.pojo.ErrorCode;
import com.coe.scanner.pojo.MessageType;
import com.coe.scanner.pojo.Response;
import com.coe.wms.service.storage.IScannerService;
import com.coe.wms.service.storage.impl.ScannerServiceImpl;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.GsonUtil;
import com.coe.wms.util.SessionConstant;
import com.coe.wms.util.StringUtil;

@Controller
@RequestMapping("/api/scanner")
public class Scanner {

	@Resource(name = "scannerService")
	private IScannerService scannerService;

	@Resource(name = "userService")
	private IUserService userService;

	private static final Logger logger = Logger.getLogger(Scanner.class);

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
			// String sign = request.getParameter("sign");
			logger.info("request:" + content + "  :" + messageType);
			Response response = new Response();
			response.setSuccess(false);
			// 验证帐号密码
			Map<String, String> loginResult = userService.checkAdminLogin(loginName, password);
			if (StringUtil.isEqual(loginResult.get(Constant.STATUS), Constant.FAIL)) {
				response.setMessage(loginResult.get(Constant.MESSAGE));
				response.setReason(ErrorCode.S06_CODE);
				return GsonUtil.toJson(response);
			}
			// 登录
			if (StringUtil.isEqualIgnoreCase(messageType, MessageType.LOGIN)) {
				response.setSuccess(true);
				return GsonUtil.toJson(response);
			}
			// 获取用户id
			Long userIdOfOperator = Long.valueOf(loginResult.get(SessionConstant.USER_ID));

			// 跟踪单号查询订单id(收货记录id)
			if (StringUtil.isEqualIgnoreCase(messageType, MessageType.GET_ORDER_ID)) {
				response = scannerService.getOrderId(content, userIdOfOperator);
			}
			// 查询入库信息(根据收货记录id查询已上架的商品条码,商品数量)
			if (StringUtil.isEqualIgnoreCase(messageType, MessageType.GET_ON_SHELF_INFO)) {
				response = scannerService.getOnShelfInfo(content, userIdOfOperator);
			}
			// 查询出库信息(根据收货记录id查询已上架的商品条码,商品数量)
			if (StringUtil.isEqualIgnoreCase(messageType, MessageType.GET_OUT_SHELF_INFO)) {
				response = scannerService.getOutShelfInfo(content, userIdOfOperator);
			}
			// 获取订单商品条码批次
			if (StringUtil.isEqualIgnoreCase(messageType, MessageType.GET_BATCH_NO)) {
				response = scannerService.getBatchNo(content, userIdOfOperator);
			}
			// 上架
			if (StringUtil.isEqualIgnoreCase(messageType, MessageType.ON_SHELF)) {
				response = scannerService.onShelf(content, userIdOfOperator);
			}
			// 下架
			if (StringUtil.isEqualIgnoreCase(messageType, MessageType.OUT_SHELF)) {
				response = scannerService.outShelf(content, userIdOfOperator);
			}
			String json = GsonUtil.toJson(response);
			logger.info("response:" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			Response response = new Response();
			response.setMessage(ErrorCode.S04);
			response.setReason(ErrorCode.S04_CODE);
			response.setSuccess(false);
			return GsonUtil.toJson(response);
		}
	}
}
