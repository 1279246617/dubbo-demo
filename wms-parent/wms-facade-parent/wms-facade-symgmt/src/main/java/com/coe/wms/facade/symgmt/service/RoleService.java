package com.coe.wms.facade.symgmt.service;

import com.coe.wms.facade.symgmt.entity.Role;
import org.mybatis.plugin.model.Pager;

public interface RoleService {
    Role add(Role record);

    boolean delete(Long id);

    Role update(Role record);

    Role get(Long id);

    Pager<Role> list(int page, int limit);
}