package com.shixun.common.service;

import org.mybatis.plugin.model.Pager;
import com.shixun.common.entity.AccountHistory;

public interface AccountHistoryService {
    AccountHistory add(AccountHistory record);

    boolean delete(String id);

    AccountHistory update(AccountHistory record);

    AccountHistory get(String id);

    Pager<AccountHistory> list(int page, int limit);
}