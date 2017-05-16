package com.coe.wms.common.core.cache;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coe.wms.common.core.cache.annot.InvalidateCache;
import com.coe.wms.common.core.cache.annot.ReadAndSetCache;
import com.coe.wms.common.core.cache.annot.SetCache;
import com.coe.wms.common.core.cache.annot.UpdateCache;

/**
 * redis 缓存切面类
 * 
 * @ClassName: RedisCacheAspect
 * @author yechao
 * @date 2017年5月9日 下午5:36:46
 * @Description: TODO
 */
@Aspect
public class CacheAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheAspect.class);

	/** 切入点 */
	@Pointcut("execution(* com.coe.fcs.service.*.biz.*.*(..))")
	private void pointcut() {
	}

	@Around(value = "pointcut()")
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object target = proceedingJoinPoint.getTarget();

		String methodName = proceedingJoinPoint.getSignature().getName();
		String className = target.getClass().getName();

		LOGGER.info("{}.{} cache aop in ...", className, methodName);

		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		// 参数
		Class<?>[] parameterTypes = methodSignature.getMethod().getParameterTypes();

		// 方法
		Method method = target.getClass().getMethod(methodName, parameterTypes);

		if (method == null) {
			return proceedingJoinPoint.proceed();
		}
		// 获取注解

		// 设置缓存
		SetCache setCache = method.getAnnotation(SetCache.class);
		UpdateCache updateCache = method.getAnnotation(UpdateCache.class);
		InvalidateCache invalidateCache = method.getAnnotation(InvalidateCache.class);
		ReadAndSetCache readAndSetCache = method.getAnnotation(ReadAndSetCache.class);

		// 执行目标方法,得到结果
		Object result = proceedingJoinPoint.proceed();
		return result;
	}

}
