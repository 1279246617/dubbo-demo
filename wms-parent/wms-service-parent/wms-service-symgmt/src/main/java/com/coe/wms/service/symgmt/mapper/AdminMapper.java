package com.coe.wms.service.symgmt.mapper;

import java.util.List;

import com.coe.wms.facade.symgmt.criteria.AdminCriteria;
import com.coe.wms.facade.symgmt.entity.Admin;

public interface AdminMapper {
    int deleteByCondition(AdminCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Admin record);

    List<Admin> selectByConditionList(AdminCriteria condition);

    Admin selectByPrimaryKey(Long id);

    int countByCondition(AdminCriteria condition);

    int updateByPrimaryKeySelective(Admin record);
}