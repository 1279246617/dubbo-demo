package com.coe.wms.facade.symgmt.entity;

import java.io.Serializable;

public class Config implements Serializable {
    private Long id;

    private String sKey;

    private String sValue;

    private String identification;

    private static final long serialVersionUID = 1L;

    public Config() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getsKey() {
        return sKey;
    }

    public void setsKey(String sKey) {
        this.sKey = sKey == null ? null : sKey.trim();
    }

    public String getsValue() {
        return sValue;
    }

    public void setsValue(String sValue) {
        this.sValue = sValue == null ? null : sValue.trim();
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification == null ? null : identification.trim();
    }
}