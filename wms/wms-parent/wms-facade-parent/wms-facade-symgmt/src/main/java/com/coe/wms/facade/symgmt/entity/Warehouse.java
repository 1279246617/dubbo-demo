package com.coe.wms.facade.symgmt.entity;

import java.io.Serializable;

public class Warehouse implements Serializable {
    private Long id;

    private String whseName;

    private String whseCode;

    private static final long serialVersionUID = 1L;

    public Warehouse() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWhseName() {
        return whseName;
    }

    public void setWhseName(String whseName) {
        this.whseName = whseName == null ? null : whseName.trim();
    }

    public String getWhseCode() {
        return whseCode;
    }

    public void setWhseCode(String whseCode) {
        this.whseCode = whseCode == null ? null : whseCode.trim();
    }
}