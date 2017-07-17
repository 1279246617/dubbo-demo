package com.coe.wms.facade.symgmt.entity;

import java.io.Serializable;

public class rolePermission implements Serializable {
    private Long id;

    private Long menuId;

    private Long roleId;

    private static final long serialVersionUID = 1L;

    public rolePermission() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}