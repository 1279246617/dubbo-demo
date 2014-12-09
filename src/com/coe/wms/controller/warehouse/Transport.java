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
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus;
import com.coe.wms.model.warehouse.transport.BigPackageStatus;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.service.transport.ITransportService;
import com.coe.wms.service.user.IUserService;
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
		List<BigPackageStatus> bigPackageStatusList = transportService.findAllBigPackageStatus();
		view.addObject("bigPackageStatusList", bigPackageStatusList);
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
			String status, String nos, String noType) throws IOException {
		HttpSession session = request.getSession();
		// 当前操作员
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;

		OutWarehouseOrder param = new OutWarehouseOrder();
		param.setStatus(status);
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
		pagination = storageService.getOutWarehouseOrderData(param, moreParam, pagination);
		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);
		return GsonUtil.toJson(map);
	}

}
