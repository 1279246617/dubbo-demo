package com.coe.wms.facade.symgmt.entity;

import java.io.Serializable;

public class AdminWarehouseRole implements Serializable {
    private Long id;

    private Long warehouseId;

    private Long adminId;

    private Long roleId;

    private static final long serialVersionUID = 1L;

    public AdminWarehouseRole() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}