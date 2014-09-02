package com.coe.wms.model.user;

import java.io.Serializable;

/**
 * 用户权限表
 * 
 * @author Administrator
 * 
 */
public class UserPermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5535684610017545209L;
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 权限代码
	 */
	private String permissionCode;

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

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}
}
