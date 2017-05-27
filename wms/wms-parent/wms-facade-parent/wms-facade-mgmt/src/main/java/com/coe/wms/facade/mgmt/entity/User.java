package com.coe.wms.facade.mgmt.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Long id;

    private String userCode;

    private String userName;

    private String userPwd;

    private String userPhone;

    private String userEmail;

    private String dictIsEmp;

    private String dictSex;

    private String dictPosStatus;

    private Long siteId;

    private String siteCode;

    private String dictDept;

    private String dictPosition;

    private String dictIsEnableRight;

    private String createOperator;

    private Date createTime;

    private String updateOperator;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public User() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getDictIsEmp() {
        return dictIsEmp;
    }

    public void setDictIsEmp(String dictIsEmp) {
        this.dictIsEmp = dictIsEmp == null ? null : dictIsEmp.trim();
    }

    public String getDictSex() {
        return dictSex;
    }

    public void setDictSex(String dictSex) {
        this.dictSex = dictSex == null ? null : dictSex.trim();
    }

    public String getDictPosStatus() {
        return dictPosStatus;
    }

    public void setDictPosStatus(String dictPosStatus) {
        this.dictPosStatus = dictPosStatus == null ? null : dictPosStatus.trim();
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode == null ? null : siteCode.trim();
    }

    public String getDictDept() {
        return dictDept;
    }

    public void setDictDept(String dictDept) {
        this.dictDept = dictDept == null ? null : dictDept.trim();
    }

    public String getDictPosition() {
        return dictPosition;
    }

    public void setDictPosition(String dictPosition) {
        this.dictPosition = dictPosition == null ? null : dictPosition.trim();
    }

    public String getDictIsEnableRight() {
        return dictIsEnableRight;
    }

    public void setDictIsEnableRight(String dictIsEnableRight) {
        this.dictIsEnableRight = dictIsEnableRight == null ? null : dictIsEnableRight.trim();
    }

    public String getCreateOperator() {
        return createOperator;
    }

    public void setCreateOperator(String createOperator) {
        this.createOperator = createOperator == null ? null : createOperator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateOperator() {
        return updateOperator;
    }

    public void setUpdateOperator(String updateOperator) {
        this.updateOperator = updateOperator == null ? null : updateOperator.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}