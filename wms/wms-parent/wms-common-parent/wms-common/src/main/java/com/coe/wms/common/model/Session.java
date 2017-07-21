package com.coe.wms.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 封装会话
 * 
 * @ClassName: Session
 * @author yechao
 * @date 2017年5月3日 上午9:36:58
 * @Description: TODO
 */
public class Session implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -5831560176413366670L;
	// 角色key
	public static final String USER_ROLE_KEY = "USER_ROLE_KEY";

	public static final String SESSION_ID_KEY = "SESSION_ID";

	public static final String SESSION_TOKEN_KEY = "SESSION_TOKEN";

	public static final String USER_IDENTIFICATION = "USER_IDENTIFICATION";// 用户标识是否登录

	public static final String USER_ADMINVO_KEY = "USER_VO";

	public static final String REFRESH_TIME = "REFRESH_TIME";

	/** cookie 过期时间 一年,约等于无限 */
	public final static int COOKIE_MAX_AGE = 60 * 60 * 24 * 30 * 12;

	/**
	 * session过期时间 2小时
	 * 
	 * 后续应改成由系统管理模块配置
	 */
	public final static int SESSION_MAX_AGE = 60 * 60 * 2;

	/**
	 * session 在redis 刷新时长,不得小于SESSION_MAX_AGE
	 * 
	 * 此时长保持为SESSION_MAX_AGE的一半即可
	 */
	public final static int SESSION_REFRESH_SECONDS = 60 * 60;

	/**
	 * 获取session id 在redis 存储的key
	 * 
	 * @return
	 */
	public static String getIdRedisKey(String id) {
		return Session.SESSION_ID_KEY + id;
	}

	/**
	 * 会话id
	 */
	private String id;
	/**
	 * 令牌 不定时刷新令牌,重设cookie,拦截器每次验证会话id和令牌
	 */
	private String token;

	/**
	 * 登录成功时间
	 */
	private Date signInTime;

	/**
	 * 最后刷新时间 (在redis刷新的时间)
	 */
	private Date refreshTime;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 用户名字
	 */
	private String userName;

	/**
	 * 用户账号
	 */
	private String userCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
	}

	public Date getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
