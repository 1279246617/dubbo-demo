package com.coe.wms.facade.symgmt.service.impl;

import com.coe.wms.facade.symgmt.entity.Server;
import com.coe.wms.facade.symgmt.entity.ServerCriteria;
import com.coe.wms.facade.symgmt.entity.ServerCriteria.Criteria;
import com.coe.wms.facade.symgmt.service.ServerService;
import com.coe.wms.service.symgmt.mapper.ServerMapper;

import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("serverService")
public class ServerServiceImpl implements ServerService {
    @Autowired
    private ServerMapper serverMapper;

    private static final Logger logger = LoggerFactory.getLogger(ServerServiceImpl.class);

    public Server add(Server record) {
        if(this.serverMapper.insertSelective(record)==1)
        	return record; 
        return null;
    }

    public boolean delete(Long id) {
        return this.serverMapper.deleteByPrimaryKey(id)==1;
    }

    public Server update(Server record) {
        if(this.serverMapper.updateByPrimaryKeySelective(record)==1)
        	return record;
        return null;
    }

    public Server get(Long id) {
        return this.serverMapper.selectByPrimaryKey(id);
    }

    public Pager<Server> list(int page, int limit) {
        ServerCriteria criteria = new ServerCriteria();
        criteria.setPage(page);
        criteria.setLimit(limit);
        Criteria cri = criteria.createCriteria();
        List<Server> list = serverMapper.selectByConditionList(criteria);
        return PagerUtil.getPager(list, criteria);
    }
}