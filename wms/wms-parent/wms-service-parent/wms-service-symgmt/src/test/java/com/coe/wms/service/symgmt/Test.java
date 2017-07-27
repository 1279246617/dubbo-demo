package com.coe.wms.service.symgmt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.coe.wms.facade.symgmt.entity.Admin;
import com.coe.wms.facade.symgmt.entity.Config;
import com.coe.wms.facade.symgmt.entity.Menu;
import com.coe.wms.facade.symgmt.service.AdminService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Test extends BaseServiceTest {

	@Autowired
	private AdminService adminService;
	
	@org.junit.Test
	public void test(){
		Admin admin = adminService.get(2l);
		System.out.println(new Gson().toJson(admin));
	}
}
