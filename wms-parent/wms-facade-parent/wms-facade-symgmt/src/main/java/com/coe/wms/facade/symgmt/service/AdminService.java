package com.coe.wms.facade.symgmt.service;

import com.coe.wms.facade.symgmt.entity.Admin;
import com.coe.wms.facade.symgmt.entity.vo.AdminVo;

import org.mybatis.plugin.model.Pager;

public interface AdminService {
    Admin add(Admin record);

    boolean delete(Long id);

    Admin update(Admin record);

    Admin get(Long id);

    Pager<Admin> list(int page, int limit);
    /**
     * 登录
     * @param adminVo
     * @return
     */
    AdminVo login(AdminVo adminVo);
    
    /**
     * 根据前端查询条件查询
     * @param adminVo
     * @return
     */
    Pager<Admin> getFromFront(AdminVo adminVo);
    
    
    
}