package com.shixun.common.service;

import org.mybatis.plugin.model.Pager;
import com.shixun.common.entity.PaidFlag;

public interface PaidFlagService {
    PaidFlag add(PaidFlag record);

    boolean delete(String id);

    PaidFlag update(PaidFlag record);

    PaidFlag get(String id);

    Pager<PaidFlag> list(int page, int limit);
}