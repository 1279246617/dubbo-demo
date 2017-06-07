package com.coe.wms.service.symgmt.mapper;

import java.util.List;

import com.coe.wms.facade.symgmt.criteria.WarehouseCriteria;
import com.coe.wms.facade.symgmt.entity.Warehouse;

public interface WarehouseMapper {
    int deleteByCondition(WarehouseCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Warehouse record);

    List<Warehouse> selectByConditionList(WarehouseCriteria condition);

    Warehouse selectByPrimaryKey(Long id);

    int countByCondition(WarehouseCriteria condition);

    int updateByPrimaryKeySelective(Warehouse record);
}