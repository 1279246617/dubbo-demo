package com.coe.wms.controller.warehouse;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.coe.wms.controller.Application;
import com.coe.wms.model.user.User;
import com.coe.wms.service.importorder.IImportService;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.Config;
import com.coe.wms.util.Constant;
import com.coe.wms.util.GsonUtil;
import com.coe.wms.util.SessionConstant;
import com.coe.wms.util.StringUtil;

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

	@Resource(name = "config")
	private Config config;

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
	 * 下载订单excel模版文件
	 * 
	 * 约定templateId = 1 表示入库订单模版 templateId =2 表示出库订单模版
	 * 
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "downloadTemplate", method = RequestMethod.GET)
	public void downloadTemplate(HttpServletResponse response, Integer templateId) throws IOException {
		OutputStream os = response.getOutputStream();
		try {
			if (templateId == null) {
				return;
			}
			// 模版文件路径
			String filePathAndName = config.getTemplateFilePath();
			if (templateId == 1) {
				filePathAndName += "/" + "in-warehouse-template.xlsx";
			} else if (templateId == 2) {
				filePathAndName += "/" + "out-warehouse-template.xlsx";
			}
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

	/**
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/executeImportInWarehouseOrder")
	public String executeImportInWarehouseOrder(HttpServletRequest request, String userLoginName, Long warehouseId) throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(userLoginName)) {
			resultMap.put(Constant.MESSAGE, "请输入客户帐号");
			return GsonUtil.toJson(resultMap);
		}
		if (warehouseId == null) {
			resultMap.put(Constant.MESSAGE, "请选择到货仓库");
			return GsonUtil.toJson(resultMap);
		}
		Long userIdOfCustomer = userService.findUserIdByLoginName(userLoginName);
		if (userIdOfCustomer == null || userIdOfCustomer == 0) {
			resultMap.put(Constant.MESSAGE, "请输入有效的客户帐号");
			return GsonUtil.toJson(resultMap);
		}
		HttpSession session = request.getSession();
		Long userIdOfOperator = (Long) session.getAttribute(SessionConstant.USER_ID);
		String uploadDir = config.getRuntimeFilePath() + "/order/import";
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = mRequest.getFileMap();
		// 保存文件
		Map<String, String> saveFileResultMap = importService.saveMultipartFile(fileMap, userIdOfCustomer, warehouseId, uploadDir);
		if (StringUtil.isEqual(saveFileResultMap.get(Constant.STATUS), Constant.FAIL)) {
			return GsonUtil.toJson(saveFileResultMap);
		}
		// 得到文件路径和名
		String filePathAndName = saveFileResultMap.get("filePathAndName");
		// 检测格式
		Map<String, Object> validateFileResultMap = importService.validateImportInWarehouseOrder(filePathAndName);
		if (StringUtil.isEqual((String) validateFileResultMap.get(Constant.STATUS), Constant.FAIL)) {
			return GsonUtil.toJson(validateFileResultMap);
		}
		List<Map<String, String>> mapList = (List<Map<String, String>>) validateFileResultMap.get("rows");
		// 执行导入
		resultMap = importService.executeImportInWarehouseOrder(mapList, userIdOfCustomer, warehouseId, userIdOfOperator);
		return GsonUtil.toJson(resultMap);
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
