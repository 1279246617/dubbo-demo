package com.coe.wms.facade.symgmt.service;

import com.coe.wms.facade.symgmt.entity.Admin;
import org.mybatis.plugin.model.Pager;

public interface AdminService {
    Admin add(Admin record);

    boolean delete(Long id);

    Admin update(Admin record);

    Admin get(Long id);

    Pager<Admin> list(int page, int limit);
}