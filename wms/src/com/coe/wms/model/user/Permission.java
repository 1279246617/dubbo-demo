package com.coe.wms.model.user;

import java.io.Serializable;

public class Permission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5797010394083555574L;

	/**
	 * 权限id 主键
	 */
	private Long id;

	/**
	 * 权限代码
	 */
	private String permissionCode;

	/**
	 * 全选名称
	 */
	private String permissionName;

	/**
	 * 父权限代码
	 */
	private String parentCode;

	/**
	 * 1 :允许授权 2：不允许授权 6：不允许授权但默认已有权限
	 */
	private Integer isAuthorize;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Integer getIsAuthorize() {
		return isAuthorize;
	}

	public void setIsAuthorize(Integer isAuthorize) {
		this.isAuthorize = isAuthorize;
	}
}
