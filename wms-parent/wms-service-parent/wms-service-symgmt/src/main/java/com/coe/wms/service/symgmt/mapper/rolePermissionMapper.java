package com.coe.wms.service.symgmt.mapper;

import com.coe.wms.facade.symgmt.entity.rolePermission;
import com.coe.wms.facade.symgmt.entity.rolePermissionCriteria;

import java.util.List;

public interface rolePermissionMapper {
    int deleteByCondition(rolePermissionCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(rolePermission record);

    List<rolePermission> selectByConditionList(rolePermissionCriteria condition);

    rolePermission selectByPrimaryKey(Long id);

    int countByCondition(rolePermissionCriteria condition);

    int updateByPrimaryKeySelective(rolePermission record);
}