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
import com.coe.wms.dao.warehouse.impl.ShelfDaoImpl;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Seat;
import com.coe.wms.model.warehouse.Shelf;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.OnShelf;
import com.coe.wms.model.warehouse.storage.record.OutShelf;
import com.coe.wms.service.inventory.IShelfService;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.GsonUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.SessionConstant;
import com.coe.wms.util.StringUtil;

@Controller("shelves")
@RequestMapping("/warehouse/shelves")
public class Shelves {
	private static final Logger logger = Logger.getLogger(Shelves.class);

	@Resource(name = "storageService")
	private IStorageService storageService;

	@Resource(name = "shelfService")
	private IShelfService shelfService;

	@Resource(name = "userService")
	private IUserService userService;

	/**
	 * 上架界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/onShelves", method = RequestMethod.GET)
	public ModelAndView onShelves(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView view = new ModelAndView();
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		User user = userService.getUserById(userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.setViewName("warehouse/shelves/onShelves");
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		return view;
	}

	/**
	 * 上架 查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listOnShelves", method = RequestMethod.GET)
	public ModelAndView listOnShelves(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.setViewName("warehouse/shelves/listOnShelves");
		return view;
	}

	/**
	 * 下架 查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listOutShelves", method = RequestMethod.GET)
	public ModelAndView listOutShelves(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.setViewName("warehouse/shelves/listOutShelves");
		return view;
	}

	/**
	 * 获取下架数据
	 * 
	 * @param request
	 * @param response
	 * @param userLoginName
	 *            客户登录名,仅当根据跟踪号无法找到订单时,要求输入
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getOutShelvesData")
	public String getOutShelvesData(HttpServletRequest request, String sortorder, String sortname, int page, int pagesize, String userLoginName, Long warehouseId, String customerReferenceNo, String batchNo, String createdTimeStart,
			String createdTimeEnd) throws IOException {
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;

		OutShelf param = new OutShelf();
		param.setWarehouseId(warehouseId);
		param.setCustomerReferenceNo(customerReferenceNo);
		if (StringUtil.isNotNull(userLoginName)) {
			Long userIdOfCustomer = userService.findUserIdByLoginName(userLoginName);
			param.setUserIdOfCustomer(userIdOfCustomer);
		}
		param.setWarehouseId(warehouseId);
		param.setBatchNo(batchNo);
		// 更多参数
		Map<String, String> moreParam = new HashMap<String, String>();
		moreParam.put("createdTimeStart", createdTimeStart);
		moreParam.put("createdTimeEnd", createdTimeEnd);
		pagination = shelfService.getOutShelvesData(param, moreParam, pagination);
		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);
		return GsonUtil.toJson(map);
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
	@RequestMapping(value = "/checkFindInWarehouseRecord")
	public String checkFindInWarehouseRecord(HttpServletRequest request, String trackingNo) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.STATUS, Constant.FAIL);
		InWarehouseRecord param = new InWarehouseRecord();
		param.setTrackingNo(trackingNo);
		List<Map<String, String>> mapList = shelfService.checkInWarehouseRecord(param);
		if (mapList.size() < 1) {
			map.put(Constant.STATUS, "-1");
			map.put(Constant.MESSAGE, "该单号无收货记录,请先进行入库订单收货.");
			return GsonUtil.toJson(map);
		}
		map.put("mapList", mapList);
		if (mapList.size() > 1) {
			// 找到多个入库订单,返回跟踪号,承运商,参考号,客户等信息供操作员选择
			map.put(Constant.MESSAGE, "该单号找到超过一个入库收货记录,请选择其中一个.");
			map.put(Constant.STATUS, "2");
			return GsonUtil.toJson(map);
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		return GsonUtil.toJson(map);
	}

	@ResponseBody
	@RequestMapping(value = "/saveOnShelvesItem", method = RequestMethod.POST)
	public String saveOnShelvesItem(HttpServletRequest request, String itemSku, Integer itemQuantity, String seatCode, Long inWarehouseRecordId) throws IOException {
		// 操作员
		Long userIdOfOperator = (Long) request.getSession().getAttribute(SessionConstant.USER_ID);
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		// 校验和保存
		Map<String, String> serviceResult = shelfService.saveOnShelvesItem(itemSku, itemQuantity, seatCode, inWarehouseRecordId, userIdOfOperator);
		// 失败
		if (!StringUtil.isEqual(serviceResult.get(Constant.STATUS), Constant.SUCCESS)) {
			map.put(Constant.MESSAGE, serviceResult.get(Constant.MESSAGE));
			return GsonUtil.toJson(map);
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		return GsonUtil.toJson(map);
	}

	/**
	 * 获取上架数据
	 * 
	 * @param request
	 * @param response
	 * @param userLoginName
	 *            客户登录名,仅当根据跟踪号无法找到订单时,要求输入
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getOnShelvesData")
	public String getOnShelvesData(HttpServletRequest request, String sortorder, String sortname, int page, int pagesize, String userLoginName, Long warehouseId, String trackingNo, String batchNo, String createdTimeStart, String createdTimeEnd)
			throws IOException {
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;

		OnShelf param = new OnShelf();
		param.setWarehouseId(warehouseId);
		param.setTrackingNo(trackingNo);
		if (StringUtil.isNotNull(userLoginName)) {
			Long userIdOfCustomer = userService.findUserIdByLoginName(userLoginName);
			param.setUserIdOfCustomer(userIdOfCustomer);
		}
		param.setWarehouseId(warehouseId);
		param.setBatchNo(batchNo);
		// 更多参数
		Map<String, String> moreParam = new HashMap<String, String>();
		moreParam.put("createdTimeStart", createdTimeStart);
		moreParam.put("createdTimeEnd", createdTimeEnd);

		pagination = shelfService.getOnShelvesData(param, moreParam, pagination);
		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);
		return GsonUtil.toJson(map);
	}

	/**
	 * 下架界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/outShelves", method = RequestMethod.GET)
	public ModelAndView outShelves(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView view = new ModelAndView();
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.setViewName("warehouse/shelves/outShelves");
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/submitOutShelfItems")
	public String submitOutShelfItems(HttpServletRequest request, String customerReferenceNo, String outShelfItems) throws IOException {
		logger.info("提交下架:customerReferenceNo:" + customerReferenceNo + " outShelfItems:" + outShelfItems);
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		Map<String, String> map = shelfService.submitOutShelfItems(customerReferenceNo, outShelfItems, userId);
		return GsonUtil.toJson(map);
	}

	/**
	 * 
	 * 找到唯一的出库订单,并且是待下架的
	 * 
	 * @param request
	 * @param trackingNo
	 * @param userLoginName
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/checkFindOutWarehouseOrder")
	public String checkFindOutWarehouseOrder(HttpServletRequest request, String customerReferenceNo) throws IOException {
		Map<String, String> map = shelfService.checkOutWarehouseOrderByCustomerReferenceNo(customerReferenceNo);
		return GsonUtil.toJson(map);
	}

	/**
	 * 货位 查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listSeat", method = RequestMethod.GET)
	public ModelAndView listSeat(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.setViewName("warehouse/shelves/listSeat");
		return view;
	}

	/**
	 * 获取货位
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getSeatData")
	public String getSeatData(HttpServletRequest request, String sortorder, String sortname, int page, int pagesize, String seatCode, String shelfCode, Long warehouseId, Long shelfId) throws IOException {
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;
		Seat param = new Seat();
		// 如果 shelfCodeId 不为空,表示是点击货架表格的显示货位按钮, 只显示改货架的货位
		if (shelfId != null) {
			Shelf shelf = shelfService.getShelfById(shelfId);
			if (shelf != null) {
				param.setShelfCode(shelf.getShelfCode());
			}
		} else {
			param.setWarehouseId(warehouseId);
			param.setSeatCode(seatCode);
			param.setShelfCode(shelfCode);
		}
		// 更多参数
		Map<String, String> moreParam = new HashMap<String, String>();
		pagination = shelfService.getSeatData(param, moreParam, pagination);
		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);
		return GsonUtil.toJson(map);
	}

	/**
	 * 获取货架
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getShelfData")
	public String getShelfData(HttpServletRequest request, String sortorder, String sortname, int page, int pagesize, String shelfType, String shelfCode, Long warehouseId) throws IOException {
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;
		Shelf param = new Shelf();
		param.setWarehouseId(warehouseId);
		param.setShelfType(shelfType);
		param.setShelfCode(shelfCode);
		// 更多参数
		Map<String, String> moreParam = new HashMap<String, String>();
		pagination = shelfService.getShelfData(param, moreParam, pagination);
		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);
		return GsonUtil.toJson(map);
	}

	/**
	 * 添加货架
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/addShelf", method = RequestMethod.GET)
	public ModelAndView addShelf(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject("userId", userId);
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.setViewName("warehouse/shelves/addShelf");
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/saveAddShelf", method = RequestMethod.POST)
	public String saveAddShelf(HttpServletRequest request, Long warehouseId, String shelfType, String shelfTypeName, Integer start, Integer end, Integer rows, Integer cols, Integer shelfNoStart, Integer shelfNoEnd, String remark) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		Map<String, String> map = shelfService.saveAddShelf(warehouseId, shelfType, shelfTypeName, start, end, rows, cols, shelfNoStart, shelfNoEnd, remark);
		return GsonUtil.toJson(map);
	}

	@ResponseBody
	@RequestMapping(value = "/getSeatItemInventory", method = RequestMethod.POST)
	public String getSeatItemInventory(Long seatId) {
		List<Map<String, String>> mapList = shelfService.getSeatItemInventory(seatId);
		return GsonUtil.toJson(mapList);
	}
}
