package com.coe.wms.common.core.cache.annot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 删除缓存里面的数据
 * 
 * @ClassName: UpdateCache
 * @author lqg
 * @date 2017年7月19日 上午11:36:49
 * @Description: TODO
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface UpdateCache {

	/**
	 * key值 方法有参数则加前缀,若方法无参则作为key
	 * 
	 * @return
	 */
	String key();

	/**
	 * 缓存超时时间
	 * 
	 * @return
	 */
	int expire() default -1;
}
