/**
 *
 * 2015年1月27日
 */
package com.coe.wms.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.coe.wms.util.StringUtil;

/**
 * @author yechao
 * 
 */
public class DBUtil {
	public static <T> String query(T object, String tbName) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from " + tbName + " where");
		try {
			Class<? extends Object> c = object.getClass();
			String className = c.getSimpleName();
			Method[] methods = c.getMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (!methodName.startsWith("get")) {
					continue;
				}
				// get方法, 值
				Object value = method.invoke(object);
				// 返回类型
				Type returnType = method.getGenericReturnType();
				// 字符串
				if (StringUtil.isEqual(String.class.toString(), returnType.toString())) {

				}
				// 数字类型
				if (StringUtil.isEqual(returnType.toString(), new String[] { Long.class.toString(), Integer.class.toString(), Short.class.toString(), Double.class.toString(), Float.class.toString() })) {
					System.out.println(value);
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
