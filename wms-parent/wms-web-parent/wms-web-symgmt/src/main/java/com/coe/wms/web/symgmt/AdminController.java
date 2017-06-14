package com.coe.wms.web.symgmt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.coe.wms.facade.symgmt.service.IAdminFacade;

@Controller
@RequestMapping("/symgmt/admin")
public class AdminController {

	@Reference(version = "1.1.1")
	private IAdminFacade adminFacade;
	
}
