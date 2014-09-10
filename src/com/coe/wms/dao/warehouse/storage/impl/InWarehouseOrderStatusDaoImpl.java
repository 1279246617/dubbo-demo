package com.coe.wms.dao.warehouse.storage.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderStatusDao;
import com.coe.wms.model.warehouse.storage.order.InWareHouseOrderStatus;
import com.google.code.ssm.api.ParameterValueKeyProvider;

/**
 * 
 * @author Administrator
 * 
 */
@Repository("packageDao")
public class InWarehouseOrderStatusDaoImpl implements IInWarehouseOrderStatusDao {

	Logger logger = Logger.getLogger(InWarehouseOrderStatusDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public InWareHouseOrderStatus findInWarehouseOrderStatusByCode(@ParameterValueKeyProvider String code) {
		String sql = "select id,code,cn,en from w_package_status where code ='" + code + "'";
		InWareHouseOrderStatus packageStatus = jdbcTemplate.queryForObject(sql,
				new BeanPropertyRowMapper<InWareHouseOrderStatus>(InWareHouseOrderStatus.class));
		return packageStatus;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}