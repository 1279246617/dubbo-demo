package com.coe.message.common;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

/***/
public class DateUtil {

	/** 年月日时分秒(无下划线) yyyyMMddHHmmss */
	public static final String dtLong = "yyyyMMddHHmmss";

	/** 完整时间 yyyy-MM-dd HH:mm:ss */
	public static final String simple = "yyyy-MM-dd HH:mm:ss";

	/** 年月日(无下划线) yyyyMMdd */
	public static final String dtShort = "yyyy-MM-dd";

	/**返回系统当前时间(精确到毫秒)*/
	public static String getOrderNum() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtLong);
		return df.format(date);
	}

	/** 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss*/
	public static String getDateFormatter() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(simple);
		return df.format(date);
	}

	/** 获取系统当期年月日(精确到天)，格式：yyyyMMdd*/
	public static String getDate() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtShort);
		return df.format(date);
	}
	/**
	 * 将时间戳转换成时间字符串
	 * @param timestamp 时间戳
	 * @param style 时间格式（dtLong,simple or dtShort）
	 * @return 时间字符串
	 */
	public static String getDateStrFromTimestamp(long timestamp,String style){
		Date date = new Date(timestamp);
		DateFormat df = new SimpleDateFormat(style);
		return df.format(date);
	}
	/**
	 * 将时间字符串转换成时间戳
	 * @param dateStr 时间字符串
	 * @param style  时间字符串的格式
	 * @return 时间戳
	 */
	public static long getTimestampFromDateStr(String dateStr,String style){
		DateFormat df = new SimpleDateFormat(style);
		long timestamp = 0;
		try {
			Date date = df.parse(dateStr);
			timestamp = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}
}
