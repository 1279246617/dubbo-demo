package com.coe.wms.common.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coe.wms.common.web.model.Result;

/**
 * web工程切面 异常处理 不适用@Component注解方式,由依赖工程根据需要自由配置
 * 
 * @ClassName: WebExceptionAspect
 * @author yechao
 * @date 2017年4月17日 上午10:37:59
 * @Description: TODO
 */
@Aspect
public class WebExceptionAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebExceptionAspect.class);

	/** 切入点 */
	@Pointcut("execution(* com.coe.wms.web..*.*(..))")
	private void pointcut() {
	}

	@Around(value = "pointcut()")
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = null;
		try {
			result = proceedingJoinPoint.proceed();
		} catch (Exception e) {
			// 捕获controller异常,返回失败状态和异常消息
			result = Result.error(e.getMessage());

			String className = proceedingJoinPoint.getTarget().getClass().getName();
			String methodName = proceedingJoinPoint.getSignature().getName();

			LOGGER.error("{}.{} Throws {}", className, methodName, e.getMessage());
		}
		return result;
	}

	

}