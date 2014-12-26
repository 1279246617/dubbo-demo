package com.coe.wms.dao.warehouse.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.IShipwayDao;
import com.coe.wms.model.warehouse.Shipway;
import com.coe.wms.util.SsmNameSpace;
import com.coe.wms.util.StringUtil;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughAssignCache;
import com.google.code.ssm.api.ReadThroughSingleCache;

/**
 * @author Administrator
 * 
 */
@Repository("shipwayDao")
public class ShipwayDaoImpl implements IShipwayDao {

	Logger logger = Logger.getLogger(ShipwayDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughSingleCache(namespace = SsmNameSpace.SHIPWAY, expiration = 36000)
	public Shipway findShipwayByCode(@ParameterValueKeyProvider String code) {
		if (StringUtil.isNull(code)) {
			return null;
		}
		String sql = "select id,code,cn,en from w_w_shipway where code ='" + code + "'";
		Shipway packageStatus = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Shipway>(Shipway.class));
		return packageStatus;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughAssignCache(assignedKey = "AllShipway", namespace = SsmNameSpace.SHIPWAY, expiration = 3600)
	public List<Shipway> findAllShipway() {
		String sql = "select id,code,cn,en from w_w_shipway";
		List<Shipway> statusList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(Shipway.class));
		return statusList;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}