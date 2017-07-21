package com.coe.wms.service.symgmt.mapper;

import com.coe.wms.facade.symgmt.entity.Config;
import com.coe.wms.facade.symgmt.entity.ConfigCriteria;

import java.util.List;

public interface ConfigMapper {
    int deleteByCondition(ConfigCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Config record);

    List<Config> selectByConditionList(ConfigCriteria condition);

    Config selectByPrimaryKey(Long id);

    int countByCondition(ConfigCriteria condition);

    int updateByPrimaryKeySelective(Config record);
}