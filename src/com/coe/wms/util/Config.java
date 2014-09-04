package com.coe.wms.util;

import org.springframework.stereotype.Component;

@Component("config")
public class Config {

	private Integer cookieMaxAgeLoginName;

	private Integer cookieMaxAgeLoginPassword;

	private Integer cookieMaxAgeRememberMe;

	/**
	 * session过期时间 单位秒
	 */
	private Integer sessionMaxInactiveInterval;

	public Integer getCookieMaxAgeLoginName() {
		return cookieMaxAgeLoginName;
	}

	public void setCookieMaxAgeLoginName(Integer cookieMaxAgeLoginName) {
		this.cookieMaxAgeLoginName = cookieMaxAgeLoginName;
	}

	public Integer getCookieMaxAgeLoginPassword() {
		return cookieMaxAgeLoginPassword;
	}

	public void setCookieMaxAgeLoginPassword(Integer cookieMaxAgeLoginPassword) {
		this.cookieMaxAgeLoginPassword = cookieMaxAgeLoginPassword;
	}

	public Integer getCookieMaxAgeRememberMe() {
		return cookieMaxAgeRememberMe;
	}

	public void setCookieMaxAgeRememberMe(Integer cookieMaxAgeRememberMe) {
		this.cookieMaxAgeRememberMe = cookieMaxAgeRememberMe;
	}

	public Integer getSessionMaxInactiveInterval() {
		return sessionMaxInactiveInterval;
	}

	public void setSessionMaxInactiveInterval(Integer sessionMaxInactiveInterval) {
		this.sessionMaxInactiveInterval = sessionMaxInactiveInterval;
	}
}
