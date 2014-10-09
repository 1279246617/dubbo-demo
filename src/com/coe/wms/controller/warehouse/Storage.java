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
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.GsonUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.SessionConstant;
import com.coe.wms.util.StringUtil;

/**
 * 仓库仓配业务 控制类
 * 
 * 
 * 
 * api/Storage 是:API接受预报订单
 * 
 * 
 * warehouse/Storage 是操作员真实收货,出库
 * 
 * @author Administrator
 * 
 */
@Controller("storage")
@RequestMapping("/warehouse/storage")
public class Storage {

	private static final Logger logger = Logger.getLogger(Storage.class);

	@Resource(name = "storageService")
	private IStorageService storageService;

	@Resource(name = "userService")
	private IUserService userService;

	/**
	 * 入库订单 查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listInWarehouseOrder", method = RequestMethod.GET)
	public ModelAndView listInWarehouseOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.addObject("todayStart", DateUtil.getTodayStart());
		view.setViewName("warehouse/storage/listInWarehouseOrder");
		return view;
	}

	/**
	 * 获取入库订单
	 * 
	 * @param request
	 * @param response
	 * @param userLoginName
	 *            客户登录名,仅当根据跟踪号无法找到订单时,要求输入
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getInWarehouseOrderData")
	public String getInWarehouseOrderData(HttpServletRequest request, String sortorder, String sortname, int page, int pagesize,
			String userLoginName, Long warehouseId, String trackingNo, String createdTimeStart, String createdTimeEnd) throws IOException {
		if (StringUtil.isNotNull(createdTimeStart) && createdTimeStart.contains(",")) {
			createdTimeStart = createdTimeStart.substring(createdTimeStart.lastIndexOf(",") + 1, createdTimeStart.length());
		}

		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;

		InWarehouseOrder param = new InWarehouseOrder();
		param.setPackageTrackingNo(trackingNo);
		if (StringUtil.isNotNull(userLoginName)) {
			Long userIdOfCustomer = userService.findUserIdByLoginName(userLoginName);
			param.setUserIdOfCustomer(userIdOfCustomer);
		}
		param.setWarehouseId(warehouseId);
		// 更多参数
		Map<String, String> moreParam = new HashMap<String, String>();
		moreParam.put("createdTimeStart", createdTimeStart);
		moreParam.put("createdTimeEnd", createdTimeEnd);

		pagination = storageService.getInWarehouseOrderData(param, moreParam, pagination);
		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);
		return GsonUtil.toJson(map);
	}

	/**
	 * 出库订单 查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listOutWarehouseOrder", method = RequestMethod.GET)
	public ModelAndView listOutWarehouseOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.addObject("todayStart", DateUtil.getTodayStart());
		view.setViewName("warehouse/storage/listOutWarehouseOrder");
		return view;
	}

	/**
	 * 获取出库订单
	 * 
	 * @param request
	 * @param response
	 * @param userLoginName
	 *            客户登录名,仅当根据跟踪号无法找到订单时,要求输入
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getOutWarehouseOrderData", method = RequestMethod.POST)
	public String getOutWarehouseOrderData(HttpServletRequest request, String sortorder, String sortname, int page, int pagesize,
			String userLoginName, Long warehouseId, String customerReferenceNo, String createdTimeStart, String createdTimeEnd)
			throws IOException {
		if (StringUtil.isNotNull(createdTimeStart) && createdTimeStart.contains(",")) {
			createdTimeStart = createdTimeStart.substring(createdTimeStart.lastIndexOf(",") + 1, createdTimeStart.length());
		}
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;

		OutWarehouseOrder param = new OutWarehouseOrder();
		// 客户单号
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

		pagination = storageService.getOutWarehouseOrderData(param, moreParam, pagination);
		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);
		return GsonUtil.toJson(map);
	}

	/**
	 * 待审核出库订单 查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listWaitCheckOutWarehouseOrder", method = RequestMethod.GET)
	public ModelAndView listWaitCheckOutWarehouseOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.setViewName("warehouse/storage/listWaitCheckOutWarehouseOrder");
		return view;
	}

	/**
	 * 获取待审核出库订单
	 * 
	 * @param request
	 * @param response
	 * @param userLoginName
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getWaitCheckOutWarehouseOrderData", method = RequestMethod.POST)
	public String getWaitCheckOutWarehouseOrderData(HttpServletRequest request, String sortorder, String sortname, int page, int pagesize,
			String userLoginName, Long warehouseId, String customerReferenceNo, String createdTimeStart, String createdTimeEnd)
			throws IOException {
		if (StringUtil.isNotNull(createdTimeStart) && createdTimeStart.contains(",")) {
			createdTimeStart = createdTimeStart.substring(createdTimeStart.lastIndexOf(",") + 1, createdTimeStart.length());
		}
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;

		OutWarehouseOrder param = new OutWarehouseOrder();
		// 客户单号
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

		pagination = storageService.getOutWarehouseOrderData(param, moreParam, pagination);
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
		view.setViewName("warehouse/storage/inWarehouse");
		return view;
	}

	/**
	 * 检查 跟踪号和客户帐号是否能找到唯一的入库订单
	 * 
	 * @param request
	 * @param trackingNo
	 * @param userLoginName
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/checkFindInWarehouseOrder")
	public String checkFindInWarehouseOrder(HttpServletRequest request, String trackingNo, String userLoginName) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.STATUS, Constant.FAIL);
		InWarehouseOrder param = new InWarehouseOrder();
		param.setPackageTrackingNo(trackingNo);
		List<InWarehouseOrder> inWarehouseOrderList = storageService.findInWarehouseOrder(param, null, null);
		if (inWarehouseOrderList.size() < 1) {
			map.put(Constant.STATUS, "-1");
			map.put(Constant.MESSAGE, "找不到订单,请输入客户帐号.");
			return GsonUtil.toJson(map);
		}
		// 找到预报信息,向前台返回用户名
		List<User> userList = storageService.findUserByInWarehouseOrder(inWarehouseOrderList);
		map.put("userList", userList);
		if (inWarehouseOrderList.size() > 1) {
			// 已经控制 同一个客户下的跟踪号不可能重复,所以多个订单表示有多个客户帐号
			map.put(Constant.MESSAGE, "找到超过一个订单,请选择客户帐号.");
			map.put(Constant.STATUS, "2");
			return GsonUtil.toJson(map);
		}
		// 只找到一个订单,对应一个客户
		map.put("user", userList.get(0));
		map.put(Constant.STATUS, Constant.SUCCESS);
		return GsonUtil.toJson(map);
	}

	/**
	 * 保存入库主单
	 * 
	 * @param request
	 * @param trackingNo
	 * @param userLoginName
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/saveInWarehouseRecord", method = RequestMethod.POST)
	public String saveInWarehouseRecord(HttpServletRequest request, String trackingNo, String userLoginName, String isUnKnowCustomer,
			String remark) throws IOException {
		logger.info("trackingNo:" + trackingNo + " userLoginName:" + userLoginName + " isUnKnowCustomer:" + isUnKnowCustomer + "  remark:"
				+ remark);
		// 操作员
		Long userIdOfOperator = (Long) request.getSession().getAttribute(SessionConstant.USER_ID);
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		// 校验和保存
		Map<String, String> serviceResult = storageService.saveInWarehouseRecord(trackingNo, userLoginName, isUnKnowCustomer, remark,
				userIdOfOperator);
		// 成功,返回id
		map.put("id", serviceResult.get("id"));
		// 失败
		if (!StringUtil.isEqual(serviceResult.get(Constant.STATUS), Constant.SUCCESS)) {
			map.put(Constant.MESSAGE, serviceResult.get(Constant.MESSAGE));
			return GsonUtil.toJson(map);
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		return GsonUtil.toJson(map);
	}

	 
	/**
	 * 保存入库明细
	 * @param request
	 * @param itemSku 物品sku
	 * @param itemQuantity 收货数量
	 * @param itemRemark 备注
	 * @param warehouseId 仓库id
	 * @param shelvesNo 货架
	 * @param seatNo 货位
	 * @param inWarehouseRecordId 所属的入库主单
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/saveInWarehouseRecordItem", method = RequestMethod.POST)
	public String saveInWarehouseRecordItem(HttpServletRequest request, String itemSku, Integer itemQuantity, String itemRemark,
			Long warehouseId, String shelvesNo, String seatNo,Long inWarehouseRecordId) throws IOException {
		logger.info("保存入库明细: itemSku="+itemSku+" itemQuantity="+itemQuantity+" itemRemark="+ itemRemark+" warehouseId="+warehouseId+" shelvesId="+shelvesNo+" seatId="+seatNo+" inWarehouseRecordId="+inWarehouseRecordId);
		// 操作员
		Long userIdOfOperator = (Long) request.getSession().getAttribute(SessionConstant.USER_ID);
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		// 校验和保存
		Map<String, String> serviceResult = storageService.saveInWarehouseRecordItem(itemSku, itemQuantity, itemRemark, warehouseId, shelvesNo, seatNo, inWarehouseRecordId,userIdOfOperator);
		// 失败
		if (!StringUtil.isEqual(serviceResult.get(Constant.STATUS), Constant.SUCCESS)) {
			map.put(Constant.MESSAGE, serviceResult.get(Constant.MESSAGE));
			return GsonUtil.toJson(map);
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		return GsonUtil.toJson(map);
	}

	/**
	 * 获取入库记录明细
	 * 
	 * @param request
	 * @param response
	 * @param userLoginName
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getInWarehouseRecordItemData")
	public String getInWarehouseRecordItemData(HttpServletRequest request, String sortorder, String sortname, int page, int pagesize,
			Long inWarehouseRecordId) throws IOException {
		Map map = new HashMap();
		if (inWarehouseRecordId == null) {
			return GsonUtil.toJson(map);
		}
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;
		// 客户id
		pagination = storageService.getInWarehouseRecordItemData(inWarehouseRecordId, pagination);
		if (pagination != null) {
			map.put("Rows", pagination.rows);
			map.put("Total", pagination.total);
		}
		return GsonUtil.toJson(map);
	}

	/**
	 * 获取入库订单明细(头程运单号下的所有SKU和数量)
	 * 
	 * @param request
	 * @param response
	 * @param userLoginName
	 *            客户登录名,仅当根据跟踪号无法找到订单时,要求输入
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getInWarehouseOrderItem", method = RequestMethod.POST)
	public String getInWarehouseOrderItem(HttpServletRequest request, String sortorder, String sortname, int page, int pagesize,
			String trackingNo, String userLoginName) throws IOException {
		Map map = new HashMap();
		logger.info("trackingNo:" + trackingNo + "  userLoginName:" + userLoginName);
		logger.info("sortorder:" + sortorder + "  sortname:" + sortname + " page:" + page + " pagesize:" + pagesize);
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;
		// 客户id
		Long userIdOfCustomer = userService.findUserIdByLoginName(userLoginName);
		InWarehouseOrder param = new InWarehouseOrder();
		param.setPackageTrackingNo(trackingNo);
		param.setUserIdOfCustomer(userIdOfCustomer);
		// 执行查询(应当只有1个结果, 多个结果,只取第一个结果. 跟踪号重复的情况 ,待处理)
		List<InWarehouseOrder> inWarehouseOrderList = storageService.findInWarehouseOrder(param, null, null);
		if (inWarehouseOrderList.size() < 0) {
			return GsonUtil.toJson(map);
		}
		InWarehouseOrder order = inWarehouseOrderList.get(0);
		pagination = storageService.getInWarehouseItemData(order.getId(), pagination);
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);

		return GsonUtil.toJson(map);
	}

	/**
	 * 入库主单 查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listInWarehouseRecord", method = RequestMethod.GET)
	public ModelAndView listInWarehouseRecord(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.setViewName("warehouse/storage/listInWarehouseRecord");
		return view;
	}

}
