package com.coe.wms.model.user;

import java.io.Serializable;

/**
 * 用户角色表
 * 
 * @author Administrator
 * 
 */
public class UserRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8001556626352868665L;
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 角色id
	 */
	private Long roleId;

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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
