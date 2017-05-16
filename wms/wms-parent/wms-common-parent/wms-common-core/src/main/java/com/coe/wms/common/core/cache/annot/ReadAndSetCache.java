package com.coe.wms.common.core.cache.annot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 获取缓存
 * 
 * 如能获取缓存,则阻止方法继续执行
 * 
 * @ClassName: ReadAndSetCache
 * @author yechao
 * @date 2017年5月9日 下午3:34:26
 * @Description: TODO
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ReadAndSetCache {
	/**
	 * 命名空间
	 * 
	 * 默认:包+类名
	 * 
	 * @return
	 */
	String namespace() default "default";

	/**
	 * key值 默认:方法名+参数值
	 * 
	 * @return
	 */
	String key() default "default";

	long expire() default -1;
}
