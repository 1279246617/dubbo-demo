package com.coe.wms.common.core.db;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;

@Aspect
public class DataSourceAspect implements Ordered {

	private static String REGEX = "select.*List.*|count.*|find.*|query.*|get.*";

	/** 切入点 */
	@Pointcut("execution(* com.coe.fcs.facade..*(..))")
	private void pointcut() {
	}

	@Before(value = "pointcut()")
	public void before(JoinPoint point) {
		Object target = point.getTarget();
		String method = point.getSignature().getName();
		MethodSignature methodSignature = (MethodSignature) point.getSignature();
		Class<?>[] parameterTypes = methodSignature.getMethod().getParameterTypes();
		try {
			Method m = target.getClass().getMethod(method, parameterTypes);
			if (m == null) {
				return;
			}
			DataSourceAnnot data = m.getAnnotation(DataSourceAnnot.class);
			if (m.isAnnotationPresent(DataSourceAnnot.class)) {
				DataSourceHolder.clearType();
				DataSourceHolder.setType(data.value().getName());
				return;
			}
			// 如果无DataSource注解,按方法名字选择数据源
			if (m.getName().matches(REGEX)) {
				DataSourceHolder.setType(DataSourceType.Slave.name());
				return;
			}
			DataSourceHolder.setType(DataSourceType.Master.name());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getOrder() {
		return 1;
	}
}
