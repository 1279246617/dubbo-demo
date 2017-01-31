package com.coe.wms.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
	 * 检查日期格式
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static boolean checkDate(String time, String format) {
		if (stringConvertDate(time, format) == null) {
			return false;
		}
		return true;
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

	/**
	 * 多少天以前
	 * 
	 * @param ago
	 * @return
	 */
	public static String getDaysAgoStart(int ago) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -ago);
		String time = DateUtil.dateConvertString(calendar.getTime(), DateUtil.yyyy_MM_dd);
		String timeFrom = time + " 00:00:00";
		return timeFrom;
	}

	/**
	 * 获得指定日期的下一天
	 * 
	 * @param day
	 * @return
	 */
	public static String getNextDayTime(String day) {
		Date date = DateUtil.stringConvertDate(day, DateUtil.yyyy_MM_ddHHmmss);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);

		return new SimpleDateFormat(DateUtil.yyyy_MM_ddHHmmss).format(cal.getTime());
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param day
	 * @return
	 */
	public static String getPreDayTime(String day) {
		Date date = DateUtil.stringConvertDate(day, DateUtil.yyyy_MM_ddHHmmss);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);

		return new SimpleDateFormat(DateUtil.yyyy_MM_ddHHmmss).format(cal.getTime());
	}

	/**
	 * 多少天以后
	 * 
	 * @param date
	 * @param dayNum
	 * @return
	 */
	public static Date getNextPrevDay(Date date, int dayNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, dayNum);
		return calendar.getTime();
	}

	/**
	 * 判断time1 是否早于time2
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean before(String time1, String time2) {
		Date date1 = DateUtil.stringConvertDate(time1, DateUtil.yyyy_MM_ddHHmmss);
		Date date2 = DateUtil.stringConvertDate(time2, DateUtil.yyyy_MM_ddHHmmss);

		return date1.before(date2);
	}

	/**
	 * 获得当前系统时间
	 * 
	 * @return
	 */
	public static String getSystemDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.yyyy_MM_ddHHmmss);
		return sdf.format(new Date());
	}

	/**
	 * 得到某个时间段加上多少小时的时间
	 * 
	 * @param dateTime
	 * @param hours
	 * @return
	 */
	public static String getDateTimeAddHours(Date dateTime, Integer hours) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.yyyy_MM_ddHHmmss);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		calendar.add(Calendar.HOUR, hours);
		return simpleDateFormat.format(calendar.getTime());
	}

}
