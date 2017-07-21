package com.coe.wms.facade.symgmt.service.impl;

import com.coe.wms.facade.symgmt.entity.AdminWarehouseRole;
import com.coe.wms.facade.symgmt.entity.AdminWarehouseRoleCriteria;
import com.coe.wms.facade.symgmt.entity.AdminWarehouseRoleCriteria.Criteria;
import com.coe.wms.facade.symgmt.service.AdminWarehouseRoleService;
import com.coe.wms.service.symgmt.mapper.AdminWarehouseRoleMapper;

import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminWarehouseRoleService")
@com.alibaba.dubbo.config.annotation.Service
public class AdminWarehouseRoleServiceImpl implements AdminWarehouseRoleService {
    @Autowired
    private AdminWarehouseRoleMapper adminWarehouseRoleMapper;

    private static final Logger logger = LoggerFactory.getLogger(AdminWarehouseRoleServiceImpl.class);

    public AdminWarehouseRole add(AdminWarehouseRole record) {
        if(this.adminWarehouseRoleMapper.insertSelective(record)==1)
        	return record; 
        return null;
    }

    public boolean delete(Long id) {
        return this.adminWarehouseRoleMapper.deleteByPrimaryKey(id)==1;
    }

    public AdminWarehouseRole update(AdminWarehouseRole record) {
        if(this.adminWarehouseRoleMapper.updateByPrimaryKeySelective(record)==1)
        	return record;
        return null;
    }

    public AdminWarehouseRole get(Long id) {
        return this.adminWarehouseRoleMapper.selectByPrimaryKey(id);
    }

    public Pager<AdminWarehouseRole> list(int page, int limit) {
        AdminWarehouseRoleCriteria criteria = new AdminWarehouseRoleCriteria();
        criteria.setPage(page);
        criteria.setLimit(limit);
        Criteria cri = criteria.createCriteria();
        List<AdminWarehouseRole> list = adminWarehouseRoleMapper.selectByConditionList(criteria);
        return PagerUtil.getPager(list, criteria);
    }
}