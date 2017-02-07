package com.shixun.common.mapper;

import com.shixun.common.entity.Account;
import com.shixun.common.entity.AccountCriteria;
import java.util.List;

public interface AccountMapper {
    int deleteByCondition(AccountCriteria condition);

    int deleteByPrimaryKey(String id);

    int insertSelective(Account record);

    List<Account> selectByConditionList(AccountCriteria condition);

    Account selectByPrimaryKey(String id);

    int countByCondition(AccountCriteria condition);

    int updateByPrimaryKeySelective(Account record);
}