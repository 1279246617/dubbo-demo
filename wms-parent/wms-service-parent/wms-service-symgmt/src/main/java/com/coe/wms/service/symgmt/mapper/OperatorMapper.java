package com.coe.wms.service.symgmt.mapper;

import com.coe.wms.facade.symgmt.entity.Operator;
import com.coe.wms.facade.symgmt.entity.OperatorCriteria;

import java.util.List;

public interface OperatorMapper {
    int deleteByCondition(OperatorCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Operator record);

    List<Operator> selectByConditionList(OperatorCriteria condition);

    Operator selectByPrimaryKey(Long id);

    int countByCondition(OperatorCriteria condition);

    int updateByPrimaryKeySelective(Operator record);
}