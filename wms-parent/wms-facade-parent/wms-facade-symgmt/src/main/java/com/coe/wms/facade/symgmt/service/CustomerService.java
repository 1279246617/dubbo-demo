package com.coe.wms.facade.symgmt.service;

import com.coe.wms.facade.symgmt.entity.Customer;
import org.mybatis.plugin.model.Pager;

public interface CustomerService {
    Customer add(Customer record);

    boolean delete(Long id);

    Customer update(Customer record);

    Customer get(Long id);

    Pager<Customer> list(int page, int limit);
}