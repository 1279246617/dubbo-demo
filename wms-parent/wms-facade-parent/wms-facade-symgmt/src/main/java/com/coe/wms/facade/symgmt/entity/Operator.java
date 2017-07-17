package com.coe.wms.facade.symgmt.entity;

import java.io.Serializable;

public class Operator implements Serializable {
    private Long id;

    private String userName;

    private String loginName;

    private String password;

    private Integer status;

    private Long createdTime;

    private Long createdByAdminId;

    private Long lastUpdatedTime;

    private Long lastsUpdatedByAdminId;

    private static final long serialVersionUID = 1L;

    public Operator() {
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

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getCreatedByAdminId() {
        return createdByAdminId;
    }

    public void setCreatedByAdminId(Long createdByAdminId) {
        this.createdByAdminId = createdByAdminId;
    }

    public Long getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Long lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Long getLastsUpdatedByAdminId() {
        return lastsUpdatedByAdminId;
    }

    public void setLastsUpdatedByAdminId(Long lastsUpdatedByAdminId) {
        this.lastsUpdatedByAdminId = lastsUpdatedByAdminId;
    }
}