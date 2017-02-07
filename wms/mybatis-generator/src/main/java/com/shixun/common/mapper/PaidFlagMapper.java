package com.shixun.common.mapper;

import com.shixun.common.entity.PaidFlag;
import com.shixun.common.entity.PaidFlagCriteria;
import java.util.List;

public interface PaidFlagMapper {
    int deleteByCondition(PaidFlagCriteria condition);

    int deleteByPrimaryKey(String id);

    int insertSelective(PaidFlag record);

    List<PaidFlag> selectByConditionList(PaidFlagCriteria condition);

    PaidFlag selectByPrimaryKey(String id);

    int countByCondition(PaidFlagCriteria condition);

    int updateByPrimaryKeySelective(PaidFlag record);
}