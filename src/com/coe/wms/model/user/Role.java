package com.coe.wms.model.user;

import java.io.Serializable;

public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8697896112284244168L;

	/**
	 * 角色id 主键
	 */
	private Long id;

	/**
	 * 角色名
	 */
	private String roleName;

	/**
	 * 角色描述
	 */
	private String description;
	
	/**
	 * 创建时间
	 */
	private Long createdTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

}
