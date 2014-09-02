package com.coe.wms.util;

import java.util.regex.Pattern;

/**
 * 基础的常量
 * 
 * @author sunkey
 * @date Mar 15, 2013 6:27:29 PM
 * @version 1.0.0
 * @copyright fpx.com
 */
public class BaseConstant {
	/**
	 * 字符编码
	 */
	public static final String ENCODE_TYPE = "UTF-8";

	/**
	 * 逗号
	 */
	public static final String SYMBOL_COMMA = ",";

	public static final String SYMBOL_COLON = ":";

	public static final String SYMBOL_UNDERLINE = "_";

	public static final String SYMBOL_SLASH = "/";

	public static final String ITEM_PRE = "i_";

	public static final String LANGUAGE_CN = "zh_CN";

	public static final String LANGUAGE_EN = "en_US";

	public final static String LANGUAGE = "language";

	public final static String FORMAT = "format";

	public static final Pattern SEPARATOR_TAB = Pattern.compile("[\t]");
	public static final Pattern SEPARATOR_SPACE = Pattern.compile(" ");
	public static final Pattern SEPARATOR_COMMA = Pattern.compile(SYMBOL_COMMA);
	public static final Pattern SEPARATOR_COLON = Pattern.compile(SYMBOL_COLON);
	public static final Pattern SEPARATOR_AND = Pattern.compile("&");
	public static final Pattern SEPARATOR_EQUAL = Pattern.compile("=");
	public static final Pattern SEPARATOR_SEMICOLON = Pattern.compile(";");
	public static final Pattern SEPARATOR_VERTICALBAR = Pattern.compile("[|]");
	public static final Pattern SEPARATOR_X01 = Pattern.compile("[\001]");
	public static final Pattern SEPARATOR_NUMBER = Pattern.compile("[0-9]{0,}");
	public static final Pattern FILE_PATTERN1 = Pattern.compile("^(part-r-)[0-9]{5}");
	public static final Pattern SEPARATOR_INVISIBILITY = Pattern.compile("\6");//看不见的分隔符
}
