package com.coe.wms.facade.symgmt.service.impl;

import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coe.wms.facade.symgmt.criteria.WarehouseCriteria;
import com.coe.wms.facade.symgmt.criteria.WarehouseCriteria.Criteria;
import com.coe.wms.facade.symgmt.entity.Warehouse;
import com.coe.wms.facade.symgmt.service.WarehouseService;
import com.coe.wms.service.symgmt.mapper.WarehouseMapper;

@Service("warehouseService")
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private WarehouseMapper warehouseMapper;

    private static final Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);

    public Warehouse add(Warehouse record) {
        if(this.warehouseMapper.insertSelective(record)==1)
        	return record; 
        return null;
    }

    public boolean delete(Long id) {
        return this.warehouseMapper.deleteByPrimaryKey(id)==1;
    }

    public Warehouse update(Warehouse record) {
        if(this.warehouseMapper.updateByPrimaryKeySelective(record)==1)
        	return record;
        return null;
    }

    public Warehouse get(Long id) {
        return this.warehouseMapper.selectByPrimaryKey(id);
    }

    public Pager<Warehouse> list(int page, int limit) {
        WarehouseCriteria criteria = new WarehouseCriteria();
        criteria.setPage(page);
        criteria.setLimit(limit);
        Criteria cri = criteria.createCriteria();
        List<Warehouse> list = warehouseMapper.selectByConditionList(criteria);
        return PagerUtil.getPager(list, criteria);
    }
}