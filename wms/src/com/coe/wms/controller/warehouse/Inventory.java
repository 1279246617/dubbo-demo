package com.coe.wms.controller.warehouse;

import java.io.IOException;
import java.util.HashMap;
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
import com.coe.wms.model.warehouse.storage.record.ItemInventory;
import com.coe.wms.model.warehouse.storage.record.ItemShelfInventory;
import com.coe.wms.service.inventory.IItemInventoryService;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.GsonUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.SessionConstant;
import com.coe.wms.util.StringUtil;

@Controller("inventory")
@RequestMapping("/warehouse/inventory")
public class Inventory {

	private static final Logger logger = Logger.getLogger(Inventory.class);

	@Resource(name = "storageService")
	private IStorageService storageService;

	@Resource(name = "itemInventoryService")
	private IItemInventoryService itemInventoryService;

	@Resource(name = "userService")
	private IUserService userService;

	/**
	 * 库存
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listInventory", method = RequestMethod.GET)
	public ModelAndView listInventory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView view = new ModelAndView();
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		User user = userService.getUserById(userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.setViewName("warehouse/inventory/listInventory");
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		return view;
	}

	/**
	 * 库存
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listItemShelfInventory", method = RequestMethod.GET)
	public ModelAndView listItemShelfInventory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView view = new ModelAndView();
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		User user = userService.getUserById(userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.setViewName("warehouse/inventory/listItemShelfInventory");
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		return view;
	}

	/**
	 * 获取库存信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getListInventoryData")
	public String getListInventoryData(HttpServletRequest request, String sortorder, String sortname, int page, int pagesize, String userLoginName, Long warehouseId, String sku, String batchNo, String timeStart, String timeEnd) throws IOException {
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;

		ItemInventory param = new ItemInventory();
		param.setWarehouseId(warehouseId);
		param.setBatchNo(batchNo);
		param.setSku(sku);
		// 客户帐号
		if (StringUtil.isNotNull(userLoginName)) {
			Long userIdOfCustomer = userService.findUserIdByLoginName(userLoginName);
			param.setUserIdOfCustomer(userIdOfCustomer);
		}
		// 更多参数
		Map<String, String> moreParam = new HashMap<String, String>();
		moreParam.put("lastUpdateTimeStart", timeStart);
		moreParam.put("lastUpdateTimeEnd", timeEnd);
		pagination = itemInventoryService.getListInventoryData(param, moreParam, pagination);
		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);
		return GsonUtil.toJson(map);
	}

	/**
	 * 获取库存信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getListItemShelfInventoryData")
	public String getListItemShelfInventoryData(HttpServletRequest request, String sortorder, String sortname, int page, int pagesize, String userLoginName, Long warehouseId, String sku, String seatCode, String timeStart, String timeEnd)
			throws IOException {
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;

		ItemShelfInventory param = new ItemShelfInventory();
		param.setWarehouseId(warehouseId);
		param.setSeatCode(seatCode);

		param.setSku(sku);
		// 客户帐号
		if (StringUtil.isNotNull(userLoginName)) {
			Long userIdOfCustomer = userService.findUserIdByLoginName(userLoginName);
			param.setUserIdOfCustomer(userIdOfCustomer);
		}
		// 更多参数
		Map<String, String> moreParam = new HashMap<String, String>();
		moreParam.put("lastUpdateTimeStart", timeStart);
		moreParam.put("lastUpdateTimeEnd", timeEnd);
		pagination = itemInventoryService.getListItemShelfInventoryData(param, moreParam, pagination);
		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);
		return GsonUtil.toJson(map);
	}

	/**
	 * 库存日结报表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listInventoryReport", method = RequestMethod.GET)
	public ModelAndView listInventoryReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView view = new ModelAndView();
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		User user = userService.getUserById(userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.setViewName("warehouse/inventory/listInventoryReport");
		return view;
	}
	
}
