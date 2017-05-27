package com.coe.wms.facade.mgmt.resultvo;

import org.mybatis.plugin.model.QueryParam;

public class UserResultVo extends QueryParam {
	private static final long serialVersionUID = -2058349581487989481L;

	private String id;

	private String userCode;

	private String userName;

	private String userPwd;

	private String userPhone;

	private String userEmail;

	private String isEmp;

	private String sex;

	private String posStatus;

	private String siteCode;

	private String dept;

	private String pos;// 职位

	private String password;

	private String createOperator;

	private String createTime;

	private String updateOperator;

	private String updateTime;

	private String isEnableRight;

	private String userGroup;
	
	private String groupName;

	public String getPosStatus() {
		return posStatus;
	}

	public void setPosStatus(String posStatus) {
		this.posStatus = posStatus;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getIsEnableRight() {
		return isEnableRight;
	}

	public void setIsEnableRight(String isEnableRight) {
		this.isEnableRight = isEnableRight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getIsEmp() {
		return isEmp;
	}

	public void setIsEmp(String isEmp) {
		this.isEmp = isEmp;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getCreateOperator() {
		return createOperator;
	}

	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateOperator() {
		return updateOperator;
	}

	public void setUpdateOperator(String updateOperator) {
		this.updateOperator = updateOperator;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
