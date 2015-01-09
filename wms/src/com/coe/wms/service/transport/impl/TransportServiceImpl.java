package com.coe.wms.service.transport.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.sockjs.transport.TransportType;

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
import com.coe.wms.model.unit.Currency.CurrencyCode;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.shipway.Shipway;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.model.warehouse.transport.FirstWaybill;
import com.coe.wms.model.warehouse.transport.FirstWaybillItem;
import com.coe.wms.model.warehouse.transport.FirstWaybillStatus;
import com.coe.wms.model.warehouse.transport.FirstWaybillStatus.FirstWaybillStatusCode;
import com.coe.wms.model.warehouse.transport.Order;
import com.coe.wms.model.warehouse.transport.OrderAdditionalSf;
import com.coe.wms.model.warehouse.transport.OrderPackage;
import com.coe.wms.model.warehouse.transport.OrderPackageStatus.OrderPackageStatusCode;
import com.coe.wms.model.warehouse.transport.OrderReceiver;
import com.coe.wms.model.warehouse.transport.OrderSender;
import com.coe.wms.model.warehouse.transport.OrderStatus.OrderStatusCode;
import com.coe.wms.pojo.api.warehouse.Buyer;
import com.coe.wms.pojo.api.warehouse.ClearanceDetail;
import com.coe.wms.pojo.api.warehouse.ErrorCode;
import com.coe.wms.pojo.api.warehouse.EventBody;
import com.coe.wms.pojo.api.warehouse.Item;
import com.coe.wms.pojo.api.warehouse.LogisticsDetail;
import com.coe.wms.pojo.api.warehouse.LogisticsOrder;
import com.coe.wms.pojo.api.warehouse.Response;
import com.coe.wms.pojo.api.warehouse.Responses;
import com.coe.wms.pojo.api.warehouse.SenderDetail;
import com.coe.wms.pojo.api.warehouse.TradeDetail;
import com.coe.wms.pojo.api.warehouse.TradeOrder;
import com.coe.wms.service.transport.ITransportService;
import com.coe.wms.util.Config;
import com.coe.wms.util.Constant;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.StringUtil;
import com.coe.wms.util.XmlUtil;

/**
 * 仓配服务
 * 
 * @author Administrator
 * 
 */
@Service("transportService")
public class TransportServiceImpl implements ITransportService {

	private static final Logger logger = Logger.getLogger(TransportServiceImpl.class);

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
	public List<Shipway> findAllShipway() throws ServiceException {
		return shipwayDao.findAllShipway();
	}
}
