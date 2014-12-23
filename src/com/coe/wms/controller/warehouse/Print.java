package com.coe.wms.controller.warehouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.servlet.ModelAndView;

import com.coe.wms.controller.Application;
import com.coe.wms.model.product.Product;
import com.coe.wms.model.warehouse.TrackingNo;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.service.print.IPrintService;
import com.coe.wms.service.product.IProductService;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.StringUtil;

@Controller("print")
@RequestMapping("/warehouse/print")
public class Print {
	private static final Logger logger = Logger.getLogger(Print.class);

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
	 * 打印捡货单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/printPackageList", method = RequestMethod.GET)
	public ModelAndView printPackageList(HttpServletRequest request, HttpServletResponse response, String orderIds) throws IOException {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		if (StringUtil.isNull(orderIds)) {
			return view;
		}
		// 返回页面的list,装map 每个map 是每个订单的数据
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		String orderIdArray[] = orderIds.split(",");
		for (int i = 0; i < orderIdArray.length; i++) {
			if (StringUtil.isNull(orderIdArray[i])) {
				continue;
			}
			Long orderId = Long.valueOf(orderIdArray[i]);
			Map<String, Object> map = printService.getPrintPackageListData(orderId);
			if (map != null) {
				mapList.add(map);
			}
		}
		view.addObject("mapList", mapList);
		view.addObject("timeNow", DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_dd));
		view.setViewName("warehouse/print/printPackageList");
		return view;
	}

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
	@RequestMapping(value = "/printShipLabel", method = RequestMethod.GET)
	public ModelAndView printShipLabel(HttpServletRequest request, HttpServletResponse response, String orderIds) throws IOException {
		HttpSession session = request.getSession();
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		if (StringUtil.isNull(orderIds)) {
			return view;
		}
		// 返回页面的list,装map 每个map 是每个订单的数据
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		String orderIdArray[] = orderIds.split(",");
		for (int i = 0; i < orderIdArray.length; i++) {
			if (StringUtil.isNull(orderIdArray[i])) {
				continue;
			}
			Long orderId = Long.valueOf(orderIdArray[i]);
			Map<String, Object> map = printService.getPrintShipLabelData(orderId);
			if (map != null) {
				mapList.add(map);
			}
		}
		view.addObject("mapList", mapList);
		view.addObject("timeNow", DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss));

		// 根据出库渠判断打印顺丰运单还是ETK运单判断
		view.setViewName("warehouse/print/printSfLabel");
		// view.setViewName("warehouse/print/printEtkLabel");
		return view;
	}

	/**
	 * 
	 * 打印货位号
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/printSeatCode", method = RequestMethod.GET)
	public ModelAndView printSeatCode(HttpServletRequest request, HttpServletResponse response, String seatIds) throws IOException {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		if (StringUtil.isNull(seatIds)) {
			return view;
		}
		// 返回页面的list,装map 每个map 是每个订单的数据
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		String seatIdArray[] = seatIds.split(",");
		for (int i = 0; i < seatIdArray.length; i++) {
			if (StringUtil.isNull(seatIdArray[i])) {
				continue;
			}
			Long id = Long.valueOf(seatIdArray[i]);
			Map<String, Object> map = printService.getPrintSeatCodeLabelData(id);
			if (map != null) {
				mapList.add(map);
			}
		}
		view.addObject("mapList", mapList);
		view.addObject("timeNow", DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss));
		view.setViewName("warehouse/print/printSeatCode");
		return view;
	}

	/**
	 * 
	 * 打印出货交接单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/printOutWarehouseEIR", method = RequestMethod.GET)
	public ModelAndView printOutWarehouseEIR(HttpServletRequest request, HttpServletResponse response, Long coeTrackingNoId) throws IOException {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		List<Map<String, String>> mapList = printService.getOutWarehouseShippings(coeTrackingNoId);
		int total = 0;
		double totalWeight = 0;
		if (mapList != null && mapList.size() > 0) {
			Long warehouseId = Long.valueOf(mapList.get(0).get("warehouseId"));
			Warehouse warehouse = storageService.getWarehouseById(warehouseId);
			view.addObject("warehouse", warehouse);
			for (Map<String, String> map : mapList) {
				totalWeight += Double.valueOf(map.get("outWarehouseWeight"));
				total++;
			}
		}
		TrackingNo trackingNo = storageService.getTrackingNoById(coeTrackingNoId);
		// 跟踪号 (COE交接单号)
		String coeTrackingNo = trackingNo.getTrackingNo();
		view.addObject("totalWeight", NumberUtil.getNumPrecision(totalWeight, 2));
		view.addObject("total", total);
		view.addObject("mapList", mapList);
		view.addObject("coeTrackingNo", coeTrackingNo);
		view.addObject("timeNow", DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss));
		view.setViewName("warehouse/print/printOutWarehouseEIR");
		return view;
	}

	/**
	 * 
	 * 打印出库COE标签
	 * 
	 * 根据出库渠道 判断打印顺丰运单还是ETK运单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/printCoeLabel", method = RequestMethod.GET)
	public ModelAndView printCoeLabel(HttpServletRequest request, HttpServletResponse response, Long coeTrackingNoId) throws IOException {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		if (coeTrackingNoId == null) {
			return view;
		}
		// 返回页面的list,装map 每个map 是每个订单的数据
		Map<String, Object> map = printService.getPrintCoeLabelData(coeTrackingNoId);
		view.addObject("map", map);
		view.addObject("timeNow", DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss));
		view.setViewName("warehouse/print/printCoeLabel");
		return view;
	}

	/**
	 * 
	 * 打印SKU条码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/printSkuBarcode", method = RequestMethod.GET)
	public ModelAndView printSkuBarcode(HttpServletRequest request, HttpServletResponse response, String ids, String sku, Integer quantity) throws IOException {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<String> barcodes = new ArrayList<String>();
		if (StringUtil.isNotNull(ids)) {
			String idArray[] = ids.split(",");
			for (int i = 0; i < idArray.length; i++) {
				if (StringUtil.isNull(idArray[i])) {
					continue;
				}
				Long id = Long.valueOf(idArray[i]);
				// 根据商品id,找到sku
				Product product = productService.getProductById(id);
				if (product.getBarcode() != null) {
					barcodes.add(product.getBarcode());
				}
			}
		}
		if (StringUtil.isNotNull(sku)) {
			barcodes.add(sku);
		}
		if (barcodes.size() == 0) {
			return view;
		}
		for (String sku2 : barcodes) {
			Map<String, Object> map = printService.getPrintSkuBarcodeData(sku2);
			if (map != null) {
				for (int i = 0; i < quantity; i++) {// 打印份数
					mapList.add(map);
				}
			}
		}
		view.addObject("mapList", mapList);
		view.addObject("timeNow", DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss));
		view.setViewName("warehouse/print/printSkuBarcode");
		return view;
	}

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
	@RequestMapping(value = "/printTransportShipLabel", method = RequestMethod.GET)
	public ModelAndView printTransportShipLabel(HttpServletRequest request, HttpServletResponse response, String bigPackageIds) throws IOException {
		HttpSession session = request.getSession();
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		if (StringUtil.isNull(bigPackageIds)) {
			return view;
		}
		// 返回页面的list,装map 每个map 是每个订单的数据
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		String orderIdArray[] = bigPackageIds.split(",");
		for (int i = 0; i < orderIdArray.length; i++) {
			if (StringUtil.isNull(orderIdArray[i])) {
				continue;
			}
			Long bigPackageId = Long.valueOf(orderIdArray[i]);
			Map<String, Object> map = printService.printTransportShipLabel(bigPackageId);
			if (map != null) {
				mapList.add(map);
			}
		}
		view.addObject("mapList", mapList);
		view.addObject("timeNow", DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss));

		// 根据出库渠判断打印顺丰运单还是ETK运单判断
		view.setViewName("warehouse/print/printTransportShipSfLabel");
		// view.setViewName("warehouse/print/printEtkLabel");
		return view;
	}

	/**
	 * 
	 * 打印转运订单捡货单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/printTransportPackageList", method = RequestMethod.GET)
	public ModelAndView printTransportPackageList(HttpServletRequest request, HttpServletResponse response, String bigPackageIds) throws IOException {
		HttpSession session = request.getSession();
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		if (StringUtil.isNull(bigPackageIds)) {
			return view;
		}
		// 返回页面的list,装map 每个map 是每个订单的数据
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		String orderIdArray[] = bigPackageIds.split(",");
		for (int i = 0; i < orderIdArray.length; i++) {
			if (StringUtil.isNull(orderIdArray[i])) {
				continue;
			}
			Long orderId = Long.valueOf(orderIdArray[i]);
			Map<String, Object> map = printService.getPrintTransportPackageListData(orderId);
			if (map != null) {
				mapList.add(map);
			}
		}
		view.addObject("mapList", mapList);
		view.addObject("timeNow", DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss));

		// 根据出库渠判断打印顺丰运单还是ETK运单判断
		view.setViewName("warehouse/print/printTransportPackageList");
		// view.setViewName("warehouse/print/printEtkLabel");
		return view;
	}

	/**
	 * 
	 * 打印出货交接单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/printTransportEIR", method = RequestMethod.GET)
	public ModelAndView printTransportEIR(HttpServletRequest request, HttpServletResponse response, Long coeTrackingNoId) throws IOException {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		List<Map<String, String>> mapList = printService.getOutWarehouseShippings(coeTrackingNoId);
		int total = 0;
		double totalWeight = 0;
		if (mapList != null && mapList.size() > 0) {
			Long warehouseId = Long.valueOf(mapList.get(0).get("warehouseId"));
			Warehouse warehouse = storageService.getWarehouseById(warehouseId);
			view.addObject("warehouse", warehouse);
			for (Map<String, String> map : mapList) {
				totalWeight += Double.valueOf(map.get("outWarehouseWeight"));
				total++;
			}
		}
		TrackingNo trackingNo = storageService.getTrackingNoById(coeTrackingNoId);
		// 跟踪号 (COE交接单号)
		String coeTrackingNo = trackingNo.getTrackingNo();
		view.addObject("totalWeight", NumberUtil.getNumPrecision(totalWeight, 2));
		view.addObject("total", total);
		view.addObject("mapList", mapList);
		view.addObject("coeTrackingNo", coeTrackingNo);
		view.addObject("timeNow", DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss));
		view.setViewName("warehouse/print/printTransportEIR");
		return view;
	}

	/**
	 * 
	 * 打印出库COE标签
	 * 
	 * 根据出库渠道 判断打印顺丰运单还是ETK运单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/printTransportCoeLabel", method = RequestMethod.GET)
	public ModelAndView printTransportCoeLabel(HttpServletRequest request, HttpServletResponse response, Long coeTrackingNoId) throws IOException {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		if (coeTrackingNoId == null) {
			return view;
		}
		// 返回页面的list,装map 每个map 是每个订单的数据
		Map<String, Object> map = printService.getPrintCoeLabelData(coeTrackingNoId);
		view.addObject("map", map);
		view.addObject("timeNow", DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_ddHHmmss));
		view.setViewName("warehouse/print/printTransportCoeLabel");
		return view;
	}

}
