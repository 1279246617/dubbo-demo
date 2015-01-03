package com.coe.wms.dao.warehouse.transport.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.transport.IOrderPackageStatusDao;
import com.coe.wms.model.warehouse.transport.OrderPackageStatus;
import com.coe.wms.util.SsmNameSpace;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughAssignCache;
import com.google.code.ssm.api.ReadThroughSingleCache;

/**
 * 
 * 入库订单状态 DAO
 * 
 * @author Administrator
 * 
 */
@Repository("orderPackageStatusDao")
public class OrderPackageStatusDaoImpl implements IOrderPackageStatusDao {

	Logger logger = Logger.getLogger(OrderPackageStatusDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughSingleCache(namespace = SsmNameSpace.TRANSPORT_ORDER_PACKAGE_STATUS, expiration = 36000)
	public OrderPackageStatus findOrderPackageStatusByCode(@ParameterValueKeyProvider String code) {
		String sql = "select id,code,cn,en from w_t_order_package_status where code ='" + code + "'";
		OrderPackageStatus packageStatus = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<OrderPackageStatus>(OrderPackageStatus.class));
		return packageStatus;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughAssignCache(assignedKey = "AllOrderPackageStatus", namespace = SsmNameSpace.TRANSPORT_ORDER_PACKAGE_STATUS, expiration = 3600)
	public List<OrderPackageStatus> findAllOrderPackageStatus() {
		String sql = "select id,code,cn,en from w_t_order_package_status";
		List<OrderPackageStatus> statusList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OrderPackageStatus.class));
		return statusList;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}