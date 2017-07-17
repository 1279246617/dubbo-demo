package com.coe.wms.facade.symgmt.service;

import com.coe.wms.facade.symgmt.entity.rolePermission;
import org.mybatis.plugin.model.Pager;

public interface rolePermissionService {
    rolePermission add(rolePermission record);

    boolean delete(Long id);

    rolePermission update(rolePermission record);

    rolePermission get(Long id);

    Pager<rolePermission> list(int page, int limit);
}