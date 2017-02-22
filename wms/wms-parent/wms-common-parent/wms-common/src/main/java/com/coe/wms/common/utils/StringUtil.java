package com.coe.wms.common.utils;

import java.util.List;

/**
 * 字符串工具类
 * 
 * @author yechao
 * @date 2013年12月12日
 */
public class StringUtil {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            “ ”、null 都返回true
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (null == str || "".equals(str.trim()));
	}

	/**
	 * 判断2个字符串是否相同,可以对比 2个都为 null的字符串
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean isEqual(String s1, String s2) {
		if (s1 == null && s2 == null) {
			return true;
		}
		if (s1 != null && s2 == null) {
			return false;
		}
		if (s2 != null && s1 == null) {
			return false;
		}
		if (s1.equals(s2)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param str
	 * @param strings
	 * @return
	 */
	public static boolean isEquals(String str, String... strings) {
		for (int i = 0; i < strings.length; i++) {
			if (StringUtil.isEqual(str, strings[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断2个字符串是否相同 比String 自带的Equal的好处是 ,可以对比 2个都为 null的字符串
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean isEqualIgnoreCase(String s1, String s2) {
		if (s1 == null && s2 == null) {
			return true;
		}
		if (s1 != null && s2 == null) {
			return false;
		}
		if (s2 != null && s1 == null) {
			return false;
		}
		if (s1.equalsIgnoreCase(s2)) {
			return true;
		}
		return false;
	}

	/**
	 * 去除重复
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> removeDuplicate(List<String> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (StringUtil.isEqual(list.get(j), list.get(i))) {
					list.remove(j);
				}
			}
		}
		return list;
	}

	/**
	 * 拼接数组,
	 * 
	 * @param strs
	 * @return
	 */
	public static String concatArray(String[] strs) {
		String concats = "";
		for (String str : strs) {
			concats += str + ",";
		}
		if (concats.length() > 0) {
			concats = concats.substring(0, concats.length() - 1);
		}
		return concats;
	}

	/**
	 * 拼接集合
	 * 
	 * @param strs
	 * @return
	 */
	public static String concatList(List<?> strs) {
		String concats = "";
		for (Object str : strs) {
			concats += str + ",";
		}
		if (concats.length() > 0) {
			concats = concats.substring(0, concats.length() - 1);
		}
		return concats;
	}

	/**
	 * 去边界空白字符
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		if (!isEmpty(str)) {
			str = str.trim();
		}
		return str;
	}
}