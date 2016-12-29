package com.coe.message.common;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**javeBean和Map集合互转类*/
public class BeanMapUtil {
	/**javabean转map*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> convertBean(Object bean) {
		Map returnMap = new HashMap();
		try {
			//类型
			Class type = bean.getClass();
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			int len = propertyDescriptors.length;
			for (int i = 0; i < len; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!"class".equals(propertyName)) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					returnMap.put(propertyName, result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	/**将Map转成javaBean*/
	public static Object convertMap(Class<?> clazz, Map<String, Object> map) {
		Object obj = null;
		try {
			obj = clazz.newInstance();
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			int len = propertyDescriptors.length;
			for (int i = 0; i < len; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (map.containsKey(propertyName)) {
					Object value = map.get(propertyName);
					Object[] args = new Object[1];
					args[0] = value;
					descriptor.getWriteMethod().invoke(obj, args);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
