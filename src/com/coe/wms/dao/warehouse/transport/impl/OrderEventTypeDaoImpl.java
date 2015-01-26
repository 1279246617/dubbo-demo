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
import com.coe.wms.dao.warehouse.transport.IOrderEventTypeDao;
import com.coe.wms.model.warehouse.transport.OrderEventType;
import com.coe.wms.util.SsmNameSpace;
import com.coe.wms.util.StringUtil;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughAssignCache;
import com.google.code.ssm.api.ReadThroughSingleCache;

/**
 * @author Administrator
 * 
 */
@Repository("orderEventTypeDao")
public class OrderEventTypeDaoImpl implements IOrderEventTypeDao {

	Logger logger = Logger.getLogger(OrderEventTypeDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughSingleCache(namespace = SsmNameSpace.ORDER_EVENT_TYPE, expiration = 36000)
	public OrderEventType findOrderEventTypeByCode(@ParameterValueKeyProvider String code) {
		if (StringUtil.isNull(code)) {
			return null;
		}
		String sql = "select id,code,cn,en from w_t_order_event_type where code ='" + code + "'";
		OrderEventType packageStatus = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<OrderEventType>(OrderEventType.class));
		return packageStatus;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughAssignCache(assignedKey = "AllOrderEventType", namespace = SsmNameSpace.ORDER_EVENT_TYPE, expiration = 3600)
	public List<OrderEventType> findAllOrderEventType() {
		String sql = "select id,code,cn,en from w_t_order_event_type";
		List<OrderEventType> statusList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OrderEventType.class));
		return statusList;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}