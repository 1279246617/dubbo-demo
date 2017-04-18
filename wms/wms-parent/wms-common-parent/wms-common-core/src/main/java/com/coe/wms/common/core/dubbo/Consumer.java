package com.coe.wms.common.core.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动dubbo消费者
 * 
 * @ClassName: Consumer
 * @author yechao
 * @date 2017年3月6日 下午11:55:04
 * @Description: TODO
 */
public class Consumer {
	public static ClassPathXmlApplicationContext context = null;

	public static ClassPathXmlApplicationContext singleton() {
		if (context == null) {
			context = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/spring-dubbo-consumer.xml" });
			context.start();
		}
		return context;
	};
}
