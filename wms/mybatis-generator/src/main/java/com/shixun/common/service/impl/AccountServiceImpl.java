package com.shixun.common.service.impl;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import com.shixun.common.entity.Account;
import com.shixun.common.entity.AccountCriteria.Criteria;
import com.shixun.common.entity.AccountCriteria;
import com.shixun.common.mapper.AccountMapper;
import com.shixun.common.service.AccountService;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    public Account add(Account record) {
        record.setCreateTime(new Date());
        if(this.accountMapper.insertSelective(record)==1)
        	return record; 
        return null;
    }

    public boolean delete(String id) {
        return this.accountMapper.deleteByPrimaryKey(id)==1;
    }

    public Account update(Account record) {
        record.setUpdateTime(new Date());
        if(this.accountMapper.updateByPrimaryKeySelective(record)==1)
        	return record;
        return null;
    }

    public Account get(String id) {
        return this.accountMapper.selectByPrimaryKey(id);
    }

    public Pager<Account> list(int page, int limit) {
        AccountCriteria criteria = new AccountCriteria();
        criteria.setPage(page);
        criteria.setLimit(limit);
        Criteria cri = criteria.createCriteria();
        List<Account> list = accountMapper.selectByConditionList(criteria);
        return PagerUtil.getPager(list, criteria);
    }
}