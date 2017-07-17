package com.coe.wms.facade.symgmt.service.impl;

import com.coe.wms.facade.symgmt.entity.Admin;
import com.coe.wms.facade.symgmt.entity.AdminCriteria;
import com.coe.wms.facade.symgmt.entity.AdminCriteria.Criteria;
import com.coe.wms.facade.symgmt.service.AdminService;
import com.coe.wms.service.symgmt.mapper.AdminMapper;

import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    public Admin add(Admin record) {
        if(this.adminMapper.insertSelective(record)==1)
        	return record; 
        return null;
    }

    public boolean delete(Long id) {
        return this.adminMapper.deleteByPrimaryKey(id)==1;
    }

    public Admin update(Admin record) {
        if(this.adminMapper.updateByPrimaryKeySelective(record)==1)
        	return record;
        return null;
    }

    public Admin get(Long id) {
        return this.adminMapper.selectByPrimaryKey(id);
    }

    public Pager<Admin> list(int page, int limit) {
        AdminCriteria criteria = new AdminCriteria();
        criteria.setPage(page);
        criteria.setLimit(limit);
        Criteria cri = criteria.createCriteria();
        List<Admin> list = adminMapper.selectByConditionList(criteria);
        return PagerUtil.getPager(list, criteria);
    }
}