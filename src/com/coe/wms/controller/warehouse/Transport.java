package com.coe.wms.controller.warehouse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.coe.wms.controller.Application;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Shipway;
import com.coe.wms.model.warehouse.TrackingNo;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.transport.Order;
import com.coe.wms.model.warehouse.transport.OrderStatus;
import com.coe.wms.model.warehouse.transport.FirstWaybill;
import com.coe.wms.model.warehouse.transport.FirstWaybillItem;
import com.coe.wms.model.warehouse.transport.FirstWaybillOnShelf;
import com.coe.wms.model.warehouse.transport.FirstWaybillStatus;
import com.coe.wms.model.warehouse.transport.OutWarehousePackage;
import com.coe.wms.model.warehouse.transport.OutWarehousePackageItem;
import com.coe.wms.service.storage.IInWarehouseOrderService;
import com.coe.wms.service.storage.IOutWarehouseOrderService;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.service.storage.IWarehouseInterfaceService;
import com.coe.wms.service.transport.ITransportService;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.GsonUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.SessionConstant;
import com.coe.wms.util.StringUtil;

/**
 * 仓库转运业务 控制类
 * 
 * warehouse/Storage 是操作员真实收货,出库
 * 
 * @author Administrator
 * 
 */
@Controller("transport")
@RequestMapping("/warehouse/transport")
public class Transport {

	private static final Logger logger = Logger.getLogger(Transport.class);

	@Resource(name = "storageService")
	private IStorageService storageService;

	@Resource(name = "transportService")
	private ITransportService transportService;

	@Resource(name = "userService")
	private IUserService userService;

	@Resource(name = "inWarehouseOrderService")
	private IInWarehouseOrderService inWarehouseOrderService;

	@Resource(name = "outWarehouseOrderService")
	private IOutWarehouseOrderService outWarehouseOrderService;

	@Resource(name = "warehouseInterfaceService")
	private IWarehouseInterfaceService warehouseInterfaceService;

	/**
	 * 转运订单 查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listBigPackage", method = RequestMethod.GET)
	public ModelAndView listBigPackage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		List<OrderStatus> bigPackageStatusList = transportService.findAllBigPackageStatus();
		view.addObject("bigPackageStatusList", bigPackageStatusList);
		List<Shipway> shipwayList = transportService.findAllShipway();
		view.addObject("shipwayList", shipwayList);
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.setViewName("warehouse/transport/listBigPackage");
		return view;
	}

	/**
	 * 待审核转运订单 查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listWaitCheckBigPackage", method = RequestMethod.GET)
	public ModelAndView listWaitCheckBigPackage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		User user = userService.getUserById(userId);
		List<OrderStatus> bigPackageStatusList = transportService.findAllBigPackageStatus();
		view.addObject("bigPackageStatusList", bigPackageStatusList);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.setViewName("warehouse/transport/listWaitCheckBigPackage");
		return view;
	}

	/**
	 * 获取转运订单
	 * 
	 * @param request
	 * @param response
	 * @param userLoginName
	 *            客户登录名,仅当根据跟踪号无法找到订单时,要求输入
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getBigPackageData", method = RequestMethod.POST)
	public String getBigPackageData(HttpServletRequest request, String sortorder, String sortname, int page, int pagesize, String userLoginName, Long warehouseId, String customerReferenceNo, String createdTimeStart, String createdTimeEnd,
			String status, String shipway, String nos, String noType, String trackingNoIsNull) throws IOException {
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;

		Order param = new Order();
		param.setStatus(status);
		param.setShipwayCode(shipway);
		// 客户订单号
		param.setCustomerReferenceNo(customerReferenceNo);
		// 客户帐号
		if (StringUtil.isNotNull(userLoginName)) {
			Long userIdOfCustomer = userService.findUserIdByLoginName(userLoginName);
			param.setUserIdOfCustomer(userIdOfCustomer);
		}
		// 仓库
		param.setWarehouseId(warehouseId);
		// 更多参数
		Map<String, String> moreParam = new HashMap<String, String>();
		moreParam.put("createdTimeStart", createdTimeStart);
		moreParam.put("createdTimeEnd", createdTimeEnd);
		moreParam.put("nos", nos);
		moreParam.put("noType", noType);
		moreParam.put("trackingNoIsNull", trackingNoIsNull);// 跟踪号是否为空

		pagination = transportService.getBigPackageData(param, moreParam, pagination);
		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);
		return GsonUtil.toJson(map);
	}

	@ResponseBody
	@RequestMapping(value = "/getLittlePackageItemByBigPackageId", method = RequestMethod.POST)
	public String getLittlePackageItemByBigPackageId(Long bigPackageId) {
		List<Map<String, Object>> littlePackageItems = transportService.getLittlePackageItems(bigPackageId);
		return GsonUtil.toJson(littlePackageItems);
	}

	@ResponseBody
	@RequestMapping(value = "/getLittlePackageItemBylittlePackageId", method = RequestMethod.POST)
	public String getLittlePackageItemBylittlePackageId(Long littlePackageId) {
		List<FirstWaybillItem> littlePackageItems = transportService.getLittlePackageItemsByLittlePackageId(littlePackageId);
		return GsonUtil.toJson(littlePackageItems);
	}

	/**
	 * 
	 * 审核转运大包
	 * 
	 * checkResult:1 审核通过 2审核不通过
	 */
	@ResponseBody
	@RequestMapping(value = "/checkBigPackage")
	public String checkBigPackage(HttpServletRequest request, String bigPackageIds, String checkResult) throws IOException {
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		logger.info("审核转运订单 操作员id:" + userIdOfOperator + " checkResult:" + checkResult + " 订单:" + bigPackageIds);
		Map<String, String> checkResultMap = transportService.checkBigPackage(bigPackageIds, checkResult, userIdOfOperator);
		return GsonUtil.toJson(checkResultMap);
	}

