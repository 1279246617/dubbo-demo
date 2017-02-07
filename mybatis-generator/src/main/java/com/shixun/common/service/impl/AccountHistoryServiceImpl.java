package com.shixun.common.service.impl;

import com.fpx.mybatis.plugin.model.Pager;
import com.fpx.mybatis.plugin.util.PagerUtil;
import com.shixun.common.entity.AccountHistory;
import com.shixun.common.entity.AccountHistoryCriteria.Criteria;
import com.shixun.common.entity.AccountHistoryCriteria;
import com.shixun.common.mapper.AccountHistoryMapper;
import com.shixun.common.service.AccountHistoryService;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountHistoryService")
public class AccountHistoryServiceImpl implements AccountHistoryService {
    @Autowired
    private AccountHistoryMapper accountHistoryMapper;

    private static final Logger logger = LoggerFactory.getLogger(AccountHistoryServiceImpl.class);

    public AccountHistory add(AccountHistory record) {
        record.setCreateTime(new Date());
        if(this.accountHistoryMapper.insertSelective(record)==1)
        	return record; 
        return null;
    }

    public boolean delete(String id) {
        return this.accountHistoryMapper.deleteByPrimaryKey(id)==1;
    }

    public AccountHistory update(AccountHistory record) {
        record.setUpdateTime(new Date());
        if(this.accountHistoryMapper.updateByPrimaryKeySelective(record)==1)
        	return record;
        return null;
    }

    public AccountHistory get(String id) {
        return this.accountHistoryMapper.selectByPrimaryKey(id);
    }

    public Pager<AccountHistory> list(int page, int limit) {
        AccountHistoryCriteria criteria = new AccountHistoryCriteria();
        criteria.setPage(page);
        criteria.setLimit(limit);
        Criteria cri = criteria.createCriteria();
        List<AccountHistory> list = accountHistoryMapper.selectByConditionList(criteria);
        return PagerUtil.getPager(list, criteria);
    }
}