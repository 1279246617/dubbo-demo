package com.coe.wms.facade.symgmt.service.impl;

import org.mybatis.plugin.model.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coe.wms.facade.symgmt.entity.Admin;
import com.coe.wms.facade.symgmt.service.AdminService;
import com.coe.wms.service.symgmt.biz.AdminBiz;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminBiz adminBiz;

	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	public Admin add(Admin record) {
		return this.adminBiz.add(record);
	}

	public boolean delete(Long id) {
		return this.adminBiz.delete(id);
	}

	public Admin update(Admin record) {
		return this.adminBiz.update(record);
	}

	public Admin get(Long id) {
		return this.adminBiz.get(id);
	}

	public Pager<Admin> list(int page, int limit) {
		return this.adminBiz.list(page, limit);
	}
}