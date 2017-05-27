package com.coe.wms.facade.mgmt.queryvo;

import org.mybatis.plugin.model.QueryParam;

public class UserQueryVo extends QueryParam{

	private static final long serialVersionUID = 3672597090494694395L;

	private String userCode;//用户code

    private String userName;//用户姓名
    
    private String dictStatus;//职位状态

    private String siteCode;//所属站点

    private String dictDept;//所属部门

    private String dictPosition;//所属职位
    
    private String dictIsEnableRight;//权限启用?
    
    private String groupCode;//用户组 

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

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getDictStatus() {
		return dictStatus;
	}

	public void setDictStatus(String dictStatus) {
		this.dictStatus = dictStatus;
	}

	public String getDictDept() {
		return dictDept;
	}

	public void setDictDept(String dictDept) {
		this.dictDept = dictDept;
	}

	public String getDictPosition() {
		return dictPosition;
	}

	public void setDictPosition(String dictPosition) {
		this.dictPosition = dictPosition;
	}

	public String getDictIsEnableRight() {
		return dictIsEnableRight;
	}

	public void setDictIsEnableRight(String dictIsEnableRight) {
		this.dictIsEnableRight = dictIsEnableRight;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

}
