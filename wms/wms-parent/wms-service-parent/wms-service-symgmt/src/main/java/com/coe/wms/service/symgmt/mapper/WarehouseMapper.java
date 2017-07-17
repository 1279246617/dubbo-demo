package com.coe.wms.service.symgmt.mapper;

import com.coe.wms.facade.symgmt.entity.Warehouse;
import com.coe.wms.facade.symgmt.entity.WarehouseCriteria;

import java.util.List;

public interface WarehouseMapper {
    int deleteByCondition(WarehouseCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Warehouse record);

    List<Warehouse> selectByConditionList(WarehouseCriteria condition);

    Warehouse selectByPrimaryKey(Long id);

    int countByCondition(WarehouseCriteria condition);

    int updateByPrimaryKeySelective(Warehouse record);
}