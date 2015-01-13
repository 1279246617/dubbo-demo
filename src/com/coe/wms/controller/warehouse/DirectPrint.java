package com.coe.wms.controller.warehouse;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.coe.wms.controller.Application;
import com.coe.wms.model.warehouse.shipway.Shipway.ShipwayCode;
import com.coe.wms.service.print.IPrintService;
import com.coe.wms.service.product.IProductService;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.StringUtil;

@Controller("directPrint")
@RequestMapping("/warehouse/directPrint")
public class DirectPrint {

	@Resource(name = "storageService")
	private IStorageService storageService;

	@Resource(name = "userService")
	private IUserService userService;

	@Resource(name = "printService")
	private IPrintService printService;

	@Resource(name = "productService")
	private IProductService productService;

	/**
	 * 
	 * 打印出库发货单
	 * 
	 * 根据出库渠道 判断打印顺丰运单还是ETK运单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/storageShipLabel")
	public ModelAndView storageShipLabel(HttpServletRequest request, HttpServletResponse response, Long orderId) throws IOException {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		Map<String, Object> map = printService.getPrintShipLabelData(orderId);
		view.addObject("map", map);
		String shipwayCode = (String) map.get("shipwayCode");
		if (StringUtil.isEqual(shipwayCode, ShipwayCode.SF)) {
			view.setViewName("warehouse/directprint/storageSfShipLabel");
		} else if (StringUtil.isEqual(shipwayCode, ShipwayCode.ETK)) {
			view.setViewName("warehouse/directprint/storageEtkShipLabel");
		}
		return view;
	}
}
