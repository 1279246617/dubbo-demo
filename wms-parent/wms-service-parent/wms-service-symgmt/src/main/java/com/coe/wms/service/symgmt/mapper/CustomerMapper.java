package com.coe.wms.service.symgmt.mapper;

import com.coe.wms.facade.symgmt.entity.Customer;
import com.coe.wms.facade.symgmt.entity.CustomerCriteria;

import java.util.List;

public interface CustomerMapper {
    int deleteByCondition(CustomerCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Customer record);

    List<Customer> selectByConditionList(CustomerCriteria condition);

    Customer selectByPrimaryKey(Long id);

    int countByCondition(CustomerCriteria condition);

    int updateByPrimaryKeySelective(Customer record);
}