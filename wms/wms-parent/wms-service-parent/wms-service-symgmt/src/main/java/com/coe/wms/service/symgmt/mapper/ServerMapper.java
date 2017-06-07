package com.coe.wms.service.symgmt.mapper;

import java.util.List;

import com.coe.wms.facade.symgmt.criteria.ServerCriteria;
import com.coe.wms.facade.symgmt.entity.Server;

public interface ServerMapper {
    int deleteByCondition(ServerCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Server record);

    List<Server> selectByConditionList(ServerCriteria condition);

    Server selectByPrimaryKey(Long id);

    int countByCondition(ServerCriteria condition);

    int updateByPrimaryKeySelective(Server record);
}