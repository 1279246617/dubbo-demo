package com.coe.wms.facade.symgmt.service.impl;

import org.mybatis.plugin.model.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coe.wms.facade.symgmt.entity.Customer;
import com.coe.wms.facade.symgmt.service.ICustomerFacade;
import com.coe.wms.service.symgmt.biz.CustomerBiz;

@Service("customerService")
public class CustomerServiceImpl implements ICustomerFacade {
	@Autowired
	private CustomerBiz customerBiz;

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	public Customer add(Customer record) {
		return this.customerBiz.add(record);
	}

	public boolean delete(Long id) {
		return this.customerBiz.delete(id);
	}

	public Customer update(Customer record) {
		return this.customerBiz.update(record);
	}

	public Customer get(Long id) {
		return this.customerBiz.get(id);
	}

	public Pager<Customer> list(int page, int limit) {
		return this.customerBiz.list(page, limit);
	}
}