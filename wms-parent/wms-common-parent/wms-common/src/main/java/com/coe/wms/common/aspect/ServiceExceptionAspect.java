package com.coe.wms.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coe.wms.common.exception.ServiceException;

/**
 * service工程切面 异常处理 不适用@Component注解方式,由依赖工程根据需要自由配置
 * 
 * @ClassName: ServiceExceptionAspect
 * @author yechao
 * @date 2017年4月17日 上午10:44:37
 * @Description: TODO
 */
@Aspect
public class ServiceExceptionAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExceptionAspect.class);

	/** 切入点 */
	@Pointcut("execution(* com.coe.fcs.facade..*.*(..))")
	private void pointcut() {
	}

	@AfterThrowing(pointcut = "pointcut()", throwing = "e")
	public void AfterThrowing(JoinPoint point, Exception e) {

		String className = point.getTarget().getClass().getName();
		String methodName = point.getSignature().getName();

		LOGGER.error("{}.{} Throws {}", className, methodName, e.getMessage());

		throw new ServiceException(e);
	}
}