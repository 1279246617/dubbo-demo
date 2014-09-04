package com.coe.wms.model.user;

import java.io.Serializable;

import com.google.code.ssm.api.CacheKeyMethod;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2687493146734506503L;

	/**
	 * 被删除的
	 */
	public static final Integer STATUS_DELETE = -1;

	/**
	 * 没问题的状态
	 */
	public static final Integer STATUS_OK = 1;

	/**
	 * 冻结,不允许登录
	 */
	public static final Integer STATUS_FREEZE = 2;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 父用户id
	 */
	private Long parentId;
	/**
	 * 登录帐号名
	 */
	private String loginName;

	/**
	 * 登录密码
	 */
	private String password;

	/**
	 * 用户姓名
	 */
	private String userName;

	/**
	 * 用户类型. 简单区分是客户还是操作员. 用于登录时 跳转到不同的首页. 与具体权限无关
	 */
	private String userType;

	/**
	 * 电话
	 */
	private String phone;

	private String email;

	private Long createdTime;

	/**
	 * 1正常 -1删除 2冻结
	 */
	private Integer status;

	@CacheKeyMethod
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
