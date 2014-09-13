package com.coe.wms.controller.warehouse;

import java.io.IOException;

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
import com.coe.wms.service.api.IStorageService;
import com.coe.wms.util.SessionConstant;

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
}
