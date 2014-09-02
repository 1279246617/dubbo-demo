package com.coe.wms.model.user;

import java.io.Serializable;

/**
 * 角色权限表
 * 
 * @author Administrator
 * 
 */
public class RolePermission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1202084852437066225L;
	private Long id;
	/**
	 * 角色id
	 */
	private Long roleId;
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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}
}
