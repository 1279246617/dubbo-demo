package com.coe.wms.controller.product;

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
import com.coe.wms.model.product.Product;
import com.coe.wms.model.product.ProductType;
import com.coe.wms.model.unit.Currency;
import com.coe.wms.model.user.User;
import com.coe.wms.service.product.IProductService;
import com.coe.wms.service.product.IProductTypeService;
import com.coe.wms.service.storage.IStorageService;
import com.coe.wms.service.unit.IUnitService;
import com.coe.wms.service.user.IUserService;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.GsonUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.SessionConstant;
import com.coe.wms.util.StringUtil;

@Controller("products")
@RequestMapping("/products")
public class Products {

	Logger logger = Logger.getLogger(Products.class);

	@Resource(name = "storageService")
	private IStorageService storageService;

	@Resource(name = "userService")
	private IUserService userService;

	@Resource(name = "productService")
	private IProductService productService;

	@Resource(name = "productTypeService")
	private IProductTypeService productTypeService;

	@Resource(name = "unitService")
	private IUnitService unitService;

	/**
	 * 添加商品界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public ModelAndView addProduct(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		List<ProductType> productTypeList = productTypeService.getAllProductType();
		view.addObject("productTypeList", productTypeList);

		List<Currency> currencyList = unitService.findAllCurrency();
		view.addObject("currencyList", currencyList);
		view.setViewName("warehouse/product/addProduct");
		return view;
	}

	/**
	 * 根据商品Id删除商品
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteProductById", method = RequestMethod.POST)
	public String deleteProductById(HttpServletRequest request, Long id) {
		logger.info("商品ID:" + id);
		Map<String, String> map = productService.deleteProductById(id);
		return GsonUtil.toJson(map);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteProductByIds", method = RequestMethod.POST)
	public String deleteProductByIds(HttpServletRequest request, String ids) {
		Map<String, String> map = productService.deleteProductByIds(ids);
		return GsonUtil.toJson(map);
	}

	/**
	 * 商品查询
	 * 
	 * @param request
	 * @param sortorder
	 * @param sortname
	 * @param page
	 * @param pagesize
	 * @param userLoginName
	 * @param keyword
	 * @param createdTimeStart
	 * @param createdTimeEnd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getListProductData", method = RequestMethod.POST)
	public String getListProductData(HttpServletRequest request, String sortorder, String sortname, Integer page, Integer pagesize, String userLoginName, String keyword, String createdTimeStart, String createdTimeEnd) {
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;
		if (StringUtil.isNotNull(createdTimeStart) && createdTimeStart.contains(",")) {
			createdTimeStart = createdTimeStart.substring(createdTimeStart.lastIndexOf(",") + 1, createdTimeStart.length());
		}
		Product param = new Product();
		// 客户帐号
		if (StringUtil.isNotNull(userLoginName)) {
			Long userIdOfCustomer = userService.findUserIdByLoginName(userLoginName);
			param.setUserIdOfCustomer(userIdOfCustomer);
			logger.info("userIdOfCustomer:" + userIdOfCustomer);
		}
		// 更多参数
		Map<String, String> moreParam = new HashMap<String, String>();
		moreParam.put("createdTimeStart", createdTimeStart);
		moreParam.put("createdTimeEnd", createdTimeEnd);
		moreParam.put("keyword", keyword);
		pagination = productService.findProduct(param, moreParam, pagination);
		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);
		return GsonUtil.toJson(map);
	}

	/**
	 * 商品 界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listProduct", method = RequestMethod.GET)
	public ModelAndView listProduct(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConstant.USER_ID);
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		User user = userService.getUserById(userId);
		view.addObject("warehouseList", storageService.findAllWarehouse(user.getDefaultWarehouseId()));
		view.addObject("sevenDaysAgoStart", DateUtil.getSevenDaysAgoStart());
		view.setViewName("warehouse/product/listProduct");
		return view;
	}

	/**
	 * 添加新商品
	 * 
	 * @param request
	 * @param productName
	 * @param productTypeName
	 * @param userIdOfCustomer
	 * @param isNeedBatchNo
	 * @param sku
	 * @param warehouseSku
	 * @param model
	 * @param volume
	 * @param customsWeight
	 * @param currency
	 * @param customsValue
	 * @param taxCode
	 * @param origin
	 * @param remark
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveAddProduct", method = RequestMethod.POST)
	public String saveAddProduct(HttpServletRequest request, String productName, Long productTypeId, String userLoginName, String isNeedBatchNo, String sku, String warehouseSku, String model, Double volume, Double customsWeight, String currency,
			Double customsValue, String taxCode, String origin, String remark) {
		Long userIdOfCustomer = userService.findUserIdByLoginName(userLoginName);
		Long createdTime = System.currentTimeMillis();
		Product product = new Product();
		product.setProductName(productName);
		product.setProductTypeId(productTypeId);
		product.setUserIdOfCustomer(userIdOfCustomer);
		product.setIsNeedBatchNo(isNeedBatchNo);
		product.setModel(model);
		product.setSku(sku);
		product.setWarehouseSku(warehouseSku);
		product.setVolume(volume);
		product.setCustomsWeight(customsWeight);
		product.setCurrency(currency);
		product.setCustomsValue(customsValue);
		product.setTaxCode(taxCode);
		product.setOrigin(origin);
		product.setRemark(remark);
		product.setCreatedTime(createdTime);
		Map<String, String> map = productService.addProduct(product);
		return GsonUtil.toJson(map);
	}

	/**
	 * 更新界面
	 * 
	 * @param request
	 * @param response
	 * @return 显示选中跟新商品
	 */
	@RequestMapping(value = "/updateProduct", method = RequestMethod.GET)
	public ModelAndView updateProduct(HttpServletRequest request, HttpServletResponse response, Long id) {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		Product product = productService.getProductById(id);
		view.addObject("product", product);
		if (product.getProductTypeId() != null) {
			ProductType productType = productTypeService.getProductTypeById(product.getProductTypeId());
			view.addObject("productType", productType);
		}
		if (product.getUserIdOfCustomer() != null) {
			User user = userService.getUserById(product.getUserIdOfCustomer());
			view.addObject("user", user);
		}
		if (StringUtil.isNotNull(product.getCurrency())) {
			Currency currency = unitService.findCurrencyByCode(product.getCurrency());
			view.addObject("currency", currency);
		}
		List<ProductType> productTypeList = productTypeService.getAllProductType();
		view.addObject("productTypeList", productTypeList);
		List<Currency> currencyList = unitService.findAllCurrency();
		view.addObject("currencyList", currencyList);
		view.setViewName("warehouse/product/updateProduct");
		return view;
	}

	/**
	 * 更新商品
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveUpdateProduct", method = RequestMethod.POST)
	public String saveUpdateProduct(HttpServletRequest request, Long id, String productName, Long productTypeId, String userLoginName, String isNeedBatchNo, String sku, String warehouseSku, String model, Double volume, Double customsWeight,
			String currency, Double customsValue, String taxCode, String origin, String remark) {
		Long userId = userService.findUserIdByLoginName(userLoginName);
		Long lastUpdateTime = System.currentTimeMillis();
		Product product = new Product();
		product.setId(id);
		product.setProductName(productName);
		product.setProductTypeId(productTypeId);
		product.setUserIdOfCustomer(userId);
		product.setIsNeedBatchNo(isNeedBatchNo);
		product.setSku(sku);
		product.setWarehouseSku(warehouseSku);
		product.setModel(model);
		product.setVolume(volume);
		product.setCustomsWeight(customsWeight);
		product.setCurrency(currency);
		product.setCustomsValue(customsValue);
		product.setTaxCode(taxCode);
		product.setOrigin(origin);
		product.setRemark(remark);
		product.setLastUpdateTime(lastUpdateTime);
		Map<String, String> map = productService.updateProduct(product);
		return GsonUtil.toJson(map);
	}
}
