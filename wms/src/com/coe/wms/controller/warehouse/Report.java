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

@Controller("report")
@RequestMapping("/warehouse/report")
public class Report {

	private static final Logger logger = Logger.getLogger(Report.class);

	@Resource(name = "storageService")
	private IStorageService storageService;

	@Resource(name = "itemInventoryService")
	private IItemInventoryService itemInventoryService;

	@Resource(name = "userService")
	private IUserService userService;

	/**
	 * 下载报表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listReportForDownload", method = RequestMethod.GET)
	public ModelAndView listReportForDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView view = new ModelAndView();
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		User user = userService.getUserById(userId);
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.setViewName("warehouse/report/listReportForDownload");
		return view;
	}

}
