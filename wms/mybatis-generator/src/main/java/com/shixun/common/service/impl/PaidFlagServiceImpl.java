package com.shixun.common.service.impl;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import com.shixun.common.entity.PaidFlag;
import com.shixun.common.entity.PaidFlagCriteria.Criteria;
import com.shixun.common.entity.PaidFlagCriteria;
import com.shixun.common.mapper.PaidFlagMapper;
import com.shixun.common.service.PaidFlagService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("paidFlagService")
public class PaidFlagServiceImpl implements PaidFlagService {
    @Autowired
    private PaidFlagMapper paidFlagMapper;

    private static final Logger logger = LoggerFactory.getLogger(PaidFlagServiceImpl.class);

    public PaidFlag add(PaidFlag record) {
        if(this.paidFlagMapper.insertSelective(record)==1)
        	return record; 
        return null;
    }

    public boolean delete(String id) {
        return this.paidFlagMapper.deleteByPrimaryKey(id)==1;
    }

    public PaidFlag update(PaidFlag record) {
        if(this.paidFlagMapper.updateByPrimaryKeySelective(record)==1)
        	return record;
        return null;
    }

    public PaidFlag get(String id) {
        return this.paidFlagMapper.selectByPrimaryKey(id);
    }

    public Pager<PaidFlag> list(int page, int limit) {
        PaidFlagCriteria criteria = new PaidFlagCriteria();
        criteria.setPage(page);
        criteria.setLimit(limit);
        Criteria cri = criteria.createCriteria();
        List<PaidFlag> list = paidFlagMapper.selectByConditionList(criteria);
        return PagerUtil.getPager(list, criteria);
    }
}