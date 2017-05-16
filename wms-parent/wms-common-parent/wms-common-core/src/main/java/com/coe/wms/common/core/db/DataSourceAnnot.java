package com.coe.wms.common.core.db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据源注解,注解在方法上
 * 
 * @ClassName: DataSource
 * @author yechao
 * @date 2017年4月21日 上午11:05:06
 * @Description: TODO
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DataSourceAnnot {
	DataSourceType value() default DataSourceType.Master;
}
