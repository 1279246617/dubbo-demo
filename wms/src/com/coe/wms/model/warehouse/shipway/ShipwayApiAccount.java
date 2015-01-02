package com.coe.wms.model.warehouse.shipway;

import java.io.Serializable;

public class ShipwayApiAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4817739009191093096L;

	private Long id;
	/**
	 * 所属的用户id 如果没有用户id,表示是系统公用帐号
	 */
	private Long userId;

	private String shipwayCode;

	/**
	 * 申请单号 etk系统的登录帐号
	 */
	private String apiAccount;

	/**
	 * 在申请单号 etk系统需要的token
	 */
	private String token;

	/**
	 * 在申请单号 etk系统需要的tokenKey
	 */
	private String tokenKey;

	/**
	 * api url
	 */
	private String url;

	/**
	 * 附加字段1
	 */
	private String extra1;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getShipwayCode() {
		return shipwayCode;
	}

	public void setShipwayCode(String shipwayCode) {
		this.shipwayCode = shipwayCode;
	}

	public String getApiAccount() {
		return apiAccount;
	}

	public void setApiAccount(String apiAccount) {
		this.apiAccount = apiAccount;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExtra1() {
		return extra1;
	}

	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}
}
