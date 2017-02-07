package com.shixun.common.service;

import com.fpx.mybatis.plugin.model.Pager;
import com.shixun.common.entity.Account;

public interface AccountService {
    Account add(Account record);

    boolean delete(String id);

    Account update(Account record);

    Account get(String id);

    Pager<Account> list(int page, int limit);
}