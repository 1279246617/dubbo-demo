package com.coe.wms.common.core.db;

/**
 * 设置、获取数据源连接
 * 
 * @ClassName: JdbcContextHolder
 * @author yechao
 * @date 2017年4月21日 上午10:57:32
 * @Description: TODO
 */
public class DataSourceHolder {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setType(String jdbcType) {
		contextHolder.set(jdbcType);
	}

	public static String getType() {
		return (String) contextHolder.get();
	}

	public static void clearType() {
		contextHolder.remove();
	}
}
