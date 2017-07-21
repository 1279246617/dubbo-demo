package com.coe.wms.common.core.cache;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.coe.wms.common.core.cache.annot.SetCache;
import com.coe.wms.common.core.cache.annot.UpdateCache;
import com.coe.wms.common.core.cache.redis.RedisClient;
import com.google.gson.Gson;

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

	@Around("execution(* com.coe.wms.*.*.service.impl.*.*(..))")
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
		Gson gson = new Gson();

		// 设置缓存
		SetCache cache = method.getAnnotation(SetCache.class);
		if (cache != null) {
			// 后缀
			String cacheSuffix = null;
			// 获取请求参数
			Object[] args = proceedingJoinPoint.getArgs();
			// 如果有参数
			if (args != null && args.length != 0) {
				cacheSuffix = gson.toJson(args);
			}
			String cacheKey = cache.key();
			if (cacheSuffix != null) {
				cacheKey = cacheKey + cacheSuffix;
			}
			RedisClient redisClient = RedisClient.getInstance();

			Object value = redisClient.getObject(cacheKey);

			if (value != null) {
				return value;
			} else {
				// 执行目标方法,得到结果
				Object result = proceedingJoinPoint.proceed();
				if (result != null) {
					// 放入缓存中
					if (cache.expire() != -1)
						redisClient.setObject(cacheKey, result, cache.expire());
					else
						redisClient.setObject(cacheKey, result);
				}
				return result;
			}
		}
		// 删除缓存
		UpdateCache updateCache = method.getAnnotation(UpdateCache.class);
		if (updateCache != null) {
			// 后缀
			String cacheSuffix = null;
			// 获取请求参数
			Object[] args = proceedingJoinPoint.getArgs();
			// 如果有参数
			if (args != null && args.length != 0) {
				cacheSuffix = gson.toJson(args);
			}
			String cacheKey = updateCache.key();
			if (cacheSuffix != null) {
				cacheKey = cacheKey + cacheSuffix;
			}
			RedisClient redisClient = RedisClient.getInstance();
			redisClient.delete(cacheKey);

		}

		// 执行目标方法,得到结果
		Object result = proceedingJoinPoint.proceed();
		return result;
	}

}
