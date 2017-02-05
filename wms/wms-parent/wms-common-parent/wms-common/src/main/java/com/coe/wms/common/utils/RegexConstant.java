package com.coe.wms.common.utils;

public class RegexConstant {
	/**
	 * 邮箱地址正则
	 */
	public static final String EMAIL_REGEX = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
	/**
	 * 手机号码
	 */
	public static final String MOBILE_REGEX = "^1[0-9]{10}$";
}
