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
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.service.api.IStorageService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.GsonUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.SessionConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
	public String checkFindInWarehouseOrder(HttpServletRequest request, String trackingNo, String userLoginName)
			throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		InWarehouseOrder param = new InWarehouseOrder();
		param.setPackageTrackingNo(trackingNo);
		List<InWarehouseOrder> inWarehouseOrderList = storageService.findInWarehouseOrder(param, null, null);
		if (inWarehouseOrderList.size() < 1) {
			map.put(Constant.MESSAGE, "找不到订单,请输入客户帐号,或者标记为无主件.");
		} else if (inWarehouseOrderList.size() > 1) {
			map.put(Constant.MESSAGE, "找到超过一个订单,请输入客户帐号.");
		} else {
			map.put(Constant.STATUS, Constant.SUCCESS);
		}
		return GsonUtil.toJson(map);
	}

	/**
	 * 获取某个大包号(头程运单号下的所有SKU和数量)
	 * 
	 * @param request
	 * @param response
	 * @param userLoginName
	 *            客户登录名,仅当根据跟踪号无法找到订单时,要求输入
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getInWarehouseOrder", method = RequestMethod.POST)
	public String getInWarehouseOrder(HttpServletRequest request, String sortorder, String sortname, int page,
			int pagesize, String trackingNo, String userLoginName) throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		Pagination pagination = new Pagination();

		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);

		return GsonUtil.toJson(map);
	}
}
