package com.coe.wms.service.symgmt.mapper;

import com.coe.wms.facade.symgmt.entity.Server;
import com.coe.wms.facade.symgmt.entity.ServerCriteria;

import java.util.List;

public interface ServerMapper {
    int deleteByCondition(ServerCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Server record);

    List<Server> selectByConditionList(ServerCriteria condition);

    Server selectByPrimaryKey(Long id);

    int countByCondition(ServerCriteria condition);

    int updateByPrimaryKeySelective(Server record);
}