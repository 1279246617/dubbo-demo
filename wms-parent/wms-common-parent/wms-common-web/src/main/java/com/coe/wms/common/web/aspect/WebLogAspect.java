package com.coe.wms.common.web.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * web工程切面 日志处理
 * 
 * 不适用@Component注解方式,由依赖工程自由配置
 * 
 * @ClassName: ServiceExceptionAspect
 * @author yechao
 * @date 2017年4月17日 上午10:44:37
 * @Description: TODO
 */
@Aspect
public class WebLogAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

	@Before("execution(* com.coe.wms.web..*.*(..))")
	public void before(JoinPoint joinPoint) {
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			sb.append(args[i]);
			if (i + 1 < args.length) {
				sb.append(" , ");
			}
		}
		LOGGER.debug("{}.{}({}) begin ", className, methodName, sb.toString());
	}

	@AfterReturning(value = "execution(* com.coe.wms.web..*.*(..))", returning = "returnVal")
	public void afterReturin(JoinPoint joinPoint, Object returnVal) {
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			sb.append(args[i]);
			if (i + 1 < args.length) {
				sb.append(" , ");
			}
		}
		LOGGER.debug("{}.{}({}) return : {}", className, methodName, sb.toString(), returnVal);
	}
}