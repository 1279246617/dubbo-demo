package com.coe.wms.facade.symgmt.service;

import org.mybatis.plugin.model.Pager;

import com.coe.wms.facade.symgmt.entity.Admin;

public interface IAdminFacade {

	Admin add(Admin record);

	boolean delete(Long id);

	Admin update(Admin record);

	Admin get(Long id);

	Pager<Admin> list(int page, int limit);
}