package com.coe.wms.service.symgmt.mapper;

import com.coe.wms.facade.symgmt.entity.Admin;
import com.coe.wms.facade.symgmt.entity.AdminCriteria;

import java.util.List;

public interface AdminMapper {
    int deleteByCondition(AdminCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Admin record);

    List<Admin> selectByConditionList(AdminCriteria condition);

    Admin selectByPrimaryKey(Long id);

    int countByCondition(AdminCriteria condition);

    int updateByPrimaryKeySelective(Admin record);
}