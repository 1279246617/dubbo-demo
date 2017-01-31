/**
 *2016年8月10日
 *yechao
 */
package com.coe.wms.common.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

/**
 * @author yechao
 * 
 */
public class EnCodeUtil {
	private static final char[] HEX_LOOKUP_STRING = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 字符串 转 urlEncode
	 * 
	 * @param str
	 * @param encoding
	 * @return
	 */
	public static String urlEncode(String str, String encoding) {
		try {
			return URLEncoder.encode(str, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * urlEncode转 字符串
	 * 
	 * @param str
	 * @param encoding
	 * @return
	 */
	public static String urlDeEncode(String str, String encoding) {
		try {
			return URLDecoder.decode(str, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String base64(String str) {
		try {
			BASE64Encoder base64en = new BASE64Encoder();
			String newstr = base64en.encode(str.getBytes());
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
	public final static String md5_32(String plainText) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5Str;
	}

	/**
	 * md5 16位加密方法
	 * 
	 */
	public final static String md5_16(String plainText) {
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
			md5Str = buf.toString().substring(8, 24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5Str;
	}

	/**
	 * 将字节数组转换为16进制字符串的形式.
	 */
	public static String bytesToHexStr(byte[] bcd) {
		StringBuffer s = new StringBuffer(bcd.length * 2);

		for (byte aBcd : bcd) {
			s.append(HEX_LOOKUP_STRING[(aBcd >>> 4) & 0x0f]);
			s.append(HEX_LOOKUP_STRING[aBcd & 0x0f]);
		}

		return s.toString();
	}

	/**
	 * 将16进制字符串还原为字节数组.
	 */
	public static byte[] hexStrToBytes(String s) {
		byte[] bytes;

		bytes = new byte[s.length() / 2];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
		}

		return bytes;
	}

}
