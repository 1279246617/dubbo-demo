package com.coe.wms.service.symgmt.mapper;

import com.coe.wms.facade.symgmt.entity.Menu;
import com.coe.wms.facade.symgmt.entity.MenuCriteria;

import java.util.List;

public interface MenuMapper {
    int deleteByCondition(MenuCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Menu record);

    List<Menu> selectByConditionList(MenuCriteria condition);

    Menu selectByPrimaryKey(Long id);

    int countByCondition(MenuCriteria condition);

    int updateByPrimaryKeySelective(Menu record);
}