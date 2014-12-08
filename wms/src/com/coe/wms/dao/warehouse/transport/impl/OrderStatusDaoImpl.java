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
import com.coe.wms.dao.warehouse.transport.IOrderStatusDao;
import com.coe.wms.model.warehouse.transport.OrderStatus;
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
@Repository("orderStatusDao")
public class OrderStatusDaoImpl implements IOrderStatusDao {

	Logger logger = Logger.getLogger(OrderStatusDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughSingleCache(namespace = SsmNameSpace.TRANSPORT_ORDER_STATUS, expiration = 36000)
	public OrderStatus findOrderStatusByCode(@ParameterValueKeyProvider String code) {
		String sql = "select id,code,cn,en from w_s_out_warehouse_order_status where code ='" + code + "'";
		OrderStatus packageStatus = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<OrderStatus>(OrderStatus.class));
		return packageStatus;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughAssignCache(assignedKey = "AllOrderStatus", namespace = SsmNameSpace.TRANSPORT_ORDER_STATUS, expiration = 3600)
	public List<OrderStatus> findAllOrderStatus() {
		String sql = "select id,code,cn,en from w_s_out_warehouse_order_status";
		List<OrderStatus> statusList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OrderStatus.class));
		return statusList;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}