package com.coe.wms.facade.symgmt.service.impl;

import com.coe.wms.facade.symgmt.entity.Role;
import com.coe.wms.facade.symgmt.entity.RoleCriteria;
import com.coe.wms.facade.symgmt.entity.RoleCriteria.Criteria;
import com.coe.wms.facade.symgmt.service.RoleService;
import com.coe.wms.service.symgmt.mapper.RoleMapper;

import java.util.Date;
import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    public Role add(Role record) {
        record.setCreateTime(System.currentTimeMillis());
        if(this.roleMapper.insertSelective(record)==1)
        	return record; 
        return null;
    }

    public boolean delete(Long id) {
        return this.roleMapper.deleteByPrimaryKey(id)==1;
    }

    public Role update(Role record) {
        record.setUpdateTime(System.currentTimeMillis());
        if(this.roleMapper.updateByPrimaryKeySelective(record)==1)
        	return record;
        return null;
    }

    public Role get(Long id) {
        return this.roleMapper.selectByPrimaryKey(id);
    }

    public Pager<Role> list(int page, int limit) {
        RoleCriteria criteria = new RoleCriteria();
        criteria.setPage(page);
        criteria.setLimit(limit);
        Criteria cri = criteria.createCriteria();
        List<Role> list = roleMapper.selectByConditionList(criteria);
        return PagerUtil.getPager(list, criteria);
    }
}