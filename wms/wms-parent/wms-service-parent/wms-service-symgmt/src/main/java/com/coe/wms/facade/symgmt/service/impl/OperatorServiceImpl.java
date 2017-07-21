package com.coe.wms.facade.symgmt.service.impl;

import com.coe.wms.facade.symgmt.entity.Operator;
import com.coe.wms.facade.symgmt.entity.OperatorCriteria;
import com.coe.wms.facade.symgmt.entity.OperatorCriteria.Criteria;
import com.coe.wms.facade.symgmt.service.OperatorService;
import com.coe.wms.service.symgmt.mapper.OperatorMapper;

import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("operatorService")
@com.alibaba.dubbo.config.annotation.Service
public class OperatorServiceImpl implements OperatorService {
    @Autowired
    private OperatorMapper operatorMapper;

    private static final Logger logger = LoggerFactory.getLogger(OperatorServiceImpl.class);

    public Operator add(Operator record) {
        if(this.operatorMapper.insertSelective(record)==1)
        	return record; 
        return null;
    }

    public boolean delete(Long id) {
        return this.operatorMapper.deleteByPrimaryKey(id)==1;
    }

    public Operator update(Operator record) {
        if(this.operatorMapper.updateByPrimaryKeySelective(record)==1)
        	return record;
        return null;
    }

    public Operator get(Long id) {
        return this.operatorMapper.selectByPrimaryKey(id);
    }

    public Pager<Operator> list(int page, int limit) {
        OperatorCriteria criteria = new OperatorCriteria();
        criteria.setPage(page);
        criteria.setLimit(limit);
        Criteria cri = criteria.createCriteria();
        List<Operator> list = operatorMapper.selectByConditionList(criteria);
        return PagerUtil.getPager(list, criteria);
    }
}