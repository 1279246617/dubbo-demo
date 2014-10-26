package com.coe.wms.controller.warehouse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.GsonUtil;

@Controller("shelves")
@RequestMapping("/warehouse/shelves")
public class Shelves {
	private static final Logger logger = Logger.getLogger(Shelves.class);

	@Resource(name = "storageService")
	private IStorageService storageService;

	@Resource(name = "userService")
	private IUserService userService;

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
}
