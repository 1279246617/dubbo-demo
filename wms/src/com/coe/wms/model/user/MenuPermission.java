package com.coe.wms.model.user;

import java.io.Serializable;

/**
 * 菜单权限表
 * 
 * @author Administrator
 * 
 */
public class MenuPermission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2221781358032129227L;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	private Long id;
	/**
	 * 菜单id
	 */
	private Long menuId;
	/**
	 * 权限代码
	 */
	private String permissionCode;
}
