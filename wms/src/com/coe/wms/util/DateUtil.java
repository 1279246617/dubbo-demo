package com.coe.wms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.XMLGregorianCalendar;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

/**
 * 日期工具类
 * 
 * @author yechao
 * @date 2013年12月12日
 */
public class DateUtil {
	/**
	 * 日期格式
	 */
	public static String yyyyMMddHHmmss = "yyyy/MM/dd HH:mm:ss";

	public static String yyyyMMdd = "yyyy/MM/dd";

	public static String yyyy_MM_dd = "yyyy-MM-dd";

	public static String MMddYYYY = "MM/dd/yyyy";

	/**
	 * 常用日期格式 中国常用格式
	 */
	public static String yyyy_MM_ddHHmmss = "yyyy-MM-dd HH:mm:ss";

	public static String MMMdd_comma_yyyyhhmmaa = "MMM dd,yyyy hh:mm aa";

	public static String MMMdd_comma_yyyyHHmmaa = "MMM dd,yyyy HH:mm aa";

	public static String MMMdd_comma_yyyy_comma_hhmmaa = "MMM dd,yyyy, hh:mm aa";

	public static String MMMdd_comma_yyyy_comma_HHmmaa = "MMM dd,yyyy, HH:mm aa";

	public final static String YYYYMMDD_HH24MISS = "YYYY-MM-DD HH24:MI:SS";

	/**
	 * 字符串的日期类型 转换称 Date 类型
	 * 
	 * @param timeContent
	 *            字符串的日期类型
	 * @param formatStyle
	 *            日期格式
	 * @return
	 */
	public static Date stringConvertDate(String timeContent, String formatStyle) {
		SimpleDateFormat format = new SimpleDateFormat(formatStyle);
		try {
			return format.parse(timeContent);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * Date 转化成 String 类型的字符串日期格式
	 * 
	 * @param date
	 * @param formatStyle
	 *            转化成 什么格式
	 * @return
	 */
	public static String dateConvertString(Date date, String formatStyle) {
		SimpleDateFormat format = new SimpleDateFormat(formatStyle);
		return format.format(date);
	}

	/**
	 * 字符串日期格式 转换成 带 地区标识的 Date 类型
	 * 
	 * @param strDate
	 * @param locale
	 * @param formatStyle
	 * @return
	 */
	public static Date stringConvertLocalDate(String strDate, Locale locale, String formatStyle) {
		SimpleDateFormat srcsdf = new SimpleDateFormat(formatStyle, locale);
		try {
			return srcsdf.parse(strDate);
		} catch (ParseException e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * Calendar 类型 转 XMLGregorianCalendar 类型
	 * 
	 * @param calendar
	 * @return
	 */
	public static XMLGregorianCalendar calendarTurnToXMLGregorianCalendar(Calendar calendar) {
		GregorianCalendar greCalendar = new GregorianCalendar();
		greCalendar.setTime(calendar.getTime());
		XMLGregorianCalendar re = new XMLGregorianCalendarImpl(greCalendar);
		return re;
	}

	/**
	 * 获取本地时间相对的UTC | GMT时间
	 * 
	 * @return
	 */
	public static Date getUtcTime() {
		// 1、取得本地时间：
		Calendar calendar = java.util.Calendar.getInstance();
		// 2、取得时间偏移量：
		final int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		final int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return calendar.getTime();
	}

	/**
	 * 获取2个时间相差多少秒
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Long getDiffSeconds(Date date1, Date date2) {
		long milliseconds1 = date1.getTime();
		long milliseconds2 = date2.getTime();
		long diff = milliseconds1 - milliseconds2;
		if (diff < 0) {
			diff = -diff;
		}
		long diffSeconds = diff / (1000);
		return diffSeconds;
	}

	/**
	 * 获取2个时间相差多少分钟
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Long getDiffMinutes(Date date1, Date date2) {
		Long diffMinutes = getDiffSeconds(date1, date2) / 60;
		return diffMinutes;
	}

	/**
	 * 获取2个时间直接 相差多少小时
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Long getDiffHours(Date date1, Date date2) {
		Long diffHours = getDiffMinutes(date1, date2) / 60;
		return diffHours;
	}

	/**
	 * 获取2个时间直接 相差多少天
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Long getDiffDays(Date date1, Date date2) {
		Long diffDays = getDiffHours(date1, date2) / 24;
		return diffDays;
	}

	public static String getTodayStart() {
		String time = DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_dd);
		String timeFrom = time + " 00:00:00";
		return timeFrom;
	}
	
	public static String getTodayEnd() {
		String time = DateUtil.dateConvertString(new Date(), DateUtil.yyyy_MM_dd);
		String timeTo = time + " 23:59:59";
		return timeTo;
	}
}
