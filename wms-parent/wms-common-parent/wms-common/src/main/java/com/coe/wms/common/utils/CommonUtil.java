package com.coe.wms.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

/**
 * 
 * @ClassName: CommonUtil
 * @author yechao
 * @date 2017年3月1日 上午11:50:30
 * @Description: TODO
 */
public class CommonUtil {

	private static final Logger LOGGER = Logger.getLogger(CommonUtil.class);

	/**
	 * 将异常堆栈转换为字符串
	 * 
	 * @param aThrowable
	 *            异常 允许为空
	 * @return aThrowable 为null, 则返回 ""
	 */
	public static String getStackTrace(Throwable aThrowable) {
		return getStackTrace(aThrowable, -1);
	}

	/**
	 * 将异常堆栈转换为字符串
	 * 
	 * @param aThrowable
	 *            异常 允许为空
	 * @param length
	 *            -1则返回全部
	 * @return aThrowable 为null, 则返回 ""
	 */
	public static String getStackTrace(Throwable aThrowable, int length) {
		if (aThrowable == null) {
			return "";
		}

		String result = "";
		final Writer writer = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(writer);
		try {
			aThrowable.printStackTrace(printWriter);
			result = writer.toString();
		} catch (Exception e) {
			LOGGER.error("get stack trace error", e);
		} finally {
			printWriter.close();
		}

		if (length > 0 && result.length() > length) {
			result = result.substring(0, length);
		}
		return result;
	}

}
