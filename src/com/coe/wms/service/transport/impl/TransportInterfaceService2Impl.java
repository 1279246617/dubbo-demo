package com.coe.wms.service.transport.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.product.IProductDao;
import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.ISeatDao;
import com.coe.wms.dao.warehouse.IShelfDao;
import com.coe.wms.dao.warehouse.ITrackingNoDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.dao.warehouse.shipway.IShipwayApiAccountDao;
import com.coe.wms.dao.warehouse.shipway.IShipwayDao;
import com.coe.wms.dao.warehouse.storage.IOnShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutShelfDao;
import com.coe.wms.dao.warehouse.storage.IReportDao;
import com.coe.wms.dao.warehouse.storage.IReportTypeDao;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillDao;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillItemDao;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillOnShelfDao;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillStatusDao;
import com.coe.wms.dao.warehouse.transport.IOrderAdditionalSfDao;
import com.coe.wms.dao.warehouse.transport.IOrderDao;
import com.coe.wms.dao.warehouse.transport.IOrderPackageDao;
import com.coe.wms.dao.warehouse.transport.IOrderReceiverDao;
import com.coe.wms.dao.warehouse.transport.IOrderSenderDao;
import com.coe.wms.dao.warehouse.transport.IOrderStatusDao;
import com.coe.wms.dao.warehouse.transport.IOutWarehousePackageDao;
import com.coe.wms.dao.warehouse.transport.IOutWarehousePackageItemDao;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.user.User;
import com.coe.wms.pojo.api2.warehouse.ErrorCode;
import com.coe.wms.pojo.api2.warehouse.EventBody;
import com.coe.wms.pojo.api2.warehouse.Response;
import com.coe.wms.pojo.api2.warehouse.Responses;
import com.coe.wms.service.transport.ITransportInterfaceService2;
import com.coe.wms.util.Config;
import com.coe.wms.util.Constant;
import com.coe.wms.util.StringUtil;
import com.coe.wms.util.XmlUtil;

/**
 * 仓配服务
 * 
 * @author Administrator
 * 
 */
@Service("transportInterfaceService2")
public class TransportInterfaceService2Impl implements ITransportInterfaceService2 {

	private static final Logger logger = Logger.getLogger(TransportInterfaceService2Impl.class);

	@Resource(name = "warehouseDao")
	private IWarehouseDao warehouseDao;

	@Resource(name = "reportTypeDao")
	private IReportTypeDao reportTypeDao;

	@Resource(name = "reportDao")
	private IReportDao reportDao;

	@Resource(name = "trackingNoDao")
	private ITrackingNoDao trackingNoDao;

	@Resource(name = "seatDao")
	private ISeatDao seatDao;

	@Resource(name = "shelfDao")
	private IShelfDao shelfDao;

	@Resource(name = "onShelfDao")
	private IOnShelfDao onShelfDao;

	@Resource(name = "outShelfDao")
	private IOutShelfDao outShelfDao;

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Resource(name = "productDao")
	private IProductDao productDao;

	@Resource(name = "config")
	private Config config;

	@Resource(name = "orderAdditionalSfDao")
	private IOrderAdditionalSfDao orderAdditionalSfDao;

	@Resource(name = "orderDao")
	private IOrderDao orderDao;

	@Resource(name = "orderPackageDao")
	private IOrderPackageDao orderPackageDao;

	@Resource(name = "orderReceiverDao")
	private IOrderReceiverDao orderReceiverDao;

	@Resource(name = "orderSenderDao")
	private IOrderSenderDao orderSenderDao;

	@Resource(name = "orderStatusDao")
	private IOrderStatusDao orderStatusDao;

	@Resource(name = "firstWaybillItemDao")
	private IFirstWaybillItemDao firstWaybillItemDao;

	@Resource(name = "firstWaybillDao")
	private IFirstWaybillDao firstWaybillDao;

	@Resource(name = "firstWaybillStatusDao")
	private IFirstWaybillStatusDao firstWaybillStatusDao;

	@Resource(name = "firstWaybillOnShelfDao")
	private IFirstWaybillOnShelfDao firstWaybillOnShelfDao;

	@Resource(name = "transportPackageDao")
	private IOutWarehousePackageDao transportPackageDao;

	@Resource(name = "transportPackageItemDao")
	private IOutWarehousePackageItemDao transportPackageItemDao;

	@Resource(name = "shipwayDao")
	private IShipwayDao shipwayDao;

	@Resource(name = "shipwayApiAccountDao")
	private IShipwayApiAccountDao shipwayApiAccountDao;

	@Override
	public String warehouseInterfaceSaveTransportOrderPackage(EventBody eventBody, Long userIdOfCustomer) throws ServiceException {

		return null;
	}

	@Override
	public String warehouseInterfaceSaveTransportOrder(EventBody eventBody, Long userIdOfCustomer) throws ServiceException {

		return null;
	}

	@Override
	public Map<String, Object> warehouseInterfaceEventType(String content) {

		return null;
	}

	@Override
	public Map<String, String> warehouseInterfaceValidate(String content, String token, String sign) {
		Map<String, String> map = new HashMap<String, String>();
		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);
		map.put(Constant.STATUS, Constant.FAIL);
		// 缺少关键字段
		if (StringUtil.isNull(content) || StringUtil.isNull(token) || StringUtil.isNull(sign)) {
			response.setReason(ErrorCode.S12_CODE);
			response.setErrorInfo("缺少关键字段,请检查以下字段:content,token,sign");
			map.put(Constant.MESSAGE, XmlUtil.toXml(responses));
			return map;
		}
		// 根据token 查找客户
		User user = userDao.findUserByToken(token);
		if (user == null) {
			response.setReason(ErrorCode.B0008_CODE);
			response.setErrorInfo("根据token 找不到客户");
			map.put(Constant.MESSAGE, XmlUtil.toXml(responses));
			return map;
		}
		// 验证内容和签名字符串
		String md5dataDigest = StringUtil.encoderByMd5(content + user.getSecretKey());
		if (!StringUtil.isEqual(md5dataDigest, sign)) {
			// 签名错误
			response.setReason(ErrorCode.S02_CODE);
			response.setErrorInfo("收到消息签名:" + sign + " 系统计算消息签名:" + md5dataDigest);
			map.put(Constant.MESSAGE, XmlUtil.toXml(responses));
			return map;
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		map.put(Constant.USER_ID_OF_CUSTOMER, "" + user.getId());
		return map;
	}
}
