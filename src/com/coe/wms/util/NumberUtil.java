package com.coe.wms.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * 数字 工具类
 * 
 * @author yechao
 * @date 2013年12月12日
 */
public class NumberUtil {

	private static final int DEF_DIV_SCALE = 2;

	/**
	 * 判断字符串是否是小数
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isDecimal(String num) {
		if (StringUtil.isNull(num)) {
			return false;
		}
		return Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?").matcher(num).matches();
	}

	/**
	 * 判断字符串是否为整数
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isNumberic(String num) {
		if (StringUtil.isNull(num)) {
			return false;
		}
		return Pattern.compile("[0-9]+").matcher(num).matches();
	}

	/**
	 * 对比2个整形数 是否相同
	 * 
	 * @param int1
	 * @param int2
	 * @return
	 */
	public static boolean isEqualLong(Long a, Long b) {
		if (a == null && b == null) {
			return true;
		}
		if (a == null && b != null) {
			return false;
		}
		if (a != null && b == null) {
			return false;
		}
		return a.equals(b);
	}

	/**
	 * 对比2个整形数 是否相同
	 * 
	 * @param int1
	 * @param int2
	 * @return
	 */
	public static boolean isEqualInt(int int1, int int2) {
		boolean isEqual = int1 == int2 ? true : false;
		return isEqual;
	}

	/**
	 * * 两个Double数相加 *
	 * 
	 * @param v1
	 *            *
	 * @param v2
	 *            *
	 * @return Double
	 */
	public static Double add(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.add(b2).doubleValue());
	}

	/**
	 * * 两个Double数相减 *
	 * 
	 * @param v1
	 *            *
	 * @param v2
	 *            *
	 * @return Double
	 */
	public static Double sub(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.subtract(b2).doubleValue());
	}

	/**
	 * * 两个Double数相乘 *
	 * 
	 * @param v1
	 *            *
	 * @param v2
	 *            *
	 * @return Double
	 */
	public static Double mul(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.multiply(b2).doubleValue());
	}

	/**
	 * * 两个Double数相除 *
	 * 
	 * @param v1
	 *            *
	 * @param v2
	 *            *
	 * @return Double
	 */
	public static Double div(Double v1, Double v2) {
		if (v1 == null) {
			return 0d;
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue());
	}

	/**
	 * * 两个Double数相除，并保留scale位小数 *
	 * 
	 * @param v1
	 *            *
	 * @param v2
	 *            *
	 * @param scale
	 *            *
	 * @return Double
	 */
	public static Double div(Double v1, Double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue());
	}

	/**
	 * 截位 取精度
	 * 
	 * @param num
	 *            浮点数
	 * @param pre
	 *            保留的小数点位数
	 * @return
	 */
	public static Double getNumPrecision(Double num, int pre) {
		long pow = (long) Math.pow(10, pre);
		long temp = (long) (pow * round(num, 100, BigDecimal.ROUND_CEILING));
		return temp / (double) pow;
	}

	/**
	 * @param v1
	 * @return 返回指定Double的负数
	 */
	public static Double neg(Double v1) {
		return sub(new Double(0), v1);
	}

	/**
	 * 对double数据进行取精度.
	 * 
	 * @param value
	 *            double数据.
	 * @param scale
	 *            精度位数(保留的小数位数).
	 * @param roundingMode
	 *            精度取值方式.
	 * @return 精度计算后的数据.
	 */
	public static double round(double value, int scale, int roundingMode) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, roundingMode);
		double d = bd.doubleValue();
		bd = null;
		return d;
	}

	/**
	 * 数组 全加
	 * 
	 * @param arry
	 * @return
	 */
	public static int sumArry(int[] arry) {
		int sum = 0;
		if (arry != null) {
			for (int a : arry) {
				sum += a;
			}
		}
		return sum;
	}

	/**
	 * 大于0,非空
	 * 
	 * @param num
	 * @return
	 */
	public static boolean greaterThanZero(Long num) {
		if (num != null && num > 0) {
			return true;
		}
		return false;
	}

	public static String intToString(Integer i) {
		if (i != null) {
			return i.toString();
		}
		return 0 + "";
	}
}