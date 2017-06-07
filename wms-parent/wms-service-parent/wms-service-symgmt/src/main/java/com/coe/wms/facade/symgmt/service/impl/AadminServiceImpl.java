package com.coe.wms.facade.symgmt.service.impl;

import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coe.wms.facade.symgmt.criteria.AadminCriteria;
import com.coe.wms.facade.symgmt.criteria.AadminCriteria.Criteria;
import com.coe.wms.facade.symgmt.entity.Aadmin;
import com.coe.wms.facade.symgmt.service.AadminService;
import com.coe.wms.service.symgmt.mapper.AadminMapper;

@Service("aadminService")
public class AadminServiceImpl implements AadminService {
    @Autowired
    private AadminMapper aadminMapper;

    private static final Logger logger = LoggerFactory.getLogger(AadminServiceImpl.class);

    public Aadmin add(Aadmin record) {
        if(this.aadminMapper.insertSelective(record)==1)
        	return record; 
        return null;
    }

    public boolean delete(Long id) {
        return this.aadminMapper.deleteByPrimaryKey(id)==1;
    }

    public Aadmin update(Aadmin record) {
        if(this.aadminMapper.updateByPrimaryKeySelective(record)==1)
        	return record;
        return null;
    }

    public Aadmin get(Long id) {
        return this.aadminMapper.selectByPrimaryKey(id);
    }

    public Pager<Aadmin> list(int page, int limit) {
        AadminCriteria criteria = new AadminCriteria();
        criteria.setPage(page);
        criteria.setLimit(limit);
        Criteria cri = criteria.createCriteria();
        List<Aadmin> list = aadminMapper.selectByConditionList(criteria);
        return PagerUtil.getPager(list, criteria);
    }
}