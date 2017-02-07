package com.shixun.common.mapper;

import com.shixun.common.entity.AccountHistory;
import com.shixun.common.entity.AccountHistoryCriteria;
import java.util.List;

public interface AccountHistoryMapper {
    int deleteByCondition(AccountHistoryCriteria condition);

    int deleteByPrimaryKey(String id);

    int insertSelective(AccountHistory record);

    List<AccountHistory> selectByConditionList(AccountHistoryCriteria condition);

    AccountHistory selectByPrimaryKey(String id);

    int countByCondition(AccountHistoryCriteria condition);

    int updateByPrimaryKeySelective(AccountHistory record);
}