	/**
	 * 转运订单详情 查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listLittlePackage", method = RequestMethod.GET)
	public ModelAndView listLittlePackage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());

		List<FirstWaybillStatus> littlePackageStatusList = transportService.findAllLittlePackageStatus();
		view.addObject("littlePackageStatusList", littlePackageStatusList);

		List<Shipway> shipwayList = transportService.findAllShipway();
		view.addObject("shipwayList", shipwayList);
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.setViewName("warehouse/transport/listLittlePackage");
		return view;
	}

	/**
	 * 转运订单详情 查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listReceivedLittlePackage", method = RequestMethod.GET)
	public ModelAndView listReceivedLittlePackage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.setViewName("warehouse/transport/listReceivedLittlePackage");
		return view;
	}

	/**
	 * 获取转运订单小包
	 * 
	 * @param request
	 * @param response
	 * @param userLoginName
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getLittlePackageData", method = RequestMethod.POST)
	public String getLittlePackageData(HttpServletRequest request, String sortorder, String sortname, int page, int pagesize, String userLoginName, Long warehouseId, String trackingNo, String createdTimeStart, String createdTimeEnd,
			String receivedTimeStart, String receivedTimeEnd, String status, String nos, String noType, String isReceived) throws IOException {
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;
		FirstWaybill param = new FirstWaybill();
		param.setStatus(status);
		// 客户订单号
		param.setTrackingNo(trackingNo);
		// 客户帐号
		if (StringUtil.isNotNull(userLoginName)) {
			Long userIdOfCustomer = userService.findUserIdByLoginName(userLoginName);
			param.setUserIdOfCustomer(userIdOfCustomer);
		}
		// 仓库
		param.setWarehouseId(warehouseId);
		// 更多参数
		Map<String, String> moreParam = new HashMap<String, String>();
		moreParam.put("createdTimeStart", createdTimeStart);
		moreParam.put("createdTimeEnd", createdTimeEnd);
		moreParam.put("receivedTimeStart", receivedTimeStart);
		moreParam.put("receivedTimeEnd", receivedTimeEnd);
		moreParam.put("nos", nos);
		moreParam.put("noType", noType);
		moreParam.put("isReceived", isReceived);
		pagination = transportService.getLittlePackageData(param, moreParam, pagination);
		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);
		return GsonUtil.toJson(map);
	}

	/**
	 * 入库订单收货
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/inWarehouse", method = RequestMethod.GET)
	public ModelAndView inWarehouse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.setViewName("warehouse/transport/inWarehouse");
		return view;
	}

	/**
	 * 检查 跟踪号是否能找到唯一转运小包
	 * 
	 * @param request
	 * @param trackingNo
	 * @param userLoginName
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/checkReceivedLittlePackage")
	public String checkReceivedLittlePackage(HttpServletRequest request, String trackingNo) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.STATUS, Constant.FAIL);
		FirstWaybill param = new FirstWaybill();
		param.setTrackingNo(trackingNo);
		// 查询转运订单
		List<Map<String, String>> mapList = transportService.checkReceivedLittlePackage(param);
		if (mapList.size() < 1) {
			map.put(Constant.STATUS, "-1");
			// 查询是否是仓配订单
			InWarehouseOrder inWarehouseParam = new InWarehouseOrder();
			inWarehouseParam.setTrackingNo(trackingNo);
			mapList = inWarehouseOrderService.checkInWarehouseOrder(inWarehouseParam);
			if (mapList.size() > 0) {
				map.put(Constant.MESSAGE, "该单号无转运订单,但找到" + mapList.size() + "个仓配订单,请确认订单类型");
			} else {
				map.put(Constant.MESSAGE, "该单号无转运订单,也无仓配订单,请先添加订单");
			}
			return GsonUtil.toJson(map);
		}
		map.put("mapList", mapList);
		if (mapList.size() > 1) {
			// 找到多个入库订单,返回跟踪号,承运商,参考号,客户等信息供操作员选择
			map.put(Constant.MESSAGE, "该单号找到" + mapList.size() + "个转运订单小包,请选择其中一个,并按回车!");
			map.put(Constant.STATUS, "2");
			return GsonUtil.toJson(map);
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		return GsonUtil.toJson(map);
	}

	/**
	 * 保存转运订单入库
	 * 
	 * @param request
	 * @param trackingNo
	 * @param userLoginName
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/submitInWarehouse")
	public String submitInWarehouse(HttpServletRequest request, String trackingNo, Long warehouseId, Long littlePackageId, String remark) throws IOException {
		// 操作员
		Long userIdOfOperator = (Long) request.getSession().getAttribute(SessionConstant.USER_ID);
		Map<String, String> serviceResult = transportService.submitInWarehouse(trackingNo, remark, userIdOfOperator, warehouseId, littlePackageId);
		return GsonUtil.toJson(serviceResult);
	}

	/**
	 * 大包称重 小包下架
	 * 
	 * @param request
	 * @param trackingNo
	 * @param userLoginName
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/bigPackageSubmitWeight")
	public String bigPackageSubmitWeight(HttpServletRequest request, Long bigPackageId, Double weight) throws IOException {
		Long userIdOfOperator = (Long) request.getSession().getAttribute(SessionConstant.USER_ID);
		Map<String, String> serviceResult = transportService.bigPackageSubmitWeight(userIdOfOperator, bigPackageId, weight);
		return GsonUtil.toJson(serviceResult);
	}

	/**
	 * 上架
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/littlePackageOnShelf", method = RequestMethod.GET)
	public ModelAndView littlePackageOnShelf(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.setViewName("warehouse/transport/littlePackageOnShelf");
		return view;
	}

	/**
	 * 保存转运订单入库
	 * 
	 * @param request
	 * @param trackingNo
	 * @param userLoginName
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/saveLittlePackageOnShelves")
	public String saveLittlePackageOnShelves(HttpServletRequest request, Long littlePackageId, String seatCode) throws IOException {
		Long userIdOfOperator = (Long) request.getSession().getAttribute(SessionConstant.USER_ID);
		Map<String, String> serviceResult = transportService.saveLittlePackageOnShelves(userIdOfOperator, littlePackageId, seatCode);
		return GsonUtil.toJson(serviceResult);
	}

	/**
	 * 上架记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listLittlePackageOnShelf", method = RequestMethod.GET)
	public ModelAndView listLittlePackageOnShelf(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.setViewName("warehouse/transport/listLittlePackageOnShelf");
		return view;
	}

	/**
	 * 获取转运订单上架记录
	 * 
	 * @param request
	 * @param response
	 * @param userLoginName
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getLittlePackageOnShelfData", method = RequestMethod.POST)
	public String getLittlePackageOnShelfData(HttpServletRequest request, String sortorder, String sortname, int page, int pagesize, String userLoginName, Long warehouseId, String trackingNo, String seatCode, String createdTimeStart,
			String createdTimeEnd) throws IOException {
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;
		FirstWaybillOnShelf param = new FirstWaybillOnShelf();
		// 客户帐号
		if (StringUtil.isNotNull(userLoginName)) {
			Long userIdOfCustomer = userService.findUserIdByLoginName(userLoginName);
			param.setUserIdOfCustomer(userIdOfCustomer);
		}
		// 仓库
		param.setWarehouseId(warehouseId);
		param.setTrackingNo(trackingNo);
		param.setSeatCode(seatCode);

		// 更多参数
		Map<String, String> moreParam = new HashMap<String, String>();
		moreParam.put("createdTimeStart", createdTimeStart);
		moreParam.put("createdTimeEnd", createdTimeEnd);
		pagination = transportService.getLittlePackageOnShelfData(param, moreParam, pagination);
		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);
		return GsonUtil.toJson(map);
	}

	/**
	 * 称重
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/bigPackageWeightAndPrint", method = RequestMethod.GET)
	public ModelAndView bigPackageWeightAndPrint(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.setViewName("warehouse/transport/bigPackageWeightAndPrint");
		return view;
	}

	/**
	 * 待审核转运订单 查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listWaiPrintBigPackage", method = RequestMethod.GET)
	public ModelAndView listWaiPrintBigPackage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		User user = userService.getUserById(userId);
		List<OrderStatus> bigPackageStatusList = transportService.findAllBigPackageStatus();
		view.addObject("bigPackageStatusList", bigPackageStatusList);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.setViewName("warehouse/transport/listWaiPrintBigPackage");
		return view;
	}

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/bigPackageWeightSubmitCustomerReferenceNo", method = RequestMethod.POST)
	public String bigPackageWeightSubmitCustomerReferenceNo(HttpServletRequest request, HttpServletResponse response, String customerReferenceNo) throws IOException {
		HttpSession session = request.getSession();
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Map<String, String> checkResultMap = transportService.bigPackageWeightSubmitCustomerReferenceNo(customerReferenceNo, userIdOfOperator);
		return GsonUtil.toJson(checkResultMap);
	}

	/**
	 * 出库扫描运单建包界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/outWarehousePackage", method = RequestMethod.GET)
	public ModelAndView outWarehousePackage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		// 进入界面 分配coe单号,并锁定coe单号,下次不能再使用
		TrackingNo trackingNo = outWarehouseOrderService.getCoeTrackingNoforOutWarehouseShipping();

		if (trackingNo != null) {
			view.addObject("coeTrackingNo", trackingNo.getTrackingNo());
			view.addObject("coeTrackingNoId", trackingNo.getId());
		}
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.setViewName("warehouse/transport/outWarehousePackage");
		return view;
	}

	/**
	 * 
	 * 出货重新输入coe交接单号
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/outWarehousePackageEnterCoeTrackingNo")
	public String outWarehousePackageEnterCoeTrackingNo(HttpServletRequest request, HttpServletResponse response, String coeTrackingNo) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> objectMap = transportService.outWarehousePackageEnterCoeTrackingNo(coeTrackingNo);
		List<OutWarehousePackageItem> packageRecordItemList = (List<OutWarehousePackageItem>) objectMap.get("packageRecordItemList");
		map.put("packageRecordItemList", packageRecordItemList);
		map.put(Constant.STATUS, objectMap.get(Constant.STATUS));
		map.put(Constant.MESSAGE, objectMap.get(Constant.MESSAGE));
		map.put("coeTrackingNo", objectMap.get("coeTrackingNo"));
		return GsonUtil.toJson(map);
	}

	/**
	 * 建包扫运单动作, 检查每个运单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/checkOutWarehousePackage")
	public String checkOutWarehousePackage(HttpServletRequest request, HttpServletResponse response, String trackingNo, Long coeTrackingNoId, String coeTrackingNo, String addOrSub, String orderIds) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		Map<String, String> checkResultMap = transportService.checkOutWarehousePackage(trackingNo, userId, coeTrackingNoId, coeTrackingNo, addOrSub, orderIds);
		return GsonUtil.toJson(checkResultMap);
	}

	@RequestMapping(value = "/outWarehousePackageBatchTrackingNo", method = RequestMethod.GET)
	public ModelAndView outWarehousePackageBatchTrackingNo(HttpServletRequest request, HttpServletResponse response, String trackingNo) throws IOException {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		if (trackingNo == null) {
			trackingNo = "";
		}
		view.addObject("trackingNo", trackingNo);
		view.setViewName("warehouse/transport/outWarehousePackageBatchTrackingNo");
		return view;
	}

	/**
	 * 提交出货建包
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/outWarehousePackageConfirm")
	public String outWarehousePackageConfirm(HttpServletRequest request, HttpServletResponse response, String orderIds, String coeTrackingNo, Long coeTrackingNoId) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		Map<String, String> checkResultMap = transportService.outWarehousePackageConfirm(coeTrackingNo, coeTrackingNoId, orderIds, userId);
		return GsonUtil.toJson(checkResultMap);
	}

	/**
	 * 出库扫描运单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/outWarehouseShipping", method = RequestMethod.GET)
	public ModelAndView outWarehouseShipping(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.setViewName("warehouse/transport/outWarehouseShipping");
		return view;
	}

	/**
	 * 出库建包记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listOutWarehousePackage", method = RequestMethod.GET)
	public ModelAndView listOutWarehousePackage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.setViewName("warehouse/transport/listOutWarehousePackage");
		return view;
	}

	/**
	 * 复核小包数量
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/checkLittlePackage")
	public synchronized String checkLittlePackage(HttpServletRequest request, HttpServletResponse response, Long bigPackageId, String trackingNo) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		Map<String, String> checkResultMap = transportService.checkLittlePackage(bigPackageId, trackingNo);
		return GsonUtil.toJson(checkResultMap);
	}

	/**
	 * 提交出货总单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/outWarehouseShippingConfirm")
	public synchronized String outWarehouseShippingConfirm(HttpServletRequest request, HttpServletResponse response, String coeTrackingNo) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		Map<String, String> checkResultMap = transportService.outWarehouseShippingConfirm(coeTrackingNo, userId);
		return GsonUtil.toJson(checkResultMap);
	}

	/**
	 * 获取出库建包记录
	 * 
	 * @param request
	 * @param response
	 * @param userLoginName
	 *            客户登录名,仅当根据跟踪号无法找到订单时,要求输入
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getPackageRecordData")
	public String getPackageRecordData(HttpServletRequest request, String sortorder, String sortname, int page, int pagesize, String userLoginName, Long warehouseId, String coeTrackingNo, String createdTimeStart, String createdTimeEnd)
			throws IOException {
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;
		OutWarehousePackage param = new OutWarehousePackage();
		param.setCoeTrackingNo(coeTrackingNo);
		if (StringUtil.isNotNull(userLoginName)) {
			Long userIdOfCustomer = userService.findUserIdByLoginName(userLoginName);
			param.setUserIdOfCustomer(userIdOfCustomer);
		}
		param.setWarehouseId(warehouseId);
		// 更多参数
		Map<String, String> moreParam = new HashMap<String, String>();
		moreParam.put("createdTimeStart", createdTimeStart);
		moreParam.put("createdTimeEnd", createdTimeEnd);
		pagination = transportService.getPackageRecordData(param, moreParam, pagination);
		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);
		return GsonUtil.toJson(map);
	}

	@ResponseBody
	@RequestMapping(value = "/getPackageRecordItemByPackageRecordId", method = RequestMethod.POST)
	public String getPackageRecordItemByPackageRecordId(Long packageRecordId) {
		List<Map<String, String>> mapList = transportService.getPackageRecordItemByPackageRecordId(packageRecordId);
		return GsonUtil.toJson(mapList);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/editPackageRecordRemark", method = RequestMethod.GET)
	public ModelAndView editPackageRecordRemark(HttpServletRequest request, HttpServletResponse response, Long id, String remark) throws IOException {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		if (remark == null) {
			remark = "";
		}
		view.addObject("remark", remark);
		view.setViewName("warehouse/transport/editPackageRecordRemark");
		return view;
	}

	/**
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/savePackageRecordRemark")
	public String savePackageRecordRemark(HttpServletRequest request, Long id, String remark) throws IOException {
		Map<String, String> map = transportService.savePackageRecordRemark(remark, id);
		return GsonUtil.toJson(map);
	}

	@ResponseBody
	@RequestMapping(value = "/applyTrackingNo")
	public String applyTrackingNo(Long orderId) {
		Map<String, String> map = transportService.applyTrackingNo(orderId);
		return GsonUtil.toJson(map);
	}
}
