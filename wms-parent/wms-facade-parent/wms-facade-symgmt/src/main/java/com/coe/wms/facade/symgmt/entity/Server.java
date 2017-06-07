package com.coe.wms.facade.symgmt.entity;

import java.io.Serializable;

public class Server implements Serializable {
    private Long id;

    private String serverCode;

    private String serverName;

    private String serverIndexUrl;

    private static final long serialVersionUID = 1L;

    public Server() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerCode() {
        return serverCode;
    }

    public void setServerCode(String serverCode) {
        this.serverCode = serverCode == null ? null : serverCode.trim();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public String getServerIndexUrl() {
        return serverIndexUrl;
    }

    public void setServerIndexUrl(String serverIndexUrl) {
        this.serverIndexUrl = serverIndexUrl == null ? null : serverIndexUrl.trim();
    }
}