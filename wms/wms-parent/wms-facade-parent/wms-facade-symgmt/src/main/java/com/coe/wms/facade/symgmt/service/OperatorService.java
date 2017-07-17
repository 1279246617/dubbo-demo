package com.coe.wms.facade.symgmt.service;

import com.coe.wms.facade.symgmt.entity.Operator;
import org.mybatis.plugin.model.Pager;

public interface OperatorService {
    Operator add(Operator record);

    boolean delete(Long id);

    Operator update(Operator record);

    Operator get(Long id);

    Pager<Operator> list(int page, int limit);
}