package com.coe.wms.dao.warehouse.storage.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.storage.IOnShelfStatusDao;
import com.coe.wms.model.warehouse.storage.record.OnShelfStatus;
import com.coe.wms.util.SsmNameSpace;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;

/**
 * 
 * 
 * @author Administrator
 * 
 */
@Repository("onShelfStatusDao")
public class OnShelfStatusDaoImpl implements IOnShelfStatusDao {

	Logger logger = Logger.getLogger(OnShelfStatusDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughSingleCache(namespace = SsmNameSpace.ON_SHELF_STATUS, expiration = 36000)
	public OnShelfStatus findOnShelfStatusByCode(@ParameterValueKeyProvider String code) {
		String sql = "select id,code,cn,en from w_s_on_shelf_status where code ='" + code + "'";
		OnShelfStatus onShelfStatus = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<OnShelfStatus>(OnShelfStatus.class));
		return onShelfStatus;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}