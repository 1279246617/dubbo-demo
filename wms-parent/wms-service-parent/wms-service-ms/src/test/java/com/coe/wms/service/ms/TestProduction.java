package com.coe.wms.service.ms;


import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 用于启动生产者
* @ClassName: TestProduction  
* @author lqg  
* @date 2017年7月27日 下午5:10:36  
* @Description: TODO
 */
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
