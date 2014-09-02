package com.coe.wms.dao.datasource;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

public class DataSourceAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

	private Logger logger = Logger.getLogger(this.getClass());

	public void before(Method method, Object[] args, Object target) throws Throwable {
		DataSource dataSource = method.getAnnotation(DataSource.class);
		String methodName = method.getName();
		if (dataSource != null) {
			DataSourceSwitcher.setDataSourceType(dataSource.value());
			logger.debug("datasource advice, method:" + methodName + ", database:" + dataSource.value());
		} else {
			// 如果dao 方法未注解数据源,默认使用wms 数据库
			DataSourceSwitcher.setDataSourceType(DataSourceCode.WMS);
		}
	}

	public void afterReturning(Object arg0, Method method, Object[] args, Object target) throws Throwable {

	}

	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
		DataSourceSwitcher.clearDataSourceType();
	}
}