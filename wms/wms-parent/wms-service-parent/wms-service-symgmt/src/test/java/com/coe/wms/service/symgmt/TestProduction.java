package com.coe.wms.service.symgmt;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.coe.wms.facade.symgmt.entity.Admin;
import com.coe.wms.facade.symgmt.service.impl.AdminServiceImpl;
import com.google.gson.Gson;

public class TestProduction {
public static void main(String[] args) throws Exception {
	ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
			"/spring/spring-context.xml");
	classPathXmlApplicationContext.start();
	/*IAdminFacade adminFacade = classPathXmlApplicationContext.getBean(IAdminFacade.class);
	Gson gson=new Gson();
	Admin admin = adminFacade.get(0l);
	System.out.println(gson.toJson(admin));
	*/
	System.in.read();
}
}
