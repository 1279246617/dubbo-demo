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
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.GsonUtil;
import com.coe.wms.util.SessionConstant;
import com.coe.wms.util.StringUtil;

@Controller("shelves")
@RequestMapping("/warehouse/shelves")
public class Shelves {
	private static final Logger logger = Logger.getLogger(Shelves.class);

	@Resource(name = "storageService")
	private IStorageService storageService;

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
		view.setViewName("warehouse/storage/onShelves");
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
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
	@RequestMapping(value = "/checkFindInWarehouseRecord")
	public String checkFindInWarehouseRecord(HttpServletRequest request, String trackingNo) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.STATUS, Constant.FAIL);
		InWarehouseRecord param = new InWarehouseRecord();
		param.setTrackingNo(trackingNo);
		List<Map<String, String>> mapList = storageService.checkInWarehouseRecord(param);
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
	public String saveOnShelvesItem(HttpServletRequest request, String itemSku, Integer itemQuantity, String seatCode,
			Long inWarehouseRecordId) throws IOException {
		// 操作员
		Long userIdOfOperator = (Long) request.getSession().getAttribute(SessionConstant.USER_ID);
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		// 校验和保存
		Map<String, String> serviceResult = storageService.saveOnShelvesItem(itemSku, itemQuantity, seatCode, inWarehouseRecordId,
				userIdOfOperator);
		// 失败
		if (!StringUtil.isEqual(serviceResult.get(Constant.STATUS), Constant.SUCCESS)) {
			map.put(Constant.MESSAGE, serviceResult.get(Constant.MESSAGE));
			return GsonUtil.toJson(map);
		}
		map.put(Constant.STATUS, Constant.SUCCESS);
		return GsonUtil.toJson(map);
	}
}
