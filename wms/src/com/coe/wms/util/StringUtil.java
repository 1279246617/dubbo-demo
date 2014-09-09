package com.coe.wms.util;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.List;

import sun.misc.BASE64Encoder;

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
	public static boolean isNull(String str) {
		return (null == str || "".equals(str.trim()));
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            非空返回true
	 * @return
	 */
	public static boolean isNotNull(String str) {
		return (null != str && !"".equals(str.trim()));
	}

	/**
	 * 判断2个字符串是否相同 比String 自带的Equal的好处是 ,可以对比 2个都为 null的字符串
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
	 * 去除整数后的000. 非0 不能去掉
	 * 
	 * @param sNum
	 * @return
	 */
	public static String getRightStr(String sNum) {
		DecimalFormat decimalFormat = new DecimalFormat("#.000000");
		String resultStr = decimalFormat.format(new Double(sNum));
		if (resultStr.matches("^[-+]?\\d+\\.[0]+$")) {
			resultStr = resultStr.substring(0, resultStr.indexOf("."));
		}
		return resultStr;
	}

	/**
	 * 生成 md5
	 * 
	 * @param str
	 * @return
	 */
	public static String encoderByMd5(String str) {
		try {
			// 确定计算方法
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			// 加密后的字符串
			String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
			return newstr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * md5加密方法
	 * 
	 * @return String 返回32位md5加密字符串(16位加密取substring(8,24))
	 */
	public final static String md5(String plainText) {
		String md5Str = null;
		try {
			StringBuffer buf = new StringBuffer();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			// 32位的加密
			md5Str = buf.toString();
			// 16位的加密
			// md5Str = buf.toString().md5Strstring(8,24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5Str;
	}
}