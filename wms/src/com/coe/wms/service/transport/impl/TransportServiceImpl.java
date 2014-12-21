package com.coe.wms.service.transport.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.coe.wms.dao.warehouse.storage.IOnShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutShelfDao;
import com.coe.wms.dao.warehouse.storage.IReportDao;
import com.coe.wms.dao.warehouse.storage.IReportTypeDao;
import com.coe.wms.dao.warehouse.transport.IBigPackageAdditionalSfDao;
import com.coe.wms.dao.warehouse.transport.IBigPackageDao;
import com.coe.wms.dao.warehouse.transport.IBigPackageReceiverDao;
import com.coe.wms.dao.warehouse.transport.IBigPackageSenderDao;
import com.coe.wms.dao.warehouse.transport.IBigPackageStatusDao;
import com.coe.wms.dao.warehouse.transport.ILittlePackageDao;
import com.coe.wms.dao.warehouse.transport.ILittlePackageItemDao;
import com.coe.wms.dao.warehouse.transport.ILittlePackageOnShelfDao;
import com.coe.wms.dao.warehouse.transport.ILittlePackageStatusDao;
import com.coe.wms.dao.warehouse.transport.IPackageRecordDao;
import com.coe.wms.dao.warehouse.transport.IPackageRecordItemDao;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.unit.Currency.CurrencyCode;
import com.coe.wms.model.unit.Weight.WeightCode;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.TrackingNo;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.model.warehouse.transport.BigPackage;
import com.coe.wms.model.warehouse.transport.BigPackageAdditionalSf;
import com.coe.wms.model.warehouse.transport.BigPackageReceiver;
import com.coe.wms.model.warehouse.transport.BigPackageSender;
import com.coe.wms.model.warehouse.transport.BigPackageStatus;
import com.coe.wms.model.warehouse.transport.BigPackageStatus.BigPackageStatusCode;
import com.coe.wms.model.warehouse.transport.LittlePackage;
import com.coe.wms.model.warehouse.transport.LittlePackageItem;
import com.coe.wms.model.warehouse.transport.LittlePackageOnShelf;
import com.coe.wms.model.warehouse.transport.LittlePackageStatus;
import com.coe.wms.model.warehouse.transport.LittlePackageStatus.LittlePackageStatusCode;
import com.coe.wms.model.warehouse.transport.PackageRecordItem;
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
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
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

	@Resource(name = "bigPackageAdditionalSfDao")
	private IBigPackageAdditionalSfDao bigPackageAdditionalSfDao;

	@Resource(name = "bigPackageDao")
	private IBigPackageDao bigPackageDao;

	@Resource(name = "bigPackageReceiverDao")
	private IBigPackageReceiverDao bigPackageReceiverDao;

	@Resource(name = "bigPackageSenderDao")
	private IBigPackageSenderDao bigPackageSenderDao;

	@Resource(name = "bigPackageStatusDao")
	private IBigPackageStatusDao bigPackageStatusDao;

	@Resource(name = "littlePackageItemDao")
	private ILittlePackageItemDao littlePackageItemDao;

	@Resource(name = "littlePackageDao")
	private ILittlePackageDao littlePackageDao;

	@Resource(name = "littlePackageStatusDao")
	private ILittlePackageStatusDao littlePackageStatusDao;

	@Resource(name = "littlePackageOnShelfDao")
	private ILittlePackageOnShelfDao littlePackageOnShelfDao;

	@Resource(name = "packageRecordDao")
	private IPackageRecordDao packageRecordDao;

	@Resource(name = "IPackageRecordItemDao")
	private IPackageRecordItemDao packageRecordItemDao;

	@Override
	public String warehouseInterfaceSaveTransportOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException {
		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);
		// 取 tradeDetail 中tradeOrderId 作为客户订单号
		TradeDetail tradeDetail = eventBody.getTradeDetail();
		if (tradeDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventBody对象获取TradeDetail对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		List<TradeOrder> tradeOrderList = tradeDetail.getTradeOrders();
		if (tradeOrderList == null || tradeOrderList.size() == 0) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeDetail对象获取TradeOrders对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		TradeOrder tradeOrder = tradeOrderList.get(0);
		// 客户订单号
		String customerReferenceNo = tradeOrder.getTradeOrderId();
		if (StringUtil.isNull(customerReferenceNo)) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeOrder对象获取tradeOrderId得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		// 交易备注,等于打印捡货单上的买家备注
		String tradeRemark = tradeOrder.getTradeRemark();
		Buyer buyer = tradeOrder.getBuyer();
		if (buyer == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeOrder对象获取Buyer对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		// 小包
		LogisticsDetail logisticsDetail = eventBody.getLogisticsDetail();
		if (logisticsDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventBody对象获取LogisticsDetail对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		ClearanceDetail clearanceDetail = eventBody.getClearanceDetail();
		if (clearanceDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventBody对象获取ClearanceDetail对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		List<LogisticsOrder> logisticsOrders = logisticsDetail.getLogisticsOrders();
		if (logisticsOrders == null || logisticsOrders.size() == 0) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsDetail对象获取LogisticsOrders对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		// 2014-12-09顺丰确认 多个 logisticsOrder的SenderDetail 是一样的. 所以仅需保存一份
		LogisticsOrder logisticsOrderFirst = logisticsOrders.get(0);
		SenderDetail senderDetail = logisticsOrderFirst.getSenderDetail();
		if (senderDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("LogisticsOrders对象获取SenderDetail对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		Warehouse warehouse = warehouseDao.getWarehouseByNo(warehouseNo);
		if (warehouse == null) {
			response.setReason(ErrorCode.B0003_CODE);
			response.setReasonDesc("根据仓库编号(eventTarget)获取仓库得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		Long warehouseId = warehouse.getId();
		// 检测大包是否重复
		BigPackage bigPackageParam = new BigPackage();
		bigPackageParam.setCustomerReferenceNo(customerReferenceNo);
		Long count = bigPackageDao.countBigPackage(bigPackageParam, null);
		if (count > 0) {
			response.setReason(ErrorCode.B0200_CODE);
			response.setReasonDesc("客户订单号(tradeOrderId)重复,保存失败");
			return XmlUtil.toXml(Responses.class, responses);
		}
		// 创建大包
		BigPackage bigPackage = new BigPackage();
		bigPackage.setCreatedTime(System.currentTimeMillis());
		bigPackage.setCustomerReferenceNo(customerReferenceNo);
		bigPackage.setTradeRemark(tradeRemark);
		bigPackage.setStatus(BigPackageStatusCode.WWC);
		bigPackage.setUserIdOfCustomer(userIdOfCustomer);
		bigPackage.setWarehouseId(warehouseId);
		// 顺丰指定,出货运单号和渠道
		bigPackage.setTrackingNo(clearanceDetail.getMailNo());
		bigPackage.setShipwayCode(clearanceDetail.getCarrierCode());
		if (logisticsOrders.size() == 1) {
			// 直接转运
			bigPackage.setTransportType(BigPackage.TRANSPORT_TYPE_Z);
		} else {
			// 集货转运
			bigPackage.setTransportType(BigPackage.TRANSPORT_TYPE_J);
		}
		Long bigPackageId = bigPackageDao.saveBigPackage(bigPackage);// 保存大包,得到大包id
		// // 顺丰标签附加内容
		BigPackageAdditionalSf additionalSf = new BigPackageAdditionalSf();
		// 顺丰指定,出货运单号和渠道
		additionalSf.setCarrierCode(clearanceDetail.getCarrierCode());
		additionalSf.setCustId(clearanceDetail.getCustId());
		additionalSf.setDeliveryCode(clearanceDetail.getDeliveryCode());
		additionalSf.setMailNo(clearanceDetail.getMailNo());
		additionalSf.setPayMethod(clearanceDetail.getPayMethod());
		additionalSf.setSenderAddress(clearanceDetail.getSenderAddress());
		additionalSf.setShipperCode(clearanceDetail.getShipperCode());
		additionalSf.setBigPackageId(bigPackageId);
		bigPackageAdditionalSfDao.saveBigPackageAdditionalSf(additionalSf);

		// 大包收件人
		BigPackageReceiver bigPackageReceiver = new BigPackageReceiver();
		bigPackageReceiver.setAddressLine1(buyer.getStreetAddress());
		bigPackageReceiver.setCity(buyer.getCity());
		bigPackageReceiver.setCountryCode(OutWarehouseOrderReceiver.CN);
		bigPackageReceiver.setCountryName(OutWarehouseOrderReceiver.CN_VALUE);
		bigPackageReceiver.setCounty(buyer.getDistrict());
		bigPackageReceiver.setEmail(buyer.getEmail());
		bigPackageReceiver.setName(buyer.getName());
		bigPackageReceiver.setPhoneNumber(buyer.getPhone());
		bigPackageReceiver.setPostalCode(buyer.getZipCode());
		bigPackageReceiver.setStateOrProvince(buyer.getProvince());
		bigPackageReceiver.setMobileNumber(buyer.getMobile());
		bigPackageReceiver.setBigPackageId(bigPackageId);
		bigPackageReceiverDao.saveBigPackageReceiver(bigPackageReceiver); // 保存收件人

		// 发件人信息
		BigPackageSender bigPackageSender = new BigPackageSender();
		bigPackageSender.setAddressLine1(senderDetail.getStreetAddress());
		bigPackageSender.setCity(senderDetail.getCity());
		bigPackageSender.setCountryCode(senderDetail.getCountry());
		bigPackageSender.setCountryName(senderDetail.getCountry());
		bigPackageSender.setCounty(senderDetail.getDistrict());
		bigPackageSender.setEmail(senderDetail.getEmail());
		bigPackageSender.setName(senderDetail.getName());
		bigPackageSender.setPhoneNumber(senderDetail.getPhone());
		bigPackageSender.setPostalCode(senderDetail.getZipCode());
		bigPackageSender.setStateOrProvince(senderDetail.getProvince());
		bigPackageSender.setMobileNumber(senderDetail.getMobile());
		bigPackageSender.setBigPackageId(bigPackageId);
		bigPackageSenderDao.saveBigPackageSender(bigPackageSender);
		// tradeOrder的商品详情
		List<Item> items = tradeOrder.getItems();
		// 保存小包
		for (int i = 0; i < logisticsOrders.size(); i++) {
			LogisticsOrder logisticsOrder = logisticsOrders.get(i);
			if (logisticsOrder == null) {
				continue;
			}
			// 小包, 到货运单
			LittlePackage littlePackage = new LittlePackage();
			littlePackage.setBigPackageId(bigPackageId);
			littlePackage.setCarrierCode(logisticsOrder.getCarrierCode());
			littlePackage.setCreatedTime(System.currentTimeMillis());
			littlePackage.setPoNo(logisticsOrder.getPoNo());
			littlePackage.setTrackingNo(logisticsOrder.getMailNo());
			littlePackage.setStatus(LittlePackageStatusCode.WWR);
			littlePackage.setUserIdOfCustomer(userIdOfCustomer);
			littlePackage.setWarehouseId(warehouseId);
			littlePackage.setRemark(logisticsOrder.getLogisticsRemark());
			littlePackage.setBigPackageId(bigPackageId);
			// 转运类型
			littlePackage.setTransportType(bigPackage.getTransportType());
			Long littlePackageId = littlePackageDao.saveLittlePackage(littlePackage);
			// 小包裹物品id
			String itemsIncluded = logisticsOrder.getItemsIncluded();
			if (StringUtil.isNull(itemsIncluded)) {
				continue;
			}
			String[] itemIds = itemsIncluded.split(",");
			for (String itemIncluded : itemIds) {
				for (Item item : items) {
					String itemId = item.getItemId();
					if (!StringUtil.isEqualIgnoreCase(itemIncluded, itemId)) {
						continue;
					}
					LittlePackageItem littlePackageItem = new LittlePackageItem();
					littlePackageItem.setSku(itemId);
					littlePackageItem.setQuantity(item.getItemQuantity() == null ? 1 : item.getItemQuantity());
					littlePackageItem.setLittlePackageId(littlePackageId);
					littlePackageItem.setBigPackageId(bigPackageId);
					littlePackageItem.setSkuName(item.getItemName());
					littlePackageItem.setRemark(item.getItemRemark());
					if (NumberUtil.isDecimal(item.getItemUnitPrice()) || NumberUtil.isNumberic(item.getItemUnitPrice())) {
						littlePackageItem.setSkuUnitPrice(Double.valueOf(item.getItemUnitPrice()));
					}
					littlePackageItem.setSkuPriceCurrency(CurrencyCode.CNF);
					littlePackageItem.setSpecification(item.getSpecification());
					if (NumberUtil.isDecimal(item.getNetWeight()) || NumberUtil.isNumberic(item.getNetWeight())) {
						littlePackageItem.setSkuNetWeight(Double.valueOf(item.getNetWeight()));
					}
					littlePackageItemDao.saveLittlePackageItem(littlePackageItem);
				}
			}
		}
		response.setSuccess(Constant.TRUE);
		return XmlUtil.toXml(Responses.class, responses);
	}

	@Override
	public List<BigPackageStatus> findAllBigPackageStatus() throws ServiceException {
		return bigPackageStatusDao.findAllBigPackageStatus();
	}

	/**
	 * 获取转运订单列表数据
	 */
	@Override
	public Pagination getBigPackageData(BigPackage param, Map<String, String> moreParam, Pagination pagination) {
		List<BigPackage> bigPackageList = bigPackageDao.findBigPackage(param, moreParam, pagination);
		List<Object> list = new ArrayList<Object>();
		for (BigPackage bigPackage : bigPackageList) {
			Map<String, Object> map = new HashMap<String, Object>();
			Long bigPackageId = bigPackage.getId();
			map.put("id", bigPackageId);
			if (bigPackage.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(bigPackage.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			map.put("shipwayCode", bigPackage.getShipwayCode());
			if (StringUtil.isNotNull(bigPackage.getCheckResult())) {
				if (StringUtil.isEqual(bigPackage.getCheckResult(), "SECURITY")) {
					map.put("checkResult", "拒收(安全不通过)");
				} else if (StringUtil.isEqual(bigPackage.getCheckResult(), "OTHER_REASON")) {
					map.put("checkResult", "拒收(其他不通过)");
				} else if (StringUtil.isEqual(bigPackage.getCheckResult(), "SUCCESS")) {
					map.put("checkResult", "接件(审核已通过)");
				} else {
					map.put("checkResult", bigPackage.getCheckResult());
				}
			} else {
				map.put("checkResult", "");
			}
			// 回传审核
			if (StringUtil.isEqual(bigPackage.getCallbackSendCheckIsSuccess(), Constant.Y)) {
				map.put("callbackSendCheckIsSuccess", "成功");
			} else {
				if (bigPackage.getCallbackSendCheckCount() != null && bigPackage.getCallbackSendCheckCount() > 0) {
					map.put("callbackSendCheckIsSuccess", "失败次数:" + bigPackage.getCallbackSendCheckCount());
				} else {
					map.put("callbackSendCheckIsSuccess", "未回传");
				}
			}
			// 回传称重
			if (StringUtil.isEqual(bigPackage.getCallbackSendWeightIsSuccess(), Constant.Y)) {
				map.put("callbackSendWeightIsSuccess", "成功");
			} else {
				if (bigPackage.getCallbackSendWeighCount() != null && bigPackage.getCallbackSendWeighCount() > 0) {
					map.put("callbackSendWeightIsSuccess", "失败次数:" + bigPackage.getCallbackSendWeighCount());
				} else {
					map.put("callbackSendWeightIsSuccess", "未回传");
				}
			}
			// 回传出库
			if (StringUtil.isEqual(bigPackage.getCallbackSendStatusIsSuccess(), Constant.Y)) {
				map.put("callbackSendStatusIsSuccess", "成功");
			} else {
				if (bigPackage.getCallbackSendStatusCount() != null && bigPackage.getCallbackSendStatusCount() > 0) {
					map.put("callbackSendStatusIsSuccess", "失败次数:" + bigPackage.getCallbackSendStatusCount());
				} else {
					map.put("callbackSendStatusIsSuccess", "未回传");
				}
			}
			// 查询用户名
			User user = userDao.getUserById(bigPackage.getUserIdOfCustomer());
			map.put("userNameOfCustomer", user.getLoginName());
			map.put("customerReferenceNo", bigPackage.getCustomerReferenceNo());
			if (NumberUtil.greaterThanZero(bigPackage.getWarehouseId())) {
				Warehouse warehouse = warehouseDao.getWarehouseById(bigPackage.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
				}
			}
			if (StringUtil.isEqual(BigPackage.TRANSPORT_TYPE_J, bigPackage.getTransportType())) {
				map.put("transportType", "集货转运");
			}
			if (StringUtil.isEqual(BigPackage.TRANSPORT_TYPE_Z, bigPackage.getTransportType())) {
				map.put("transportType", "直接转运");
			}
			map.put("remark", bigPackage.getRemark());
			BigPackageStatus bigPackageStatus = bigPackageStatusDao.findBigPackageStatusByCode(bigPackage.getStatus());
			if (bigPackageStatus != null) {
				map.put("status", bigPackageStatus.getCn());
			}

			// 收件人信息
			BigPackageReceiver bigPackageReceiver = bigPackageReceiverDao.getBigPackageReceiverByPackageId(bigPackageId);
			if (bigPackageReceiver != null) {
				map.put("receiverAddressLine1", bigPackageReceiver.getAddressLine1());
				map.put("receiverAddressLine2", bigPackageReceiver.getAddressLine2());
				map.put("receiverCity", bigPackageReceiver.getCity());
				map.put("receiverCompany", bigPackageReceiver.getCompany());
				map.put("receiverCountryCode", bigPackageReceiver.getCountryCode());
				map.put("receiverCountryName", bigPackageReceiver.getCountryName());
				map.put("receiverCounty", bigPackageReceiver.getCounty());
				map.put("receiverEmail", bigPackageReceiver.getEmail());
				map.put("receiverFirstName", bigPackageReceiver.getFirstName());
				map.put("receiverLastName", bigPackageReceiver.getLastName());
				map.put("receiverMobileNumber", bigPackageReceiver.getMobileNumber());
				map.put("receiverName", bigPackageReceiver.getName());
				map.put("receiverPhoneNumber", bigPackageReceiver.getPhoneNumber());
				map.put("receiverPostalCode", bigPackageReceiver.getPostalCode());
				map.put("receiverStateOrProvince", bigPackageReceiver.getStateOrProvince());
			}
			// 发件人
			BigPackageSender bigPackageSender = bigPackageSenderDao.getBigPackageSenderByPackageId(bigPackageId);
			if (bigPackageSender != null) {
				map.put("senderName", bigPackageSender.getName());
			}
			// 物品明细(目前仅展示SKU*数量)
			String littlePackages = "";
			LittlePackage littlePackageParam = new LittlePackage();
			littlePackageParam.setBigPackageId(bigPackageId);
			List<LittlePackage> littlePackageList = littlePackageDao.findLittlePackage(littlePackageParam, null, null);
			for (LittlePackage littlePackage : littlePackageList) {
				littlePackages += littlePackage.getTrackingNo() + " ; ";
			}
			map.put("littlePackages", littlePackages);
			list.add(map);
		}
		pagination.total = bigPackageDao.countBigPackage(param, moreParam);
		pagination.rows = list;
		return pagination;
	}

	@Override
	public List<Map<String, Object>> getLittlePackageItems(Long bigPackageId) throws ServiceException {
		LittlePackage param = new LittlePackage();
		param.setBigPackageId(bigPackageId);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<LittlePackage> littlePackageList = littlePackageDao.findLittlePackage(param, null, null);
		for (LittlePackage littlePackage : littlePackageList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("trackingNo", littlePackage.getTrackingNo());
			map.put("poNo", littlePackage.getPoNo());
			map.put("carrierCode", littlePackage.getCarrierCode());
			LittlePackageStatus littlePackageStatus = littlePackageStatusDao.findLittlePackageStatusByCode(littlePackage.getStatus());
			if (littlePackageStatus != null) {
				map.put("status", littlePackageStatus.getCn());
			}
			if (littlePackage.getReceivedTime() != null) {
				String receivedTime = DateUtil.dateConvertString(new Date(littlePackage.getReceivedTime()), DateUtil.yyyy_MM_ddHHmmss);
				map.put("receivedTime", receivedTime);
			} else {
				map.put("receivedTime", "");
			}
			if (littlePackage.getCreatedTime() != null) {
				String createdTime = DateUtil.dateConvertString(new Date(littlePackage.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss);
				map.put("createdTime", createdTime);
			}
			if (StringUtil.isEqual(littlePackage.getCallbackIsSuccess(), Constant.Y)) {
				map.put("callbackIsSuccess", "成功");
			} else {
				if (littlePackage.getCallbackCount() != null && littlePackage.getCallbackCount() > 0) {
					map.put("callbackIsSuccess", "失败次数:" + littlePackage.getCallbackCount());
				} else {
					map.put("callbackIsSuccess", "未回传");
				}
			}
			// 获取小包内物品详情
			LittlePackageItem littlePackageItemParam = new LittlePackageItem();
			littlePackageItemParam.setLittlePackageId(littlePackage.getId());
			List<LittlePackageItem> littlePackageItemList = littlePackageItemDao.findLittlePackageItem(littlePackageItemParam, null, null);
			map.put("littlePackageItemList", littlePackageItemList);
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public Map<String, String> checkBigPackage(String bigPackageIds, String checkResult, Long userIdOfOperator) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(bigPackageIds)) {
			map.put(Constant.MESSAGE, "转运订单id(bigPackageIds)为空,无法处理");
			return map;
		}
		if (StringUtil.isNull(checkResult)) {
			map.put(Constant.MESSAGE, "审核结果(checkResult)为空,无法处理");
			return map;
		}
		int updateQuantity = 0;//
		int noUpdateQuantity = 0;// 因非待审核状态,未更新
		String bigPackageIdArr[] = bigPackageIds.split(",");
		for (String bigPackageId : bigPackageIdArr) {
			if (StringUtil.isNull(bigPackageId)) {
				continue;
			}
			Long bigPackageIdLong = Long.valueOf(bigPackageId);
			// 查询订单的当前状态
			String oldStatus = bigPackageDao.getBigPackageStatus(bigPackageIdLong);
			// 如果不是等待审核状态的订单,直接跳过
			if (!StringUtil.isEqual(oldStatus, BigPackageStatusCode.WWC)) {
				noUpdateQuantity++;
				continue;
			}
			// 更改状态为审核中
			bigPackageDao.updateBigPackageStatus(bigPackageIdLong, BigPackageStatusCode.WCI);
			// SUCCESS SECURITY 包裹安全监测不通过 OTHER_REASON 其他异常
			bigPackageDao.updateBigPackageCheckResult(bigPackageIdLong, checkResult);
			updateQuantity++;
		}
		map.put(Constant.MESSAGE, "审核执行中:" + updateQuantity + "个转运订单,  审核无效:" + noUpdateQuantity + "个非待审核状态转运订单");
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public List<LittlePackageStatus> findAllLittlePackageStatus() throws ServiceException {
		return littlePackageStatusDao.findAllLittlePackageStatus();
	}

	@Override
	public Pagination getLittlePackageData(LittlePackage param, Map<String, String> moreParam, Pagination page) throws ServiceException {
		List<LittlePackage> littlePackageList = littlePackageDao.findLittlePackage(param, moreParam, page);
		List<Object> list = new ArrayList<Object>();
		for (LittlePackage littlePackage : littlePackageList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", littlePackage.getId());
			Long bigPackageId = littlePackage.getBigPackageId();
			String bigPackageStatusCode = bigPackageDao.getBigPackageStatus(bigPackageId);
			if (StringUtil.isNotNull(bigPackageStatusCode)) {
				BigPackageStatus bigPackageStatus = bigPackageStatusDao.findBigPackageStatusByCode(bigPackageStatusCode);
				map.put("bigPackageStatus", bigPackageStatus.getCn());
			}
			map.put("bigPackageId", littlePackage.getBigPackageId());
			if (littlePackage.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(littlePackage.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			if (littlePackage.getReceivedTime() != null) {
				map.put("receivedTime", DateUtil.dateConvertString(new Date(littlePackage.getReceivedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			map.put("trackingNo", littlePackage.getTrackingNo());
			map.put("carrierCode", littlePackage.getCarrierCode());
			// 回传审核
			if (StringUtil.isEqual(littlePackage.getCallbackIsSuccess(), Constant.Y)) {
				map.put("callbackIsSuccess", "成功");
			} else {
				if (littlePackage.getCallbackCount() != null && littlePackage.getCallbackCount() > 0) {
					map.put("callbackIsSuccess", "失败次数:" + littlePackage.getCallbackCount());
				} else {
					map.put("callbackIsSuccess", "未回传");
				}
			}
			// 查询用户名
			User user = userDao.getUserById(littlePackage.getUserIdOfCustomer());
			map.put("userNameOfCustomer", user.getLoginName());
			if (littlePackage.getUserIdOfOperator() != null) {
				User operator = userDao.getUserById(littlePackage.getUserIdOfOperator());
				map.put("userNameOfOperator", operator.getLoginName());
			}
			map.put("poNo", littlePackage.getPoNo());
			if (NumberUtil.greaterThanZero(littlePackage.getWarehouseId())) {
				Warehouse warehouse = warehouseDao.getWarehouseById(littlePackage.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
				}
			}
			if (StringUtil.isEqual(BigPackage.TRANSPORT_TYPE_J, littlePackage.getTransportType())) {
				map.put("transportType", "集货转运");
			}
			if (StringUtil.isEqual(BigPackage.TRANSPORT_TYPE_Z, littlePackage.getTransportType())) {
				map.put("transportType", "直接转运");
			}
			map.put("remark", littlePackage.getRemark());
			LittlePackageStatus littlePackageStatus = littlePackageStatusDao.findLittlePackageStatusByCode(littlePackage.getStatus());
			if (littlePackageStatus != null) {
				map.put("status", littlePackageStatus.getCn());
			}
			// 物品明细(目前仅展示SKU*数量)
			String items = "";
			LittlePackageItem littlePackageItemParam = new LittlePackageItem();
			littlePackageItemParam.setLittlePackageId(littlePackage.getId());
			List<LittlePackageItem> littlePackageItemList = littlePackageItemDao.findLittlePackageItem(littlePackageItemParam, null, null);
			for (LittlePackageItem littlePackageItem : littlePackageItemList) {
				items += littlePackageItem.getSku() + " * " + littlePackageItem.getQuantity() + " ; ";
			}
			map.put("items", items);
			list.add(map);
		}
		page.total = littlePackageDao.countLittlePackage(param, moreParam);
		page.rows = list;
		return page;
	}

	@Override
	public List<LittlePackageItem> getLittlePackageItemsByLittlePackageId(Long littlePackageId) throws ServiceException {
		LittlePackageItem littlePackageItemParam = new LittlePackageItem();
		littlePackageItemParam.setLittlePackageId(littlePackageId);
		List<LittlePackageItem> littlePackageItemList = littlePackageItemDao.findLittlePackageItem(littlePackageItemParam, null, null);
		return littlePackageItemList;
	}

	@Override
	public String warehouseInterfaceConfirmTransportOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException {
		Responses responses = new Responses();
		List<Response> responseItems = new ArrayList<Response>();
		Response response = new Response();
		response.setSuccess(Constant.FALSE);
		responseItems.add(response);
		responses.setResponseItems(responseItems);
		// 取 tradeDetail 中tradeOrderId 作为客户订单号
		TradeDetail tradeDetail = eventBody.getTradeDetail();
		if (tradeDetail == null) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("EventBody对象获取TradeDetail对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		List<TradeOrder> tradeOrderList = tradeDetail.getTradeOrders();
		if (tradeOrderList == null || tradeOrderList.size() == 0) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeDetail对象获取TradeOrders对象得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		TradeOrder tradeOrder = tradeOrderList.get(0);
		// 客户订单号
		String customerReferenceNo = tradeOrder.getTradeOrderId();
		if (StringUtil.isNull(customerReferenceNo)) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeOrder对象获取tradeOrderId得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		// 根据客户订单号 (traderOrderId)查找转运订单,修改WWO:待出库操作
		BigPackage param = new BigPackage();
		param.setCustomerReferenceNo(customerReferenceNo);
		List<BigPackage> bigPackageList = bigPackageDao.findBigPackage(param, null, null);
		if (bigPackageList == null || bigPackageList.size() == 0) {
			response.setReason(ErrorCode.S01_CODE);
			response.setReasonDesc("TradeOrder对象获取tradeOrderId得到Null");
			return XmlUtil.toXml(Responses.class, responses);
		}
		BigPackage bigPackage = bigPackageList.get(0);
		if (!StringUtil.isEqual(bigPackage.getStatus(), BigPackageStatusCode.WCC)) {
			response.setReason(ErrorCode.B0100_CODE);
			response.setReasonDesc("订单当前状态非待客户核重状态");
			return XmlUtil.toXml(Responses.class, responses);
		}
		bigPackageDao.updateBigPackageStatus(bigPackage.getId(), BigPackageStatusCode.WWO);
		response.setSuccess(Constant.TRUE);
		return XmlUtil.toXml(Responses.class, responses);
	}

	@Override
	public List<Map<String, String>> checkReceivedLittlePackage(LittlePackage param) {
		List<LittlePackage> littlePackageList = littlePackageDao.findLittlePackage(param, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (LittlePackage littlePackage : littlePackageList) {
			Map<String, String> map = new HashMap<String, String>();
			Long userId = littlePackage.getUserIdOfCustomer();
			User user = userDao.getUserById(userId);
			map.put("littlePackageId", String.valueOf(littlePackage.getId()));
			map.put("userLoginName", user.getLoginName());
			map.put("trackingNo", littlePackage.getTrackingNo());
			map.put("bigPackageId", littlePackage.getBigPackageId() + "");
			BigPackage bigPackage = bigPackageDao.getBigPackageById(littlePackage.getBigPackageId());
			map.put("outWarehouseTrackingNo", bigPackage.getTrackingNo());
			map.put("shipwayCode", bigPackage.getShipwayCode());
			map.put("seatCode", littlePackage.getSeatCode());
			map.put("carrierCode", littlePackage.getCarrierCode());
			String time = DateUtil.dateConvertString(new Date(littlePackage.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss);
			map.put("createdTime", time);
			LittlePackageStatus status = littlePackageStatusDao.findLittlePackageStatusByCode(littlePackage.getStatus());
			if (status != null) {
				map.put("status", status.getCn());
			} else {
				map.put("status", "");
			}
			String onShelfstatus = littlePackageOnShelfDao.findStatusByLittlePackageId(littlePackage.getId());
			if (StringUtil.isEqual(onShelfstatus, LittlePackageOnShelf.STATUS_ON_SHELF)) {
				map.put("onShelfstatus", "已上架");
			} else if (StringUtil.isEqual(onShelfstatus, LittlePackageOnShelf.STATUS_OUT_SHELF)) {
				map.put("onShelfstatus", "已下架");
			} else if (StringUtil.isEqual(onShelfstatus, LittlePackageOnShelf.STATUS_PRE_ON_SHELF)) {
				map.put("onShelfstatus", "待上架");
			} else {
				map.put("onShelfstatus", "");
			}
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public Map<String, String> submitInWarehouse(String trackingNo, String remark, Long userIdOfOperator, Long warehouseId, Long littlePackageId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(trackingNo)) {
			map.put(Constant.MESSAGE, "请输入跟踪单号.");
			return map;
		}
		LittlePackage littlePackage = littlePackageDao.getLittlePackageById(littlePackageId);
		if (!StringUtil.isEqual(littlePackage.getStatus(), LittlePackageStatusCode.WWR)) {
			// 非待收货状态
			map.put(Constant.MESSAGE, "该转运订单非待收货状态,请输入新的跟踪单号");
			return map;
		}
		// 更新为已收货前,查找空闲的转运业务专用货位
		String seatCode = littlePackageOnShelfDao.findSeatCodeForOnShelf(littlePackage.getTransportType());
		if (StringUtil.isNull(seatCode)) {
			// 货位不足,收货失败
			map.put(Constant.MESSAGE, "转运货位不足,请添加货位再收货");
			return map;
		}
		littlePackage.setSeatCode(seatCode);// 收货预分配货位 真正的货位信息要在上架记录寻找
		// 更改为已收货, 待添加操作日志
		littlePackage.setStatus(LittlePackageStatusCode.WSR);
		littlePackage.setReceivedTime(System.currentTimeMillis());
		// 保存货位,状态,时间
		littlePackageDao.receivedLittlePackage(littlePackage);
		// 判断bigPackage下的所有littlePackage是否已经全部收货
		LittlePackage littlePackageParam = new LittlePackage();
		littlePackageParam.setBigPackageId(littlePackage.getBigPackageId());
		List<LittlePackage> littlePackageList = littlePackageDao.findLittlePackage(littlePackageParam, null, null);
		boolean isReceived = true;// 小包是否已经全部收货
		for (LittlePackage temp : littlePackageList) {
			if (StringUtil.isEqual(temp.getStatus(), LittlePackageStatusCode.WWR)) {
				isReceived = false;
			}
		}
		if (isReceived) {
			bigPackageDao.updateBigPackageStatus(littlePackage.getBigPackageId(), BigPackageStatusCode.WOS);// 收货完成
		} else {
			bigPackageDao.updateBigPackageStatus(littlePackage.getBigPackageId(), BigPackageStatusCode.WRP);
		}
		// 保存预分配货位上架记录
		LittlePackageOnShelf onShelf = new LittlePackageOnShelf();
		onShelf.setCreatedTime(System.currentTimeMillis());
		onShelf.setLittlePackageId(littlePackageId);
		onShelf.setBigPackageId(littlePackage.getBigPackageId());
		onShelf.setSeatCode(seatCode);
		onShelf.setStatus(LittlePackageOnShelf.STATUS_PRE_ON_SHELF);
		onShelf.setTrackingNo(trackingNo);
		onShelf.setUserIdOfCustomer(littlePackage.getUserIdOfCustomer());
		onShelf.setUserIdOfOperator(userIdOfOperator);
		onShelf.setWarehouseId(warehouseId);
		littlePackageOnShelfDao.saveLittlePackageOnShelf(onShelf);
		map.put("seatCode", seatCode);
		map.put("bigPackageId", "" + littlePackage.getBigPackageId());

		if (StringUtil.isEqual(littlePackage.getTransportType(), BigPackage.TRANSPORT_TYPE_J)) {// 集货转运
			map.put(Constant.MESSAGE, "集货转运订单收货成功,请继续收货");
			map.put(Constant.STATUS, Constant.SUCCESS);
		} else if (StringUtil.isEqual(littlePackage.getTransportType(), BigPackage.TRANSPORT_TYPE_Z)) {// 直接转运
			map.put(Constant.MESSAGE, "直接转运订单收货成功,请称重打单");
			map.put(Constant.STATUS, "2");
			BigPackage bigPackage = bigPackageDao.getBigPackageById(littlePackage.getBigPackageId());
			map.put("shipwayCode", bigPackage.getShipwayCode());
			map.put("trackingNo", bigPackage.getTrackingNo());
			map.put("seatCode", seatCode);
		}
		return map;
	}

	@Override
	public Map<String, String> bigPackageSubmitWeight(Long userIdOfOperator, Long bigPackageId, Double weight) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (weight == null || weight <= 0) {
			map.put(Constant.MESSAGE, "请先输入装箱重量");
			return map;
		}
		// 查询大包是否已经称重,不可重复称重
		BigPackage bigPackage = bigPackageDao.getBigPackageById(bigPackageId);
		if (bigPackage.getOutWarehouseWeight() != null) {
			map.put(Constant.MESSAGE, "该转运订单已经称重,不能更改重量");
			return map;
		}
		// 集货转运订单,非待称重状态不可称重
		// 直接转运订单:收货(大包:收货完成,小包:待发送入库) 称重(大包:待发送重量 ,小包:无变化) 打印捡货单:无需做上架就可以打
		if (StringUtil.isEqual(bigPackage.getTransportType(), BigPackage.TRANSPORT_TYPE_J)) {
			if (!StringUtil.isEqual(bigPackage.getStatus(), BigPackageStatusCode.WWW)) {
				map.put(Constant.MESSAGE, "该集货转运订单非待称重状态,不能更改重量");
				return map;
			}
		}
		if (StringUtil.isEqual(bigPackage.getTransportType(), BigPackage.TRANSPORT_TYPE_Z)) {
			// 直接转运订单,在几个状态都可以称重
			if (!(StringUtil.isEqual(bigPackage.getStatus(), BigPackageStatusCode.WOS) || StringUtil.isEqual(bigPackage.getStatus(), BigPackageStatusCode.WWP) || StringUtil.isEqual(bigPackage.getStatus(), BigPackageStatusCode.WWW))) {
				map.put(Constant.MESSAGE, "该直接转运订单非待称重状态,不能更改重量");
				return map;
			}
		}
		bigPackage.setOutWarehouseWeight(weight);
		bigPackage.setStatus(BigPackageStatusCode.WSW);
		bigPackage.setWeightCode(WeightCode.KG);
		bigPackage.setUserIdOfOperator(userIdOfOperator);
		bigPackageDao.updateBigPackageWeight(bigPackage);

		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public Map<String, String> saveLittlePackageOnShelves(Long userIdOfOperator, Long littlePackageId, String seatCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(seatCode)) {
			map.put(Constant.MESSAGE, "请输入货位号");
			return map;
		}
		// 查询订单状态是否是待上架
		LittlePackage littlePackage = littlePackageDao.getLittlePackageById(littlePackageId);
		if (!StringUtil.isEqual(littlePackage.getStatus(), LittlePackageStatusCode.WOS)) {
			map.put(Constant.MESSAGE, "该订单非待上架状态,不能上架");
			return map;
		}
		// 查找小包最新上架记录
		LittlePackageOnShelf onshelf = littlePackageOnShelfDao.findLittlePackageOnShelfByLittlePackageId(littlePackageId);
		if (onshelf == null) {
			map.put(Constant.MESSAGE, "预分配货位丢失,请联系管理员");
			return map;
		}
		if (StringUtil.isEqual(onshelf.getStatus(), LittlePackageOnShelf.STATUS_ON_SHELF)) {
			map.put(Constant.MESSAGE, "该订单已上架,不能重复上架");
			return map;
		}
		littlePackage.setStatus(LittlePackageStatusCode.W_OUT_S);
		littlePackageDao.updateLittlePackageStatus(littlePackage);

		onshelf.setStatus(LittlePackageOnShelf.STATUS_ON_SHELF);
		// 否则修改为已上架
		littlePackageOnShelfDao.updateLittlePackageOnShelf(onshelf);

		// 判断bigpackage下是不是所有littlepackage都已经上架
		LittlePackage littlePackageParam = new LittlePackage();
		littlePackageParam.setBigPackageId(littlePackage.getBigPackageId());
		List<LittlePackage> littlePackageList = littlePackageDao.findLittlePackage(littlePackageParam, null, null);
		boolean isReceived = true;// 小包是否已经全部收货
		for (LittlePackage temp : littlePackageList) {
			if (!StringUtil.isEqual(temp.getStatus(), LittlePackageStatusCode.W_OUT_S)) {
				isReceived = false;
			}
		}
		if (isReceived) {
			bigPackageDao.updateBigPackageStatus(littlePackage.getBigPackageId(), BigPackageStatusCode.WWP);// 上架完成待打印捡货
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public Pagination getLittlePackageOnShelfData(LittlePackageOnShelf param, Map<String, String> moreParam, Pagination page) throws ServiceException {
		List<LittlePackageOnShelf> littlePackageOnShelfList = littlePackageOnShelfDao.findLittlePackageOnShelf(param, moreParam, page);
		List<Object> list = new ArrayList<Object>();
		for (LittlePackageOnShelf littlePackageOnShelf : littlePackageOnShelfList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", littlePackageOnShelf.getId());
			if (littlePackageOnShelf.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(littlePackageOnShelf.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			map.put("trackingNo", littlePackageOnShelf.getTrackingNo());
			map.put("seatCode", littlePackageOnShelf.getSeatCode());
			// 查询用户名
			User user = userDao.getUserById(littlePackageOnShelf.getUserIdOfCustomer());
			map.put("userNameOfCustomer", user.getLoginName());
			if (littlePackageOnShelf.getUserIdOfOperator() != null) {
				User operator = userDao.getUserById(littlePackageOnShelf.getUserIdOfOperator());
				map.put("userNameOfOperator", operator.getLoginName());
			}
			if (NumberUtil.greaterThanZero(littlePackageOnShelf.getWarehouseId())) {
				Warehouse warehouse = warehouseDao.getWarehouseById(littlePackageOnShelf.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
				}
			}
			if (StringUtil.isEqual(littlePackageOnShelf.getStatus(), LittlePackageOnShelf.STATUS_ON_SHELF)) {
				map.put("status", "已上架");
			} else if (StringUtil.isEqual(littlePackageOnShelf.getStatus(), LittlePackageOnShelf.STATUS_OUT_SHELF)) {
				map.put("status", "已下架");
			} else if (StringUtil.isEqual(littlePackageOnShelf.getStatus(), LittlePackageOnShelf.STATUS_PRE_ON_SHELF)) {
				map.put("status", "预上架");
			}
			BigPackage bigPackage = bigPackageDao.getBigPackageById(littlePackageOnShelf.getBigPackageId());
			String transportType = bigPackage.getTransportType();
			if (StringUtil.isEqual(transportType, BigPackage.TRANSPORT_TYPE_J)) {
				map.put("transportType", "集货转运");
			} else if (StringUtil.isEqual(transportType, BigPackage.TRANSPORT_TYPE_Z)) {
				map.put("transportType", "直接转运");
			}
			map.put("outWarehouseTrackingNo", bigPackage.getTrackingNo());
			map.put("outWarehouseShipwayCode", bigPackage.getShipwayCode());
			list.add(map);
		}
		page.total = littlePackageOnShelfDao.countLittlePackageOnShelf(param, moreParam);
		page.rows = list;
		return page;
	}

	@Override
	public Map<String, String> bigPackageWeightSubmitCustomerReferenceNo(String customerReferenceNo, Long userIdOfOperator) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(customerReferenceNo)) {
			map.put(Constant.MESSAGE, "请输入客户订单号(扫描捡货单右上角条码即可)");
			return map;
		}
		BigPackage param = new BigPackage();
		param.setCustomerReferenceNo(customerReferenceNo);
		List<BigPackage> bigPackageList = bigPackageDao.findBigPackage(param, null, null);
		if (bigPackageList == null || bigPackageList.size() <= 0) {
			map.put(Constant.MESSAGE, "根据该客户订单号找不到转运订单,请输入正确客户订单号");
			return map;
		}
		BigPackage bigPackage = bigPackageList.get(0);
		map.put("bigPackageId", bigPackage.getId() + "");
		BigPackageStatus status = bigPackageStatusDao.findBigPackageStatusByCode(bigPackage.getStatus());
		map.put("bigPackageStatus", status.getCn());
		map.put("shipwayCode", bigPackage.getShipwayCode());
		map.put("trackingNo", bigPackage.getTrackingNo());
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public Map<String, Object> outWarehouseShippingEnterCoeTrackingNo(String coeTrackingNo) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.STATUS, Constant.FAIL);
		PackageRecordItem outWarehouseShipping = new PackageRecordItem();
		outWarehouseShipping.setCoeTrackingNo(coeTrackingNo);
		List<PackageRecordItem> outWarehouseShippingList = packageRecordItemDao.findPackageRecordItem(outWarehouseShipping, null, null);
		List<TrackingNo> trackingNos = trackingNoDao.findTrackingNo(coeTrackingNo, TrackingNo.TYPE_COE);
		// 暂不处理,单号可能重复问题
		if (trackingNos == null || trackingNos.size() <= 0) {
			map.put(Constant.MESSAGE, "该COE交接单号无效,请输入新单号");
			return map;
		}
		TrackingNo trackingNo = trackingNos.get(0);
		if (StringUtil.isEqual(trackingNo.getStatus(), TrackingNo.STATUS_USED + "")) {
			map.put(Constant.MESSAGE, "该COE交接单号已经使用,请输入新单号");
			return map;
		}
		map.put("coeTrackingNo", trackingNo);
		map.put(Constant.STATUS, Constant.SUCCESS);
		map.put("outWarehouseShippingList", outWarehouseShippingList);
		return map;
	}
}
