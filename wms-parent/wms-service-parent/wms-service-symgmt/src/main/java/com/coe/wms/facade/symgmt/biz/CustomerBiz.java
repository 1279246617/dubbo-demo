package com.coe.wms.facade.symgmt.biz;

import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coe.wms.facade.symgmt.criteria.CustomerCriteria;
import com.coe.wms.facade.symgmt.criteria.CustomerCriteria.Criteria;
import com.coe.wms.facade.symgmt.entity.Customer;
import com.coe.wms.facade.symgmt.service.CustomerService;
import com.coe.wms.service.symgmt.mapper.CustomerMapper;

@Service("customerService")
public class CustomerBiz implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    private static final Logger logger = LoggerFactory.getLogger(CustomerBiz.class);

    public Customer add(Customer record) {
        if(this.customerMapper.insertSelective(record)==1)
        	return record; 
        return null;
    }

    public boolean delete(Long id) {
        return this.customerMapper.deleteByPrimaryKey(id)==1;
    }

    public Customer update(Customer record) {
        if(this.customerMapper.updateByPrimaryKeySelective(record)==1)
        	return record;
        return null;
    }

    public Customer get(Long id) {
        return this.customerMapper.selectByPrimaryKey(id);
    }

    public Pager<Customer> list(int page, int limit) {
        CustomerCriteria criteria = new CustomerCriteria();
        criteria.setPage(page);
        criteria.setLimit(limit);
        Criteria cri = criteria.createCriteria();
        List<Customer> list = customerMapper.selectByConditionList(criteria);
        return PagerUtil.getPager(list, criteria);
    }
}