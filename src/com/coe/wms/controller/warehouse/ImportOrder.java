package com.coe.wms.controller.warehouse;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.coe.wms.controller.Application;
import com.coe.wms.model.user.User;
import com.coe.wms.service.importorder.IImportService;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.GsonUtil;
import com.coe.wms.util.SessionConstant;

@Controller("importorder")
@RequestMapping("/warehouse/importorder")
public class ImportOrder {

	private static final Logger logger = Logger.getLogger(ImportOrder.class);

	@Resource(name = "storageService")
	private IStorageService storageService;

	@Resource(name = "userService")
	private IUserService userService;

	@Resource(name = "importService")
	private IImportService importService;

	/**
	 * 导入入库订单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/importInWarehouseOrder", method = RequestMethod.GET)
	public ModelAndView importInWarehouseOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		view.addObject("userId", userId);
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.setViewName("warehouse/importorder/importInWarehouseOrder");
		return view;
	}

	/**
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/executeImportInWarehouseOrder")
	public String executeImportInWarehouseOrder(HttpServletRequest request, String userLoginName, Long warehouseId) throws IOException {
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = mRequest.getFileMap();
		Map<String, Object> map = importService.executeImportInWarehouseOrder(fileMap, userLoginName, warehouseId);
		return GsonUtil.toJson(map);
	}

	/**
	 * 导入出库订单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/importOutWarehouseOrder", method = RequestMethod.GET)
	public ModelAndView importOutWarehouseOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		view.addObject("userId", userId);
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.setViewName("warehouse/importorder/inportOutWarehouseOrder");
		return view;
	}

	/**
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/executeImportOutWarehouseOrder")
	public String executeImportOutWarehouseOrder(HttpServletRequest reques) throws IOException {

		return null;
	}
}
