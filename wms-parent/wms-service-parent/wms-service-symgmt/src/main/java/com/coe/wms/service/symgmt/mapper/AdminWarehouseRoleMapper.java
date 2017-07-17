package com.coe.wms.service.symgmt.mapper;

import com.coe.wms.facade.symgmt.entity.AdminWarehouseRole;
import com.coe.wms.facade.symgmt.entity.AdminWarehouseRoleCriteria;

import java.util.List;

public interface AdminWarehouseRoleMapper {
    int deleteByCondition(AdminWarehouseRoleCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(AdminWarehouseRole record);

    List<AdminWarehouseRole> selectByConditionList(AdminWarehouseRoleCriteria condition);

    AdminWarehouseRole selectByPrimaryKey(Long id);

    int countByCondition(AdminWarehouseRoleCriteria condition);

    int updateByPrimaryKeySelective(AdminWarehouseRole record);
}