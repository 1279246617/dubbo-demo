package com.coe.wms.util;

/**
 * 常量类
 * 
 * @author yechao
 * @date 2013-11-7
 */
public class Constant {
	/**
	 * 打印订单时，1表示标签纸 2表示A4纸
	 */
	public final static Integer PAPER_LABEL = 1;

	public final static Integer PAPER_A4 = 2;

	/**
	 * 状态常量:很多表都有 status 状态 共用以下常量 2 表示已经被删除的
	 */
	public final static Integer STATUS_DELETE = 2;
	/**
	 * 1 正常的,可以用的
	 */
	public final static Integer STATUS_NORMAL = 1;

	/**
	 * 0 未准备好的. 将来才启用的
	 */
	public final static Integer STATUS_UNUSED = 0;

	/**
	 * session常量系统用户id
	 */
	public final static String SESSION_USER_ID = "userId";

	public final static String SESSION_USER_NAME = "userName";

	/**
	 * 用于 is_delete 之类的场景
	 */
	public final static String Y = "Y";

	public final static String N = "N";
	/**
	 * 用于 is_delete 之类的场景
	 */
	public final static String Y_CN = "是";
	/**
	 * 用于 is_delete 之类的场景
	 */
	public final static String N_CN = "否";

	/**
	 * 字符串 true 用于 判断checkbox checked 场景
	 */
	public final static String TRUE = "true";

	public final static String FALSE = "false";

	/**
	 * render success
	 */
	public final static String SUCCESS = "1";

	/**
	 * render fail
	 */
	public final static String FAIL = "0";

	/**
	 * 电话正则 限制尽量放宽
	 */
	public final static String REGEX_PHONE = "[\\d-+]{8,}";

	/**
	 * EMAIL正则 限制尽量放宽
	 */
	public final static String REGEX_EMAIL = "\\S+@\\S+\\.\\S+";
}
