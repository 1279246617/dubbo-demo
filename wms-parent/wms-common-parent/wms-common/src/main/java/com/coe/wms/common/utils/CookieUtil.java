package com.coe.wms.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie工具类
 * 
 * @ClassName: CookieUtil
 * @author yechao
 * @date 2017年5月4日 上午9:32:55
 * @Description: TODO
 */
public class CookieUtil {

	private CookieUtil() {

	}

	/**
	 * 添加一个新Cookie
	 * 
	 * @author zifangsky
	 * @param response
	 *            HttpServletResponse
	 * @param cookieName
	 *            cookie名称
	 * @param cookieValue
	 *            cookie值
	 * @param domain
	 *            cookie所属的子域
	 * @param httpOnly
	 *            是否将cookie设置成HttpOnly
	 * @param maxAge
	 *            设置cookie的最大生存期
	 * @param path
	 *            设置cookie路径
	 * @param secure
	 *            是否只允许HTTPS访问
	 * 
	 * @return null
	 */
	public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, String domain, boolean httpOnly, int maxAge,
			String path, boolean secure) {
		if (StringUtil.isEmpty(cookieName)) {
			return;
		}
		if (cookieValue == null) {
			cookieValue = "";
		}

		Cookie cookie = new Cookie(cookieName, cookieValue);
		if (domain != null) {
			cookie.setDomain(domain);
		}
		cookie.setHttpOnly(httpOnly);

		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}

		cookie.setPath(path);
		if (path == null) {
			cookie.setPath("/");
		}
		cookie.setSecure(secure);
		response.addCookie(cookie);
	}

	/**
	 * 添加cookie
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}

	/**
	 * 删除cookie
	 * 
	 * @param response
	 * @param name
	 */
	public static void removeCookie(HttpServletResponse response, String name) {
		Cookie uid = new Cookie(name, null);
		uid.setPath("/");
		uid.setMaxAge(0);
		response.addCookie(uid);
	}

	/**
	 * 获取cookie值
	 * 
	 * @param request
	 * @return
	 */
	public static String getValue(HttpServletRequest request, String cookieName) {
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
