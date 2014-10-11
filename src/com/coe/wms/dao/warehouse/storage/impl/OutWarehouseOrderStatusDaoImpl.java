package com.coe.wms.dao.warehouse.storage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderStatusDao;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus;
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
@Repository("outWarehouseOrderStatusDao")
public class OutWarehouseOrderStatusDaoImpl implements IOutWarehouseOrderStatusDao {

	Logger logger = Logger.getLogger(OutWarehouseOrderStatusDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughSingleCache(namespace = SsmNameSpace.OUT_WAREHOUSE_ORDER_STATUS, expiration = 36000)
	public OutWarehouseOrderStatus findOutWarehouseOrderStatusByCode(@ParameterValueKeyProvider String code) {
		String sql = "select id,code,cn,en from w_s_out_warehouse_order_status where code ='" + code + "'";
		OutWarehouseOrderStatus packageStatus = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<OutWarehouseOrderStatus>(
				OutWarehouseOrderStatus.class));
		return packageStatus;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughAssignCache(assignedKey = "AllOutWarehouseOrderStatus", namespace = SsmNameSpace.OUT_WAREHOUSE_ORDER_STATUS, expiration = 3600)
	public List<OutWarehouseOrderStatus> findAllOutWarehouseOrderStatus() {
		String sql = "select id,code,cn,en from w_s_out_warehouse_order_status";
		List<OutWarehouseOrderStatus> statusList = jdbcTemplate.query(sql,ParameterizedBeanPropertyRowMapper.newInstance(OutWarehouseOrderStatus.class));
		return statusList;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}