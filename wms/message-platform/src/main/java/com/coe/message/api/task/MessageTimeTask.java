package com.coe.message.api.task;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MessageTimeTask {

	public void saveMessageFromRedis() {
		ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:xml/spring-mybatis.xml");
		System.out.println(ac.getBean("messageImpl"));
	}

}
