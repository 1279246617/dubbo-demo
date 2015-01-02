package com.coe.wms.dao.warehouse.shipway.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.shipway.IShipwayApiAccountDao;
import com.coe.wms.dao.warehouse.shipway.IShipwayDao;
import com.coe.wms.model.warehouse.shipway.Shipway;
import com.coe.wms.model.warehouse.shipway.ShipwayApiAccount;
import com.coe.wms.util.SsmNameSpace;
import com.coe.wms.util.StringUtil;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughAssignCache;
import com.google.code.ssm.api.ReadThroughSingleCache;

/**
 * @author Administrator
 * 
 */
@Repository("shipwayApiAccountDao")
public class ShipwayApiAccountDaoImpl implements IShipwayApiAccountDao {

	Logger logger = Logger.getLogger(ShipwayApiAccountDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public ShipwayApiAccount getShipwayApiAccountByUserId(Long userId) {
		String sql = "";
		
		return null;
	}
}