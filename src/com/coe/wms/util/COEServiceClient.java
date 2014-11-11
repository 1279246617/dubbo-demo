package com.coe.wms.util;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 专用于调用COE公司接口
 * 
 * <pre>
 * 方法名命名 使用COE接口文档的接口名称.
 * 
 * <pre/>
 * @author Administrator
 * 2013-12-25
 */
public class COEServiceClient {
	/**
	 * COE公司接口地址
	 */
	private static String url = "http://121.40.97.48:8181/XYB/COE";
	/**
	 * 接口秘银
	 */
	private static String key = "VWGVCUPMYECTUUR";

	/**
	 * #coe接口用户名不同于coe的客户用户名
	 */
	private static String serverUserId = "COEWEBCLIENT";

	private static String serverPassword = "COEWEBCLIENT";

	private static Logger logger = Logger.getLogger(COEServiceClient.class);

	/**
	 * 专用于提交仓储系统提交顺丰出库订单给哲盟系统, 客户代码,收发件人地址固定在代码中
	 */
	public static Map<String, String> orderReceive(String id, String coeTrackingNo, Integer quantity, Double weight) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constant.STATUS, Constant.FAIL);
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<logisticsEventsRequest>");
		sb.append("<logisticsEvent>");
		sb.append("<eventHeader>");
		sb.append("<eventType>LOGISTICS_PACKAGE_SEND</eventType>");
		sb.append("<eventMessageId>" + id + "_" + System.currentTimeMillis() + "</eventMessageId>");
		sb.append("<eventTime>" + DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss) + "</eventTime>");
		sb.append("<eventSource>COEWEBCLIENT</eventSource>");
		sb.append("<eventTarget>COE</eventTarget>");
		sb.append("</eventHeader>");
		sb.append("<eventBody>");
		sb.append("<orders>");
		// 订单信息
		sb.append("<order>");
		// 运单号码 
		sb.append("<jobNo>" + coeTrackingNo + "</jobNo>");
		sb.append("<date>" + DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss) + "</date>");
		// 客户账号
		sb.append("<custNo>ECM1833M</custNo>");
		// 渠道路线
		sb.append("<hub>HKG</hub>");
		// 件数
		sb.append("<pcs>" + quantity + "</pcs>");
		// 收费重kg
		sb.append("<weight>" + NumberUtil.getNumPrecision(weight, 2) + "</weight>");
		// 发货人信息
		sb.append("<sendContact>");
		sb.append("<companyName>COE香港仓</companyName>");
		sb.append("<personName>COE香港仓</personName>");
		sb.append("<countryCode>CN</countryCode>");
		sb.append("<countryName>中国</countryName>");
		sb.append("<phoneNumber></phoneNumber>");
		sb.append("<divisioinCode></divisioinCode>");
		sb.append("<city></city>");
		sb.append("<address1></address1>");
		sb.append("<address2></address2>");
		sb.append("<address3></address3>");
		sb.append("<postalCode></postalCode>");
		sb.append("</sendContact>");
		
		// 收货人信息
		sb.append("<receiverContact>");
		sb.append("<companyName></companyName>");
		sb.append("<personName></personName>");
		sb.append("<countryCode></countryCode>");
		sb.append("<countryName></countryName>");
		sb.append("<phoneNumber></phoneNumber>");
		sb.append("<divisioinCode>HK</divisioinCode>");
		sb.append("<city></city>");
		sb.append("<address1></address1>");
		sb.append("<address2></address2>");
		sb.append("<address3></address3>");
		sb.append("<postalCode></postalCode>");
		sb.append("</receiverContact>");

		sb.append("</order>");
		sb.append("</orders>");
		sb.append("</eventBody>");
		sb.append("</logisticsEvent>");
		sb.append("</logisticsEventsRequest>");

		String context = sb.toString();
		logger.info("coe orderReceive request:" + context);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("Content", context);
		paramMap.put("UserId", serverUserId);
		paramMap.put("UserPassword", serverPassword);
		String sign = StringUtil.md5_32(context + key);
		paramMap.put("Key", sign.toUpperCase());
		try {
			String response = HttpUtil.post(url, paramMap);
			logger.info("coe orderReceive response:" + response);
			//
			resultMap.put(Constant.STATUS, Constant.SUCCESS);
		} catch (IOException e) {
			logger.info("coe orderReceive exception:" + e.getMessage());
			resultMap.put(Constant.MESSAGE, "发送运单到COE接口发生IO异常,此次提交失败!");
		}
		return resultMap;
	}
}
