package com.coe.wms.service.user;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动dubbo服务 仅开发,测试时使用
 * 
 * @ClassName: DubboProvider
 * @author yechao
 * @date 2017年3月6日 上午10:39:32
 * @Description: TODO
 */
public class DubboProvider {

	private final static Logger log = Logger.getLogger(DubboProvider.class);
	private static ClassPathXmlApplicationContext context;

	public static void main(String[] args) {
		String contextPath = "classpath:spring/spring-context.xml";
		try {
			context = new ClassPathXmlApplicationContext(contextPath);
			context.start();
		} catch (Exception e) {
			log.info("dubbo provider start fail ... error:" + e);
		}
		synchronized (DubboProvider.class) {
			while (true) {
				try {
					DubboProvider.class.wait();
				} catch (InterruptedException e) {
					log.info("synchronized error:" + e);
				}
			}
		}
	}
}
