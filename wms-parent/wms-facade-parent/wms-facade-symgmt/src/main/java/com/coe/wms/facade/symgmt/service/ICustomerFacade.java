package com.coe.wms.facade.symgmt.service;

import org.mybatis.plugin.model.Pager;

import com.coe.wms.facade.symgmt.entity.Customer;

public interface ICustomerFacade {

	Customer add(Customer record);

	boolean delete(Long id);

	Customer update(Customer record);

	Customer get(Long id);

	Pager<Customer> list(int page, int limit);
}