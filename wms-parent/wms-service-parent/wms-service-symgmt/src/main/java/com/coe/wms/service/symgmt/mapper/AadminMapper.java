package com.coe.wms.service.symgmt.mapper;

import java.util.List;

import com.coe.wms.facade.symgmt.criteria.AadminCriteria;
import com.coe.wms.facade.symgmt.entity.Aadmin;

public interface AadminMapper {
    int deleteByCondition(AadminCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Aadmin record);

    List<Aadmin> selectByConditionList(AadminCriteria condition);

    Aadmin selectByPrimaryKey(Long id);

    int countByCondition(AadminCriteria condition);

    int updateByPrimaryKeySelective(Aadmin record);
}