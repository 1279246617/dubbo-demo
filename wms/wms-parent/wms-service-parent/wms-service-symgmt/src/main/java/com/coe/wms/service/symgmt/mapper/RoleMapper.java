package com.coe.wms.service.symgmt.mapper;

import com.coe.wms.facade.symgmt.entity.Role;
import com.coe.wms.facade.symgmt.entity.RoleCriteria;

import java.util.List;

public interface RoleMapper {
    int deleteByCondition(RoleCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Role record);

    List<Role> selectByConditionList(RoleCriteria condition);

    Role selectByPrimaryKey(Long id);

    int countByCondition(RoleCriteria condition);

    int updateByPrimaryKeySelective(Role record);
}