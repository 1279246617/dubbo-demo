package com.coe.wms.dao.warehouse.storage.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderStatusDao;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderStatus;
import com.coe.wms.util.SsmNameSpace;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;

/**
 * 
 * 入库订单状态 DAO
 * @author Administrator
 * 
 */
@Repository("inWarehouseOrderStatusDao")
public class InWarehouseOrderStatusDaoImpl implements IInWarehouseOrderStatusDao {

	Logger logger = Logger.getLogger(InWarehouseOrderStatusDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughSingleCache(namespace = SsmNameSpace.IN_WAREHOUSE_ORDER_STATUS, expiration = 36000)
	public InWarehouseOrderStatus findInWarehouseOrderStatusByCode(@ParameterValueKeyProvider String code) {
		String sql = "select id,code,cn,en from w_s_in_warehouse_order_status where code ='" + code + "'";
		InWarehouseOrderStatus packageStatus = jdbcTemplate.queryForObject(sql,
				new BeanPropertyRowMapper<InWarehouseOrderStatus>(InWarehouseOrderStatus.class));
		return packageStatus;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}