package com.coe.wms.controller.product;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.coe.wms.controller.Application;
import com.coe.wms.model.product.Product;
import com.coe.wms.service.product.IproductService;
import com.coe.wms.util.GsonUtil;
import com.coe.wms.util.Pagination;

@Controller("product")
@RequestMapping("/product")
public class Products {
	
	Logger logger = Logger.getLogger(Products.class);
	
	@Resource(name = "productService")
	private IproductService productService;

	@RequestMapping(value = "/listProduct", method = RequestMethod.GET)
	public ModelAndView listProduct(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		view.addObject(Application.getBaseUrlName(), Application.getBaseUrl());
		view.setViewName("warehouse/product/listProduct");
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/getListProductData", method = RequestMethod.POST)
	public String getListProductData(HttpServletRequest request, String sortorder,
			String sortname, Integer page, Integer pagesize) {
		logger.info("sortorder:"+sortorder +" sortname:"+sortname +" page:"+page +" pagesize:"+pagesize);
		Pagination pagination = new Pagination();
		pagination.curPage = page;
		pagination.pageSize = pagesize;
		pagination.sortName = sortname;
		pagination.sortOrder = sortorder;
		Product product = new Product();
		// 更多参数
		Map<String, String> moreParam = new HashMap<String, String>();
		pagination = productService.findAllProduct(product, moreParam,
				pagination);
		Map map = new HashMap();
		map.put("Rows", pagination.rows);
		map.put("Total", pagination.total);
		return GsonUtil.toJson(map);
	}

}
