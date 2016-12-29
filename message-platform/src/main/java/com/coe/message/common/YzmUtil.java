package com.coe.message.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**验证码工具类*/
public class YzmUtil {
	private static List<Integer> letterList = new ArrayList<Integer>();
	private static String mixCode = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
	static {
		// A-Z
		for (int i = 65; i < 91; i++) {
			letterList.add(i);
		}
		// a-z
		for (int i = 97; i < 123; i++) {
			letterList.add(i);
		}
	}

	/**
	 * 随机生成字母验证码
	 * @param length 验证码长度
	 * @return 生成指定长度的验证码
	 */
	public static String getCodeForLetter(int length) {
		int size = letterList.size();
		StringBuffer codeBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			Random random = new Random();
			int letterInt = letterList.get(random.nextInt(size));
			codeBuffer.append((char) letterInt);
		}
		return codeBuffer.toString();
	}

	/**
	 * 生成数字和字母混合的验证码
	 * @param length
	 * @return
	 */
	public static String getMixCode(int length) {
		int len = mixCode.length();
		char[] charCode = new char[length];
		for (int i = 0; i < length; i++) {
			Random random = new Random();
			char c = mixCode.charAt(random.nextInt(len));
			charCode[i] = c;
		}
		return new String(charCode);
	}

	/**
	 * 随机生成数字验证码
	 * @param length 验证码长度
	 * @return 生成指定长度的验证码
	 */
	public static String getCodeForNumber(int length) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			Random random = new Random();
			// 取得随机数
			int rdGet;
			char ch;
			// 产生48到57的随机数(0-9的键位值)
			rdGet = Math.abs(random.nextInt()) % 10 + 48;
			// 产生97到122的随机数(a-z的键位值)
			// rdGet=Math.abs(random.nextInt())%26+97;
			ch = (char) rdGet;
			sBuffer.append(ch);
		}
		return sBuffer.toString();

	}

	/**
	 * 随机生成汉字验证码
	 * @param length 验证码长度
	 * @return 生成指定长度的验证码
	 */
	public static String getCodeForChinese(int length) {
		StringBuffer code = new StringBuffer();
		for (int i = 0; i < length; i++) {
			// 定义高低位
			int hightPos, lowPos;
			Random random = new Random();
			// 获取高位值
			hightPos = (176 + Math.abs(random.nextInt(39)));
			// 获取低位值
			lowPos = (161 + Math.abs(random.nextInt(93)));
			byte[] b = new byte[2];
			b[0] = (new Integer(hightPos).byteValue());
			b[1] = (new Integer(lowPos).byteValue());
			try {
				// 转成中文
				code.append(new String(b, "GBk"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return code.toString();
	}

	public static void main(String[] args) {
		System.out.println(getMixCode(6));
	}
}
