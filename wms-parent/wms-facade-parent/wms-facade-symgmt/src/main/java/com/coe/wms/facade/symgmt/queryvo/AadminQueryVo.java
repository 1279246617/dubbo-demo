package com.coe.wms.facade.symgmt.queryvo;

import java.util.Date;

import org.mybatis.plugin.model.QueryParam;

public class AadminQueryVo extends QueryParam {
	
	/**  
	* @Fields serialVersionUID : TODO  
	*/   
	private static final long serialVersionUID = -268326298282768868L;

	private Long id;

	private String userName;

	private String loginName;

	private String password;

	private Integer status;

	private Date createdTime;

	private Long createdByAdminId;

	private Date lastUpdatedTime;

	private Long lastsUpdatedByAdminId;

	public AadminQueryVo() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName == null ? null : loginName.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Long getCreatedByAdminId() {
		return createdByAdminId;
	}

	public void setCreatedByAdminId(Long createdByAdminId) {
		this.createdByAdminId = createdByAdminId;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public Long getLastsUpdatedByAdminId() {
		return lastsUpdatedByAdminId;
	}

	public void setLastsUpdatedByAdminId(Long lastsUpdatedByAdminId) {
		this.lastsUpdatedByAdminId = lastsUpdatedByAdminId;
	}
}
