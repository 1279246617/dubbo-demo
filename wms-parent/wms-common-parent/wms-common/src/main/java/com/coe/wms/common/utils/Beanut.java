package com.coe.wms.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
/**
 * 
 * @author liqigui
 *
 */
public class Beanut {
	/**
	 * 把object1中属性付给objectb
	 * @param object1
	 * @param object2
	 * @throws Exception
	 * @throws Exception
	 */
	public static void copy(Object object1, Object object2)  {
		Class<? extends Object> class1 = object1.getClass();
		takeClazzCopy(class1, object1, object2);
	}
	private static void takeClazzCopy(Class<? extends Object> cla_ss, Object object1,
			Object object2) {
		Field[] declaredFields2 = cla_ss.getDeclaredFields();
		for (Field field : declaredFields2) {
			field.setAccessible(true);
			Object object=null;
			try {
				object = field.get(object1);
			} catch (IllegalArgumentException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
			if (object != null) {
				try {
					BeanUtils.setProperty(object2, field.getName(), object);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		if (cla_ss.getSuperclass() != Object.class) {
			takeClazzCopy(cla_ss.getSuperclass(), object1, object2);
		}

	}
	/**
	 * 把空字符串设为null
	 * @param clazz
	 * @param entity
	 */
	public static  void trimString(Class clazz,Object entity) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			
			if(field.getType()==String.class){
				try {
					Object obj=field.get(entity);
					if(obj!=null){
						if(obj.toString().trim().length()==0){
							field.set(entity, null);
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				
			}
			
			
			
		}
	}
}
