package com.coe.wms.service.symgmt.mapper;

import java.util.List;

import com.coe.wms.facade.symgmt.criteria.CustomerCriteria;
import com.coe.wms.facade.symgmt.entity.Customer;

public interface CustomerMapper {
    int deleteByCondition(CustomerCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Customer record);

    List<Customer> selectByConditionList(CustomerCriteria condition);

    Customer selectByPrimaryKey(Long id);

    int countByCondition(CustomerCriteria condition);

    int updateByPrimaryKeySelective(Customer record);
}