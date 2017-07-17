package com.coe.wms.facade.symgmt.service.impl;

import com.coe.wms.facade.symgmt.entity.rolePermission;
import com.coe.wms.facade.symgmt.entity.rolePermissionCriteria;
import com.coe.wms.facade.symgmt.entity.rolePermissionCriteria.Criteria;
import com.coe.wms.facade.symgmt.service.rolePermissionService;
import com.coe.wms.service.symgmt.mapper.rolePermissionMapper;

import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rolePermissionService")
public class rolePermissionServiceImpl implements rolePermissionService {
    @Autowired
    private rolePermissionMapper rolePermissionMapper;

    private static final Logger logger = LoggerFactory.getLogger(rolePermissionServiceImpl.class);

    public rolePermission add(rolePermission record) {
        if(this.rolePermissionMapper.insertSelective(record)==1)
        	return record; 
        return null;
    }

    public boolean delete(Long id) {
        return this.rolePermissionMapper.deleteByPrimaryKey(id)==1;
    }

    public rolePermission update(rolePermission record) {
        if(this.rolePermissionMapper.updateByPrimaryKeySelective(record)==1)
        	return record;
        return null;
    }

    public rolePermission get(Long id) {
        return this.rolePermissionMapper.selectByPrimaryKey(id);
    }

    public Pager<rolePermission> list(int page, int limit) {
        rolePermissionCriteria criteria = new rolePermissionCriteria();
        criteria.setPage(page);
        criteria.setLimit(limit);
        Criteria cri = criteria.createCriteria();
        List<rolePermission> list = rolePermissionMapper.selectByConditionList(criteria);
        return PagerUtil.getPager(list, criteria);
    }
}