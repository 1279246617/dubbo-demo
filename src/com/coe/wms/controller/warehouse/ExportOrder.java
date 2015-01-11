package com.coe.wms.controller.warehouse;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.coe.wms.controller.Application;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus;
import com.coe.wms.service.exportorder.IExportService;
import com.coe.wms.service.importorder.IImportService;
import com.coe.wms.service.storage.IOutWarehouseOrderService;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.Config;
import com.coe.wms.util.SessionConstant;
import com.coe.wms.util.StringUtil;

@Controller("exportOrder")
@RequestMapping("/warehouse/exportOrder")
public class ExportOrder {

	private static final Logger logger = Logger.getLogger(ExportOrder.class);

	@Resource(name = "storageService")
	private IStorageService storageService;

	@Resource(name = "outWarehouseOrderService")
	private IOutWarehouseOrderService outWarehouseOrderService;

	@Resource(name = "userService")
	private IUserService userService;

	@Resource(name = "importService")
	private IImportService importService;

	@Resource(name = "exportService")
	private IExportService exportService;

	@Resource(name = "config")
	private Config config;

	/**
	 * 导出订单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportOutWarehouseOrder", method = RequestMethod.GET)
	public ModelAndView exportOutWarehouseOrder(HttpServletRequest request, HttpServletResponse response, String status) throws IOException {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		view.addObject("userId", userId);
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		List<OutWarehouseOrderStatus> outWarehouseOrderStatusList = new ArrayList<OutWarehouseOrderStatus>();
		if (StringUtil.isNotNull(status)) {
			OutWarehouseOrderStatus outWarehouseOrderStatus = outWarehouseOrderService.getOutWarehouseOrderStatusByCode(status);
			outWarehouseOrderStatusList.add(outWarehouseOrderStatus);
		} else {
			outWarehouseOrderStatusList = outWarehouseOrderService.findAllOutWarehouseOrderStatus();
		}
		view.addObject("outWarehouseOrderStatusList", outWarehouseOrderStatusList);
		view.setViewName("warehouse/exportorder/exportOutWarehouseOrder");
		return view;
	}

	/**
	 * 导出订单
	 * 
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "executeExportOrder", method = RequestMethod.GET)
	public void executeExportOrder(HttpServletResponse response, Long warehouseId, String status, String userLoginName, String createdTimeStart, String createdTimeEnd, String isQuantityOnly1) throws IOException {
		OutputStream os = response.getOutputStream();
		try {
			String filePathAndName = exportService.executeExportOutWarehouseOrder(warehouseId, status, userLoginName, createdTimeStart, createdTimeEnd,isQuantityOnly1);
			int a = filePathAndName.lastIndexOf("\\");
			int b = filePathAndName.lastIndexOf("/");
			String fileName = filePathAndName.substring((a > b ? a : b) + 1, filePathAndName.length());
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
			response.setContentType("application/octet-stream; charset=utf-8");
			File file = new File(filePathAndName);
			os.write(FileUtils.readFileToByteArray(file));
			os.flush();
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

}
