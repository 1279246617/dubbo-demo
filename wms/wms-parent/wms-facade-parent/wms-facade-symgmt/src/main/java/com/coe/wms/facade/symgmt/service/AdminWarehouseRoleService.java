package com.coe.wms.facade.symgmt.service;

import com.coe.wms.facade.symgmt.entity.AdminWarehouseRole;
import org.mybatis.plugin.model.Pager;

public interface AdminWarehouseRoleService {
    AdminWarehouseRole add(AdminWarehouseRole record);

    boolean delete(Long id);

    AdminWarehouseRole update(AdminWarehouseRole record);

    AdminWarehouseRole get(Long id);

    Pager<AdminWarehouseRole> list(int page, int limit);
}