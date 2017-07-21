package com.coe.wms.facade.symgmt;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestProduction {
public static void main(String[] args) throws Exception {
	ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
			"spring/dubbo-demo-costomer.xml");
	classPathXmlApplicationContext.start();
	
	
	System.in.read();
}
}
